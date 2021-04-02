package com.nanomachine.di.testdata;

import com.nanomachine.di.annotations.Component;
import com.nanomachine.di.annotations.ComponentType;

@Component
@ComponentType(EmptyEntity.class)
public class EmptyClass implements EmptyEntity{
}
