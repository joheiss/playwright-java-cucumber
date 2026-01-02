@login @regression
Feature: Login Portal

  Background:
    Given user navigates to home page
    And user clicks the Login Portal button

  @smoke
  Scenario Outline: Successful & Unsuccessful Login
    When user enters the username <username>
    And user enters the password <password>
    And user clicks the login button
    Then system displays an alert with message <loginValidationText>

    Examples:
      | username  | password     | loginValidationText  |
      | webdriver | webdriver123 | validation succeeded |
      | webdriver | wrongpassw   | validation failed    |
      | wronguser | webdriver123 | validation failed    |
      | wronguser | wrongpassw   | validation failed    |
