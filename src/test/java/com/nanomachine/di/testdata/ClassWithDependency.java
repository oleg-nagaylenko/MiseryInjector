package com.nanomachine.di.testdata;

import com.nanomachine.di.annotations.Component;
import com.nanomachine.di.annotations.Inject;
import com.nanomachine.di.annotations.Key;

@Component
public class ClassWithDependency {
    private ClassWithAnnotation classWithAnnotation;
    private EmptyEntity emptyEntity;

    @Inject
    public ClassWithDependency(@Key("specific-obj") ClassWithAnnotation classWithAnnotation, EmptyEntity emptyEntity) {
        this.classWithAnnotation = classWithAnnotation;
        this.emptyEntity = emptyEntity;
    }

    public ClassWithAnnotation getClassWithAnnotation() {
        return classWithAnnotation;
    }

    public EmptyEntity getEmptyEntity() {
        return emptyEntity;
    }
}
