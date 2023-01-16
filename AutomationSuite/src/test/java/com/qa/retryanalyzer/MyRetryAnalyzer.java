package com.qa.retryanalyzer;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import com.qa.base.TestBase;
import com.qa.util.BtLogger;

public class MyRetryAnalyzer extends TestBase implements IRetryAnalyzer {
    private int count = 0;

    public boolean retry(ITestResult iiTestResult) {
        if (!iiTestResult.isSuccess()) { // Check if test not succeed
            if (count < maxTry) { // Check if max try count is reached
                count++; // Increase the maxTry count by 1
                iiTestResult.setStatus(ITestResult.FAILURE);
                BtLogger.info("Test failed " + count + " times");
                BtLogger.info("Retrying..."); // Mark test as failed
                return true; // Tells TestNG to re-run the test
            } else {
                BtLogger.info("Test failed more than max retry limit");
                
                // If maxCount reached,test marked as failed
                iiTestResult.setStatus(ITestResult.FAILURE);
            }
        } else {
            // If test passes, TestNG marks it as passed
            iiTestResult.setStatus(ITestResult.SUCCESS);
        }
        return false;
    }
}