package com.example.intake.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.example.support.Config;

/** Shared helpers. Every page/component extends this so waits and field handling live in one place. */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Config.waitTimeout());
    }

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text) {
        WebElement el = visible(locator);
        el.clear();
        el.sendKeys(text);
    }

    protected void selectByVisibleText(By locator, String text) {
        new Select(visible(locator)).selectByVisibleText(text);
    }

    protected String textOf(By locator) {
        return visible(locator).getText().trim();
    }
}
