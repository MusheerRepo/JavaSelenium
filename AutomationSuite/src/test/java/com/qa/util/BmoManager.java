package com.qa.util;

import com.qa.base.TestBase;
import com.qa.pages.Content;
import com.qa.pages.Home;
import com.qa.pages.UploadModal;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

public class BmoManager extends TestBase {
    //This class will return the BMO which will be used by tests

    public static String getBmo(int index) {
        if (xmlSuite.equalsIgnoreCase("Regression.xml")) {
            try {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(new FileReader(String.format("%s%s%s", System.getProperty("user.dir"),
                        "/src/main/java/com/qa/testdata", "/createdBmo.json")));
                JSONObject jsonObject = (JSONObject) obj;
                String bmo = (String) jsonObject.get(Integer.toString(index));
                Home.clickOnContentButton();
                Content.selectSearchedFileImage(bmo);
                return bmo;
            } catch (Exception e) {
                BtLogger.trace("Error", e);
                return null;
            }
        } else {
            MasterUtility muo = new MasterUtility();
            String[] filePath = muo.downloadFile(prop.getProperty("randomwordUrl"));
            Home.uploadFile();
            if (index % 2 == 0) {
                UploadModal.uploadOnModalWindowAsSeperateObject(filePath[1], "Display");
            } else {
                UploadModal.uploadOnModalWindowAsSeperateObject(filePath[1], "Instagram");
            }
            Home.clickOnContentButton();
            Content.selectSearchedFileImage(filePath[0].split(".png")[0]);
            return filePath[0].split(".png")[0];
        }
    }

    public static void getBmo(String existingFile) {
        Home.clickOnContentButton();
        Content.selectSearchedFileImage(existingFile);
    }
}
