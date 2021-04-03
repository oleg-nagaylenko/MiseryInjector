package com.nanomachine.di.testdata.component;

import com.nanomachine.di.annotations.Component;
import com.nanomachine.di.annotations.Inject;

@Component
public class ContainsEmptyClass {
    EmptyClass emptyClass;

    @Inject
    public ContainsEmptyClass(EmptyClass emptyClass) {
        this.emptyClass = emptyClass;
    }

    public EmptyClass getEmptyClass() {
        return emptyClass;
    }
}
