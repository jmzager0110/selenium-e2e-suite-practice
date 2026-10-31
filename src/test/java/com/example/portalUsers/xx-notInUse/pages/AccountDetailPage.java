package com.example.portalUsers.notInUse.pages;

import com.example.e2e.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Detail page of the selected account (client variant). */
public class AccountDetailPage extends BasePage {

    // TODO: replace locators and URL fragment
    private static final By HEADER = By.cssSelector("h1");
    private static final By ADD_LOCATIONS_BUTTON = By.id("addLocationsButton");

    public AccountDetailPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void assertLoaded() {
        assertUrlContains("/accounts/");
        assertDisplayed(HEADER, "Account header");
        assertDisplayed(ADD_LOCATIONS_BUTTON, "Add Locations button");
    }

    public ApplicationPageOne clickAddLocations() {
        click(ADD_LOCATIONS_BUTTON);
        return arriveAt(new ApplicationPageOne(driver));
    }
}
