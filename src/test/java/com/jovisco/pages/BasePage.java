package com.jovisco.pages;

import com.jovisco.browser.BrowserManager;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.awt.*;

public class BasePage {

    protected BrowserManager browserManager;
    public BasePage(BrowserManager browserManager) {
        this.browserManager = browserManager;
    }

    public void navigate(String url) {
        browserManager.getPage().navigate(url);
    }
    public void enterValue(Page.GetByRoleOptions options, String value) {
        browserManager.getPage().getByRole(AriaRole.TEXTBOX, options).fill(value);
    }
    public void enterValue(String placeholder, String value) {
        browserManager.getPage().getByPlaceholder(placeholder).fill(value);
    }
    public void clickButton(Page.GetByRoleOptions options) {
        Locator locator = browserManager.getPage().getByRole(AriaRole.BUTTON, options);
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        locator.click();
    }
    public void clickButton(String selector) {
        browserManager.getPage().waitForSelector(selector);
        browserManager.getPage().locator(selector).click();
    }
    public void clickLink(String selector) {
        browserManager.getPage().waitForSelector(selector);
        browserManager.setPage(browserManager.getContext().waitForPage(() -> {
            browserManager.getPage().locator(selector).click();
        }));
        browserManager.getPage().bringToFront();
    }
    public void waitForMessage(String selector) {
        var options = new Page.WaitForSelectorOptions().setTimeout(5000);
        browserManager.getPage().waitForSelector(selector, options);
    }
}
