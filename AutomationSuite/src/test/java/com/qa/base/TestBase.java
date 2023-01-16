package com.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import com.google.gson.stream.JsonWriter;
import com.qa.pages.SignIn1;
import com.qa.pages.SignIn2;
import com.qa.testrails.ApiClient;
import com.qa.util.BrowserManager;
import com.qa.util.BtLogger;
import com.qa.util.PreExecutionActivities;
import com.qa.util.TestRailsManager;
import com.qa.util.TestUtil;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import com.qa.testrails.ApiException;

public class TestBase {
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
    public static Properties prop;
    public static String environment = "";
    public static String browserApp = "";
    public String testCaseID;
    public static int maxTry;
    public static String urL;
    public static String username;
    public static String password;
    public static String xmlSuite;
    public static String testRunId = "1";
    public static String testrailconfig;
    public static boolean exitCode = true;
    public static JavascriptExecutor jsE;
    public static int totalTestCaseCount = 0;
    public static int totalPassedCaseCount = 0;
    public static int totalFailedCaseCount = 0;
    public static String testrailsUsername;
    public static String testrailsPassword;
    public static String railsEngineUrl;

    /*
     * Before suite will be before executed execution of automation suite
     */
    @BeforeSuite(alwaysRun = true)
    @Parameters({ "retryCount" })
    public void initialization(int retryCount) throws Exception {
        maxTry = retryCount; // Initializing maxTry

        //Seting environment variables
        System.setProperty("java.awt.headless", "true");
        System.setProperty("webdriver.chrome.whitelistedIps", "");
        // Setting Env variable
        urL = System.getProperty("URL");
        username = System.getProperty("username");
        password = System.getProperty("password");
        xmlSuite = System.getProperty("xmlsuite");
        testrailconfig = System.getProperty("testrailconfig");

        System.out.println("Env variables are");
        System.out.println(String.format("%s%s", "URL : ", urL));
        System.out.println(String.format("%s%s", "Username : ", username));
        System.out.println(String.format("%s%s", "Password : ", password));
        System.out.println(String.format("%s%s", "XmlSuite : ", xmlSuite));

        //Parsing testrailconfig to json
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(testrailconfig);
            railsEngineUrl = (String) jsonObject.get("url");
            testrailsUsername = (String) jsonObject.get("username");
            testrailsPassword = (String) jsonObject.get("password");
            jsonObject.clear();
        } catch (Exception e) {
            BtLogger.trace("Error", e);
        }

        // Initializing properties files object to load the properties file of given
        // environment
        prop = new Properties();
        String propfile = "config.properties";

