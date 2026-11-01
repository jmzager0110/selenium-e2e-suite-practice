package com.example.portalUsers.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * The page reached after the locations step, where one selection is made.
 * TODO: rename to match the real page (coverage, plan, package, ...).
 */
public class SelectionPage extends BasePage {

    // TODO: replace with the real locators, header text and URL fragment
    private static final By HEADER          = By.cssSelector("h1");
    private static final By CONTINUE_BUTTON = By.xpath("//button[normalize-space()='Continue']");
    private static final String OPTION_XPATH = "//input[@type='radio' and @value='%s']"; // TODO: adjust

    public SelectionPage(WebDriver driver) {
        super(driver);
    }

    private static By option(String value) {
        return By.xpath(String.format(OPTION_XPATH, value));
    }

    @Override
    public void assertLoaded() {
        assertUrlContains("/TODO-selection");
        assertDisplayed(HEADER, "Selection page header");
        assertDisplayed(CONTINUE_BUTTON, "Continue button");
    }

    public SelectionPage select(String value) {
        click(option(value));
        assertChecked(option(value), "Selection " + value);
        return this;
    }

    public PaymentOptionsPage clickContinue() {
        scrollAndClick(CONTINUE_BUTTON);
        return arriveAt(new PaymentOptionsPage(driver));
    }
}
