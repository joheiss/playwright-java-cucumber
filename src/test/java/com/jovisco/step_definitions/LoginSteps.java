package com.jovisco.step_definitions;

import com.jovisco.browser.BrowserManager;
import com.jovisco.pages.LoginPage;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.testng.Assert.assertEquals;

public class LoginSteps extends BaseSteps{
    private String alertMessage = null;

    private final LoginPage loginPage;
    public LoginSteps(BrowserManager browserManager) {
        super(browserManager);
        loginPage = new LoginPage(browserManager);
    }
    @Before(order = 1)
    public void setup() {
    }
    @When("user enters the username {}")
    public void user_enters_the_username(String userName) {
        loginPage.enterUserName(userName);
    }
    @And("user enters the password {}")
    public void user_enters_the_password(String password) {
        loginPage.enterPassword(password);
    }
    @And("user clicks the login button")
    public void user_clicks_the_login_button() {
        browserManager.getPage().onceDialog(alert -> {
            assertEquals(alert.type(), "alert");
            alertMessage = alert.message();
            alert.dismiss();
        });
        loginPage.clickLoginButton();
    }
    @Then("system displays an alert with message {}")
    public void system_displays_an_alert_with_message(String message) {
        assertEquals(alertMessage, message, "Alert message does not match");
    }


}