        try {
            FileInputStream fis = new FileInputStream(
                    System.getProperty("user.dir") + "//src//test//java//com//qa//config//"
                        + propfile);
            prop.load(fis);
            BtLogger.info("Property files loaded");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (xmlSuite.equalsIgnoreCase("Regression.xml")) {
            BtLogger.info("Performing pre execution activities");
            boolean reprocessFile = true;
            try {
                BtLogger.info("Performing test data check");
                reprocessFile = PreExecutionActivities.checkTestData(urL);
                BtLogger.info("Performed test data check");
            } catch (Exception e) {
                driver.get().close();
                driver.get().quit();
                BtLogger.trace("Error ", e);
                JsonWriter jsonWriter = new JsonWriter(
                        new FileWriter(String.format("%s%s%s", System.getProperty("user.dir"),
                                "/test-output/Extent_Report", "/CountReport.json")));
                jsonWriter.beginObject();
                jsonWriter.name("Total");
                jsonWriter.value(totalTestCaseCount);
                jsonWriter.name("Passed");
                jsonWriter.value(totalPassedCaseCount);
                jsonWriter.name("Failed");
                jsonWriter.value(totalFailedCaseCount);
                jsonWriter.name("message");
                jsonWriter.value("UI test run failed because of pre-execution error");
                jsonWriter.name("messageMrkd");
                jsonWriter.value("Testdata check failure");
                jsonWriter.endObject();
                jsonWriter.close();
                System.exit(100);
            }

            //This code will reprocess the existing files for testing purposes
            try {
                if (reprocessFile == true) {
                    BtLogger.info("Reprocessing BMO used for test scripts");
                    PreExecutionActivities.reprocessFile();
                    BtLogger.info("Reprocessed BMO used for test scripts");
                }
            } catch (Exception e) {
                driver.get().close();
                driver.get().quit();
                BtLogger.trace(String.format("%s%s","Error",e));
                JsonWriter jsonWriter = new JsonWriter(
                        new FileWriter(String.format("%s%s%s", System.getProperty("user.dir"),
                                "/test-output/Extent_Report", "/CountReport.json")));
                jsonWriter.beginObject();
                jsonWriter.name("Total");
                jsonWriter.value(totalTestCaseCount);
                jsonWriter.name("Passed");
                jsonWriter.value(totalPassedCaseCount);
                jsonWriter.name("Failed");
                jsonWriter.value(totalFailedCaseCount);
                jsonWriter.name("message");
                jsonWriter.value("UI test run failed because of pre-execution error");
                jsonWriter.name("messageMrkd");
                jsonWriter.value("Reprocessing of existing files failure");
                jsonWriter.endObject();
                jsonWriter.close();
                System.exit(100);
            }

            //This method will create the BMO used by test scripts
            try {
                BtLogger.info("Creating new BMO for testing");
                PreExecutionActivities.createBmo(7);
                BtLogger.info("BMO for testing created");
            } catch (Exception e) {
                BtLogger.trace(String.format("%s%s","Error",e));
                JsonWriter jsonWriter = new JsonWriter(
                        new FileWriter(String.format("%s%s%s", System.getProperty("user.dir"),
                                "/test-output/Extent_Report", "/CountReport.json")));
                jsonWriter.beginObject();
                jsonWriter.name("Total");
                jsonWriter.value(totalTestCaseCount);
                jsonWriter.name("Passed");
                jsonWriter.value(totalPassedCaseCount);
                jsonWriter.name("Failed");
                jsonWriter.value(totalFailedCaseCount);
                jsonWriter.name("message");
                jsonWriter.value("UI test run failed because of BMO creation failure");
                jsonWriter.name("messageMrkd");
                jsonWriter.value("BMO creation failure");
                jsonWriter.endObject();
                jsonWriter.close();
                System.exit(100);
            }

            //This code will trigger project creation for testing purposes
            try {
                BtLogger.info("Creating new projects for testing");
                PreExecutionActivities.createProject(6);
                BtLogger.info("Projects for testing created");
            } catch (Exception e) {
                BtLogger.trace(String.format("%s%s","Error",e));
                JsonWriter jsonWriter = new JsonWriter(
                        new FileWriter(String.format("%s%s%s", System.getProperty("user.dir"),
                                "/test-output/Extent_Report", "/CountReport.json")));
                jsonWriter.beginObject();
                jsonWriter.name("Total");
                jsonWriter.value(totalTestCaseCount);
                jsonWriter.name("Passed");
                jsonWriter.value(totalPassedCaseCount);
                jsonWriter.name("Failed");
                jsonWriter.value(totalFailedCaseCount);
                jsonWriter.name("message");
                jsonWriter.value("UI test run failed because of project creation failure");
                jsonWriter.name("messageMrkd");
                jsonWriter.value("Project creation failure");
                jsonWriter.endObject();
                jsonWriter.close();
                System.exit(100);
            }

            //Creating new test run in testrails
            try {
                ApiClient client = new ApiClient(railsEngineUrl);
                client.setUser(testrailsUsername);
                client.setPassword(testrailsPassword);
                //Create Test Run
                Map data = new HashMap();
                data.put("include_all", true);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                data.put("name", String.format("%s%s", "Automated Test Run ", dtf.format(now)));
                JSONObject c = (JSONObject) client
                        .sendPost(String.format("%s%s", "add_run/", 1), data);
                //Extract Test Run Id
                testRunId = c.get("id").toString();
                BtLogger.info(String.format("%s%s", "Created new testrun : ", testRunId));
            } catch (Exception e) {
                BtLogger.error("Error while creating new test run", e);
                JsonWriter jsonWriter = new JsonWriter(
                        new FileWriter(String.format("%s%s%s", System.getProperty("user.dir"),
                                "/test-output/Extent_Report", "/CountReport.json")));
                jsonWriter.beginObject();
                jsonWriter.name("Total");
                jsonWriter.value(totalTestCaseCount);
                jsonWriter.name("Passed");
                jsonWriter.value(totalPassedCaseCount);
                jsonWriter.name("Failed");
                jsonWriter.value(totalFailedCaseCount);
                jsonWriter.name("message");
                jsonWriter.value("UI test run failed because of new testrun creation failure");
                jsonWriter.name("messageMrkd");
                jsonWriter.value("Testrun creation failure");
                jsonWriter.endObject();
                jsonWriter.close();
                System.exit(100);
            }
        }
    }

