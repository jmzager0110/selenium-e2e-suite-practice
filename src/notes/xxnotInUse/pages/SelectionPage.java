package com.example.portalUsers.xxnotInUse.pages;

import com.example.portalUsers.xxnotInUse.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * The page reached after the locations step, where one selection is made.
 * TODO: rename to match the real page (coverage, plan, package, ...).
 */
public class SelectionPage extends BasePage {

    // TODO: replace locators, header text and URL fragment
    private static final By HEADER = By.cssSelector("h1");
    private static final By CONTINUE_BUTTON = By.id("continueButton");

    public SelectionPage(WebDriver driver) {
        super(driver);
    }

    private static By option(String value) {
        return By.cssSelector("input[type='radio'][name='selection'][value='" + value + "']");
    }

    @Override
    public void assertLoaded() {
        assertUrlContains("/selection");
        assertDisplayed(HEADER, "Selection page header");
        assertDisplayed(CONTINUE_BUTTON, "Continue button");
    }

    public SelectionPage select(String value) {
        click(option(value));
        assertChecked(option(value), "Selection " + value);
        return this;
    }

    public PaymentOptionsPage clickContinue() {
        click(CONTINUE_BUTTON);
        return arriveAt(new PaymentOptionsPage(driver));
    }
}
