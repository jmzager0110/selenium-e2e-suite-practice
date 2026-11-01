package com.example.portalUsers.steps;

import com.example.portalUsers.models.LocationData;
import com.example.portalUsers.models.Role;
import com.example.portalUsers.pages.AgentHomePage;
import com.example.portalUsers.pages.ApplicationPageOne;
import com.example.portalUsers.pages.ApplicationPageTwo;
import com.example.portalUsers.pages.ClientHomePage;
import com.example.portalUsers.pages.ConfirmationPage;
import com.example.portalUsers.pages.LocationFrame;
import com.example.portalUsers.pages.LoginPage;
import com.example.portalUsers.pages.PaymentOptionsPage;
import com.example.portalUsers.pages.SelectionPage;
import com.example.portalUsers.pages.SubmitPage;
import com.example.portalUsers.support.Config;
import com.example.portalUsers.support.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Step definitions stay thin: they translate Gherkin into page-object calls and hold no locators.
 * Page objects are built lazily from the scenario's driver so each scenario gets fresh ones.
 * Every page object validates itself on arrival, so the steps do not repeat those checks.
 */
public class ProposalSteps {

    private final TestContext context;

    public ProposalSteps(TestContext context) {
        this.context = context;
    }

    private WebDriver driver() {
        return context.getDriver();
    }

    // ------------------------------------------------------------------ login and entry (role-specific)

    @Given("I open the application and log in as a {string}")
    public void iOpenTheApplicationAndLogInAs(String roleName) {
        Role role = Role.from(roleName);
        context.setRole(role);

        String prefix = role.configPrefix();
        new LoginPage(driver())
                .open(Config.require("base.url"))
                .login(Config.require(prefix + ".username"), Config.require(prefix + ".password"));
    }

    @When("I start a new application")
    public void iStartANewApplication() {
        switch (context.getRole()) {
            case CLIENT -> {
                ClientHomePage home = new ClientHomePage(driver());
                home.assertLoaded();
                home.clickAccounts().clickFirstResult().clickAddLocations();
            }
            case AGENT -> {
                AgentHomePage home = new AgentHomePage(driver());
                home.assertLoaded();
                home.clickNewApplication();
            }
        }
    }

    // ------------------------------------------------------------------ shared flow

    @When("I complete page one with applicant name {string}, contact email {string} and phone {string}")
    public void iCompletePageOne(String applicantName, String contactEmail, String phone) {
        new ApplicationPageOne(driver()).fill(applicantName, contactEmail, phone);
    }

    @When("I continue to page two")
    public void iContinueToPageTwo() {
        new ApplicationPageOne(driver()).clickNext();
    }

    @When("I complete page two with effective date {string}, industry {string} and description {string}")
    public void iCompletePageTwo(String effectiveDate, String industry, String description) {
        new ApplicationPageTwo(driver()).fill(effectiveDate, industry, description);
    }

    @When("I add the following locations:")
    public void iAddTheFollowingLocations(DataTable table) {
        List<Map<String, String>> rows = table.asMaps();
        ApplicationPageTwo pageTwo = new ApplicationPageTwo(driver());

        for (int i = 0; i < rows.size(); i++) {
            LocationData location = LocationData.fromRow(rows.get(i));

            pageTwo.clickAddLocation();
            new LocationFrame(driver()).addLocation(location);
            pageTwo.assertLocationListed(i + 1, location.name());
        }
    }

    @When("I continue to the selection page")
    public void iContinueToTheSelectionPage() {
        new ApplicationPageTwo(driver()).clickContinue();
    }

    @When("I choose the selection {string}")
    public void iChooseTheSelection(String selection) {
        new SelectionPage(driver()).select(selection);
    }

    @When("I continue to the payment options page")
    public void iContinueToThePaymentOptionsPage() {
        new SelectionPage(driver()).clickContinue();
    }

    @When("I accept the default payment option")
    public void iAcceptTheDefaultPaymentOption() {
        String used = new PaymentOptionsPage(driver()).acceptDefault();
        System.out.println("Payment option used (default): " + used);
    }

    @When("I select the payment option {string}")
    public void iSelectThePaymentOption(String option) {
        new PaymentOptionsPage(driver()).select(option);
        System.out.println("Payment option used: " + option);
    }

    @When("I continue to the submit page")
    public void iContinueToTheSubmitPage() {
        new PaymentOptionsPage(driver()).clickContinue();
    }

    @When("I submit the proposal")
    public void iSubmitTheProposal() {
        new SubmitPage(driver()).clickSubmit();
    }

    @Then("I should see a proposal ID")
    public void iShouldSeeAProposalId() {
        String id = new ConfirmationPage(driver()).getProposalId();
        assertFalse(id.isBlank(), "Proposal ID was blank");
        context.setProposalId(id);
        System.out.println("Proposal ID: " + id);
    }
}
