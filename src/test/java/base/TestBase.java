package base;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class TestBase {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        //This code block runs once before starting to suite execution

        //This block can be used to perform activities like testrun creation
        //in third party tools, parse secrets, creation of test data

    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        /*
         * This code block runs before starting of method execution
         * As mostly test are executed on local/cloud linux server
         * it is important to keep WebDriver instance headless in that case
         */

        /*
         * //Setup code for chrome
         * ChromeOptions chromeOptions = new ChromeOptions();
         * chromeOptions.addArguments("--headless");
         * driver.set(new ChromeDriver(chromeOptions));
         */
    
        /*
         * //Setup code for firefox
         * FirefoxOptions firefoxOptions = new FirefoxOptions();
         * firefoxOptions.addArguments("--headless");
         * driver.set(new FirefoxDriver(firefoxOptions));
         */

        /*
         * //Setup code for edge
         * EdgeOptions edgeOptions = new EdgeOptions();
         * edgeOptions.addArguments("--headless");
         * driver.set(new EdgeDriver(edgeOptions));
         */
        
        driver.get().manage().window().setSize(new Dimension(1920, 1080));
        driver.get().manage().deleteAllCookies();
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        //This code block runs after completion of method execution
        
        //Teardown driver instance
        driver.get().close();
        driver.get().quit();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        /*
        This code block runs after completion of suite execution
        and can be used to kill process, publish reports
        */
    }
}
