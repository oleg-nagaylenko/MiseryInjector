package com.nanomachine.di.config;

import com.nanomachine.di.annotations.Component;
import com.nanomachine.di.annotations.Inject;
import com.nanomachine.di.annotations.Key;
import com.nanomachine.di.scanner.DirectoryScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Stream;

public class Configuration {
    private final Map<Identity, Object> beanStorage = new HashMap<>();

    public Configuration() {
    }

    public Configuration(DirectoryScanner directoryScanner) {
        Set<Class<?>> classes = directoryScanner.getClassesByAnnotation(Component.class);
        classes = new ClassSorter().sort(classes);
        register(classes);
    }

    public void register(Class<?> cls) {
        if (!cls.isAnnotationPresent(Component.class)) {
            throw new RuntimeException();
        }
        registerBean(cls);
    }

    @SuppressWarnings("unchecked")
    public <T> T getInstance(Class<T> type) {
        Stream<Identity> identities = getIdentityStreamByType(type);
        Identity identity = identities
                .findFirst()
                .orElseThrow(RuntimeException::new);

        return (T) this.beanStorage.get(identity);
    }

    private void register(Set<Class<?>> classes) {
        classes.forEach(this::registerBean);
    }

    private void registerBean(Class<?> cls) {
        Constructor<?> constructor = getAnnotatedConstructor(cls);
        Class<?>[] paramTypes = constructor.getParameterTypes();
        Object[] arguments = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            Key key = paramTypes[i].getAnnotation(Key.class);
            if (key == null) {
                arguments[i] = getInstance(paramTypes[i]);
            } else {
                arguments[i] = getInstance(paramTypes[i], key.value());
            }
        }

        Object object = loadObject(constructor, arguments);
        addToStorage(new BeanDetail(object));
    }

    @SuppressWarnings("unchecked")
    private <T> T getInstance(Class<?> type, String key) {
        Stream<Identity> identities = getIdentityStreamByType(type);
        Identity identity = identities.filter(i -> Objects.equals(i.key, key))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        return (T) beanStorage.get(identity);
    }

    private Stream<Identity> getIdentityStreamByType(Class<?> type) {
        return beanStorage.keySet()
                .stream()
                .filter(i -> Objects.equals(i.type, type));
    }

    private Object loadObject(Constructor<?> constructor, Object ... arguments) {
        int argumentCount = constructor.getParameterCount();
        try {
            return argumentCount > 0 ? constructor.newInstance(arguments) : constructor.newInstance();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e); //ToDo: throw a new config exception
        }
    }

    private Constructor<?> getAnnotatedConstructor(Class<?> cls) {
        Constructor<?>[] constructors = cls.getConstructors();
        return Arrays.stream(constructors)
                .filter(c -> c.isAnnotationPresent(Inject.class))
                .findFirst().orElse(constructors[0]);
    }

    private void addToStorage(BeanDetail detail) {
        beanStorage.put(new Identity(detail.type, detail.key), detail.instance);
    }


    public static class BeanDetail {
        private Class<?> type;
        private String key;
        private Object instance;

        BeanDetail(Object instance) {
            this.type = instance.getClass();
            this.instance = instance;
            this.key = getKey();
        }

        private BeanDetail withType(Class<?> type) {
            this.type = type;
            return this;
        }

        private String getKey() {
            Key key = this.type.getAnnotation(Key.class);
            if (key != null) {
                return key.value();
            }
            return null;
        }
    }


    private class ClassSorter {
        private final Set<Class<?>> sortedClasses = new LinkedHashSet<>();

        public Set<Class<?>> sort(Set<Class<?>> classes) {
            classes.forEach(this::sortClassByDependency);
            return sortedClasses;
        }

        private void sortClassByDependency(Class<?> cls) {
            Constructor<?> constructor = getAnnotatedConstructor(cls);
            int parameterCount = constructor.getParameterCount();

            if (parameterCount == 0) {
                sortedClasses.add(cls);
                return;
            }

            Class<?>[] paramTypes = constructor.getParameterTypes();
            Arrays.stream(paramTypes).forEach(this::sortClassByDependency);
            sortedClasses.add(cls);
        }
    }


    private static class Identity {
        private final Class<?> type;
        private final String key;

        public Identity(Class<?> type, String key) {
            this.type = type;
            this.key = key;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Identity)) return false;
            Identity identity = (Identity) o;
            return Objects.equals(type, identity.type) &&
                    Objects.equals(key, identity.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, key);
        }
    }
}
