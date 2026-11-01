package com.example.portalUsers.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Final page: shows the proposal ID. */
public class ConfirmationPage extends BasePage {

    // TODO: replace with the real locators, header text and URL fragment
    private static final By HEADER      = By.cssSelector("h1");
    private static final By PROPOSAL_ID = By.id("TODO-proposal-id");

    /** TODO: tighten to the real ID format, e.g. "^PR-\\d{8}$". */
    private static final String PROPOSAL_ID_FORMAT = "^\\S+$";

    public ConfirmationPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void assertLoaded() {
        assertUrlContains("/TODO-confirmation");
        assertDisplayed(HEADER, "Confirmation header");
        assertDisplayed(PROPOSAL_ID, "Proposal ID");
    }

    public String getProposalId() {
        // If the ID is in an input rather than text, use valueOf(PROPOSAL_ID) instead
        String id = textOf(PROPOSAL_ID);
        assertFalse(id.isBlank(), "Proposal ID was blank");
        assertTrue(id.matches(PROPOSAL_ID_FORMAT),
                "Proposal ID '" + id + "' does not match " + PROPOSAL_ID_FORMAT);
        return id;
    }
}
