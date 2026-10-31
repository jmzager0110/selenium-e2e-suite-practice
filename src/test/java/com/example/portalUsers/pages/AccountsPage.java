package com.example.proposal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertFalse;

/** Accounts list (client variant). */
public class AccountsPage extends BasePage {

    // TODO: replace with the real locators, header text and URL fragment
    private static final By HEADER            = By.cssSelector("h1");
    private static final By RESULT_ROWS       = By.cssSelector("table.TODO-accounts tbody tr");
    private static final By FIRST_RESULT_LINK = By.cssSelector("table.TODO-accounts tbody tr:first-child a");

    public AccountsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void assertLoaded() {
        assertUrlContains("/TODO-accounts");
        assertTextContains(HEADER, "Accounts", "Accounts page header");
        assertDisplayed(FIRST_RESULT_LINK, "First account result");
        assertFalse(driver.findElements(RESULT_ROWS).isEmpty(), "Accounts list should not be empty");
    }

    public AccountDetailPage clickFirstResult() {
        click(FIRST_RESULT_LINK);
        return arriveAt(new AccountDetailPage(driver));
    }
}
