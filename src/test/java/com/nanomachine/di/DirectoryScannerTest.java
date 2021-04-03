package com.nanomachine.di;

import com.nanomachine.di.annotations.Component;
import com.nanomachine.di.scanner.DirectoryScanner;
import com.nanomachine.di.testdata.component.EmptyClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DirectoryScannerTest {
    private DirectoryScanner scanner;

    @BeforeEach
    void setup() {
        this.scanner = new DirectoryScanner(DirectoryScannerTest.class);
    }

    @Test
    public void shouldReturnClassPath() throws URISyntaxException {
        //given
        Path location = Path.of(DirectoryScannerTest.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        String expectedPath = location.toString();
        //when
        String classPath = scanner.getClassPath();
        //then
        assertEquals(expectedPath, classPath);
    }

    @Test
    public void shouldReturnSetWithComponentAnnotation() {
        //given
        //when
        Set<Class<?>> actionSet = scanner.getClassesByAnnotation(Component.class);
        //then
        assertNotNull(actionSet);
        assertTrue(actionSet.contains(EmptyClass.class));
    }

}
