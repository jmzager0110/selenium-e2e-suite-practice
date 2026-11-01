package com.example.portalUsers.xxnotInUse.pages;

import com.example.portalUsers.xxnotInUse.base.BasePage;
import com.example.portalUsers.xxnotInUse.config.TestConfig;
import com.example.portalUsers.xxnotInUse.model.Credentials;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    // TODO: replace locators with the real ones
    private static final By USERNAME = By.id("username");
    private static final By PASSWORD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("loginButton");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /** Navigates to the configured base URL. */
    public LoginPage open() {
        driver.get(TestConfig.baseUrl());
        return this;
    }

    @Override
    public void assertLoaded() {
        assertDisplayed(USERNAME, "Username field");
        assertDisplayed(PASSWORD, "Password field");
        assertDisplayed(LOGIN_BUTTON, "Login button");
    }

    /** Enters credentials and clicks the login button. Shared by both the client and agent variants. */
    public void login(Credentials credentials) {
        type(USERNAME, credentials.username());
        type(PASSWORD, credentials.password());
        click(LOGIN_BUTTON);
    }
}