    // BeforeMethod is executed before every test method, it takes browser as
    // parameter on which test will be performed
    @BeforeMethod(alwaysRun = true)
    @Parameters({ "browser" })
    public void setup(String browser) throws Exception {
        // Saving browser name for extent reports
        browserApp = browser;
        BrowserManager.getBrowser(browserApp);

        //Sign in code
        SignIn1.clickOnSignIn();
        SignIn2.enterCredentials();
    }

    public static void screenshot() {
        TakesScreenshot ts = (TakesScreenshot) driver.get();
        File srcFile = ts.getScreenshotAs(OutputType.FILE);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currentDateTime = dtf.format(now);
        String path = String.format("%s%s%s%s",System.getProperty("user.dir"),
                "/test-output/Screenshots/", currentDateTime, ".jpg");
        File destFile = new File(path);

        try {
            FileUtils.copyFile(srcFile, destFile);
            BtLogger.info(String.format("%s%s",
                    "Screenshot saved at location : ", path));
        } catch (Exception e) {
            System.out.println("Error while taking screenshot");
            BtLogger.info(e.toString());
        }
    }

    // Aftermethod is executed after every test method
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException, ApiException {
        // Checking if test is passed, update test rails accordingly
        if (result.getStatus() == result.SUCCESS) {
            try {
                BtLogger.info("Logging passed test result in test rails");
                TestRailsManager.addResultForTestCase(testCaseID, 
                        TestRailsManager.testCasePassedStatus, "Test passed");
                BtLogger.info("Logged passed test result in test rails");
            } catch (Exception e) {
                BtLogger.info(e.toString());
            }
        }

        // Checking for the failed test cases, and adding screenshot of failed test
        // cases in the extent report
        if (result.getStatus() == result.FAILURE || result.getStatus() == result.SKIP) {
            String screenshotPath = "";
            try {
                screenshotPath = TestUtil.getScreenshot(driver.get(), result.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            result.setAttribute("screenshotPath", screenshotPath);
            BtLogger.info("Screenshot captured of failed test case");

            // Adding test result to testrail manager, so that results can be shared with
            // testrails
            try {
                BtLogger.info("Logging failed test result in test rails");

                //Converting stack trace to string, so it can be added in testrails results
                StringWriter sw = new StringWriter();
                result.getThrowable().printStackTrace(new PrintWriter(sw));
                String exceptionAsString = sw.toString();
                TestRailsManager.addResultForTestCase(testCaseID, 
                        TestRailsManager.testCaseFailedStatus, exceptionAsString);
                BtLogger.info("Error", result.getThrowable());
                BtLogger.info("Logged failed test result in test rails");
            } catch (Exception e) {
                BtLogger.info(e.toString());
            }
        }
        // Closing and quiting the browser
        driver.get().close();
        driver.get().quit();
        BtLogger.info("driver closed and quit");

    }

    @AfterSuite(alwaysRun = true)
    public void finish() {
        // Cleared the properties object
        prop.clear();
    }
}
