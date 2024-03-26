package com.wine.web.automation.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
    public static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    public static WebDriver driver;
    static ConfigProperties configProperties = new ConfigProperties();
    public static WebDriver getDriver() {
        if (configProperties.getParallelRunFlagValue().equalsIgnoreCase("true")) {
                switch (configProperties.getBrowserName()) {
                    case "firefox":
                        driverThread.set(new FirefoxDriver());
                        WebDriverManager.firefoxdriver().setup();
                        break;
                    default:
                        driverThread.set(new ChromeDriver());
                        WebDriverManager.chromedriver().setup();
//                ChromeOptions options = new ChromeOptions();
//                options.addArguments("--disable-notifications");
//                driverThread.set(new ChromeDriver(options));
                        break;
                }
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            return driverThread.get();
        } else {
            if (driver==null) {
                switch (configProperties.getBrowserName()) {
                    case "firefox":
                        driver = new FirefoxDriver();
                        WebDriverManager.firefoxdriver().setup();
                        break;
                    default:
                        driver = new ChromeDriver();
                        WebDriverManager.chromedriver().setup();
//                ChromeOptions options = new ChromeOptions();
//                options.addArguments("--disable-notifications");
//                WebDriver driver = new ChromeDriver(options);
                        break;
                }
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            }
            return driver;
        }
    }

    public static void quitDriver() {
        if (configProperties.getParallelRunFlagValue().equalsIgnoreCase("true")) {
            if (driverThread.get() != null) {
                driverThread.get().quit();
            }
            driverThread.remove();
        } else {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
