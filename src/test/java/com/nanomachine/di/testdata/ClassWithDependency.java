package com.nanomachine.di.testdata;

import com.nanomachine.di.annotations.Component;
import com.nanomachine.di.annotations.Inject;

@Component
public class ClassWithDependency {
    private ClassWithAnnotation classWithAnnotation;

    @Inject
    public ClassWithDependency(ClassWithAnnotation classWithAnnotation) {
        this.classWithAnnotation = classWithAnnotation;
    }

    public ClassWithAnnotation getClassWithAnnotation() {
        return classWithAnnotation;
    }
}
