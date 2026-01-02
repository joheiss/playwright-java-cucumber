package com.jovisco.step_definitions;

import com.jovisco.browser.BrowserManager;
import com.jovisco.context.PersonContext;
import com.jovisco.domain.Person;
import com.jovisco.pages.ContactUsPage;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.datafaker.Faker;

import java.util.Locale;

public class ContactUsSteps extends BaseSteps {

    private final Faker faker = new Faker(Locale.GERMANY);
    private final ContactUsPage contactUsPage;

    private final PersonContext personContext;
    public ContactUsSteps(BrowserManager browserManager, PersonContext personContext) {
        super(browserManager);
        this.contactUsPage = new ContactUsPage(browserManager);
        this.personContext = personContext;
    }
    @Before(order = 1)
    public void setup() {
        personContext.setRandomPerson(fakePerson());
    }

    @When("user enters the first name {}")
    public void user_enters_the_first_name(String firstName) {
        contactUsPage.enterFirstName(firstName);
    }
    @And("user enters the last name {}")
    public void user_enters_the_last_name(String lastName) {
        contactUsPage.enterLastName(lastName);
    }
    @And("user enters the email address {}")
    public void user_enters_the_email_address(String email) {
        contactUsPage.enterEmailAddress(email);
    }
    @And("user enters the message {}")
    public void user_enters_the_message(String message) {
        contactUsPage.enterMessage(message);
    }
    @And("user clicks the submit button")
    public void user_clicks_the_submit_button() {
        contactUsPage.clickSubmitButton();
    }
    @Then("system displays a message containing {string}")
    public void system_displays_a_message_containing(String message) {
        contactUsPage.verifySuccessMessage(message);
    }
    @Then("system displays error messages")
    public void system_displays_error_messages() {
        contactUsPage.verifyErrorMessagesExist();
    }
    @Then("system displays the error message {}")
    public void system_displays_the_error_message(String message) {
        contactUsPage.verifyErrorMessageIsDisplayed(message);
    }
    @When("user enters a first name")
    public void user_enters_a_first_name() {
        contactUsPage.enterFirstName(personContext.getRandomPerson().firstName());
    }
    @And("user enters a last name")
    public void user_enters_a_last_name() {
        contactUsPage.enterLastName(personContext.getRandomPerson().lastName());
    }
    @And("user enters an email address")
    public void user_enters_an_email_address() {
        contactUsPage.enterEmailAddress(personContext.getRandomPerson().email());
    }
    @And("user enters a message")
    public void user_enters_a_message() {
        contactUsPage.enterMessage(faker.lorem().paragraph());
    }

    private Person fakePerson() {
        var firstName = faker.name().firstName();
        var lastName = faker.name().lastName();
        var email = faker.internet().emailAddress(firstName + "." + lastName);
        return new Person(firstName, lastName, email);
    }
}
