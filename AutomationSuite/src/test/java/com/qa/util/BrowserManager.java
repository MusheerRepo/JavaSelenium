package com.qa.util;

import com.qa.base.TestBase;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BrowserManager extends TestBase {
    public static void getBrowser(String browser) {
        //This method will return the WebDriver instance
        // Initializing webdriver object for the browser given in POM.xml
        // Chrome
        if (browser.equalsIgnoreCase("Chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("download.default_directory", String.format("%s%s",
                    System.getProperty("user.dir"), "/test-output/Extent_Report"));
            options.setExperimentalOption("prefs", prefs);
            WebDriverManager.chromedriver().setup();

            try {
                driver.set(new ChromeDriver(options));
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (browser.equalsIgnoreCase("Firefox")) { // Firefox
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            WebDriverManager.firefoxdriver().setup();

            try {
                driver.set(new FirefoxDriver(options));
            } catch (Exception e) {
                BtLogger.info(e.toString());
            }
        } else if (browser.equalsIgnoreCase("Edge")) { // Edge
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            WebDriverManager.edgedriver().setup();

            try {
                driver.set(new EdgeDriver(options));
            } catch (Exception e) {
                BtLogger.info(e.toString());
            }
        }

        // Configuring webdriver object
        driver.get().manage().window().setSize(new Dimension(1920, 1080));
        driver.get().manage().deleteAllCookies();
        driver.get().manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.get().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        // Initializing javascript executor object
        jsE = (JavascriptExecutor) driver.get();

        driver.get().navigate().to(urL);
    }
}
