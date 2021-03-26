package com.nanomachine.di.config;

import com.nanomachine.di.scanner.DirectoryScanner;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Configuration {
    private final List<Object> beanStorage = new ArrayList<>();

    public Configuration() {
    }

    public Configuration(DirectoryScanner directoryScanner) {
        directoryScanner.getAllClasses();
    }

    public void register(Class<?> cls) {
        Object instance = loadObject(cls);
        this.beanStorage.add(instance);
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
        return (T) beanStorage.stream()
                .filter(i -> Objects.equals(i.getClass(), type))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
