package com.example.portalUsers.notInUse.flows;

import com.example.e2e.pages.AccountDetailPage;
import com.example.e2e.pages.AccountsPage;
import com.example.e2e.pages.ApplicationPageOne;
import com.example.e2e.pages.ClientHomePage;
import org.openqa.selenium.WebDriver;

/** Client: Accounts, first result, Add Locations. */
public class ClientEntryFlow implements EntryFlow {

    private final WebDriver driver;

    public ClientEntryFlow(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public ApplicationPageOne startApplication() {
        ClientHomePage home = new ClientHomePage(driver);
        home.assertLoaded();

        AccountsPage accounts = home.clickAccounts();
        AccountDetailPage account = accounts.clickFirstResult();
        return account.clickAddLocations();
    }
}
