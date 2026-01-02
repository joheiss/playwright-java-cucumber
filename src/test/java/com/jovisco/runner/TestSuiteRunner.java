package com.jovisco.runner;

import com.jovisco.browser.BrowserManager;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.TestNG;
import org.testng.annotations.DataProvider;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.Collections;
import java.util.Objects;
import java.util.Properties;

import static com.jovisco.utils.PropertiesHandler.getProperties;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.jovisco.step_definitions",
        plugin = { "pretty", "html:target/cucumber-report.html", "json:target/cucumber-report.json" },
        tags = "@regression and not @ignore",
        monochrome = true,
        dryRun = false
)
public class TestSuiteRunner extends AbstractTestNGCucumberTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrowserManager.class.getName());
    public static final Properties properties = getProperties();

    public static void main(String[] args) {
        // get thread count from properties and assign it to the test suite
        var threadCount = Integer.parseInt(Objects.requireNonNull(properties).getProperty("threadCount", "1"));
        LOGGER.info("Running tests with thread count: {}", threadCount);
        var suite = new XmlSuite();
        suite.setDataProviderThreadCount(threadCount);

        // create a TestNG test and add the suite
        var test = new XmlTest(suite);
        test.setName("Playwright Cucumber Test Suite");
        test.setXmlClasses((Collections.singletonList(new XmlClass(TestSuiteRunner.class))));
        var testNg = new TestNG();
        testNg.setUseDefaultListeners(false);
        testNg.setXmlSuites((Collections.singletonList(suite)));
        testNg.run();
    }

    @Override
    @DataProvider(parallel = true) // enabling parallel execution of scenarios
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
