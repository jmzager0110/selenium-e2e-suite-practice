package com.example.support;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Lookup order: -D system property, real environment variable, .env file, then config.properties.
 * A key like client.password is looked up as CLIENT_PASSWORD in the environment and in .env.
 */
public final class Config {

    private static final Properties PROPS = new Properties();
    private static final Map<String, String> DOTENV = loadDotEnv();

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
        String envName = envName(key);
        String fromEnv = System.getenv(envName);
        if (isSet(fromEnv)) {
            return fromEnv;
        }
        String fromDotEnv = DOTENV.get(envName);
        if (isSet(fromDotEnv)) {
            return fromDotEnv;
        }
        return PROPS.getProperty(key);
    }

    /** Like get(), but fails with a clear message when the value is missing. */
    public static String require(String key) {
        String value = get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required config value: " + key
                    + " (set it in .env as " + envName(key) + ", or pass -D" + key + "=...)");
        }
        return value;
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static Duration waitTimeout() {
        String value = get("explicit.wait.seconds");
        return Duration.ofSeconds(value == null || value.isBlank() ? 15 : Long.parseLong(value.trim()));
    }

    private static String envName(String key) {
        return key.toUpperCase().replace('.', '_');
    }

    // Surefire passes an unset ${key} through as an empty string or the literal "${key}",
    // so both count as "not set".
    private static boolean isSet(String value) {
        return value != null && !value.isBlank() && !value.startsWith("${");
    }

    /**
     * Reads KEY=VALUE lines from .env in the working directory (the project root), or from the file named
     * by -Ddotenv.path. Blank lines and lines starting with # are skipped; "export " prefixes and one pair
     * of surrounding quotes are removed. A # inside a value is kept, so passwords may contain it.
     */
    private static Map<String, String> loadDotEnv() {
        Map<String, String> values = new HashMap<>();
        Path file = Path.of(System.getProperty("dotenv.path", ".env"));
        if (!Files.isRegularFile(file)) {
            return values;
        }
        try {
            for (String raw : Files.readAllLines(file)) {
                String line = raw.strip();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                if (line.startsWith("export ")) {
                    line = line.substring("export ".length()).strip();
                }
                int eq = line.indexOf('=');
                if (eq <= 0) {
                    continue;
                }
                String name = line.substring(0, eq).strip();
                String value = line.substring(eq + 1).strip();
                if (value.length() >= 2
                        && ((value.startsWith("\"") && value.endsWith("\""))
                        || (value.startsWith("'") && value.endsWith("'")))) {
                    value = value.substring(1, value.length() - 1);
                }
                values.put(name, value);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Could not read " + file, e);
        }
        return values;
    }
}