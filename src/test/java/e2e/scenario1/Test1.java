package e2e.scenario1;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.TestBase;
import util.Log;

public class Test1 extends TestBase {
    @Test(groups = "Scenario 1")
    public void test1() {
        Log.info("Scenario 1 Test1 log");
        Assert.assertTrue(2==2);
    }

    @Test(groups = "Scenario 1")
    public void test2() {
        Log.info("Scenario 1 Test2 log");
        Assert.assertTrue(2==2);
    }
    @Test(groups = "Scenario 1")
    public void test3() {
        Log.info("Scenario 1 Test3 log");
        Assert.assertTrue(2==2);
    }
}