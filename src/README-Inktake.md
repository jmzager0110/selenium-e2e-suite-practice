# Intake form tests (Selenium + Cucumber + JUnit 5)

## Layout

```
pom.xml
src/test/java/com/example/intake/
  runners/RunCucumberTest.java   # the only runner; starts Cucumber via JUnit 5
  hooks/Hooks.java               # @Before start browser, @After screenshot-on-fail + quit
  steps/IntakeSteps.java         # Gherkin -> page-object calls, no locators
  pages/BasePage.java            # waits, click/type/select helpers
  pages/IntakeFormPage.java      # main page
  pages/LocationFrame.java       # the "Add Location" iframe (handles switchTo in/out)
  pages/ConfirmationPage.java    # intake form ID + Done
  models/LocationData.java       # test data for one location
  support/{Config,DriverFactory,TestContext}.java
src/test/resources/
  features/intake_form.feature   # Gherkin lives here
  config.properties              # base.url, browser, headless, wait
  junit-platform.properties      # Cucumber plugins + glue package
```

## To make it real
Search the project for `TODO` and replace: `base.url`, every `By` locator, the iframe locator,
the fields in `LocationData`, and the values in the feature file.

## Run
```
mvn test
mvn test -Dbase.url=https://your-app/intake -Dbrowser=firefox -Dheadless=true
mvn test -Dcucumber.filter.tags="@smoke"
```
Report: `target/cucumber-report.html`
