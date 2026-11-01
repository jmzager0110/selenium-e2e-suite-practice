package com.example.portalUsers.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Landing page after a client logs in. */
public class ClientHomePage extends BasePage {

    // TODO: replace with the real locators and URL fragment
    private static final By ACCOUNTS_LINK = By.xpath("//a[normalize-space()='Accounts']");

    public ClientHomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void assertLoaded() {
        assertUrlContains("/TODO-client-home");
        assertDisplayed(ACCOUNTS_LINK, "Accounts link");
    }

    public AccountsPage clickAccounts() {
        click(ACCOUNTS_LINK);
        return arriveAt(new AccountsPage(driver));
    }
}
