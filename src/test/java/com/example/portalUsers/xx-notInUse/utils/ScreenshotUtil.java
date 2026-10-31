package com.example.portalUsers.notInUse.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ScreenshotUtil {

    private ScreenshotUtil() {}

    /** Saves a PNG under target/screenshots and returns its path, or null if it could not be taken. */
    public static Path capture(WebDriver driver, String name) {
        if (!(driver instanceof TakesScreenshot camera)) {
            return null;
        }
        try {
            Path dir = Path.of("target", "screenshots");
            Files.createDirectories(dir);
            Path file = dir.resolve(name + "-" + System.currentTimeMillis() + ".png");
            Files.write(file, camera.getScreenshotAs(OutputType.BYTES));
            return file;
        } catch (IOException | WebDriverException e) {
            return null;
        }
    }
}
