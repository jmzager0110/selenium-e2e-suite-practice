package com.example.portalUsers.notInUse.pages;

import com.example.e2e.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/** Final page: shows the proposal id. */
public class ConfirmationPage extends BasePage {

    // TODO: replace locators, header text and URL fragment
    private static final By HEADER = By.cssSelector("h1");
    private static final By PROPOSAL_ID = By.id("proposalId");

    /** TODO: tighten to your real id format, e.g. "^PR-\\d{8}$". */
    private static final String PROPOSAL_ID_FORMAT = "^\\S+$";

    public ConfirmationPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void assertLoaded() {
        assertUrlContains("/confirmation");
        assertDisplayed(HEADER, "Confirmation header");
        assertDisplayed(PROPOSAL_ID, "Proposal id");
    }

    /** Reads and validates the proposal id. */
    public String getProposalId() {
        String id = getText(PROPOSAL_ID);
        Assert.assertFalse(id.isBlank(), "Proposal id was blank");
        Assert.assertTrue(id.matches(PROPOSAL_ID_FORMAT),
                "Proposal id '" + id + "' does not match " + PROPOSAL_ID_FORMAT);
        return id;
    }
}
