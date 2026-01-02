@contact-us @regression
Feature: ContactUs Page

  Background:
    Given user navigates to home page
    And user clicks the ContactUs button

  @smoke
  Scenario: User fills in the form with allowed values
    When user enters the first name Hans
    And user enters the last name Dampf
    And user enters the email address hans.dampf@dempf.de
    And user enters the message Hallo, ich bin Hans.
    And user clicks the submit button
    Then system displays a message containing "Thank you"

  Scenario: User fills in the form with random data
    When user enters a first name
    And user enters a last name
    And user enters an email address
    And user enters a message
    And user clicks the submit button
    Then system displays a message containing "Thank you"

  Scenario: User leaves all fields on the form empty and clicks the submit button
    When user clicks the submit button
    Then system displays error messages

  Scenario Outline: User enters no or invalid data in the form anc clicks submit button
    When user enters the first name <firstName>
    And user enters the last name <lastName>
    And user enters the email address <emailAddress>
    And user enters the message <comment>
    And user clicks the submit button
    Then system displays the error message <errorMsg>

    Examples:
      | firstName | lastName | emailAddress        | comment             | errorMsg                       |
      |           | Dampf    | hans.dampf@dampf.de | Hallo, ich bin Hans | Error: all fields are required |
      | Hans      |          | hans.dampf@dampf.de | Hallo, ich bin Hans | Error: all fields are required |
      | Hans      | Dampf    |                     | Hallo, ich bin Hans | Error: all fields are required |
      | Hans      | Dampf    | hans.dampf@dampf.de |                     | Error: all fields are required |
      | Hans      | Dampf    | hans.dampf#dampf.de | Hallo, ich bin Hans | Error: Invalid email address   |
      | Hans      | Dampf    | hans.dampf@dampf.de | Hallo, ich bin Hans | Thank You for your Message!    |


