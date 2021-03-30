package com.nanomachine.di.testdata;

import com.nanomachine.di.annotations.Component;
import com.nanomachine.di.annotations.Inject;

@Component
public class MoreDependencies {
    private ClassWithDependency classWithDependency;
    private EmptyClass emptyEntity;

    @Inject
    public MoreDependencies(ClassWithDependency classWithDependency, EmptyClass emptyEntity) {
        this.classWithDependency = classWithDependency;
        this.emptyEntity = emptyEntity;
    }

    public ClassWithDependency getClassWithDependency() {
        return classWithDependency;
    }

    public EmptyClass getEmptyEntity() {
        return emptyEntity;
    }
}
