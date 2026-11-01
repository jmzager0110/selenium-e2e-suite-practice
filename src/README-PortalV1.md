# Selenium E2E suite (Java, Maven, TestNG)

End-to-end proposal flow for two variants (client and agent). Every page object validates itself on arrival
(`assertLoaded()`), and filled values are re-checked before moving on.

## Run

```bash
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
