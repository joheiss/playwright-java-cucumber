package com.jovisco.step_definitions;

import com.jovisco.browser.BrowserManager;
import com.jovisco.pages.HomePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

public class HomeSteps extends BaseSteps{

    private final HomePage homePage;
    public HomeSteps(BrowserManager browserManager) {
        super(browserManager);
        this.homePage = new HomePage(browserManager);
    }
    @Given("user navigates to home page")
    public void user_navigates_to_home_page() {
        homePage.navigateToHomePage();
    }
    @And("user clicks the ContactUs button")
    public void user_clicks_the_contact_us_button() {
        homePage.clickContactUsButton();
    }
    @And("user clicks the Login Portal button")
    public void user_clicks_the_login_button() {
        homePage.clickLoginButton();
    }
}
