package com.example.portalUsers.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/** Second application page: its own fields, the locations list, and the Add Location button. */
public class ApplicationPageTwo extends BasePage {

    // TODO: replace with the real locators, header text and URL fragment
    private static final By HEADER              = By.cssSelector("h1");
    private static final By EFFECTIVE_DATE      = By.id("TODO-effective-date");
    private static final By INDUSTRY_SELECT     = By.id("TODO-industry");
    private static final By DESCRIPTION         = By.id("TODO-description");
    private static final By ADD_LOCATION_BUTTON = By.xpath("//button[normalize-space()='Add Location']");
    private static final By LOCATION_ROWS       = By.cssSelector("table.TODO-locations tbody tr");
    private static final By CONTINUE_BUTTON     = By.xpath("//button[normalize-space()='Continue']");

    public ApplicationPageTwo(WebDriver driver) {
        super(driver);
    }

    @Override
    public void assertLoaded() {
        assertUrlContains("/TODO-page-two");
        assertTextContains(HEADER, "TODO-page-two-header", "Page two header");
        assertDisplayed(EFFECTIVE_DATE, "Effective date field");
        assertDisplayed(INDUSTRY_SELECT, "Industry dropdown");
        assertDisplayed(DESCRIPTION, "Description field");
        assertDisplayed(ADD_LOCATION_BUTTON, "Add Location button");
        assertDisplayed(CONTINUE_BUTTON, "Continue button");
    }

    /** Types the values, then re-reads each field to confirm it kept them. */
    public ApplicationPageTwo fill(String effectiveDate, String industry, String description) {
        type(EFFECTIVE_DATE, effectiveDate);
        selectByVisibleText(INDUSTRY_SELECT, industry);
        type(DESCRIPTION, description);

        assertFieldValue(EFFECTIVE_DATE, effectiveDate, "Effective date");
        assertDropdownSelected(INDUSTRY_SELECT, industry, "Industry");
        assertFieldValue(DESCRIPTION, description, "Description");
        return this;
    }

    /** Clicks the button; the iframe opens as a result (LocationFrame takes it from there). */
    public void clickAddLocation() {
        scrollAndClick(ADD_LOCATION_BUTTON);
    }

    /** After a location is saved: the list must have the expected row count and show the new location. */
    public void assertLocationListed(int expectedCount, String locationName) {
        ExpectedCondition<Boolean> hasExpectedCount =
                d -> d.findElements(LOCATION_ROWS).size() == expectedCount;
        try {
            wait.until(hasExpectedCount);
        } catch (TimeoutException e) {
            fail("Expected " + expectedCount + " location row(s) but found "
                    + driver.findElements(LOCATION_ROWS).size());
        }
        boolean listed = driver.findElements(LOCATION_ROWS).stream()
                .anyMatch(row -> row.getText().contains(locationName));
        assertTrue(listed, "Location '" + locationName + "' was not listed after saving");
    }

    public SelectionPage clickContinue() {
        scrollAndClick(CONTINUE_BUTTON);
        return arriveAt(new SelectionPage(driver));
    }
}
