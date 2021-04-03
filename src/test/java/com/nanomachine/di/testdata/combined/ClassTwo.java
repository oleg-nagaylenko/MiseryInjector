package com.nanomachine.di.testdata.combined;

import com.nanomachine.di.annotations.Component;
import com.nanomachine.di.annotations.ComponentType;
import com.nanomachine.di.annotations.Key;
import com.nanomachine.di.testdata.type.InterfaceOfSmth;

@Component
@ComponentType(InterfaceOfSmth.class)
@Key("Two")
public class ClassTwo implements InterfaceOfSmth {
    String className = "ClassTwo";

    public String getClassName() {
        return className;
    }
}
