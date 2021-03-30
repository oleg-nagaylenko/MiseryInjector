package com.nanomachine.di.config;

import com.nanomachine.di.annotations.Component;
import com.nanomachine.di.annotations.Inject;
import com.nanomachine.di.scanner.DirectoryScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Configuration {
    private final Map<Class<?>, Object> beanStorage = new HashMap<>();

    public Configuration() {
    }

    public Configuration(DirectoryScanner directoryScanner) {
        Set<Class<?>> classes = directoryScanner.getClassesByAnnotation(Component.class);
        //classes.forEach(cls -> register(cls).complete());
        register(classes);
    }

    public RegistrationService register(Class<?> cls) {
        Object instance = loadObject(cls);
        return new RegistrationService(instance);
    }

    private Object loadObject(Class<?> cls) {
        try {
            return cls.getConstructors()[0].newInstance();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e); //ToDo: throw a new config exception
        }
    }

    private Object loadObject(Constructor<?> constructor, Object ... arguments) {
        int argumentCount = constructor.getParameterCount();
        try {
            return argumentCount > 0 ? constructor.newInstance(arguments) : constructor.newInstance();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e); //ToDo: throw a new config exception
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getInstance(Class<T> type) {
        Class<?> key = beanStorage.keySet().stream()
                .filter(k -> Objects.equals(k, type))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        return (T) this.beanStorage.get(key);
    }

    public class RegistrationService {
        private Object instance;
        private Class<?> type;

        public RegistrationService(Object instance) {
            this.instance = instance;
            this.type = instance.getClass();
        }

        public RegistrationService as(Class<?> type) {
            this.type = type;
            return this;
        }

        public void complete() {
            beanStorage.put(this.type, this.instance);
        }
    }

    private Constructor<?> getAnnotatedConstructor(Class<?> cls) {
        Constructor<?>[] constructors = cls.getConstructors();
        return Arrays.stream(constructors)
                .filter(c -> c.isAnnotationPresent(Inject.class))
                .findFirst().orElse(constructors[0]);
    }

    private void register(Set<Class<?>> foundClasses) {
        foundClasses.forEach(this::registerBean);
    }

    public Object registerBean(Class<?> cls) {
        Constructor<?> constructor = getAnnotatedConstructor(cls);
        List<Object> arguments = new ArrayList<>();
        Class<?>[] params = constructor.getParameterTypes();

        for (Class<?> param : params) {
            if (beanStorage.containsKey(param)) {
                arguments.add(beanStorage.get(param));
            } else {
                arguments.add(registerBean(param));
            }
        }

        Object object = loadObject(constructor, arguments.toArray());
        new RegistrationService(object).complete();
        return object;
    }

    public Map<Class<?>, Object> getBeanStorage() {
        return beanStorage;
    }
}
