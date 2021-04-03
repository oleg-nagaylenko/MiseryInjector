package com.nanomachine.di.testdata.type;

import com.nanomachine.di.annotations.Component;
import com.nanomachine.di.annotations.Inject;

@Component
public class ClassDependsOnInterface {
    InterfaceOfSmth smth;

    @Inject
    public ClassDependsOnInterface(InterfaceOfSmth smth) {
        this.smth = smth;
    }

    public InterfaceOfSmth getSmth() {
        return smth;
    }
}
