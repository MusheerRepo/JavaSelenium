package com.qa.util;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.qa.base.TestBase;

public class LogListener extends TestBase implements ITestListener {
    public void onStart(ITestContext context) {
        // On start of test
        BtLogger.info("******************************************************");
        BtLogger.info("******************************************************");
        BtLogger.info("----------Test started: ----------");
        BtLogger.info(context.getName());
        BtLogger.info("******************************************************");
        BtLogger.info("******************************************************");
    }

    public void onTestSuccess(ITestResult result) {
        // On success of test method
        BtLogger.info("******************************************************");
        BtLogger.info("******************************************************");
        BtLogger.info("----------Test method passed: ----------");
        BtLogger.info(result.getName());
        BtLogger.info("******************************************************");
        BtLogger.info("******************************************************");
    }

    public void onTestFailure(ITestResult result) {
        // On failure of test method
        // Add screenshot to testng report
        BtLogger.fatal("******************************************************");
        BtLogger.fatal("******************************************************");
        BtLogger.fatal("----------Test method failed: ----------");
        BtLogger.fatal(result.getName());
        BtLogger.fatal("******************************************************");
        BtLogger.fatal("******************************************************");
        exitCode = false;
    }

    public void onTestSkipped(ITestResult result) {
        // On skip of test method
        BtLogger.info("******************************************************");
        BtLogger.info("******************************************************");
        BtLogger.info("----------Test method failed: ----------");
        BtLogger.info(result.getName());
        BtLogger.info("******************************************************");
        BtLogger.info("******************************************************");
        exitCode = false;
    }

    public void onTestStart(ITestResult result) {
        // On start of test method
        BtLogger.startTestCase(result.getName());
        BtLogger.info("******************************************************");
        BtLogger.info("******************************************************");
        BtLogger.info("----------Test method started: ----------");
        BtLogger.info(result.getName());
        BtLogger.info("******************************************************");
        BtLogger.info("******************************************************");
    }

    public void onFinish(ITestContext context) {
        // On end of test
        BtLogger.info("******************************************************");
        BtLogger.info("******************************************************");
        BtLogger.info("----------Test finished: ----------");
        BtLogger.info(context.getName());
        BtLogger.info("******************************************************");
        BtLogger.info("******************************************************");
    }
}
