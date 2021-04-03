package com.nanomachine.di.testdata.key;

import com.nanomachine.di.annotations.Component;
import com.nanomachine.di.annotations.Inject;
import com.nanomachine.di.annotations.Key;
import com.nanomachine.di.testdata.component.EmptyClass;

@Component
public class ClassDependsOnKey {
    ClassWithKey clsKey;
    EmptyClass cls;

    @Inject
    public ClassDependsOnKey(@Key("class-with-key") ClassWithKey clsKey, EmptyClass cls) {
        this.clsKey = clsKey;
        this.cls = cls;
    }

    public ClassWithKey getClsKey() {
        return clsKey;
    }

    public EmptyClass getCls() {
        return cls;
    }
}
