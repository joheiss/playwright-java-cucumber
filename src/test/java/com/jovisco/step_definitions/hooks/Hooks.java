package com.jovisco.step_definitions.hooks;

import com.jovisco.browser.BrowserManager;
import io.cucumber.java.*;

public class Hooks {

    private final BrowserManager browserManager;

    public Hooks(BrowserManager browserManager) {
        this.browserManager = browserManager;
    }

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Before all ...");
    }
    @Before(order = 0)
    public void setup() {
        browserManager.setup();
    }
    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            var screenshot = browserManager.takeScreenshot();
            scenario.attach(screenshot, "image/png", "screenshot");
        }
        browserManager.tearDown();
    }
    @AfterAll
    public static void afterAll() {
        System.out.println("After all ...");
    }
}
