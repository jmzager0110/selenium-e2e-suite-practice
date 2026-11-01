package com.example.portalUsers.runners;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * The one and only runner. It has no test logic; it just tells JUnit 5 to start Cucumber.
 * Feature files are discovered under src/test/resources/features.
 * Glue (steps + hooks) and the default tag filter are set in junit-platform.properties.
 *
 * Run a subset by tag:  mvn test -Dcucumber.filter.tags="@client"
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
public class RunCucumberTest {
}
