package com.nanomachine.di;

import com.nanomachine.di.config.Configuration;
import com.nanomachine.di.injector.Injector;
import com.nanomachine.di.scanner.DirectoryScanner;
import com.nanomachine.di.testdata.combined.ClassDependsOnKeyAndType;
import com.nanomachine.di.testdata.combined.ClassOne;
import com.nanomachine.di.testdata.combined.ClassTwo;
import com.nanomachine.di.testdata.component.ContainsEmptyClass;
import com.nanomachine.di.testdata.component.EmptyClass;
import com.nanomachine.di.testdata.key.ClassDependsOnKey;
import com.nanomachine.di.testdata.key.ClassWithKey;
import com.nanomachine.di.testdata.type.ClassDependsOnInterface;
import com.nanomachine.di.testdata.type.ClassImplInterface;
import com.nanomachine.di.testdata.type.InterfaceOfSmth;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BeansInjectionTest {

    @Test
    public void shouldReturnBean() {
        //given
        Configuration config = new Configuration();
        config.register(EmptyClass.class);
        Injector injector = new Injector(config);
        //when
        EmptyClass cls = injector.getBean(EmptyClass.class);
        //then
        assertNotNull(cls);
    }

    @Test
    public void shouldReturnBeanWithDependency() {
        //given
        Configuration config = new Configuration();
        config.register(EmptyClass.class);
        config.register(ContainsEmptyClass.class);
        Injector injector = new Injector(config);
        //when
        ContainsEmptyClass cls = injector.getBean(ContainsEmptyClass.class);
        //then
        assertNotNull(cls);
        assertNotNull(cls.getEmptyClass());
    }

    @Test
    public void shouldReturnScannedBean() {
        //given
        DirectoryScanner scanner = new DirectoryScanner(BeansInjectionTest.class);
        Configuration config = new Configuration(scanner);
        Injector injector = new Injector(config);
        //when
        EmptyClass cls = injector.getBean(EmptyClass.class);
        //then
        assertNotNull(cls);
    }

    @Test
    public void shouldReturnBeanByType() {
        //given
        Configuration config = new Configuration();
        config.register(ClassImplInterface.class);
        ClassImplInterface clsImplInter = new ClassImplInterface();
        Injector injector = new Injector(config);
        //when
        InterfaceOfSmth inter = injector.getBean(InterfaceOfSmth.class);
        //then
        assertNotNull(inter);
        assertEquals(((ClassImplInterface)inter).getClassName(), clsImplInter.getClassName());
    }

    @Test
    public void shouldReturnBeanWithDependencyByType() {
        //given
        Configuration config = new Configuration();
        config.register(ClassImplInterface.class);
        config.register(ClassDependsOnInterface.class);
        Injector injector = new Injector(config);
        //when
        ClassDependsOnInterface cls = injector.getBean(ClassDependsOnInterface.class);
        //then
        assertNotNull(cls);
        assertNotNull(cls.getSmth());
    }

    @Test
    public void shouldReturnBeanWithDependencyByKey() {
        //given
        Configuration config = new Configuration();
        config.register(EmptyClass.class);
        config.register(ClassWithKey.class);
        config.register(ClassDependsOnKey.class);
        Injector injector = new Injector(config);
        //when
        ClassDependsOnKey cls = injector.getBean(ClassDependsOnKey.class);
        //then
        assertNotNull(cls);
        assertNotNull(cls.getCls());
        assertNotNull(cls.getClsKey());
    }

    @Test
    public void shouldReturnBeanWithDependencyByKeyAndType() {
        //given
        Configuration config = new Configuration();
        config.register(ClassOne.class);
        config.register(ClassTwo.class);
        config.register(ClassDependsOnKeyAndType.class);
        Injector injector = new Injector(config);
        //when
        ClassDependsOnKeyAndType cls = injector.getBean(ClassDependsOnKeyAndType.class);
        //then
        assertNotNull(cls);
        assertNotNull(cls.getInstanceOne());
        assertNotNull(cls.getInstanceTwo());
        assertEquals( ((ClassOne) cls.getInstanceOne()).getClassName(), ClassOne.class.getSimpleName());
        assertEquals( ((ClassTwo) cls.getInstanceTwo()).getClassName(), ClassTwo.class.getSimpleName());
    }
}
