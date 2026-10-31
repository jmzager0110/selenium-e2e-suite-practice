package com.example.portalUsers.notInUse.base;

import com.example.e2e.driver.DriverFactory;
import com.example.e2e.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.nio.file.Path;

/** Opens a fresh browser before each test and always closes it afterwards, capturing a screenshot on failure. */
public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.createDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                Path shot = ScreenshotUtil.capture(driver, result.getName());
                if (shot != null) {
                    Reporter.log("Failure screenshot: " + shot.toAbsolutePath(), true);
                }
            }
        } finally {
            DriverFactory.quitDriver();
        }
    }
}
