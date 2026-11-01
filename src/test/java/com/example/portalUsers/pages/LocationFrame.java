package com.example.portalUsers.pages;

import com.example.models.LocationData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Component object for the "Add Location" iframe.
 *
 * The key rule: Selenium can only see ONE document at a time. Switch in before touching anything
 * inside the iframe, and switch back out (defaultContent) before touching the main page again.
 * Keeping that switching inside this class means the steps never have to think about it.
 */
public class LocationFrame extends BasePage {

    // TODO: replace with the real iframe locator (id, name, or css)
    private static final By FRAME = By.cssSelector("iframe[id='TODO-location-frame']");

    // TODO: replace with the real locators inside the iframe
    private static final By NAME_INPUT            = By.id("TODO-location-name");
    private static final By STREET_INPUT          = By.id("TODO-street");
    private static final By CITY_INPUT            = By.id("TODO-city");
    private static final By STATE_INPUT           = By.id("TODO-state");
    private static final By ZIP_INPUT             = By.id("TODO-zip");
    private static final By CLASSIFICATION_SELECT = By.id("TODO-classification");
    private static final By SAVE_LOCATION_BUTTON  = By.xpath("//button[normalize-space()='Save']");
    private static final String COVERAGE_RADIO_XPATH = "//input[@type='radio' and @value='%s']";       // TODO: adjust
    private static final String OPTION_CHECKBOX_XPATH = "//input[@type='checkbox' and @value='%s']";   // TODO: adjust

    public LocationFrame(WebDriver driver) {
        super(driver);
    }

    private static By coverageRadio(String value) {
        return By.xpath(String.format(COVERAGE_RADIO_XPATH, value));
    }

    private static By optionCheckbox(String value) {
        return By.xpath(String.format(OPTION_CHECKBOX_XPATH, value));
    }

    /** Waits for the iframe and switches into it. */
    public LocationFrame switchIn() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(FRAME));
        return this;
    }

    /** Back to the main page. */
    public void switchOut() {
        driver.switchTo().defaultContent();
    }

    /** Validates the form inside the iframe. The driver must already be switched in. */
    @Override
    public void assertLoaded() {
        assertDisplayed(NAME_INPUT, "Location name field (iframe)");
        assertDisplayed(STREET_INPUT, "Street field (iframe)");
        assertDisplayed(CITY_INPUT, "City field (iframe)");
        assertDisplayed(STATE_INPUT, "State field (iframe)");
        assertDisplayed(ZIP_INPUT, "Zip field (iframe)");
        assertDisplayed(CLASSIFICATION_SELECT, "Classification dropdown (iframe)");
        assertDisplayed(SAVE_LOCATION_BUTTON, "Save button (iframe)");
    }

    /** Fills the fields and makes the selections, then re-reads them to confirm. */
    public LocationFrame fill(LocationData data) {
        type(NAME_INPUT, data.name());
        type(STREET_INPUT, data.street());
        type(CITY_INPUT, data.city());
        type(STATE_INPUT, data.state());
        type(ZIP_INPUT, data.zip());
        selectByVisibleText(CLASSIFICATION_SELECT, data.classification());
        click(coverageRadio(data.coverageType()));
        for (String option : data.additionalOptions()) {
            setChecked(optionCheckbox(option), true);
        }

        assertFieldValue(NAME_INPUT, data.name(), "Location name");
        assertFieldValue(STREET_INPUT, data.street(), "Street");
        assertFieldValue(CITY_INPUT, data.city(), "City");
        assertFieldValue(STATE_INPUT, data.state(), "State");
        assertFieldValue(ZIP_INPUT, data.zip(), "Zip");
        assertDropdownSelected(CLASSIFICATION_SELECT, data.classification(), "Classification");
        assertChecked(coverageRadio(data.coverageType()), "Coverage type " + data.coverageType());
        for (String option : data.additionalOptions()) {
            assertChecked(optionCheckbox(option), "Additional option " + option);
        }
        return this;
    }

    public void save() {
        click(SAVE_LOCATION_BUTTON);
    }

    /** Convenience: the full add-location round trip, always leaving the driver on the main page. */
    public void addLocation(LocationData data) {
        switchIn();
        try {
            assertLoaded();
            fill(data);
            save();
        } finally {
            switchOut();
        }
        // The iframe closing is the sign that the save went through.
        wait.until(ExpectedConditions.invisibilityOfElementLocated(FRAME));
    }
}
