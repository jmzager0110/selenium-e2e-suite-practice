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
## TODO
Each variant's base url has it's own varaible in the pom file:
```
${portal.base.url}
${intake.base.url}
```
For local testing, these can be updated in the associated config.properties files. But these should be added to a gitignor'ed file, like a .env file in each project's resources directory, then variablized in the configs.

Add a .env file to your local for credentials in resources/portalUsers

Set credentials with environment variables rather than editing the file:
`CLIENT_USERNAME`, `CLIENT_PASSWORD`, `AGENT_USERNAME`, `AGENT_PASSWORD`, `BASE_URL`.
Any config key can also be passed as `-Dkey=value`.

Failure screenshots land in `target/screenshots/`. The proposal id is logged and stored on the test result
(`proposalId` attribute). But do you want screenshots?

## Notes
For validating projects initially, I used some practice sites:
- PortalUsers, [Just simple auth testing site](https://courses.ultimateqa.com/users/sign_in)
  I couldn't easily find a test site that had log in plus a simple form page for testing & didn't want to wreck the structure of this skeleton
- Intake, [Text Input Test Site](https://testpages.eviltester.com/pages/input-elements/text-inputs/)

