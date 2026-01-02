package com.jovisco.pages;

import com.jovisco.browser.BrowserManager;

public class LoginPage extends BasePage{

    public LoginPage(BrowserManager browserManager) {
        super(browserManager);
    }

    public void enterUserName(String userName) {
        enterValue("Username", userName);
    }
    public void enterPassword(String password) {
        enterValue("Password", password);
    }
    public void clickLoginButton() {
        clickButton("#login-button");
    }
}
