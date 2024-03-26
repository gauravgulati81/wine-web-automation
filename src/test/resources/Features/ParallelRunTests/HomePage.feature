Feature: Feature file to test the home page of the application

  Background: Navigation to home screen
    Given the application home page is launched

  @web @sanity @smoke
  Scenario: Check presence of spotlight on home page v4
    When the user is on home page
    Then spotlight with multiple images should be present

#  @web @sanity @smoke
#  Scenario: Check the title of the web app
#    When the user is on home page
#    Then the title of the web app is correct

  @web @sanity @smoke
  Scenario: Click on the first thumbnail of the last rail on home page v4
    When the user scrolls to the last rail of the page
    Then click on the first thumbnail of the last rail