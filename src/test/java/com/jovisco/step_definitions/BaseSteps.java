package com.jovisco.step_definitions;

import com.jovisco.browser.BrowserManager;

public class BaseSteps {

    protected final BrowserManager browserManager;

    public BaseSteps(BrowserManager browserManager) {
        this.browserManager = browserManager;
    }
}
