package util;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomListener implements ITestListener {
    public void onStart(ITestContext context) {
        // On start of test
        System.out.println("----------Test started: ----------");
        System.out.println(context.getName());
    }

    public void onTestSuccess(ITestResult result) {
        // On success of test method
        System.out.println("----------Test method passed: ----------");
        System.out.println(result.getName());
    }

    public void onTestFailure(ITestResult result) {
        // On failure of test method
        System.out.println("----------Test method failed: ----------");
        System.out.println(result.getName());
    }

    public void onTestSkipped(ITestResult result) {
        // On skip of test method
        System.out.println("----------Test method failed: ----------");
        System.out.println(result.getName());
    }

    public void onTestStart(ITestResult result) {
        // On start of test method
        Log.startTestCase(result.getName());
        System.out.println("----------Test method started: ----------");
        System.out.println(result.getName());
    }

    public void onFinish(ITestContext context) {
        // On end of test
        System.out.println("----------Test finished: ----------");
        System.out.println(context.getName());
    }    
}
