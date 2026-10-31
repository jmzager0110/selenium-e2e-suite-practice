package com.example.portalUsers.notInUse.config;

import com.example.e2e.model.Credentials;
import com.example.e2e.model.Role;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Central config. Lookup order: JVM system property, environment variable
 * (key upper-cased, dots replaced by underscores), then config.properties.
 */
public final class TestConfig {

    private static final Properties FILE = load();

    private TestConfig() {}

    private static Properties load() {
        Properties props = new Properties();
        try (InputStream in = TestConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Could not read config.properties", e);
        }
        return props;
    }

    public static String get(String key, String defaultValue) {
        String fromSystem = System.getProperty(key);
        if (fromSystem != null && !fromSystem.isBlank()) {
            return fromSystem;
        }
        String fromEnv = System.getenv(key.toUpperCase().replace('.', '_'));
        if (fromEnv != null && !fromEnv.isBlank()) {
            return fromEnv;
        }
        return FILE.getProperty(key, defaultValue);
    }

    public static String require(String key) {
        String value = get(key, null);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required config value: " + key);
        }
        return value;
    }

    public static String baseUrl() {
        return require("base.url");
    }

    public static String browser() {
        return get("browser", "chrome");
    }

    public static boolean headless() {
        return Boolean.parseBoolean(get("headless", "false"));
    }

    public static int timeoutSeconds() {
        return Integer.parseInt(get("timeout.seconds", "15"));
    }

    public static int pageLoadTimeoutSeconds() {
        return Integer.parseInt(get("page.load.timeout.seconds", "60"));
    }

    public static Credentials credentials(Role role) {
        String prefix = role.name().toLowerCase();
        return new Credentials(require(prefix + ".username"), require(prefix + ".password"));
    }
}
