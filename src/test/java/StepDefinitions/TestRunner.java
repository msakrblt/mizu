package StepDefinitions;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources/Features"} ,glue={"StepDefinitions"},
        monochrome = true,
        plugin = {"html:target/cucumber-report.html","json:target/cucumber-reports/cucumber.json","junit:target/cucumber-reports/cucumber.xml"

        },

        tags="@musa"
)
public class TestRunner {

}
