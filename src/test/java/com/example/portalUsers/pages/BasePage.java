package com.example.proposal.pages;

import com.example.proposal.support.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Shared helpers. Every page/component extends this so waits, field handling and validations live in one place.
 * Every page implements assertLoaded(): the validation that runs when the user arrives on it.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Config.waitTimeout());
    }

    /** Validates that this page is displayed: URL, heading and key elements. */
    public abstract void assertLoaded();

    /** Validates the destination page, then returns it. Used by every navigation method. */
    protected <T extends BasePage> T arriveAt(T page) {
        page.assertLoaded();
        return page;
    }

    // ---------------------------------------------------------------- actions

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    /** Bottom-of-page buttons are often off-screen; scroll first to avoid ElementClickIntercepted. */
    protected void scrollAndClick(By locator) {
        WebElement element = visible(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        click(locator);
    }

    protected void type(By locator, String text) {
        WebElement el = visible(locator);
        el.clear();
        el.sendKeys(text);
    }

    protected void selectByVisibleText(By locator, String text) {
        new Select(visible(locator)).selectByVisibleText(text);
    }

    protected void setChecked(By locator, boolean checked) {
        WebElement box = wait.until(ExpectedConditions.elementToBeClickable(locator));
        if (box.isSelected() != checked) {
            box.click();
        }
    }

    // ---------------------------------------------------------------- reads

    protected String textOf(By locator) {
        return visible(locator).getText().trim();
    }

    protected String valueOf(By locator) {
        String value = visible(locator).getDomProperty("value");
        return value == null ? "" : value.trim();
    }

    // ---------------------------------------------------------------- validations

    protected void assertDisplayed(By locator, String description) {
        try {
            visible(locator);
        } catch (TimeoutException e) {
            fail(description + " was not displayed on " + driver.getCurrentUrl() + " (locator: " + locator + ")");
        }
    }

    protected void assertUrlContains(String fragment) {
        try {
            wait.until(ExpectedConditions.urlContains(fragment));
        } catch (TimeoutException e) {
            fail("Expected URL to contain '" + fragment + "' but was '" + driver.getCurrentUrl() + "'");
        }
    }

    protected void assertTextContains(By locator, String expected, String description) {
        String actual = textOf(locator);
        assertTrue(actual.contains(expected),
                description + ": expected text to contain '" + expected + "' but was '" + actual + "'");
    }

    protected void assertFieldValue(By locator, String expected, String description) {
        assertEquals(expected, valueOf(locator), description + " has the wrong value");
    }

    protected void assertDropdownSelected(By locator, String expected, String description) {
        String actual = new Select(visible(locator)).getFirstSelectedOption().getText().trim();
        assertEquals(expected, actual, description + " has the wrong selection");
    }

    protected void assertChecked(By locator, String description) {
        assertTrue(visible(locator).isSelected(), description + " should be selected");
    }
}
