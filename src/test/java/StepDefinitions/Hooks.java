package StepDefinitions;

import com.wine.web.automation.config.ConfigProperties;
import com.wine.web.automation.config.DriverFactory;
import com.wine.web.automation.utils.CommonUtils;
import com.wine.web.automation.utils.JIRAUtils;
import com.wine.web.automation.utils.SlackMessageAPI;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Scenario;

public class Hooks {
    static ConfigProperties configProperties = new ConfigProperties();
    static JIRAUtils jiraUtils = new JIRAUtils();

    @After(order = 1)
    public void closeBrowser(){
        if (configProperties.getParallelRunFlagValue().equalsIgnoreCase("true")) {
            DriverFactory.quitDriver();
        }
    }

    @After(order = 2)
    public static void countScenarios(Scenario scenario){
        switch (scenario.getStatus().name()){
            case "PASSED":
                CommonUtils.passedCount++;
                break;
            case "FAILED":
                CommonUtils.failedCount++;
                break;
            default:
                CommonUtils.othersCount++;
                break;
        }
        CommonUtils.totalScenarios++;
    }

    @After(order = 3)
    public void JiraLogging(Scenario scenario) throws NoSuchFieldException, IllegalAccessException {
        if (scenario.isFailed() && configProperties.getJiraReportingFlag().equalsIgnoreCase("true")){
            jiraUtils.jiraFlow(CommonUtils.getJiraSummary(scenario.getName()), CommonUtils.getJiraDescription(scenario));
        }
    }

    @AfterAll
    public static void teardown(){
        if (!configProperties.getParallelRunFlagValue().equalsIgnoreCase("true")) {
            DriverFactory.quitDriver();
        }
        SlackMessageAPI.slackNotification();
    }

}
