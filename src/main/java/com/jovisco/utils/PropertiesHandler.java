package com.jovisco.utils;

import com.jovisco.browser.BrowserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesHandler.class.getName());

    public static Properties getProperties() {
        var properties = new Properties();
        var configPath = Paths.get(System.getProperty("config.path",
                Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "config.properties")
                        .toString()));
        try (var inputStream = configPath.toFile().exists() ? Files.newInputStream(configPath) : null) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
            return properties;
        } catch (Exception e) {
            LOGGER.error("Failed to load configuration properties", e);
            return null;
        }
    }
}
