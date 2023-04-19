package e2e.scenario2;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.TestBase;
import util.Log;

public class Test2 extends TestBase {
    @Test(groups = "Scenario 2")
    public void test4() {
        Log.info("Scenario 2 Test4 log");
        Assert.assertTrue(2==2);
    }

    @Test(groups = "Scenario 2")
    public void test5() {
        Log.info("Scenario 2 Test5 log");
        Assert.assertTrue(2==2);
    }
    @Test(groups = "Scenario 2")
    public void test6() {
        Log.info("Scenario 2 Test6 log");
        Assert.assertTrue(2==2);
    }
}