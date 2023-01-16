package com.qa.extentreportlistener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;
import com.google.gson.stream.JsonWriter;
import com.qa.base.TestBase;
import com.qa.util.BtLogger;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReporterNG extends TestBase implements IReporter {
    ExtentReports extent;

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
                               String outputDirectory) {
        extent = new ExtentReports(String.format("%s%s", outputDirectory,
                "/Extent_Report/Extent.html"), true);
        extent.addSystemInfo(environment, environment);
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> result = suite.getResults();
            for (ISuiteResult r : result.values()) {
                ITestContext context = r.getTestContext();
                try {
                    buildTestNodes(context.getPassedTests(), LogStatus.PASS);
                    buildTestNodes(context.getFailedTests(), LogStatus.FAIL);

                    File directory = new File(String.format("%s%s",
                            System.getProperty("user.dir"), "/src/test/java/com/qa/testscripts"));
                    totalTestCaseCount = getFilesCount(directory) + 1;
                    //Creating json file when siute selected is Regression.xml
                    if (xmlSuite.equalsIgnoreCase("Regression.xml")) {
                        totalPassedCaseCount += context.getPassedTests().size();
                        totalFailedCaseCount += context.getFailedTests().size();
                        BtLogger.info(String.format("%s%s", "Creating json file for ",
                                context.getName()));
                        JsonWriter jsonWriter = new JsonWriter(
                                new FileWriter(String.format("%s%s%s", outputDirectory,
                                        "/Extent_Report", "/CountReport.json")));
                        jsonWriter.beginObject();
                        jsonWriter.name("Total");
                        jsonWriter.value(totalTestCaseCount);
                        jsonWriter.name("Passed");
                        jsonWriter.value(totalPassedCaseCount);
                        jsonWriter.name("Failed");
                        jsonWriter.value(totalFailedCaseCount);
                        jsonWriter.endObject();
                        jsonWriter.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        extent.flush();
        extent.close();
    }

    private void buildTestNodes(IResultMap tests, LogStatus status) throws IOException {
        if (tests.size() > 0) {
            for (ITestResult result : tests.getAllResults()) {
                ExtentTest test;
                // Add browser and browser version info. This data is fetched from TestBase
                // class
                extent.addSystemInfo("Browser", browserApp);

                // Get the method name and store it in a string.
                String methodName = result.getMethod().getMethodName();

                // Capitalize the first letter of the method name
                String capMethodName = String.format("%s%s", methodName.substring(0, 1)
                                .toUpperCase(), methodName.substring(1));

                // Replace the method name from underscore and replace with space. This will
                // make the method name look like a proper testcase name
                String spacedMethodName = capMethodName.replace("_", " ");

                // Add the testcase id as given under @Test(description) in test class
                String finalMethodName = String.format("%s%s%s",result.getMethod().getDescription(),
                        " - ", spacedMethodName);

                test = extent.startTest(finalMethodName);

                test.setStartedTime(getTime(result.getStartMillis()));
                test.setEndedTime(getTime(result.getEndMillis()));

                for (String group : result.getMethod().getGroups()) {
                    test.assignCategory(group);
                }

                // This code picks the log from Reporter object
                for (String message : Reporter.getOutput(result)) {
                    if (message.endsWith(".png")) {
                        test.log(LogStatus.INFO, test.addScreenCapture(message));
                    } else {
                        test.log(LogStatus.INFO, message);
                    }
                }

                String message = String.format("%s%s%s", "Test ",
                        status.toString().toLowerCase(), "ed");

                if (result.getThrowable() != null) {
                    test.log(status, result.getThrowable());
                    message = String.format("%s%s%s", result.getThrowable().getClass(), ": ",
                            result.getThrowable().getMessage());

                    // Check if the test case failed, skipped or passed and then attach the
                    // appropriate screenshot
                    // using getAttribute() to the test case result in Extent Report
                    if (result.getStatus() == result.FAILURE || result.getStatus() == result.SKIP) {
                        try {
                            String screenshotPath = (String) result.getAttribute("screenshotPath");
                            test.log(status, test.addScreenCapture(screenshotPath));
                        } catch (Exception e) {
                            BtLogger.info(e.toString());
                        }

                    }
                } else {
                    test.log(status, message);
                }
                try {
                    String path = String.format("%s%s%s", "./logs/",
                            result.getMethod().getMethodName(), ".log");
                    test.log(LogStatus.INFO, String.format("%s%s%s", "<a href=", path,
                            ">Click to view test execution log</a>"));
                } catch (Exception e) {
                    BtLogger.info(e.getStackTrace());
                }
                extent.endTest(test);
            }
        }
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    public static int getFilesCount(File file) {
        File[] files = file.listFiles();
        int count = 0;
        for (File f : files) {
            if (f.isDirectory()) {
                count += getFilesCount(f);
            } else {
                count++;
            }
        }
        return count;
    }
}