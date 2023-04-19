package e2e;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.TestBase;
import util.Log;

public class Test3 extends TestBase {
    @Test
    public void test7() {
        Log.info("Test7 log");
        Assert.assertTrue(2==2);
    }

    @Test
    public void test8() {
        Log.info("Test8 log");
        Assert.assertTrue(2==2);
    }
    @Test
    public void test9() {
        Log.info("Test9 log");
        Assert.assertTrue(2==2);
    }
}
