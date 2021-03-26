package com.nanomachine.di.injector;

import com.nanomachine.di.config.Configuration;

public class Injector {
    private final Configuration configuration;

    public Injector(Configuration configuration) {
        this.configuration = configuration;
    }

    public <T> T getBean(Class<T> type) {
        return configuration.getInstance(type);
    }
}
