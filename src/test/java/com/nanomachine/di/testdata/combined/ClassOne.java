package com.nanomachine.di.testdata.combined;

import com.nanomachine.di.annotations.Component;
import com.nanomachine.di.annotations.ComponentType;
import com.nanomachine.di.annotations.Key;
import com.nanomachine.di.testdata.type.InterfaceOfSmth;

@Component
@ComponentType(InterfaceOfSmth.class)
@Key("One")
public class ClassOne implements InterfaceOfSmth {
    String className = "ClassOne";

    public String getClassName() {
        return className;
    }
}
