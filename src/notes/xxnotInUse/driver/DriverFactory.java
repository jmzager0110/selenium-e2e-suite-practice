package com.example.portalUsers.xxnotInUse.driver;

import com.example.e2e.config.TestConfig;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Creates one WebDriver per thread. Selenium Manager (bundled with Selenium 4.6+)
 * downloads the matching browser driver automatically.
 */
public final class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {}

    public static WebDriver createDriver() {
        boolean headless = TestConfig.headless();

        WebDriver driver;
        switch (TestConfig.browser().toLowerCase()) {
            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();
                if (headless) {
                    options.addArguments("--headless=new", "--window-size=1920,1080");
                }
                driver = new ChromeDriver(options);
            }
            case "firefox" -> {
                FirefoxOptions options = new FirefoxOptions();
                if (headless) {
                    options.addArguments("-headless");
                }
                driver = new FirefoxDriver(options);
            }
            case "edge" -> {
                EdgeOptions options = new EdgeOptions();
                if (headless) {
                    options.addArguments("--headless=new", "--window-size=1920,1080");
                }
                driver = new EdgeDriver(options);
            }
            default -> throw new IllegalArgumentException(
                    "Unsupported browser '" + TestConfig.browser() + "'. Use chrome, firefox or edge.");
        }

        // No implicit wait: page objects use explicit waits only (mixing the two causes flaky timing).
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestConfig.pageLoadTimeoutSeconds()));
        if (headless) {
            driver.manage().window().setSize(new Dimension(1920, 1080));
        } else {
            driver.manage().window().maximize();
        }

        DRIVER.set(driver);
        return driver;
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("No WebDriver for this thread. Call createDriver() first.");
        }
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            try {
                driver.quit();
            } finally {
                DRIVER.remove();
            }
        }
    }
}
