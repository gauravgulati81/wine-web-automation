package com.wine.web.automation.utils;

import com.wine.web.automation.config.ConfigProperties;

import static io.restassured.RestAssured.given;


public class SlackMessageAPI
{
    public static String slackMessage;
    static ConfigProperties configProperties = new ConfigProperties();
    
    public static void slackNotification()
    {
        if (CommonUtils.existingJiraTickets.equals("")){
            if (configProperties.getJiraReportingFlag().equalsIgnoreCase("true")) {
                CommonUtils.existingJiraTickets = "0";
            } else {
                CommonUtils.existingJiraTickets = "NA (Logging in JIRA is disabled)";
            }
        }
        if (CommonUtils.newJiraTicketsCreated.equals("")){
            if (configProperties.getJiraReportingFlag().equalsIgnoreCase("true")) {
                CommonUtils.newJiraTicketsCreated = "0";
            } else {
                CommonUtils.newJiraTicketsCreated = "NA (Logging in JIRA is disabled)";
            }
        }

        switch (configProperties.getRerunFlag()){
            case "1":
                slackMessage = "{\n" +
                        "  \"blocks\": [\n" +
                        "    {\n" +
                        "      \"type\": \"section\",\n" +
                        "      \"text\": {\n" +
                        "        \"type\": \"mrkdwn\",\n" +
                        "        \"text\": \"*Rerun of failed scenarios completed*\n\n \"" +
                        "      }\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"type\": \"section\",\n" +
                        "      \"text\": {\n" +
                        "        \"type\": \"mrkdwn\",\n" +
                        "        \"text\": \"*Branch :* " +
                        CommonUtils.getExecutionBranchName() +
                        "\"" +
                        "      }\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"type\": \"section\",\n" +
                        "      \"block_id\": \"section568\",\n" +
                        "      \"text\": {\n" +
                        "        \"type\": \"mrkdwn\",\n" +
                        "        \"text\": \"*Build Env. is:* " +
                        configProperties.getBuildEnv() +
//                "\"" +
//                "      }\n" +
//                "    },\n" +
//                "     {\n" +
//                "      \"type\": \"section\",\n" +
//                "      \"block_id\": \"section569\",\n" +
//                "      \"text\": {\n" +
//                "        \"type\": \"mrkdwn\",\n" +
//                "        \"text\": \"*Device Name:* " +
//                deviceName +
                        "\"" +
                        "      }\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"type\": \"section\",\n" +
                        "      \"block_id\": \"section570\",\n" +
                        "      \"text\": {\n" +
                        "        \"type\": \"mrkdwn\",\n" +
                        "        \"text\": \"*Platform Name is :* " +
                        "Web" +
//                "\"" +
//                "      }\n" +
//                "    },\n" +
//                "     {\n" +
//                "      \"type\": \"section\",\n" +
//                "      \"block_id\": \"section571\",\n" +
//                "      \"text\": {\n" +
//                "        \"type\": \"mrkdwn\",\n" +
//                "        \"text\": \"*OS Version:* " +
//                osVersion +
                        "\"" +
                        "      }\n" +
                        "    },\n" +
                        "     {\n" +
                        "      \"type\": \"section\",\n" +
                        "      \"block_id\": \"section572\",\n" +
                        "      \"text\": {\n" +
                        "        \"type\": \"mrkdwn\",\n" +
                        "        \"text\": \"*CircleCI Link:* " +
                        CommonUtils.getCircleCIBuildUrl() +
                        "\"" +
                        "      }\n" +
                        "    },\n" +
                        "     {\n" +
                        "      \"type\": \"section\",\n" +
                        "      \"block_id\": \"section573\",\n" +
                        "      \"text\": {\n" +
                        "        \"type\": \"mrkdwn\",\n" +
                        "        \"text\": \"*Test Suite:* " +
                        CommonUtils.getCircleCIJobName() +
//                "\"" +
//                "      }\n" +
//                "    },\n" +
//                "     {\n" +
//                "      \"type\": \"section\",\n" +
//                "      \"block_id\": \"section574\",\n" +
//                "      \"text\": {\n" +
//                "        \"type\": \"mrkdwn\",\n" +
//                "        \"text\": \"*Start Time:* " +
//                startTime +
                        "\"" +
                        "      }\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"type\": \"section\",\n" +
                        "      \"block_id\": \"section575\",\n" +
                        "      \"text\": {\n" +
                        "        \"type\": \"mrkdwn\",\n" +
                        "        \"text\": \"*Total Scenarios:* " +
                        CommonUtils.totalScenarios + ", Passed: " + CommonUtils.passedCount + ", Failed: " + CommonUtils.failedCount + ", Others: " + CommonUtils.othersCount +
                        "\"" +
                        "      }\n" +
                        "    },\n" +
                        "     {\n" +
                        "      \"type\": \"section\",\n" +
                        "      \"block_id\": \"section576\",\n" +
                        "      \"text\": {\n" +
                        "        \"type\": \"mrkdwn\",\n" +
                        "        \"text\": \"*New JIRA tickets created for failed scenario(s):* " +
                        CommonUtils.newJiraTicketsCreated +
                        "\"" +
                        "      }\n" +
                        "    },\n" +
                        "     {\n" +
                        "      \"type\": \"section\",\n" +
                        "      \"block_id\": \"section577\",\n" +
                        "      \"text\": {\n" +
                        "        \"type\": \"mrkdwn\",\n" +
                        "        \"text\": \"*Existing JIRA tickets for failed scenario(s):* " +
                        CommonUtils.existingJiraTickets +
//                "\"" +
//                "      }\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"type\": \"section\",\n" +
//                "      \"block_id\": \"section578\",\n" +
//                "      \"text\": {\n" +
//                "        \"type\": \"mrkdwn\",\n" +
//                "        \"text\": \"*Duration of test:* " +
//                duration +
                        "\"" +
                        "      }\n" +
                        "    },\n" +
                        "     {\n" +
                        "      \"type\": \"section\",\n" +
                        "      \"block_id\": \"section579\",\n" +
                        "      \"text\": {\n" +
                        "        \"type\": \"mrkdwn\",\n" +
                        "        \"text\": \"***************************************************************************************" +
                        "\"" +
                        "      }\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}";
                break;
            default:
                slackMessage = "{\n" +
                        "  \"blocks\": [\n" +
                        "    {\n" +
                        "      \"type\": \"section\",\n" +
                        "      \"text\": {\n" +
                        "        \"type\": \"mrkdwn\",\n" +
                        "        \"text\": \"*Test execution completed*\n\n \"" +
                        "      }\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"type\": \"section\",\n" +
                        "      \"text\": {\n" +
                        "        \"type\": \"mrkdwn\",\n" +
                        "        \"text\": \"*Branch :* " +
                        CommonUtils.getExecutionBranchName() +
                        "\"" +
                        "      }\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"type\": \"section\",\n" +
                        "      \"block_id\": \"section568\",\n" +
                        "      \"text\": {\n" +
                        "        \"type\": \"mrkdwn\",\n" +
                        "        \"text\": \"*Build Env. is:* " +
                        configProperties.getBuildEnv() +
//                "\"" +
//                "      }\n" +
//                "    },\n" +
//                "     {\n" +
//                "      \"type\": \"section\",\n" +
//                "      \"block_id\": \"section569\",\n" +
//                "      \"text\": {\n" +
//                "        \"type\": \"mrkdwn\",\n" +
//                "        \"text\": \"*Device Name:* " +
//                deviceName +
                        "\"" +
                        "      }\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"type\": \"section\",\n" +
                        "      \"block_id\": \"section570\",\n" +
                        "      \"text\": {\n" +
                        "        \"type\": \"mrkdwn\",\n" +
                        "        \"text\": \"*Platform Name is :* " +
                        "Web" +
//                "\"" +
//                "      }\n" +
//                "    },\n" +
//                "     {\n" +
//                "      \"type\": \"section\",\n" +
//                "      \"block_id\": \"section571\",\n" +
//                "      \"text\": {\n" +
//                "        \"type\": \"mrkdwn\",\n" +
//                "        \"text\": \"*OS Version:* " +
//                osVersion +
                        "\"" +
                        "      }\n" +
                        "    },\n" +
                        "     {\n" +
                        "      \"type\": \"section\",\n" +
                        "      \"block_id\": \"section572\",\n" +
                        "      \"text\": {\n" +
                        "        \"type\": \"mrkdwn\",\n" +
                        "        \"text\": \"*CircleCI Link:* " +
                        CommonUtils.getCircleCIBuildUrl() +
                        "\"" +
                        "      }\n" +
                        "    },\n" +
                        "     {\n" +
                        "      \"type\": \"section\",\n" +
                        "      \"block_id\": \"section573\",\n" +
                        "      \"text\": {\n" +
                        "        \"type\": \"mrkdwn\",\n" +
                        "        \"text\": \"*Test Suite:* " +
                        CommonUtils.getCircleCIJobName() +
//                "\"" +
//                "      }\n" +
//                "    },\n" +
//                "     {\n" +
//                "      \"type\": \"section\",\n" +
//                "      \"block_id\": \"section574\",\n" +
//                "      \"text\": {\n" +
//                "        \"type\": \"mrkdwn\",\n" +
//                "        \"text\": \"*Start Time:* " +
//                startTime +
                        "\"" +
                        "      }\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"type\": \"section\",\n" +
                        "      \"block_id\": \"section575\",\n" +
                        "      \"text\": {\n" +
                        "        \"type\": \"mrkdwn\",\n" +
                        "        \"text\": \"*Total Scenarios:* " +
                        CommonUtils.totalScenarios + ", Passed: " + CommonUtils.passedCount + ", Failed: " + CommonUtils.failedCount + ", Others: " + CommonUtils.othersCount +
//                        "\"" +
//                        "      }\n" +
//                        "    },\n" +
//                        "     {\n" +
//                        "      \"type\": \"section\",\n" +
//                        "      \"block_id\": \"section576\",\n" +
//                        "      \"text\": {\n" +
//                        "        \"type\": \"mrkdwn\",\n" +
//                        "        \"text\": \"*New JIRA tickets created for failed scenario(s):* " +
//                        CommonUtils.newJiraTicketsCreated +
//                        "\"" +
//                        "      }\n" +
//                        "    },\n" +
//                        "     {\n" +
//                        "      \"type\": \"section\",\n" +
//                        "      \"block_id\": \"section577\",\n" +
//                        "      \"text\": {\n" +
//                        "        \"type\": \"mrkdwn\",\n" +
//                        "        \"text\": \"*Existing JIRA tickets for failed scenario(s):* " +
//                        CommonUtils.existingJiraTickets +
//                "\"" +
//                "      }\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"type\": \"section\",\n" +
//                "      \"block_id\": \"section578\",\n" +
//                "      \"text\": {\n" +
//                "        \"type\": \"mrkdwn\",\n" +
//                "        \"text\": \"*Duration of test:* " +
//                duration +
                        "\"" +
                        "      }\n" +
                        "    },\n" +
                        "     {\n" +
                        "      \"type\": \"section\",\n" +
                        "      \"block_id\": \"section579\",\n" +
                        "      \"text\": {\n" +
                        "        \"type\": \"mrkdwn\",\n" +
                        "        \"text\": \"***************************************************************************************" +
                        "\"" +
                        "      }\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}";
                break;
        }

            given().contentType("application/json").body(slackMessage).when().post(Constants.slackWebHookUrl);
    }
}