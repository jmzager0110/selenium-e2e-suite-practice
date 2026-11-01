package com.example.portalUsers.xxnotInUse.pages;

import com.example.portalUsers.xxnotInUse.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Landing page after a client logs in. */
public class ClientHomePage extends BasePage {

    // TODO: replace locators and URL fragment
    private static final By ACCOUNTS_LINK = By.linkText("Accounts");

    public ClientHomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void assertLoaded() {
        assertUrlContains("/home");
        assertDisplayed(ACCOUNTS_LINK, "Accounts link");
    }

    public AccountsPage clickAccounts() {
        click(ACCOUNTS_LINK);
        return arriveAt(new AccountsPage(driver));
    }
}
