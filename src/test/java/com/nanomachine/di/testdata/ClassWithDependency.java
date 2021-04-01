package com.nanomachine.di.testdata;

import com.nanomachine.di.annotations.Component;
import com.nanomachine.di.annotations.Inject;
import com.nanomachine.di.annotations.Key;

@Component
public class ClassWithDependency {
    private ClassWithAnnotation classWithAnnotation;

    @Inject
    public ClassWithDependency(@Key("specific-obj") ClassWithAnnotation classWithAnnotation) {
        this.classWithAnnotation = classWithAnnotation;
    }

    public ClassWithAnnotation getClassWithAnnotation() {
        return classWithAnnotation;
    }
}
