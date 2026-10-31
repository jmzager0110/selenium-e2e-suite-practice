package com.example.intake.support;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

/** Reads config.properties; any -D system property on the command line wins. */
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
        // Surefire passes an unset ${key} through as an empty string, so treat blank as "not set"
        if (fromSystem != null && !fromSystem.isBlank() && !fromSystem.startsWith("${")) {
            return fromSystem;
        }
        return PROPS.getProperty(key);
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static Duration waitTimeout() {
        return Duration.ofSeconds(Long.parseLong(get("explicit.wait.seconds")));
    }
}
