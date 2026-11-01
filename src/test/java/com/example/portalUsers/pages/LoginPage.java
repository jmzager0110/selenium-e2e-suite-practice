package com.example.portalUsers.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    // TODO: replace with the real locators
    private static final By USERNAME_INPUT = By.id("TODO-username");
    private static final By PASSWORD_INPUT = By.id("TODO-password");
    private static final By LOGIN_BUTTON   = By.xpath("//button[normalize-space()='Log in']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open(String url) {
        driver.get(url);
        assertLoaded();
        return this;
    }

    @Override
    public void assertLoaded() {
        assertDisplayed(USERNAME_INPUT, "Username field");
        assertDisplayed(PASSWORD_INPUT, "Password field");
        assertDisplayed(LOGIN_BUTTON, "Login button");
    }

    /** Shared by both variants. What happens next depends on the role (see ProposalSteps). */
    public void login(String username, String password) {
        type(USERNAME_INPUT, username);
        type(PASSWORD_INPUT, password);
        click(LOGIN_BUTTON);
    }
}
