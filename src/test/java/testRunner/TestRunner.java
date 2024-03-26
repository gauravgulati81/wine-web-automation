package testRunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/Features/SerialRunTests",
        glue = {"StepDefinitions"},
        plugin = {"pretty", "html:target/reports/cucumber.html",
                "json:target/reports/cucumber.json",
                "rerun:target/failedTests.txt"})
public class TestRunner {

//    @AfterClass   //useful only when execution is triggered from TestRunner or command line. Does not work when execution is started from feature files / scenarios.
//    public static void tearDown() {
//        BaseDriver.getDriver().quit();
//    }
}
