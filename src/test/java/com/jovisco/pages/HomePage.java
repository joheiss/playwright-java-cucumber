package com.jovisco.pages;


import com.jovisco.browser.BrowserManager;

public class HomePage extends BasePage {

    public HomePage(BrowserManager browserManager) {
        super(browserManager);
    }

    public void navigateToHomePage() {
        navigate("https://webdriveruniversity.com/");
    }
    public void clickContactUsButton() {
        clickLink("#contact-us");
    }
    public void clickLoginButton() {
       clickLink("#login-portal");
    }
}
