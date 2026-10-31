package com.example.proposal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class PaymentOptionsPage extends BasePage {

    // TODO: replace with the real locators, header text and URL fragment
    private static final By HEADER           = By.cssSelector("h1");
    private static final By ANY_OPTION       = By.cssSelector("input[type='radio'][name='TODO-payment-option']");
    private static final By CHECKED_OPTION   = By.cssSelector("input[type='radio'][name='TODO-payment-option']:checked");
    private static final By CONTINUE_BUTTON  = By.xpath("//button[normalize-space()='Continue']");
    private static final String OPTION_XPATH = "//input[@type='radio' and @value='%s']"; // TODO: adjust

    public PaymentOptionsPage(WebDriver driver) {
        super(driver);
    }

    private static By option(String value) {
        return By.xpath(String.format(OPTION_XPATH, value));
    }

    @Override
    public void assertLoaded() {
        assertUrlContains("/TODO-payment");
        assertDisplayed(HEADER, "Payment options header");
        assertDisplayed(ANY_OPTION, "Payment option");
        assertDisplayed(CONTINUE_BUTTON, "Continue button");
    }

    /** Explicit choice. */
    public PaymentOptionsPage select(String value) {
        click(option(value));
        assertChecked(option(value), "Payment option " + value);
        return this;
    }

    /** No choice: verifies a default is preselected and keeps it. Returns the value that will be used. */
    public String acceptDefault() {
        assertFalse(driver.findElements(CHECKED_OPTION).isEmpty(),
                "No payment option is preselected, so there is no default to accept");
        return driver.findElement(CHECKED_OPTION).getDomProperty("value");
    }

    public SubmitPage clickContinue() {
        scrollAndClick(CONTINUE_BUTTON);
        return arriveAt(new SubmitPage(driver));
    }
}
