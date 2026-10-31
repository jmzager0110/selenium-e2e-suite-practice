package com.example.portalUsers.notInUse.pages;

import com.example.e2e.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class PaymentOptionsPage extends BasePage {

    // TODO: replace locators, header text and URL fragment
    private static final By HEADER = By.cssSelector("h1");
    private static final By ANY_OPTION = By.cssSelector("input[type='radio'][name='paymentOption']");
    private static final By CHECKED_OPTION = By.cssSelector("input[type='radio'][name='paymentOption']:checked");
    private static final By CONTINUE_BUTTON = By.id("continueButton");

    public PaymentOptionsPage(WebDriver driver) {
        super(driver);
    }

    private static By option(String value) {
        return By.cssSelector("input[type='radio'][name='paymentOption'][value='" + value + "']");
    }

    @Override
    public void assertLoaded() {
        assertUrlContains("/payment");
        assertDisplayed(HEADER, "Payment options header");
        assertDisplayed(ANY_OPTION, "Payment option");
        assertDisplayed(CONTINUE_BUTTON, "Continue button");
    }

    /**
     * Selects the given option, or, when none is given, verifies a default is preselected and keeps it.
     * Returns the value that will be used.
     */
    public String chooseOrAcceptDefault(String optionValue) {
        if (optionValue != null) {
            click(option(optionValue));
            assertChecked(option(optionValue), "Payment option " + optionValue);
            return optionValue;
        }
        Assert.assertFalse(driver.findElements(CHECKED_OPTION).isEmpty(),
                "No payment option is preselected, so there is no default to accept");
        return driver.findElement(CHECKED_OPTION).getDomProperty("value");
    }

    public SubmitPage clickContinue() {
        click(CONTINUE_BUTTON);
        return arriveAt(new SubmitPage(driver));
    }
}
