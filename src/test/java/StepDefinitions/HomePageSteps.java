package StepDefinitions;

import com.wine.web.automation.config.DriverFactory;
import com.wine.web.automation.pages.HomePageObjects;
import com.wine.web.automation.utils.CommonUtils;
import com.wine.web.automation.utils.Constants;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePageSteps {
    WebDriver driver = DriverFactory.getDriver();
    HomePageObjects homePageObjects = new HomePageObjects(driver);
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    public void checkForNotificationPopup(){
        if (homePageObjects.notificationsAlertPopupList.size()>0){
            homePageObjects.notificationsAlertPopupSkipButton.click();
            System.out.println("Notification popup dismissed");
        }
    }

    @Given("^the application home page is launched$")
    public void the_application_website_is_launched() {
        driver.get(CommonUtils.getUrlUnderTest());
        driver.manage().window().maximize();
        wait.until(ExpectedConditions.visibilityOf(homePageObjects.appLogo));
        checkForNotificationPopup();
    }

    @Given("^reach application home page by clicking on app logo$")
    public void reach_application_home_page_by_clicking_on_app_logo() {
        wait.until(ExpectedConditions.visibilityOf(homePageObjects.appLogo));
        homePageObjects.appLogo.click();
    }

    @When("^the user is on home page$")
    public void the_user_is_on_home_page() {
        wait.until(ExpectedConditions.visibilityOf(homePageObjects.appLogo));
        Assert.assertTrue("App logo is not displayed", homePageObjects.appLogo.isDisplayed());
        Assert.assertTrue("App is not on home page", driver.getCurrentUrl().equals(CommonUtils.getUrlUnderTest()));
    }

    @Then("^the title of the web app is correct$")
    public void the_title_of_the_web_app_is_correct() {
        Assert.assertTrue("Page title is incorrect", driver.getTitle().equals(Constants.homePageTitle));
    }

    @Then("^spotlight with multiple images should be present$")
    public void spotlight_with_multiple_images_should_be_present() {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(homePageObjects.singleSpotlightItem, 1));
        Assert.assertTrue("Multiple spotlights are not present", homePageObjects.spotlightItemList.size()>1);
    }

    @When("^the user scrolls to the last rail of the page$")
    public void the_user_scrolls_to_the_last_rail_of_the_page() {
        int numberOfRailsLoaded = 0;
        Actions actions = new Actions(driver);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(homePageObjects.railContainerLocator, 5));
        while (homePageObjects.railContainerList.size() > numberOfRailsLoaded){
            numberOfRailsLoaded = homePageObjects.railContainerList.size();
            System.out.println("number of rails loaded: " + numberOfRailsLoaded);
            for (int i = 0;i<5;i++) {
                actions.keyDown(Keys.PAGE_DOWN).perform();
                CommonUtils.sleep(1000);
            }
            try {
                wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(homePageObjects.railContainerLocator, numberOfRailsLoaded));
            } catch (Exception e){
                System.out.println("Reached the bottom of page");
            }
        }
        actions.keyDown(Keys.PAGE_UP).perform();
    }

    @Then("^click on the first thumbnail of the last rail$")
    public void click_on_the_first_thumbnail_of_the_last_rail() {
        homePageObjects.firstThumbInEachRailList.get(homePageObjects.firstThumbInEachRailList.size()-1).click();
        System.out.println(driver.getCurrentUrl());
    }

}



