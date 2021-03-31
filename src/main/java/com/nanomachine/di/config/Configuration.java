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
        Class<?> key = beanStorage.keySet().stream()
                .filter(k -> Objects.equals(k, type))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        return (T) this.beanStorage.get(key);
    }

    private Object loadObject(Constructor<?> constructor, Object ... arguments) {
        int argumentCount = constructor.getParameterCount();
        try {
            return argumentCount > 0 ? constructor.newInstance(arguments) : constructor.newInstance();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e); //ToDo: throw a new config exception
        }
    }

    private void register(Set<Class<?>> classes) {
        for (Class<?> cls : classes) {
            if (!beanStorage.containsKey(cls)) {
                registerBean(cls);
            }
        }
    }

    private Constructor<?> getAnnotatedConstructor(Class<?> cls) {
        Constructor<?>[] constructors = cls.getConstructors();
        return Arrays.stream(constructors)
                .filter(c -> c.isAnnotationPresent(Inject.class))
                .findFirst().orElse(constructors[0]);
    }

    private Object registerBean(Class<?> cls) {
        Constructor<?> constructor = getAnnotatedConstructor(cls);
        Object[] arguments = new Object[constructor.getParameterCount()];
        Class<?>[] params = constructor.getParameterTypes();

        for (int i = 0; i < arguments.length; i++) {
            if (beanStorage.containsKey(params[i])) {
                arguments[i] = beanStorage.get(params[i]);
            } else {
                arguments[i] = registerBean(params[i]);
            }
        }

        Object object = loadObject(constructor, arguments);
        addToStorage(new BeanDefinition(object));
        return object;
    }

    public void addToStorage(BeanDefinition definition) {
        beanStorage.put(definition.type, definition.instance);
    }

    public Map<Class<?>, Object> getBeanStorage() {
        return beanStorage;
    }

    public class BeanDefinition {
        private Class<?> type;
        private Object instance;

        BeanDefinition(Object instance) {
            this.type = instance.getClass();
            this.instance = instance;
        }
    }

}
