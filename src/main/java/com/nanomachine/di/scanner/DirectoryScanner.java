package com.nanomachine.di.scanner;

import com.nanomachine.di.constants.Constants;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DirectoryScanner {
    private final Path classPath;
    private final Set<Class<?>> foundClasses = new HashSet<>();

    public DirectoryScanner(Class<?> startupClass) {
        this.classPath = getClassPath(startupClass);
        findAllClasses();
    }

    public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotationType) {
        return this.foundClasses.stream()
                .filter(i -> i.isAnnotationPresent(annotationType))
                .collect(Collectors.toSet());
    }

    public Set<Class<?>> getAllClasses() {
        return this.foundClasses;
    }

    private void findAllClasses() {
        FileVisitor<Path> visitor = fileVisitor();
        try {
            Files.walkFileTree(classPath, visitor);
        } catch (IOException e) {
            throw new RuntimeException(e);   //ToDo: throw a new scanner exception
        }
    }

    private FileVisitor<Path> fileVisitor() {
        return new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {

                if (!path.toFile().getName().endsWith(Constants.JAVA_BINARY_EXTENSION)) {
                    return FileVisitResult.CONTINUE;
                }

                String classPath = DirectoryScanner.this.classPath.relativize(path).toString();
                classPath = pathModifier(classPath);
                Class<?> cls = wrappedForName(classPath);
                DirectoryScanner.this.foundClasses.add(cls);

                return FileVisitResult.CONTINUE;
            }
        };
    }

    private String pathModifier(String path) {
        return path
                .replace(File.separatorChar, Constants.JAVA_PACKAGE_SEPARATOR)
                .replace(Constants.JAVA_BINARY_EXTENSION,"");
    }

    private Class<?> wrappedForName(String cls) {
        try {
            return Class.forName(cls);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);   //ToDo: throw a new scanner exception
        }
    }

    private Path getClassPath(Class<?> startupClass) {
        try {
            return Path.of(startupClass.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);   //ToDo: throw a new scanner exception
        }
    }

    public String getClassPath() {
        return classPath.toString();
    }
}
