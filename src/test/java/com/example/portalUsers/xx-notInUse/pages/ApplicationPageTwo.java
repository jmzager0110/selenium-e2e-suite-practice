package com.example.portalUsers.notInUse.pages;

import com.example.e2e.base.BasePage;
import com.example.e2e.model.ProposalData;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.Assert;

/** Second application page: its own fields, plus the locations list and the Add Location button. */
public class ApplicationPageTwo extends BasePage {

    // TODO: replace locators, header text and URL fragment
    private static final By HEADER = By.cssSelector("h1");
    private static final By EFFECTIVE_DATE = By.id("effectiveDate");
    private static final By INDUSTRY = By.id("industry");
    private static final By DESCRIPTION = By.id("description");
    private static final By ADD_LOCATION_BUTTON = By.id("addLocationButton");
    private static final By LOCATION_ROWS = By.cssSelector("table#locations tbody tr");
    private static final By CONTINUE_BUTTON = By.id("continueButton");

    public ApplicationPageTwo(WebDriver driver) {
        super(driver);
    }

    @Override
    public void assertLoaded() {
        assertUrlContains("/application/page-two");
        assertTextContains(HEADER, "Application", "Page two header");
        assertDisplayed(EFFECTIVE_DATE, "Effective date field");
        assertDisplayed(INDUSTRY, "Industry dropdown");
        assertDisplayed(DESCRIPTION, "Description field");
        assertDisplayed(ADD_LOCATION_BUTTON, "Add Location button");
        assertDisplayed(CONTINUE_BUTTON, "Continue button");
    }

    public ApplicationPageTwo fillAll(ProposalData.PageTwo data) {
        type(EFFECTIVE_DATE, data.effectiveDate());
        selectByText(INDUSTRY, data.industry());
        type(DESCRIPTION, data.description());
        return this;
    }

    public void assertFilled(ProposalData.PageTwo data) {
        assertFieldValue(EFFECTIVE_DATE, data.effectiveDate(), "Effective date");
        assertDropdownSelected(INDUSTRY, data.industry(), "Industry");
        assertFieldValue(DESCRIPTION, data.description(), "Description");
    }

    /** Opens the Add Location iframe and validates that it loaded. The driver is left inside the frame. */
    public LocationFrame clickAddLocation() {
        click(ADD_LOCATION_BUTTON);
        return arriveAt(new LocationFrame(driver));
    }

    /** After a location is saved: the list must have the expected row count and show the new location. */
    public void assertLocationListed(int expectedCount, String locationName) {
        ExpectedCondition<Boolean> hasExpectedCount =
                d -> d.findElements(LOCATION_ROWS).size() == expectedCount;
        try {
            wait.until(hasExpectedCount);
        } catch (TimeoutException e) {
            Assert.fail("Expected " + expectedCount + " location row(s) but found "
                    + driver.findElements(LOCATION_ROWS).size());
        }
        boolean listed = driver.findElements(LOCATION_ROWS).stream()
                .anyMatch(row -> row.getText().contains(locationName));
        Assert.assertTrue(listed, "Location '" + locationName + "' was not listed after saving");
    }

    public SelectionPage clickContinue() {
        click(CONTINUE_BUTTON);
        return arriveAt(new SelectionPage(driver));
    }
}
