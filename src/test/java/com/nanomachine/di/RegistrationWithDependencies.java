package com.nanomachine.di;

import com.nanomachine.di.annotations.Inject;
import com.nanomachine.di.config.Configuration;
import com.nanomachine.di.injector.Injector;
import com.nanomachine.di.scanner.DirectoryScanner;
import com.nanomachine.di.testdata.ClassWithAnnotation;
import com.nanomachine.di.testdata.ClassWithDependency;
import com.nanomachine.di.testdata.EmptyClass;
import com.nanomachine.di.testdata.MoreDependencies;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RegistrationWithDependencies {

    @Test
    public void shouldReturnBean() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        //given
        //DirectoryScanner scanner = new DirectoryScanner(RegistrationWithDependencies.class);
        Configuration configuration = new Configuration();
        MoreDependencies ob =  (MoreDependencies) configuration.registerBean(MoreDependencies.class);
        assertNotNull(ob);
        assertNotNull(ob.getClassWithDependency());
        assertNotNull(ob.getEmptyEntity());

        System.out.println("storage: " + configuration.getBeanStorage());
        //Injector injector = new Injector(configuration);
        //when
       // ClassWithAnnotation cls = injector.getBean(ClassWithAnnotation.class);
        //then
       // assertNotNull(cls);
    }

    @Test
    public void shouldRegisterAnnotatedClasses() {
        //given
        DirectoryScanner scanner = new DirectoryScanner(RegistrationWithDependencies.class);
        Configuration configuration = new Configuration(scanner);
        Injector injector = new Injector(configuration);
        //when
        MoreDependencies ob = injector.getBean(MoreDependencies.class);
        //given
        assertNotNull(ob);
        assertNotNull(ob.getClassWithDependency());
        assertNotNull(ob.getEmptyEntity());
        System.out.println(configuration.getBeanStorage());
    }
}

