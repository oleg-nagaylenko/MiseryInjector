package com.nanomachine.di.config;

import com.nanomachine.di.scanner.DirectoryScanner;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Configuration {
    private final Map<Class<?>, Object> beanStorage = new HashMap<>();

    public Configuration() {
    }

    public Configuration(DirectoryScanner directoryScanner) {
        directoryScanner.getAllClasses();
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
}
