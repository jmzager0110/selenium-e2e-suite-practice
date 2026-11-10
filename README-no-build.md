# Running the suite without Maven

Needs only the JDK and a folder of jars. Put `run-tests.sh` in the project root, next to `src/`.

## One-time setup

1. **Jar folder.** Put every jar in one folder, `lib/` in the project root by default, or anywhere with
   `export LIB_DIR=/c/path/to/jars`. It must hold the jars below AND everything they depend on
   (Selenium alone needs a few dozen: selenium-http, selenium-json, selenium-os, selenium-manager, guava,
   byte-buddy, netty, opentelemetry, ...). The Selenium download zip's `lib` folder plus the Cucumber and JUnit
   jars covers it.

   | Needed directly (checked by the script) | Used for |
   |---|---|
   | selenium-api, selenium-remote-driver, selenium-support, selenium-chrome-driver | browser control |
   | cucumber-java, cucumber-core, cucumber-picocontainer, picocontainer | Gherkin, steps, shared TestContext |
   | junit-jupiter-api, junit-platform-commons, opentest4j | the `assertEquals/assertTrue` calls in the pages |

   Also add `selenium-firefox-driver` and `selenium-edge-driver` if you run those browsers. If you ever see
   `NoClassDefFoundError: some.package.Class`, that class's jar is missing: find it and drop it in the folder.

2. **JDK.** The script uses `JAVA_HOME` if set, then the IT-deployed Temurin
   (`C:\Program Files\Eclipse Adoptium\jdk-25.0.5.8-hotspot`), then whatever is on PATH.

3. **Browser drivers.** Selenium Manager tries to download a driver and may be blocked by your network. IT said
   drivers were deployed with the JDK. Ask where, then point Selenium at them:
   ```
   ./run-tests.sh -t "@intake" -Dwebdriver.chrome.driver="C:/path/to/chromedriver.exe"
   ```
   (`-Dwebdriver.edge.driver=...` and `-Dwebdriver.gecko.driver=...` for the others.)

## Run

```
./run-tests.sh                                    # everything except @wip
./run-tests.sh -t "@intake"                       # intake only
./run-tests.sh -t "@proposal and @agent"          # one variant
./run-tests.sh -t "@intake" -Dheadless=true       # any -Dkey=value goes to the JVM
./run-tests.sh --compile-only                     # check it compiles
./run-tests.sh --dry-run                          # print the commands without running
```

Run it from Git Bash (the terminal you already use in VS Code). The report is still written to
`target/cucumber-report.html`. The exit code is non-zero when a scenario fails.

## What changed from the Maven setup

- `RunCucumberTest` (the JUnit runner) is not compiled or used. Cucumber's own command-line entry point runs the
  features instead, so you don't need `junit-platform-suite` or the Cucumber JUnit engine jars.
- `junit-platform.properties` is not read. Its settings are now in the script: glue `com.example`, the reports,
  and the default tag filter `not @wip` (change it with `-t`).
- Everything else is unchanged: the same sources, `config.properties`, features, `-D` flags and environment variables.

## VS Code

With no Maven project, VS Code needs to be told where the sources and jars are. Add these to the existing
`.vscode/settings.json`:

```json
{
  "java.project.sourcePaths": ["src/test/java"],
  "java.project.outputPath": "build/classes",
  "java.project.referencedLibraries": ["lib/**/*.jar"]
}
```

If `pom.xml` is still in the folder, VS Code will keep treating it as a Maven project and ignore those settings
(and complain that Maven is missing). Rename it (for example `pom.xml.reference`) so it is treated as a plain
Java folder, then run "Java: Clean Java Language Server Workspace". Add `build/` to `.gitignore`.
