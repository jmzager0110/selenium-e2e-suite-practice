package com.example.portalUsers.xxnotInUse.pages;

import com.example.portalUsers.xxnotInUse.base.BasePage;
import com.example.portalUsers.xxnotInUse.model.ProposalData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** First application page. Both the client and agent variants land here. */
public class ApplicationPageOne extends BasePage {

    // TODO: replace locators, header text and URL fragment
    private static final By HEADER = By.cssSelector("h1");
    private static final By APPLICANT_NAME = By.id("applicantName");
    private static final By CONTACT_EMAIL = By.id("contactEmail");
    private static final By PHONE = By.id("phone");
    private static final By NEXT_BUTTON = By.id("nextButton");

    public ApplicationPageOne(WebDriver driver) {
        super(driver);
    }

    @Override
    public void assertLoaded() {
        assertUrlContains("/application/page-one");
        assertTextContains(HEADER, "Application", "Page one header");
        assertDisplayed(APPLICANT_NAME, "Applicant name field");
        assertDisplayed(CONTACT_EMAIL, "Contact email field");
        assertDisplayed(PHONE, "Phone field");
        assertDisplayed(NEXT_BUTTON, "Next button");
    }

    public ApplicationPageOne fillAll(ProposalData.PageOne data) {
        type(APPLICANT_NAME, data.applicantName());
        type(CONTACT_EMAIL, data.contactEmail());
        type(PHONE, data.phone());
        return this;
    }

    /** Verifies the page kept what was typed. */
    public void assertFilled(ProposalData.PageOne data) {
        assertFieldValue(APPLICANT_NAME, data.applicantName(), "Applicant name");
        assertFieldValue(CONTACT_EMAIL, data.contactEmail(), "Contact email");
        assertFieldValue(PHONE, data.phone(), "Phone");
    }

    public ApplicationPageTwo clickNext() {
        click(NEXT_BUTTON);
        return arriveAt(new ApplicationPageTwo(driver));
    }
}
