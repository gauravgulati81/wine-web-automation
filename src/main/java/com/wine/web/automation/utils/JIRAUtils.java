package com.wine.web.automation.utils;

import com.wine.web.automation.config.ConfigProperties;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class JIRAUtils {
    static ConfigProperties configProperties = new ConfigProperties();

    public void jiraFlow(String jiraSummary, String jiraDescription) {
        HashMap<String, String> jiraTicketInfo = jiraTicketExists(jiraSummary);
        if (jiraTicketInfo.get("exists").equals("true")) {
            CommonUtils.existingJiraTickets = CommonUtils.existingJiraTickets + jiraTicketInfo.get("ticketID") + "\t";
        } else {
            String jiraTicketID = createIssueInJira(jiraSummary, jiraDescription);
            CommonUtils.newJiraTicketsCreated = CommonUtils.newJiraTicketsCreated + jiraTicketID + "\t";
        }
    }

    private String createIssueInJira(String jiraSummary, String jiraDescription) {
        String jiraCreateIssueApiUrl = Constants.jiraProjectUrl+Constants.jiraCreateIssueApiBasePath;
        String payload = updateBaseJiraPayload(jiraSummary, jiraDescription);
        Response response = given().contentType("application/json").header("Authorization", "Basic " + CommonUtils.encodeToBase64(Constants.jiraLoginEmail + ":" + Constants.jiraApiToken)).body(payload).post(jiraCreateIssueApiUrl);
        return response.getBody().jsonPath().get("key");
    }

    private HashMap<String,String> jiraTicketExists(String jiraSummary) {
        HashMap ticketInfo = new HashMap<String, String>();
        String jiraSearchApiUrl = Constants.jiraProjectUrl+Constants.jiraSearchApiBasePath+"?jql=summary~\"\\\""+jiraSummary+"\\\"\""+" AND project = "+Constants.jiraIssuePrefix+" AND type = Bug AND status NOT IN (Done, Closed)";
        Response response = given().header("Authorization", "Basic " + CommonUtils.encodeToBase64(Constants.jiraLoginEmail + ":" + Constants.jiraApiToken)).get(jiraSearchApiUrl);
        if((int)response.getBody().jsonPath().get("total")>0){
            System.out.println("ticket exists!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            ticketInfo.put("exists", "true");
            ticketInfo.put("ticketID", response.getBody().jsonPath().get("issues[0].key"));
            ticketInfo.put("ticketStatus", response.getBody().jsonPath().get("issues[0].fields.status.name"));
        } else {
            System.out.println("ticket does not exists!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            ticketInfo.put("exists", "false");
            ticketInfo.put("ticketID", "");
            ticketInfo.put("ticketStatus", "");
        }
        return ticketInfo;
    }

    public String updateBaseJiraPayload(String jiraSummary, String jiraDescription) {
        JSONParser parser = new JSONParser();
        JSONObject payloadObject = null;
        try {
            payloadObject = (JSONObject)parser.parse(new FileReader("BaseJIRAPayload.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        ((JSONObject) payloadObject.get("fields")).put("summary",jiraSummary);
        ((JSONObject) ((JSONArray) ((JSONObject) ((JSONArray) ((JSONObject) ((JSONObject) payloadObject.get("fields")).get("description")).get("content")).get(0)).get("content")).get(0)).put("text", jiraDescription);
        return String.valueOf(payloadObject);
    }
}
