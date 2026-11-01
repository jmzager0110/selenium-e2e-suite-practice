package com.example.portalUsers.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** First application page. Both the client and agent variants land here. */
public class ApplicationPageOne extends BasePage {

    // TODO: replace with the real locators, header text and URL fragment
    private static final By HEADER             = By.cssSelector("h1");
    private static final By APPLICANT_NAME     = By.id("TODO-applicant-name");
    private static final By CONTACT_EMAIL      = By.id("TODO-contact-email");
    private static final By PHONE              = By.id("TODO-phone");
    private static final By NEXT_BUTTON        = By.xpath("//button[normalize-space()='Next']");

    public ApplicationPageOne(WebDriver driver) {
        super(driver);
    }

    @Override
    public void assertLoaded() {
        assertUrlContains("/TODO-page-one");
        assertTextContains(HEADER, "TODO-page-one-header", "Page one header");
        assertDisplayed(APPLICANT_NAME, "Applicant name field");
        assertDisplayed(CONTACT_EMAIL, "Contact email field");
        assertDisplayed(PHONE, "Phone field");
        assertDisplayed(NEXT_BUTTON, "Next button");
    }

    /** Types the values, then re-reads each field to confirm it kept them. */
    public ApplicationPageOne fill(String applicantName, String contactEmail, String phone) {
        type(APPLICANT_NAME, applicantName);
        type(CONTACT_EMAIL, contactEmail);
        type(PHONE, phone);

        assertFieldValue(APPLICANT_NAME, applicantName, "Applicant name");
        assertFieldValue(CONTACT_EMAIL, contactEmail, "Contact email");
        assertFieldValue(PHONE, phone, "Phone");
        return this;
    }

    public ApplicationPageTwo clickNext() {
        scrollAndClick(NEXT_BUTTON);
        return arriveAt(new ApplicationPageTwo(driver));
    }
}
