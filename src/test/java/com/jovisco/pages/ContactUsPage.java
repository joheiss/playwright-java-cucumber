package com.jovisco.pages;

import com.jovisco.browser.BrowserManager;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ContactUsPage extends BasePage {

    public ContactUsPage(BrowserManager browserManager) {
        super(browserManager);
    }

    public void enterFirstName(String firstName) {
        enterValue("First Name", firstName);
    }
    public void enterLastName(String lastName) {
        enterValue("Last Name", lastName);
    }
    public void enterEmailAddress(String emailAddress) {
        enterValue("Email Address", emailAddress);
    }
    public void enterMessage(String message) {
        enterValue("Comments", message);
    }
    public void clickSubmitButton() {
        var options = new Page.WaitForSelectorOptions().setTimeout(5000);
        browserManager.getPage().waitForSelector("input[value='SUBMIT']", options);
        clickButton("input[value='SUBMIT']");
    }
    public void verifySuccessMessage(String expectedMessage) {
        // wait for the success message to be visible
        var options = new Page.WaitForSelectorOptions().setTimeout(5000);
        browserManager.getPage().waitForSelector("#contact_reply h1", options);
        var locator = browserManager.getPage().locator("#contact_reply h1");
        var containsTextOptions = new LocatorAssertions.ContainsTextOptions();
        containsTextOptions.ignoreCase = true;
        assertThat(locator).containsText(expectedMessage, containsTextOptions);
    }
    public void verifyErrorMessagesExist() {
        var options = new Page.LocatorOptions().setHasText("Error:");
        var locator = browserManager.getPage().locator("body", options);
        assertThat(locator).isVisible();
    }
    public void verifyErrorMessageIsDisplayed(String errorMessage) {
        var options = new Page.LocatorOptions().setHasText(errorMessage);
        var locator = browserManager.getPage().locator("body", options);
        assertThat(locator).isVisible();
    }
}
