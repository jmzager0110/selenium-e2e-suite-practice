package com.example.portalUsers.xxnotInUse.pages;

import com.example.portalUsers.xxnotInUse.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Landing page after an agent logs in. */
public class AgentHomePage extends BasePage {

    // TODO: replace locators and URL fragment
    private static final By NEW_APPLICATION_BUTTON = By.id("newApplicationButton");

    public AgentHomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void assertLoaded() {
        assertUrlContains("/home");
        assertDisplayed(NEW_APPLICATION_BUTTON, "New Application button");
    }

    public ApplicationPageOne clickNewApplication() {
        click(NEW_APPLICATION_BUTTON);
        return arriveAt(new ApplicationPageOne(driver));
    }
}
