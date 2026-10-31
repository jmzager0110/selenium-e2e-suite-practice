package com.example.portalUsers.notInUse.pages;

import com.example.e2e.base.BasePage;
import com.example.e2e.model.ProposalData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * The Add Location form, which lives inside an iframe.
 * assertLoaded() switches into the frame; save() switches back out to the parent page.
 */
public class LocationFrame extends BasePage {

    // TODO: replace locators
    private static final By FRAME = By.cssSelector("iframe#locationFrame");

    private static final By NAME = By.id("locationName");
    private static final By STREET = By.id("street");
    private static final By CITY = By.id("city");
    private static final By STATE = By.id("state");
    private static final By ZIP = By.id("zip");
    private static final By CLASSIFICATION = By.id("classification");
    private static final By SAVE_BUTTON = By.id("saveLocationButton");

    public LocationFrame(WebDriver driver) {
        super(driver);
    }

    private static By coverageRadio(String value) {
        return By.cssSelector("input[type='radio'][name='coverageType'][value='" + value + "']");
    }

    private static By optionCheckbox(String value) {
        return By.cssSelector("input[type='checkbox'][name='additionalOption'][value='" + value + "']");
    }

    @Override
    public void assertLoaded() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(FRAME));
        assertDisplayed(NAME, "Location name field (iframe)");
        assertDisplayed(STREET, "Street field (iframe)");
        assertDisplayed(CITY, "City field (iframe)");
        assertDisplayed(STATE, "State field (iframe)");
        assertDisplayed(ZIP, "Zip field (iframe)");
        assertDisplayed(CLASSIFICATION, "Classification dropdown (iframe)");
        assertDisplayed(SAVE_BUTTON, "Save location button (iframe)");
    }

    public LocationFrame fill(ProposalData.Location location) {
        type(NAME, location.name());
        type(STREET, location.street());
        type(CITY, location.city());
        type(STATE, location.state());
        type(ZIP, location.zip());
        selectByText(CLASSIFICATION, location.classification());
        click(coverageRadio(location.coverageType()));
        for (String option : location.additionalOptions()) {
            setChecked(optionCheckbox(option), true);
        }
        return this;
    }

    /** Verifies the typed values and selections before saving. */
    public void assertFilled(ProposalData.Location location) {
        assertFieldValue(NAME, location.name(), "Location name");
        assertFieldValue(STREET, location.street(), "Street");
        assertFieldValue(CITY, location.city(), "City");
        assertFieldValue(STATE, location.state(), "State");
        assertFieldValue(ZIP, location.zip(), "Zip");
        assertDropdownSelected(CLASSIFICATION, location.classification(), "Classification");
        assertChecked(coverageRadio(location.coverageType()), "Coverage type " + location.coverageType());
        for (String option : location.additionalOptions()) {
            assertChecked(optionCheckbox(option), "Additional option " + option);
        }
    }

    /** Clicks save, returns to the parent page and waits for the iframe to close. */
    public void save() {
        click(SAVE_BUTTON);
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(FRAME));
    }
}
