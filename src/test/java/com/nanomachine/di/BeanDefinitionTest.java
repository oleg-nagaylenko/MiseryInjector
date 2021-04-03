package com.nanomachine.di;

import com.nanomachine.di.config.bean.BeanDefinition;
import com.nanomachine.di.config.bean.Identity;
import com.nanomachine.di.testdata.combined.ClassDependsOnKeyAndType;
import com.nanomachine.di.testdata.combined.ClassOne;
import com.nanomachine.di.testdata.combined.ClassTwo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BeanDefinitionTest {

    @Test
    public void shouldCreateBeanDefinition() {
        //given
        ClassDependsOnKeyAndType cls = new ClassDependsOnKeyAndType(new ClassOne(), new ClassTwo());
        //when
        BeanDefinition definition = new BeanDefinition(cls.getClass());
        //then
        assertNotNull(definition);
        assertEquals(definition.getBeanType(), cls.getClass());
        assertEquals(definition.getConstructor(), cls.getClass().getConstructors()[0]);
        assertEquals(definition.getObjectClass(), cls.getClass());
        assertEquals(definition.getIdentity(), new Identity(cls.getClass(), null));
    }

}
