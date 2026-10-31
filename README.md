# Selenium E2E suite (Java, Maven, JUnit with TestNG Option)

End to end application flows for non-SSO users. This suite is organized into two dir naming conventions, "intake" & "portalUsers". The latter validates application flow for "client" & "agent" user types. These require standard authentication while the former does not require authentication.

## Preface
This was built to practice Selenium. The two levels of organization were fleshed out at different times. The "portalUsers" original skeleton used TestNG & did not use Cucumber/Gherkin feature files. To unify the two groups, a second version was scripted to align with "intake" which uses JUnit & Cucumber feature files.

In resources/{userTypeName}/ there are README files from when each was scripted, in portalUsers, there are two for each version.

For examples of TestNG without Cucumber feature files, see:
resources/portalUsers/README-V2
java/com/example/PortalUsers/xx-notInUse

These are written in Java 17 with Selenium 4.27.

## Run

```bash
mvn test-compile

mvn test                                   # both variants, config.properties defaults
mvn test -Dbrowser=firefox -Dheadless=true
mvn test -Dtest=ClientProposalE2ETest      # one variant
```

Set credentials with environment variables rather than editing the file:
`CLIENT_USERNAME`, `CLIENT_PASSWORD`, `AGENT_USERNAME`, `AGENT_PASSWORD`, `BASE_URL`.
Any config key can also be passed as `-Dkey=value`.

Failure screenshots land in `target/screenshots/`. The proposal id is logged and stored on the test result
(`proposalId` attribute).

## Layout

```
src/test/java/com/example/e2e/
  config/   TestConfig            property > env var > config.properties
  driver/   DriverFactory         per-thread driver (chrome | firefox | edge, optional headless)
  base/     BasePage              waits, actions, assertions; abstract assertLoaded()
            BaseTest              browser open/close, screenshot on failure
            BaseE2ETest           login -> entry flow -> shared flow -> proposal id
  pages/    one class per page, plus LocationFrame (the iframe)
  flows/    ClientEntryFlow, AgentEntryFlow (role-specific), ProposalFlow (shared)
  model/    ProposalData, TestDataFactory, Role, Credentials
  tests/    ClientProposalE2ETest, AgentProposalE2ETest
```

## Making it real

Search for `TODO` in `pages/`: each page has placeholder locators, URL fragments and header text to replace.
