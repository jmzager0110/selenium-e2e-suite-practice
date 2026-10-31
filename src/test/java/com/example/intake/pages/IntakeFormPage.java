package com.example.intake.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/** The main intake form (top-level document, not the iframe). */
public class IntakeFormPage extends BasePage {

    // TODO: replace every locator with the real one from your page
    private static final By FIRST_NAME_INPUT   = By.id("TODO-first-name");
    private static final By LAST_NAME_INPUT    = By.id("TODO-last-name");
    private static final By REQUEST_TYPE_SELECT = By.id("TODO-request-type");
    private static final By ADD_LOCATION_BUTTON = By.xpath("//button[normalize-space()='Add Location']");
    private static final By SAVE_CHANGES_BUTTON = By.xpath("//button[normalize-space()='Save Changes']");

    public IntakeFormPage(WebDriver driver) {
        super(driver);
    }

    public IntakeFormPage open(String url) {
        driver.get(url);
        visible(FIRST_NAME_INPUT); // page-loaded check
        return this;
    }

    public IntakeFormPage fillPrimaryFields(String firstName, String lastName, String requestType) {
        type(FIRST_NAME_INPUT, firstName);
        type(LAST_NAME_INPUT, lastName);
        selectByVisibleText(REQUEST_TYPE_SELECT, requestType);
        return this;
    }

    /** Clicks the button at the bottom of the form; the iframe opens as a result. */
    public void clickAddLocation() {
        // Bottom-of-page buttons are often off-screen; scroll first to avoid ElementClickIntercepted.
        WebElement button = visible(ADD_LOCATION_BUTTON);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", button);
        click(ADD_LOCATION_BUTTON);
    }

    public void clickSaveChanges() {
        click(SAVE_CHANGES_BUTTON);
    }
}
