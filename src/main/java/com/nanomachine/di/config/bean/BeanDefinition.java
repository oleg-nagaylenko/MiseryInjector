package com.nanomachine.di.config.bean;

import com.nanomachine.di.annotations.ComponentType;
import com.nanomachine.di.annotations.Inject;
import com.nanomachine.di.annotations.Key;
import com.nanomachine.di.config.Identity;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class BeanDefinition {
    private Class<?> objectClass;
    private Class<?> beanType;
    private String key;
    private Constructor<?> constructor;

    public BeanDefinition(Class<?> objectClass) {
        this.objectClass = objectClass;
        this.beanType = findBeanType();
        this.key = getKey();
        this.constructor = getAnnotatedConstructor();
    }

    public Identity getIdentity() {
        return new Identity(this.beanType, this.key);
    }

    private Class<?> findBeanType() {
        ComponentType type = this.objectClass.getAnnotation(ComponentType.class);
        if (type != null) {
            return type.value();
        }
        return this.objectClass;
    }

    private String getKey() {
        Key key = this.objectClass.getAnnotation(Key.class);
        if (key != null) {
            return key.value();
        }
        return null; //ToDO: remove null
    }

    private Constructor<?> getAnnotatedConstructor() {
        Constructor<?>[] constructors= this.objectClass.getConstructors();
        return Arrays.stream(constructors)
                .filter(i -> i.isAnnotationPresent(Inject.class))
                .findFirst().orElse(constructors[0]);
    }

    public Class<?> getObjectClass() {
        return objectClass;
    }

    public Constructor<?> getConstructor() {
        return constructor;
    }

    public Class<?> getBeanType() {
        return beanType;
    }
}
