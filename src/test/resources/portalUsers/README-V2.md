# Proposal tests (Selenium + Cucumber + JUnit 5)

Same structure and conventions as `intake-tests`. Covers the client and agent proposal flows end to end,
with a validation on every page.

## Layout

```
pom.xml
src/test/java/com/example/proposal/
  runners/RunCucumberTest.java   # the only runner; starts Cucumber via JUnit 5
  hooks/Hooks.java               # @Before start browser, @After screenshot-on-fail + proposal ID in report + quit
  steps/ProposalSteps.java       # Gherkin -> page-object calls, no locators
  pages/BasePage.java            # waits, click/type/select helpers, validation helpers, abstract assertLoaded()
  pages/LoginPage.java           # shared by both variants
  pages/ClientHomePage.java      # client: Accounts
  pages/AccountsPage.java        # client: first result
  pages/AccountDetailPage.java   # client: Add Locations
  pages/AgentHomePage.java       # agent: New Application
  pages/ApplicationPageOne.java  # both variants land here
  pages/ApplicationPageTwo.java  # fields, locations list, Add Location, Continue
  pages/LocationFrame.java       # the "Add Location" iframe (handles switchTo in/out)
  pages/SelectionPage.java       # the selection after the first Continue
  pages/PaymentOptionsPage.java  # explicit selection OR accept the default
  pages/SubmitPage.java
  pages/ConfirmationPage.java    # reads and validates the proposal ID
  models/LocationData.java       # test data for one location (built from a Gherkin table row)
  models/Role.java               # client | agent
  support/{Config,DriverFactory,TestContext}.java
src/test/resources/
  features/proposal.feature      # one Scenario Outline, run for client and agent
  config.properties              # base.url, browser, headless, wait, credentials
  junit-platform.properties      # Cucumber plugins + glue package + default tag filter
```

## How validation works
- Every page implements `assertLoaded()` (URL fragment, header, key elements). Each navigation method
  returns the destination page through `arriveAt(...)`, which runs that check.
- Fill methods re-read what they typed or selected before moving on.
- After each location is saved the locations list must show the expected row count and the new name.
- The payment step either selects an option or asserts that a default is preselected.
- The proposal ID must be non-blank and match `PROPOSAL_ID_FORMAT` in `ConfirmationPage`.

## To make it real
Search the project for `TODO` and replace: `base.url`, credentials, every `By` locator, URL fragment and header
text, the iframe locator, the fields in `LocationData`, and the values in the feature file.

## Run
```
mvn test
mvn test -Dbase.url=https://your-app/login -Dbrowser=firefox -Dheadless=true
mvn test -Dcucumber.filter.tags="@client"     # or @agent
```
Credentials: put them in `config.properties`, pass `-Dclient.password=...`, or set environment variables
(`CLIENT_USERNAME`, `CLIENT_PASSWORD`, `AGENT_USERNAME`, `AGENT_PASSWORD`).

Report: `target/cucumber-report.html`. The proposal ID is printed to the console and added to the report.
