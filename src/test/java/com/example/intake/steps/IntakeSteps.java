package com.example.intake.steps;

import com.example.intake.models.LocationData;
import com.example.intake.pages.ConfirmationPage;
import com.example.intake.pages.IntakeFormPage;
import com.example.intake.pages.LocationFrame;
import com.example.intake.support.Config;
import com.example.intake.support.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Step definitions stay thin: they translate Gherkin into page-object calls and hold no locators.
 * Page objects are built lazily from the scenario's driver so each scenario gets fresh ones.
 */
public class IntakeSteps {

    private final TestContext context;

    public IntakeSteps(TestContext context) {
        this.context = context;
    }

    private IntakeFormPage intakeForm() {
        return new IntakeFormPage(context.getDriver());
    }

    private LocationFrame locationFrame() {
        return new LocationFrame(context.getDriver());
    }

    private ConfirmationPage confirmation() {
        return new ConfirmationPage(context.getDriver());
    }

    @Given("I open the intake form")
    public void iOpenTheIntakeForm() {
        intakeForm().open(Config.get("base.url"));
    }

    @Given("I complete the primary fields with first name {string}, last name {string} and request type {string}")
    public void iCompleteThePrimaryFields(String firstName, String lastName, String requestType) {
        intakeForm().fillPrimaryFields(firstName, lastName, requestType);
    }

    @When("I add a location named {string} of type {string} in category {string}")
    public void iAddALocation(String name, String type, String category) {
        intakeForm().clickAddLocation();
        locationFrame().addLocation(new LocationData(name, type, category));
    }

    @When("I save the changes")
    public void iSaveTheChanges() {
        intakeForm().clickSaveChanges();
    }

    @Then("I should see an intake form ID")
    public void iShouldSeeAnIntakeFormId() {
        String id = confirmation().getIntakeFormId();
        assertFalse(id.isBlank(), "Intake form ID was blank");
        context.setIntakeFormId(id);
        System.out.println("Intake form ID: " + id);
    }

    @When("I click done")
    public void iClickDone() {
        confirmation().clickDone();
    }
}
