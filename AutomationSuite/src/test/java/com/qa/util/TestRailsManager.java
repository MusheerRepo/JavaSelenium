package com.qa.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.qa.base.TestBase;
import com.qa.testrails.ApiClient;
import com.qa.testrails.ApiException;

public class TestRailsManager {

    public static String testRunId = TestBase.testRunId;
    public static String testrailsUsername = TestBase.testrailsUsername;
    public static String testrailsPassword = TestBase.testrailsPassword;
    public static String railsEngineUrl = TestBase.railsEngineUrl;
    public static final int testCasePassedStatus = 1;
    public static final int testCaseFailedStatus = 5;

    public static void addResultForTestCase(String testCaseId, int status, String error)
            throws IOException, ApiException {

        ApiClient client = new ApiClient(railsEngineUrl);
        client.setUser(testrailsUsername);
        client.setPassword(testrailsPassword);
        Map data = new HashMap();
        data.put("status_id", status);
        data.put("comment", "Test Executed - Status updated automatically"
                + " from Selenium test automation. \n" + error);
        String localTestRunId = testRunId;
        client.sendPost("add_result_for_case/" + localTestRunId + "/" + testCaseId + "", data);
    }

}