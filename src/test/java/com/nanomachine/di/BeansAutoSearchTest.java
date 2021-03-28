package com.nanomachine.di;

import com.nanomachine.di.config.Configuration;
import com.nanomachine.di.injector.Injector;
import com.nanomachine.di.scanner.DirectoryScanner;
import com.nanomachine.di.testdata.ClassWithAnnotation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BeansAutoSearchTest {

    @Test
    public void shouldReturnRegisteredBeanWithAnnotation() {
        //given
        DirectoryScanner scanner = new DirectoryScanner(BeansAutoSearchTest.class);
        Configuration configuration = new Configuration(scanner);
        Injector injector = new Injector(configuration);
        //when
        ClassWithAnnotation cls = injector.getBean(ClassWithAnnotation.class);
        //then
        assertNotNull(cls);
    }
}
