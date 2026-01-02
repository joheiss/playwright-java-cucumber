package com.jovisco.browser;

import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static com.jovisco.utils.PropertiesHandler.getProperties;

public class BrowserManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrowserManager.class.getName());
    private static final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static final ThreadLocal<Page> page = new ThreadLocal<>();

    public Properties properties;

    public BrowserManager() {
        properties = getProperties();
    }

    public Page getPage() {
        return page.get();
    }

    public void setPage(Page newPage) {
        page.set(newPage);
    }

    public BrowserContext getContext() {
        return context.get();
    }

    public byte[] takeScreenshot() {
        if (page.get() != null) {
            return page.get().screenshot();
        }
        return new byte[0];
    }

    public void setup() {
        try {
            playwright.set(Playwright.create());
            browser.set(getBrowser());
            context.set(getBrowserContext(browser.get()));
            page.set(context.get().newPage());
            // set suite-specific timeouts
            var navigationTimeout = Integer.parseInt(properties.getProperty("navigationTimeout", "10000"));
            page.get().setDefaultNavigationTimeout(navigationTimeout);
            var actionTimeout = Integer.parseInt(properties.getProperty("actionTimeout", "5000"));
            page.get().setDefaultTimeout(actionTimeout);
        } catch (Exception e) {
            LOGGER.error("Failed to set up playwright environment", e);
        }
    }

    public void tearDown() {
        try {
            if (page.get() != null) page.get().close();
            if (context.get() != null) context.get().close();
            if (browser.get() != null) browser.get().close();
            if (playwright.get() != null) playwright.get().close();
        } catch (Exception e) {
            LOGGER.error("Failed to tear down playwright environment", e);
        }
    }

    private void loadProperties() {
        properties = new Properties();
        var configPath = Paths.get(System.getProperty("config.path",
                Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "config.properties")
                        .toString()));
        try (var inputStream = configPath.toFile().exists() ? Files.newInputStream(configPath) : null) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (Exception e) {
            LOGGER.error("Failed to load configuration properties", e);
        }
    }

    private Browser getBrowser() {
        try {
            var browserType = properties.getProperty("browser", "chromium");
            var headless = Boolean.parseBoolean(properties.getProperty("headless", "true"));
            return switch (browserType.toLowerCase().trim()) {
                case "chrome" -> playwright.get().chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
                case "firefox" -> playwright.get().firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless));
                case "webkit" -> playwright.get().webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless));
                default -> playwright.get().chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            };
        } catch (Exception e) {
            LOGGER.error("Failed to launch browser", e);
            return null;
        }
    }

    private BrowserContext getBrowserContext(Browser browser) {
        try {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int width = screenSize.width;
            int height = screenSize.height;
            return browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        } catch (HeadlessException e) {
            LOGGER.error("Failed to create browser context", e);
            return null;
        }
    }
}
