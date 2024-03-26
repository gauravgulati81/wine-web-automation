package com.wine.web.automation.utils;

import com.wine.web.automation.config.ConfigProperties;
import io.cucumber.core.backend.TestCaseState;
import io.cucumber.java.Scenario;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.TestCase;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class CommonUtils {

    static ConfigProperties configProperties = new ConfigProperties();
    static String urlUnderTest;
    public static int passedCount = 0;
    public static int failedCount = 0;
    public static int othersCount = 0;
    public static int totalScenarios = 0;
    public static String newJiraTicketsCreated = "";
    public static String existingJiraTickets = "";

    public static String getUrlUnderTest(){
        switch (configProperties.getBuildEnv()){
            case ("preprod"):
                urlUnderTest = Constants.preprod_url;
                break;
            case ("prod"):
                urlUnderTest = Constants.prod_url;
                break;
            default:
                urlUnderTest = Constants.uat_url;
        }
        return urlUnderTest;
    }

    public static void sleep(int sleepTime){
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getExecutionBranchName(){
        if (System.getenv("CIRCLE_BRANCH") == null){
            return "local";
        } else {
            return System.getenv("CIRCLE_BRANCH");
        }
    }
    public static String getCircleCIBuildUrl(){
        if (System.getenv("CIRCLE_BUILD_URL") == null){
            return "NA";
        } else {
            return System.getenv("CIRCLE_BUILD_URL");
        }
    }
    public static String getCircleCIJobName(){
        if (System.getenv("CIRCLE_JOB") == null){
            return "local web test";
        } else {
            return System.getenv("CIRCLE_JOB");
        }
    }

    public static String getStepsFromScenario(Scenario scenario) throws NoSuchFieldException, IllegalAccessException {
        Field delegate = scenario.getClass().getDeclaredField("delegate");
        delegate.setAccessible(true);
        TestCaseState testCaseState = (TestCaseState) delegate.get(scenario);
        Field testCaseField  = testCaseState.getClass().getDeclaredField("testCase");
        testCaseField .setAccessible(true);
        TestCase testCase  = (TestCase) testCaseField.get(testCaseState);

        List<PickleStepTestStep> testStepTitles  = testCase.getTestSteps()
                .stream()
                .filter(x -> x instanceof PickleStepTestStep)
                .map(x -> (PickleStepTestStep) x)
                .collect(Collectors.toList());

        String allSteps = "";
        for (PickleStepTestStep step : testStepTitles){
            allSteps = allSteps + step.getStep().getText() + "\n";
        }
        return allSteps;
    }

    public static String getErrorStackTraceFromFailedScenario(Scenario scenario) throws NoSuchFieldException, IllegalAccessException {
        Result failResult = null;
        Field delegate = scenario.getClass().getDeclaredField("delegate");
        delegate.setAccessible(true);
        TestCaseState testCaseState = (TestCaseState) delegate.get(scenario);
        Field stepResults = testCaseState.getClass().getDeclaredField("stepResults");
        stepResults.setAccessible(true);
        List<Result> results = (List<Result>) stepResults.get(testCaseState);
        for(Result result : results) {
            if (result.getStatus().name().equalsIgnoreCase("FAILED")) {
                failResult = result;
            }
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        failResult.getError().printStackTrace(pw);
        return sw.toString();
    }

    public static String encodeToBase64(String s){
        return Base64.getEncoder().encodeToString(s.getBytes());
    }

    public static String getJiraSummary(String scenarioTitle){
        return "Web|"+configProperties.getBrowserName()+"|"+scenarioTitle;
    }

    public static String getJiraDescription(Scenario scenario) throws NoSuchFieldException, IllegalAccessException {
        return "Steps:\n\n"+getStepsFromScenario(scenario)+"\n\n\n\n"+"Error:\n\n"+getErrorStackTraceFromFailedScenario(scenario);
    }
}
