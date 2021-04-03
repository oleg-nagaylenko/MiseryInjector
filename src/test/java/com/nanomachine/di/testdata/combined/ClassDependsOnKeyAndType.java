package com.nanomachine.di.testdata.combined;

import com.nanomachine.di.annotations.Component;
import com.nanomachine.di.annotations.Inject;
import com.nanomachine.di.annotations.Key;
import com.nanomachine.di.testdata.type.InterfaceOfSmth;

@Component
public class ClassDependsOnKeyAndType {
    InterfaceOfSmth instanceOne;
    InterfaceOfSmth instanceTwo;

    @Inject
    public ClassDependsOnKeyAndType(@Key("One") InterfaceOfSmth instanceOne,
                                    @Key("Two") InterfaceOfSmth instanceTwo) {

        this.instanceOne = instanceOne;
        this.instanceTwo = instanceTwo;
    }

    public InterfaceOfSmth getInstanceOne() {
        return instanceOne;
    }

    public InterfaceOfSmth getInstanceTwo() {
        return instanceTwo;
    }
}
