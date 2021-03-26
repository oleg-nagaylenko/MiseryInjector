package com.nanomachine.di;

import com.nanomachine.di.config.Configuration;
import com.nanomachine.di.injector.Injector;
import com.nanomachine.di.testdata.EmptyClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BeansRegistrationTest {

    @Test
    public void shouldReturnRegisteredBean() {
        //given
        Configuration configuration = new Configuration();
        configuration.register(EmptyClass.class);
        Injector injector = new Injector(configuration);
        //when
        EmptyClass emptyCls = injector.getBean(EmptyClass.class);
        //then
        assertNotNull(emptyCls);
    }
}
