package com.nanomachine.di.config.bean;

import java.util.Objects;

public class Identity {
    private final Class<?> type;
    private final String key;

    public Identity(Class<?> type, String key) {
        this.type = type;
        this.key = resolveKey(key);
    }

    private String resolveKey(String key) {
        if (key != null && !key.isEmpty()) {
            return key;
        }
        String className = this.type.getSimpleName();
        return Character.toLowerCase(className.charAt(0)) + className.substring(1);
    }

    public Class<?> getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Identity)) return false;
        Identity identity = (Identity) o;
        return Objects.equals(type, identity.type) &&
                Objects.equals(key, identity.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, key);
    }
}
