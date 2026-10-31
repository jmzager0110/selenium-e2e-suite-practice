package com.example.proposal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Review / submit page. TODO: if Submit lives on the payment page in your app, fold this into it. */
public class SubmitPage extends BasePage {

    // TODO: replace with the real locators, header text and URL fragment
    private static final By HEADER        = By.cssSelector("h1");
    private static final By SUBMIT_BUTTON = By.xpath("//button[normalize-space()='Submit']");

    public SubmitPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void assertLoaded() {
        assertUrlContains("/TODO-submit");
        assertDisplayed(HEADER, "Submit page header");
        assertDisplayed(SUBMIT_BUTTON, "Submit button");
    }

    public ConfirmationPage clickSubmit() {
        scrollAndClick(SUBMIT_BUTTON);
        return arriveAt(new ConfirmationPage(driver));
    }
}
