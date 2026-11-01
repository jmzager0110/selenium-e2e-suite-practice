package com.example.portalUsers.xxnotInUse.pages;

import com.example.portalUsers.xxnotInUse.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Review / submit page. TODO: if Submit lives on the payment page in your app, fold this into it. */
public class SubmitPage extends BasePage {

    // TODO: replace locators, header text and URL fragment
    private static final By HEADER = By.cssSelector("h1");
    private static final By SUBMIT_BUTTON = By.id("submitButton");

    public SubmitPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void assertLoaded() {
        assertUrlContains("/submit");
        assertDisplayed(HEADER, "Submit page header");
        assertDisplayed(SUBMIT_BUTTON, "Submit button");
    }

    public ConfirmationPage clickSubmit() {
        click(SUBMIT_BUTTON);
        return arriveAt(new ConfirmationPage(driver));
    }
}
