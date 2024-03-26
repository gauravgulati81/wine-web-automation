package StepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class LoginSteps {

    @Given("user is on login page")
    public void user_is_on_login_page() {
        System.out.println("Inside step - user is on login page");
        Assert.assertEquals(1,1);
    }

    @When("user enters username and password")
    public void user_enters_username_and_password() {
        System.out.println("Inside step - user enters username and password");
    }

    @When("clicks on login button")
    public void clicks_on_login_button() {
        System.out.println("Inside step - clicks on login button");
    }
    @When("step fails")
    public void step_fails() {
        Assert.assertTrue("Step failed", 1==2);
    }

    @Then("user is navigated to home page")
    public void user_is_navigated_to_home_page() {
        System.out.println("Inside step - user is navigated to home page");
    }
}
