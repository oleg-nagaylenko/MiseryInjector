package com.nanomachine.di;

import com.nanomachine.di.config.Configuration;
import com.nanomachine.di.injector.Injector;
import com.nanomachine.di.testdata.ClassWithAnnotation;
import com.nanomachine.di.testdata.EmptyClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BeansRegistrationTest {

    @Test
    public void shouldReturnRegisteredBean() {
        //given
        Configuration configuration = new Configuration();
        configuration.register(ClassWithAnnotation.class);
        Injector injector = new Injector(configuration);
        //when
        ClassWithAnnotation cls = injector.getBean(ClassWithAnnotation.class);
        //then
        assertNotNull(cls);
    }

    @Test
    public void shouldThrowException() {
        //given
        Configuration configuration = new Configuration();
        //when
        assertThrows(RuntimeException.class, () -> configuration.register(EmptyClass.class));
    }
}
