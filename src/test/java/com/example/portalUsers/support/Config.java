package com.example.portalUsers.support;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

/**
 * Reads config.properties. Lookup order: -D system property, environment variable
 * (key upper-cased, dots replaced by underscores), then config.properties.
 */
public final class Config {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) {
                PROPS.load(in);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Could not load config.properties", e);
        }
    }

    private Config() {
    }

    public static String get(String key) {
        String fromSystem = System.getProperty(key);
        if (isSet(fromSystem)) {
            return fromSystem;
        }
        String fromEnv = System.getenv(key.toUpperCase().replace('.', '_'));
        if (isSet(fromEnv)) {
            return fromEnv;
        }
        return PROPS.getProperty(key);
    }

    /** Like get(), but fails with a clear message when the value is missing. */
    public static String require(String key) {
        String value = get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required config value: " + key);
        }
        return value;
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static Duration waitTimeout() {
        return Duration.ofSeconds(Long.parseLong(get("explicit.wait.seconds")));
    }

    // Surefire passes an unset ${key} through as an empty string or the literal "${key}",
    // so both count as "not set".
    private static boolean isSet(String value) {
        return value != null && !value.isBlank() && !value.startsWith("${");
    }
}
