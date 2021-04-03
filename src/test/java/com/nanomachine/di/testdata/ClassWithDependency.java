package com.nanomachine.di.testdata;

import com.nanomachine.di.annotations.Component;
import com.nanomachine.di.annotations.Inject;
import com.nanomachine.di.annotations.Key;

@Component
public class ClassWithDependency {
    private ClassWithAnnotationAndKey classWithAnnotationAndKey;
    private ClassWithSmth classWithComponentTypeAndKey;

    @Inject
    public ClassWithDependency(@Key("key-comp") ClassWithAnnotationAndKey classWithAnnotationAndKey,
                               @Key("type-key-comp") ClassWithSmth classWithComponentTypeAndKey) {

        this.classWithAnnotationAndKey = classWithAnnotationAndKey;
        this.classWithComponentTypeAndKey = classWithComponentTypeAndKey;
    }

    public ClassWithAnnotationAndKey getClassWithAnnotationAndKey() {
        return classWithAnnotationAndKey;
    }

    public ClassWithSmth getClassWithComponentTypeAndKey() {
        return classWithComponentTypeAndKey;
    }
}
