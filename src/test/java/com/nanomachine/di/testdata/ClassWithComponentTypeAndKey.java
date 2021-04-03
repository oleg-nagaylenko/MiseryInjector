package com.nanomachine.di.testdata;

import com.nanomachine.di.annotations.Component;
import com.nanomachine.di.annotations.ComponentType;
import com.nanomachine.di.annotations.Inject;
import com.nanomachine.di.annotations.Key;

@Component
@ComponentType(ClassWithSmth.class)
@Key("type-key-comp")
public class ClassWithComponentTypeAndKey implements ClassWithSmth {
    private EmptyEntity emptyEntity;

    @Inject
    public ClassWithComponentTypeAndKey(EmptyEntity emptyEntity) {
        this.emptyEntity = emptyEntity;
    }

    public EmptyEntity getEmptyEntity() {
        return emptyEntity;
    }
}
