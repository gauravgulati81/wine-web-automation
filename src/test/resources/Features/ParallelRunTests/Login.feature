Feature: feature to test login functionality

  Background: Navigation to home screen
    Given the application home page is launched

  @Test1
  Scenario: Check login is successful with valid credentials

    Given user is on login page
    When user enters username and password
    And clicks on login button
    And step fails
    Then user is navigated to home page


  @Test2
  Scenario: Check login is successful with valid credentials - 2

    Given user is on login page
    When user enters username and password
    And clicks on login button
#    And step fails
    Then user is navigated to home page

  @web @sanity @smoke
  Scenario: Check the title of the web app v4
    When the user is on home page
    Then the title of the web app is correct

  @web @sanity @smoke
  Scenario: Click on the first thumbnail of the last rail on home page v2 v4
    When the user scrolls to the last rail of the page
    Then click on the first thumbnail of the last rail