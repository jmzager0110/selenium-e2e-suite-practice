package com.example.intake.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** What the user sees after "Save Changes": the intake form ID and a Done button. */
public class ConfirmationPage extends BasePage {

    // TODO: replace with the real locators
    private static final By INTAKE_FORM_ID = By.id("TODO-intake-form-id");
    private static final By DONE_BUTTON    = By.xpath("//button[normalize-space()='Done']");

    public ConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public String getIntakeFormId() {
        // If the ID is in an input rather than text, use visible(INTAKE_FORM_ID).getAttribute("value")
        return textOf(INTAKE_FORM_ID);
    }

    public void clickDone() {
        click(DONE_BUTTON);
    }
}
