package com.nanomachine.di.config;

import com.nanomachine.di.annotations.Component;
import com.nanomachine.di.annotations.Key;
import com.nanomachine.di.config.bean.BeanDefinition;
import com.nanomachine.di.config.bean.Identity;
import com.nanomachine.di.scanner.DirectoryScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Configuration {
    private final Map<Identity, Object> beanStorage = new HashMap<>();
    private final BeanRegistration beanRegistration = new BeanRegistration();

    public Configuration() {
    }

    public Configuration(DirectoryScanner directoryScanner) {
        Set<Class<?>> classes = directoryScanner.getClassesByAnnotation(Component.class);
        this.beanRegistration.register(classes);
    }

    public void register(Class<?> cls) {
        if (!cls.isAnnotationPresent(Component.class)) {
            throw new RuntimeException(); //ToDO : throw a new spec exception
        }
        this.beanRegistration.register(cls);
    }

    @SuppressWarnings("unchecked")
    public <T> T getInstance(Class<T> type) {
        Stream<Identity> identities = getIdentityStreamByType(type);
        Identity identity = identities
                .findFirst()
                .orElseThrow(RuntimeException::new);

        return (T) this.beanStorage.get(identity);
    }

    private Stream<Identity> getIdentityStreamByType(Class<?> type) {
        return beanStorage.keySet()
                .stream()
                .filter(i -> Objects.equals(i.getType(), type));
    }


    private class BeanRegistration {
        private final Map<Identity, BeanDefinition> beanDefinitions;

        private BeanRegistration() {
            this.beanDefinitions = new HashMap<>();
        }

        private void register(Class<?> cls) {
            register(Set.of(cls));
        }

        private void register(Set<Class<?>> classes) {
            this.beanDefinitions.putAll(createBeanDefinitionCollection(classes));

            for (Map.Entry<Identity, BeanDefinition> entry : beanDefinitions.entrySet()) {
                if (beanStorage.containsKey(entry.getKey())) {
                    continue;
                }
                recursiveBeanCreation(entry.getValue());
            }
        }

        private Object recursiveBeanCreation(BeanDefinition definition) {
            Constructor<?> constructor = definition.getConstructor();
            Class<?>[] paramTypes = constructor.getParameterTypes();
            Object[] parameters = new Object[paramTypes.length];

            for (int i = 0; i < paramTypes.length; i++) {
                Parameter parameter = constructor.getParameters()[i];
                Identity id = createIdentity(paramTypes[i], parameter);

                if (beanStorage.containsKey(id)) {
                    parameters[i] = beanStorage.get(id);
                    continue;
                }
                parameters[i] = recursiveBeanCreation(beanDefinitions.get(id));
            }
            Object instance = loadObject(constructor, parameters);
            beanStorage.put(definition.getIdentity(), instance);
            return instance;
        }

        private Object loadObject(Constructor<?> constructor, Object ... arguments) {
            int argumentCount = constructor.getParameterCount();
            try {
                return argumentCount > 0 ? constructor.newInstance(arguments) : constructor.newInstance();
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                throw new RuntimeException(e); //ToDo: throw a new config exception
            }
        }

        private Map<Identity, BeanDefinition> createBeanDefinitionCollection(Set<Class<?>> classes) {
            return classes.stream()
                    .map(BeanDefinition::new)
                    .collect(Collectors.toMap(BeanDefinition::getIdentity,i -> i));
        }

        private Identity createIdentity(Class<?> type, Parameter parameter) {

            Key key = parameter.getAnnotation(Key.class);
            if  (key != null) {
                return new Identity(type,key.value());
            }
            return new Identity(type,null);
        }
    }

}
