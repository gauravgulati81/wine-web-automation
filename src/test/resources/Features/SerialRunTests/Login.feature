Feature: feature to test login functionality

  @Test1 @Test
  Scenario: Check login is successful with valid credentials

    Given user is on login page
    When user enters username and password
    And clicks on login button
    And step fails
    Then user is navigated to home page


  @Test2 @Test
  Scenario: Check whether the user is able to login

    Given user is on login page
    When user enters username and password
    And clicks on login button
#    And step fails
    Then user is navigated to home page