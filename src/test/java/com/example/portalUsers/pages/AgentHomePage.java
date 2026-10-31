package com.example.portalUsers.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Landing page after an agent logs in. */
public class AgentHomePage extends BasePage {

    // TODO: replace with the real locators and URL fragment
    private static final By NEW_APPLICATION_BUTTON = By.xpath("//button[normalize-space()='New Application']");

    public AgentHomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void assertLoaded() {
        assertUrlContains("/TODO-agent-home");
        assertDisplayed(NEW_APPLICATION_BUTTON, "New Application button");
    }

    public ApplicationPageOne clickNewApplication() {
        click(NEW_APPLICATION_BUTTON);
        return arriveAt(new ApplicationPageOne(driver));
    }
}
