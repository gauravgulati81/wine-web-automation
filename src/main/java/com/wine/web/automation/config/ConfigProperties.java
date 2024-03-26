package com.wine.web.automation.config;


import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {
    Properties properties = new Properties();
  public ConfigProperties() {
      try {
          properties.load(getClass().getClassLoader().getResourceAsStream("test.properties"));
      } catch (IOException e) {
          throw new RuntimeException(e);
      }
  }

  public String getBrowserName() {
    return properties.getProperty("browser_name");
  }

  public String getBuildEnv() {
        return properties.getProperty("build_env");
    }

    public String getParallelRunFlagValue() {
        return properties.getProperty("parallel_run");
    }

    public String getRerunFlag(){
        return properties.getProperty("rerun_flag");
    }

    public String getJiraReportingFlag(){
        return properties.getProperty("jira_reporting");
    }


}
