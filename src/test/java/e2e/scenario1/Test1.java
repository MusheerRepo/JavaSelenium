package e2e.scenario1;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.TestBase;

public class Test1 extends TestBase {
    @Test(groups = "Scenario 1")
    public void test1() {
        Assert.assertTrue(2==2);
    }

    @Test(groups = "Scenario 1")
    public void test2() {
        Assert.assertTrue(2==2);
    }
    @Test(groups = "Scenario 1")
    public void test3() {
        Assert.assertTrue(2==2);
    }
}