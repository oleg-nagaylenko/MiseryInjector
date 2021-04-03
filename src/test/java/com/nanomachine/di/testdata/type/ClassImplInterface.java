package com.nanomachine.di.testdata.type;

import com.nanomachine.di.annotations.Component;
import com.nanomachine.di.annotations.ComponentType;

@Component
@ComponentType(InterfaceOfSmth.class)
public class ClassImplInterface implements InterfaceOfSmth{
    String className = "ClassImplInterface";

    public String getClassName() {
        return className;
    }
}
