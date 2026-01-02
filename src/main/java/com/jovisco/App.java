package com.jovisco;

import com.microsoft.playwright.*;

import java.awt.*;

public class App 
{
    public static void main( String[] args ) {
        // get viewport size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        // initialize playwright
        try(Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
            Page page = context.newPage();
            page.navigate("https://www.webdriveruniversity.com/");
            System.out.println(page.title());
            page.close();
        }
    }
}
