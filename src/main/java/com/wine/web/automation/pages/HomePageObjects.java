package com.wine.web.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePageObjects {
    WebDriver driver;
    public HomePageObjects(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, HomePageObjects.this);
    }

    @FindBy(how = How.XPATH,using = "//img[contains(@src,'header_logo.png')]")
    public WebElement appLogo;

    public By singleSpotlightItem = By.xpath("//div[@class='slick-track']/div[@class='slick-slide']");

    @FindBy(how = How.XPATH,using = "//div[@class='slick-track']/div[@class='slick-slide']")
    public List<WebElement> spotlightItemList;

    @FindBy(how = How.XPATH,using = "//div[contains(@class,'wzrk-alert')]")
    public List<WebElement> notificationsAlertPopupList;

    @FindBy(how = How.XPATH,using = "//button[@id='wzrk-cancel']")
    public WebElement notificationsAlertPopupSkipButton;

    @FindBy(how = How.XPATH,using = "//div[contains(@class,'trayindex')]")
    public List<WebElement> railContainerList;

    public By railContainerLocator = By.xpath("//div[contains(@class,'trayindex')]");

    @FindBy(how = How.XPATH,using = "//div[@data-index='0']")
    public List<WebElement> firstThumbInEachRailList;
}
