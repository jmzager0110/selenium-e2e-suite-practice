package com.example.portalUsers.notInUse.flows;

import com.example.e2e.pages.AgentHomePage;
import com.example.e2e.pages.ApplicationPageOne;
import org.openqa.selenium.WebDriver;

/** Agent: New Application straight from the landing page. */
public class AgentEntryFlow implements EntryFlow {

    private final WebDriver driver;

    public AgentEntryFlow(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public ApplicationPageOne startApplication() {
        AgentHomePage home = new AgentHomePage(driver);
        home.assertLoaded();

        return home.clickNewApplication();
    }
}
