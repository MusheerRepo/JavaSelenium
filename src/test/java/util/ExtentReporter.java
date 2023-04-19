package util;

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
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReporter implements IReporter{
    ExtentReports extent;

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
                               String outputDirectory) {
        extent = new ExtentReports(String.format("%s%s", outputDirectory,
                "/Extent_Report/Extent.html"), true);
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> result = suite.getResults();
            for (ISuiteResult r : result.values()) {
                ITestContext context = r.getTestContext();
                try {
                    buildTestNodes(context.getPassedTests(), LogStatus.PASS);
                    buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
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

                // Get the method name and store it in a string.
                String methodName = result.getMethod().getMethodName();

                test = extent.startTest(methodName);

                test.setStartedTime(getTime(result.getStartMillis()));
                test.setEndedTime(getTime(result.getEndMillis()));

                for (String group : result.getMethod().getGroups()) {
                    test.assignCategory(group);
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
                            Log.info(e.toString());
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
                    Log.info(e.getStackTrace());
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
    
}
