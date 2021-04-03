package com.nanomachine.di;

import com.nanomachine.di.config.Configuration;
import com.nanomachine.di.injector.Injector;
import com.nanomachine.di.testdata.ClassWithAnnotationAndKey;
import com.nanomachine.di.testdata.ClassWithComponentTypeAndKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BeansRegistrationTest {

    @Test
    public void shouldReturnRegisteredBean() {
        //given
        Configuration configuration = new Configuration();
        configuration.register(ClassWithAnnotationAndKey.class);
        Injector injector = new Injector(configuration);
        //when
        ClassWithAnnotationAndKey cls = injector.getBean(ClassWithAnnotationAndKey.class);
        //then
        assertNotNull(cls);
    }

    @Test
    public void shouldThrowException() {
        //given
        Configuration configuration = new Configuration();
        //when
        assertThrows(RuntimeException.class, () -> configuration.register(ClassWithComponentTypeAndKey.class));
    }
}
