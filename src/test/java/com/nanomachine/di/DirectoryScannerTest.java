package com.nanomachine.di;

import com.nanomachine.di.scanner.DirectoryScanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

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
    public void shouldReturnSetOfClasses() {
        //given
        //when
        System.out.println(scanner.getAllClasses());
        //then
    }
}
