package com.example.intake.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.example.models.LocationData;
import com.example.support.AddressAutocomplete;


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
    private static final By STREET_INPUT = By.id("TODO-street");
    private static final By NAME_INPUT      = By.id("TODO-location-name");
    private static final By TYPE_SELECT     = By.id("TODO-location-type");
    private static final String CATEGORY_RADIO_XPATH = "//input[@type='radio' and @value='%s']"; // TODO: adjust
    private static final By SAVE_LOCATION_BUTTON = By.xpath("//button[normalize-space()='Save']");

    public LocationFrame(WebDriver driver) {
        super(driver);
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

    public LocationFrame fill(LocationData data) {
        type(NAME_INPUT, data.name());
        new AddressAutocomplete(driver, STREET_INPUT).fillRandom();
        selectByVisibleText(TYPE_SELECT, data.type());
        click(By.xpath(String.format(CATEGORY_RADIO_XPATH, data.category())));
        return this;
    }

    public void save() {
        click(SAVE_LOCATION_BUTTON);
    }

    /** Convenience: the full add-location round trip, always leaving the driver on the main page. */
    public void addLocation(LocationData data) {
        switchIn();
        try {
            fill(data);
            save();
        } finally {
            switchOut();
        }
        // TODO: wait for the iframe to close or the new location to appear in the list, e.g.:
        // wait.until(ExpectedConditions.invisibilityOfElementLocated(FRAME));
    }
}
