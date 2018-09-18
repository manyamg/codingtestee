package io.ee.hotelbooking;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    tags = {"~@wip"},
    format = {"json:target/cucumber.json", "html:target/cucumber-html-report", "pretty"})
public class RunCuckesTest {
}
