package com.example.portalUsers.notInUse.base;

import com.example.e2e.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

/**
 * Shared helpers for every page object: explicit waits, interactions and assertions.
 * Every page must implement assertLoaded(), the validation that runs on arrival.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.timeoutSeconds()));
    }

    /** Validates that this page is displayed: URL, heading and key elements. */
    public abstract void assertLoaded();

    /** Validates the destination page, then returns it. Used by every navigation method. */
    protected <T extends BasePage> T arriveAt(T page) {
        page.assertLoaded();
        return page;
    }

    // ---------------------------------------------------------------- waits

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // ---------------------------------------------------------------- actions

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    /** Clears the field and types the value. A null value leaves the field untouched. */
    protected void type(By locator, String value) {
        if (value == null) {
            return;
        }
        WebElement element = waitVisible(locator);
        element.clear();
        element.sendKeys(value);
    }

    protected void selectByText(By locator, String visibleText) {
        if (visibleText == null) {
            return;
        }
        new Select(waitVisible(locator)).selectByVisibleText(visibleText);
    }

    protected void setChecked(By locator, boolean checked) {
        WebElement box = wait.until(ExpectedConditions.elementToBeClickable(locator));
        if (box.isSelected() != checked) {
            box.click();
        }
    }

    // ---------------------------------------------------------------- reads

    protected String getText(By locator) {
        return waitVisible(locator).getText().trim();
    }

    protected String getValue(By locator) {
        String value = waitVisible(locator).getDomProperty("value");
        return value == null ? "" : value.trim();
    }

    // ---------------------------------------------------------------- assertions

    protected void assertDisplayed(By locator, String description) {
        try {
            waitVisible(locator);
        } catch (TimeoutException e) {
            Assert.fail(description + " was not displayed on " + driver.getCurrentUrl()
                    + " (locator: " + locator + ")");
        }
    }

    protected void assertUrlContains(String fragment) {
        try {
            wait.until(ExpectedConditions.urlContains(fragment));
        } catch (TimeoutException e) {
            Assert.fail("Expected URL to contain '" + fragment + "' but was '" + driver.getCurrentUrl() + "'");
        }
    }

    protected void assertTextContains(By locator, String expected, String description) {
        String actual = getText(locator);
        Assert.assertTrue(actual.contains(expected),
                description + ": expected text to contain '" + expected + "' but was '" + actual + "'");
    }

    protected void assertFieldValue(By locator, String expected, String description) {
        Assert.assertEquals(getValue(locator), expected, description + " has the wrong value");
    }

    protected void assertDropdownSelected(By locator, String expectedText, String description) {
        String actual = new Select(waitVisible(locator)).getFirstSelectedOption().getText().trim();
        Assert.assertEquals(actual, expectedText, description + " has the wrong selection");
    }

    protected void assertChecked(By locator, String description) {
        Assert.assertTrue(waitVisible(locator).isSelected(), description + " should be selected");
    }
}
