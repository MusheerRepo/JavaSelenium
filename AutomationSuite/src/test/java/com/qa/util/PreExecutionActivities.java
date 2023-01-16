package com.qa.util;

import com.google.gson.stream.JsonWriter;
import com.qa.base.TestBase;
import com.qa.pages.projectdetailspages.ProjectDetails;
import com.qa.pages.bmodetailspages.BmoDetails;
import com.qa.pages.Client;
import com.qa.pages.Content;
import com.qa.pages.Home;
import com.qa.pages.Project;
import com.qa.pages.SignIn1;
import com.qa.pages.SignIn2;
import com.qa.pages.UploadModal;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class PreExecutionActivities extends TestBase {

    public static boolean checkTestData(String u) throws Exception {
        //Setting browser instance
        BrowserManager.getBrowser("Chrome");

        //Test data management
        SignIn1.clickOnSignIn();
        SignIn2.enterCredentials();
        MasterUtility muo = new MasterUtility();

        //Checking whether client exists
        Home.clickOnClientButton();
        String result = Client.searchClient(String.format("%s%s",
                prop.getProperty("clientName").split(" ")[0], "Admin"));
        if (result.equalsIgnoreCase("No such client found.")) {
            muo.navigateBack();
            String clientName = String.format("%s%s",
                    prop.getProperty("clientName").split(" ")[0], "Admin");
            Client.addNewClient(clientName);
        }
        Client.goToHomepage();

        //Checking whether project exist or not
        Home.clickOnProjectButton();
        result = Project.searchProject(String.format("%s%s",
                prop.getProperty("projectName").split(" ")[0], "Admin"));
        if (result.equalsIgnoreCase("No such project found.")) {
            muo.navigateBack();
            Project.addNewProject(String.format("%s%s",
                    prop.getProperty("projectName").split(" ")[0], "Admin"));
            //Adding necessary rules in created project
            ProjectDetails.addRules();
        } else {
            Project.selectFirstDisplayedProject();
        }
        Project.goToHomepage();

        //Checking whether files exist or not
        checkAndUpload(prop.getProperty("fileName"));
        checkAndUpload(prop.getProperty("fileInVideoFormat"));
        boolean reprocess = checkAndUpload(prop.getProperty("existingFileForTestScript"));
        checkAndUpload(prop.getProperty("existingFileForTestScript1"));
        checkAndUpload(prop.getProperty("existingFileForTestScript2"));
        checkAndUpload(prop.getProperty("existingFileForTestScript3"));
        checkAndUpload(prop.getProperty("existingFileForTestScript4"));
        driver.get().close();
        driver.get().quit();
        return reprocess;
    }

    //Reprocess of existing file used by test script
    public static void reprocessFile() {
        //Setting browser
        BrowserManager.getBrowser("Chrome");

        //Reprocess existing BMO
        SignIn1.clickOnSignIn();
        SignIn2.enterCredentials();
        BmoManager.getBmo(prop.getProperty("existingFileForTestScript"));
        BmoDetails.reprocessBmo();
        MasterUtility muo = new MasterUtility();
        muo.reload();
        while (!BmoDetails.isBmoProcessed()) {
            muo.threadSleep(30);
            muo.reload();
        }
        driver.get().close();
        driver.get().quit();
    }

    public static boolean checkAndUpload(String file) {
        //Adding correct file extension
        if (file.equalsIgnoreCase(prop.getProperty("fileName"))) {
            file = file.concat(".jpg");
        } else {
            file = file.concat(".mp4");
        }
        boolean reprocess = true;

        //Uploading the file after checking if it exists
        Home.clickOnContentButton();
        String result = Content.searchWithFilename(file.split("\\.")[0]);
        if (result.equalsIgnoreCase("File not found")) {
            //Marking reprocess flag as false if "existingFileForTestScript" is to be uploaded
            if (file.equalsIgnoreCase(prop.getProperty("existingFileForTestScript"))) {
                reprocess = false;
            }

            Content.goToHomepage();
            Home.uploadFile();
            UploadModal.uploadOnModalWindowAsSeperateObject(
                    String.format("%s%s%s", System.getProperty("user.dir"),
                            "/src/main/java/com/qa/testdata/", file), "Display");
        }
        return reprocess;
    }

    public static void createBmo(int num) throws Exception {
        //This method will create new BMO for testing and save it on json file
        String[] filePath;
        MasterUtility muo = new MasterUtility();
        JsonWriter jsonWriter = new JsonWriter(
                new FileWriter(String.format("%s%s%s", System.getProperty("user.dir"),
                        "/src/main/java/com/qa/testdata", "/createdBmo.json")));
        jsonWriter.beginObject();
        int max = 0;

        //Setting browser
        BrowserManager.getBrowser("Chrome");
        SignIn1.clickOnSignIn();
        SignIn2.enterCredentials();

        //Uploading files as seperate objects
        for (int i = 0; i < num - 2; i++) {
            try {
                filePath = muo.downloadFile(prop.getProperty("randomwordUrl"));
                Home.uploadFile();
                if (i % 2 == 0) {
                    UploadModal.uploadOnModalWindowAsSeperateObject(filePath[1], "Display");
                } else {
                    UploadModal.uploadOnModalWindowAsSeperateObject(filePath[1],
                            "Instagram");
                }
                jsonWriter.name(String.format("%s", i));
                jsonWriter.value(String.format("%s",filePath[0].split(".png")[0]));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                i--;
                muo.reload();
            }
            if (max > 10) {
                jsonWriter.endObject();
                jsonWriter.close();
                driver.get().close();
                driver.get().quit();
                throw new Exception("BMO creation failed");
            }
            max++;
        }

        //Upload 2 files as single object for BMO carousel test scenario
        try {
            List<String> filePaths = new ArrayList<String>();
            String[] file1 = muo.downloadFile(prop.getProperty("randomwordUrl"));
            String[] file2 = muo.downloadFile(prop.getProperty("randomwordUrl"));
            filePaths.add(file1[1]);
            filePaths.add(file2[1]);
            Home.uploadFile();
            UploadModal.uploadOnModalWindowAsSingleObject(filePaths);
            jsonWriter.name(String.format("%s", num - 2));
            jsonWriter.value(String.format("%s", file1[0].split(".png")[0]));
            jsonWriter.name(String.format("%s", num - 1));
            jsonWriter.value(String.format("%s", file2[0].split(".png")[0]));
        } catch (Exception e) {
            jsonWriter.endObject();
            jsonWriter.close();
            driver.get().close();
            driver.get().quit();
            throw new Exception("BMO creation failed");
        }

        jsonWriter.endObject();
        jsonWriter.close();
        driver.get().close();
        driver.get().quit();
    }

    public static void createProject(int num) throws Exception {
        //This method will create new project which will be used for testing purposes
        MasterUtility muo = new MasterUtility();
        JsonWriter jsonWriter = new JsonWriter(
                new FileWriter(String.format("%s%s%s", System.getProperty("user.dir"),
                        "/src/main/java/com/qa/testdata", "/createdProject.json")));
        jsonWriter.beginObject();
        int max = 0;

        //Setting browser
        BrowserManager.getBrowser("Chrome");
        SignIn1.clickOnSignIn();
        SignIn2.enterCredentials();
        Home.clickOnProjectButton();
        for (int i = 0; i < num; i++) {
            try {
                String projectName = String.format("%s%s",
                        prop.getProperty("projectName"), muo.randomGenerator(3));
                Project.addNewProject(projectName);
                muo.navigateBack();
                jsonWriter.name(String.format("%s", i));
                jsonWriter.value(String.format("%s", projectName));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                i--;
                muo.reload();
            }
            if (max > 10) {
                jsonWriter.endObject();
                jsonWriter.close();
                driver.get().close();
                driver.get().quit();
                throw new Exception("Project creation failed");
            }
            max++;
        }
        jsonWriter.endObject();
        jsonWriter.close();
        driver.get().close();
        driver.get().quit();
    }
}
