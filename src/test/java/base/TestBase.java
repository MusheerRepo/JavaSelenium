package base;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class TestBase {
    /*
    private static ThreadLocal<ExtentReports> extent = new ThreadLocal<>();
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @BeforeSuite
    public void beforeSuite() {
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(spark);
        extent.set(new Threadlocal)
        extent.set(extentReports);
    }

    @BeforeMethod
    public void beforeMethod(ITestResult result) {
        ExtentTest extentTest = extent.get().createTest(result.getMethod().getMethodName());
        test.set(new ThreadLocal<ExtentTest>());
        test.get().set(extentTest);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.get().fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.get().skip(result.getThrowable());
        } else {
            test.get().pass("Test passed");
        }
        extent.get().flush();
    }

    @AfterSuite
    public void afterSuite() {
        extent.get().flush();
    }*/
}
