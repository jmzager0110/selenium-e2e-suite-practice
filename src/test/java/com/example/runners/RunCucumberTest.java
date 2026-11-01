package com.example.runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.FILTER_TAGS_PROPERTY_NAME;

/**
 * The one and only runner. It has no test logic; it just tells JUnit 5 to start Cucumber.
 * Feature files are discovered under src/test/resources/features.
 * Glue (steps + hooks) is set in junit-platform.properties for intake
 * Glue (steps + hooks) and the default tag filter are set in junit-platform.properties for v2 portal
 *
 * Run a subset by tag:  mvn test -Dcucumber.filter.tags="@client"
 *
 * Run a subset by tag:  mvn test -Dcucumber.filter.tags="@smoke"
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "not @wip")
public class RunCucumberTest {
}
