package com.nanomachine.di;

import com.nanomachine.di.annotations.Component;
import com.nanomachine.di.config.Configuration;
import com.nanomachine.di.scanner.DirectoryScanner;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class RegTest {

    @Test
    public void test() {
        DirectoryScanner scanner = new DirectoryScanner(RegTest.class);
        Configuration configuration = new Configuration();
        Set<Class<?>> classes = scanner.getClassesByAnnotation(Component.class);
        configuration.test(classes);

    }
}
