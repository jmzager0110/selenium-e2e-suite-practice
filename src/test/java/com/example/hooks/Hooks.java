package com.example.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.example.support.DriverFactory;
import com.example.support.TestContext;

public class Hooks {

    private final TestContext context;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before
    public void startBrowser() {
        context.setDriver(DriverFactory.create());
    }

    @After
    public void closeBrowser(Scenario scenario) {
        // The proposal ID is the result of the run, so put it in the report as well as the console.
        if (context.getProposalId() != null) {
            scenario.log("Proposal ID: " + context.getProposalId());
        }
        //intakeFormId
        if (context.getIntakeFormId() != null) {
            scenario.log("Proposal ID: " + context.getIntakeFormId());
        }

        WebDriver driver = context.getDriver();
        if (driver == null) {
            return;
        }
        try {
            if (scenario.isFailed()) {
                byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(png, "image/png", "Failure screenshot");
            }
        } finally {
            // quit() (not close()) ends the session and shuts down the driver process,
            // and it is safe even if the "Click done" step already closed the window.
            driver.quit();
        }
    }

    
}
