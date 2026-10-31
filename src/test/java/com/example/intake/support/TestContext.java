package com.example.intake.support;

import org.openqa.selenium.WebDriver;

/**
 * Per-scenario state. PicoContainer creates one instance per scenario and injects the
 * same instance into every step class and hook class that asks for it in its constructor.
 * This is how Hooks (which creates the driver) and Steps (which uses it) share one browser.
 */
public class TestContext {

    private WebDriver driver;
    private String intakeFormId;

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public String getIntakeFormId() {
        return intakeFormId;
    }

    public void setIntakeFormId(String intakeFormId) {
        this.intakeFormId = intakeFormId;
    }
}
