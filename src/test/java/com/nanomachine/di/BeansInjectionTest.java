package com.nanomachine.di;

import com.nanomachine.di.config.Configuration;
import com.nanomachine.di.injector.Injector;
import com.nanomachine.di.scanner.DirectoryScanner;
import com.nanomachine.di.testdata.ClassWithDependency;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BeansInjectionTest {

    @Test
    public void shouldReturnBeanWithDependency() {
        //given
        Configuration configuration = new Configuration();
        configuration.register(ClassWithDependency.class);
        Injector injector = new Injector(configuration);
        //when
        ClassWithDependency cls = injector.getBean(ClassWithDependency.class);
        //then
        assertNotNull(cls);
        assertNotNull(cls.getClassWithAnnotation());
    }

    @Test
    public void shouldReturnScannedBeans() {
        //given
        DirectoryScanner scanner = new DirectoryScanner(BeansInjectionTest.class);
        Configuration configuration = new Configuration(scanner);
        Injector injector = new Injector(configuration);
        //when
        ClassWithDependency cls = injector.getBean(ClassWithDependency.class);
        //then
        assertNotNull(cls);
        assertNotNull(cls.getClassWithAnnotation());
    }
}
