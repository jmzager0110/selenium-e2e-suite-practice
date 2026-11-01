package com.example.portalUsers.xxnotInUse.pages;

import com.example.portalUsers.xxnotInUse.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.testng.Assert;

/** Accounts search/list page (client variant). */
public class AccountsPage extends BasePage {

    // TODO: replace locators and URL fragment
    private static final By HEADER = By.cssSelector("h1");
    private static final By RESULT_ROWS = By.cssSelector("table#accounts tbody tr");
    private static final By FIRST_RESULT_LINK = By.cssSelector("table#accounts tbody tr:first-child a");

    public AccountsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void assertLoaded() {
        assertUrlContains("/accounts");
        assertTextContains(HEADER, "Accounts", "Accounts page header");
        assertDisplayed(FIRST_RESULT_LINK, "First account result");
        Assert.assertFalse(driver.findElements(RESULT_ROWS).isEmpty(), "Accounts list should not be empty");
    }

    public AccountDetailPage clickFirstResult() {
        click(FIRST_RESULT_LINK);
        return arriveAt(new AccountDetailPage(driver));
    }
}
