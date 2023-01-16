package com.qa.util;

import com.qa.base.TestBase;
import com.qa.pages.Home;
import com.qa.pages.Project;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

public class ProjectManager extends TestBase {
    public static void getProject(int index) {
        if (xmlSuite.equalsIgnoreCase("Regression.xml")) {
            try {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(new FileReader(String
                        .format("%s%s%s", System.getProperty("user.dir"),
                        "/src/main/java/com/qa/testdata", "/createdProject.json")));
                JSONObject jsonObject = (JSONObject) obj;
                String bmo = (String) jsonObject.get(Integer.toString(index));
                Home.clickOnProjectButton();
                Project.searchProject(bmo);
                Project.selectFirstDisplayedProject();
            } catch (Exception e) {
                BtLogger.trace("Error", e);
            }
        } else {
            MasterUtility muo = new MasterUtility();
            Home.clickOnProjectButton();
            String projectName = String.format("%s%s",
                    prop.getProperty("projectName"), muo.randomGenerator(3));
            Project.addNewProject(projectName);
        }
    }
}
