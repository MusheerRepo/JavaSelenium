package com.qa.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import javax.imageio.ImageIO;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.qa.base.TestBase;

public class MasterUtility extends TestBase {
    public void threadSleep(int i) {
        try {
            BtLogger.info(String.format("%s%s%s", "Sleeping ",
                            i, " seconds"));
            Thread.sleep(i * 1000);
        } catch (Exception e) {
            BtLogger.info("Error in thread.sleep");
        }
    }

    public String randomGenerator(int l) {
        String s = "1";
        for (int i = 1; i <= l; i++) {
            s = s + (int) (Math.random() * 100);
        }
        return s;
    }

    public void executeJsScript(String s) {
        // This will execute the javascript passed
        BtLogger.info(String.format("%s%s", "Excecuting javascript :", s));
        jsE.executeScript(s);
        BtLogger.info(String.format("%s%s", "Executed script", s));
    }

    public void scrollToTop() {
        //This method will scroll the webpage to the top
        BtLogger.info("Scrolling to top...");
        findTheElement(By.cssSelector("body"))
                .sendKeys(Keys.CONTROL, Keys.HOME);
        threadSleep(1);
        BtLogger.info("Scrolled to top");
    }

    public void scrollToBottom() {
        //This method will scroll the webpage to the bottom
        BtLogger.info("Scrolling to bottom...");
        findTheElement(By.cssSelector("body"))
                .sendKeys(Keys.CONTROL, Keys.END);
        threadSleep(1);
        BtLogger.info("Scrolled to bottom");
    }

    public boolean isDisplayed(By locator) {
        BtLogger.info(String.format("%s%s%s", "Checking whether locator :", locator,
                " is displayed"));
        boolean result = driver.get().findElement(locator).isDisplayed();
        BtLogger.info(String.format("%s%s%s", "Checked whether locator :", locator,
                " is displayed"));
        return result;
    }

    public boolean isEnable(By locator) {
        BtLogger.info(String.format("%s%s%s", "Checking the element :", locator,
                " is enabled or not"));
        boolean result = driver.get().findElement(locator).isEnabled();
        BtLogger.info(String.format("%s%s%s", "Checked the element :", locator,
                " is enabled or not : ", result));
        return result;
    }

    public boolean isSelected(By locator) {
        BtLogger.info("Checking the element :" + locator + " is selected or not");
        boolean result = driver.get().findElement(locator).isSelected();
        BtLogger.info("Checked the element :" + locator + " is selected or not" + result);
        return result;
    }

    public String getUrl() {
        BtLogger.info("Fetching URL");
        String result = driver.get().getCurrentUrl();
        BtLogger.info(String.format("%s%s", "Fetched URL : ", result));
        return result;
    }

    public void clickOnElement(By locator) {
        BtLogger.info(String.format("%s%s", "Clicking on element: ", locator));
        untillElementCLickable(locator);
        driver.get().findElement(locator).click();
        BtLogger.info(String.format("%s%s", "Clicked on element: ", locator));
    }

    public WebElement findTheElement(By locator) {
        BtLogger.info(String.format("%s%s", "Finding element: ", locator));
        untillElementPresent(locator);
        WebElement valFindTheElement = driver.get().findElement(locator);
        BtLogger.info(String.format("%s%s", "Found element: ", locator));
        return valFindTheElement;
    }

    public String isElementPresent(By locator) {
        BtLogger.info(String.format("%s%s", "Finding element: ", locator));
        try {
            new WebDriverWait(driver.get(), Duration.ofSeconds(5))
                    .until(ExpectedConditions.presenceOfElementLocated(locator));
            BtLogger.info(String.format("%s%s", "Found element: ", locator));
            return "1";
        } catch (Exception e) {
            BtLogger.info(String.format("%s%s", "Element not found: ", locator));
            return "0";
        }
    }

    public String getTheTitle() {
        BtLogger.info("Fetching the title");
        String title = driver.get().getTitle();
        BtLogger.info(String.format("%s%s", "Fetched the title : ", title));
        return title;
    }

    public int getLocation(By locator, char axis) {
        BtLogger.info(String.format("%s%s",
                "Fetching the location of element :", locator));
        int result;
        if (axis == 'X' || axis == 'x') {
            result = driver.get().findElement(locator).getLocation().getX();
        } else {
            result = driver.get().findElement(locator).getLocation().getY();
        }
        BtLogger.info(String.format("%s%s%s%s",
                "Fetching the location of element :", locator, " : ", result));
        return result;
    }

    public int getSize(By locator, char axis) {
        BtLogger.info(String.format("%s%s",
                "Fetching the size of element :", locator));
        int result;
        if (axis == 'X' || axis == 'x') {
            result = driver.get().findElement(locator).getSize().getWidth();
        } else {
            result = driver.get().findElement(locator).getSize().getHeight();
        }
        BtLogger.info(String.format("%s%s%s%s",
                "Fetching the size of element :", locator, " : ", result));
        return result;
    }

    public void navigateTo(String url) {
        // This method will navigate to the URL passed as an argument
        BtLogger.info(String.format("%s%s", "Navigating to :", url));
        driver.get().navigate().to(url);
        BtLogger.info(String.format("%s%s", "Navigated to :", url));
    }

    public void navigateBack() {
        // This method will navigate to the previous page
        BtLogger.info("Navigating back");
        driver.get().navigate().back();
        BtLogger.info("Navigated back :");
    }

    public List<WebElement> findTheElements(By locator) {
        BtLogger.info(String.format("%s%s", "Finding elements: ", locator));
        List<WebElement> valFindTheElement = driver.get().findElements(locator);
        BtLogger.info(String.format("%s%s", "Found elements: ", locator));
        return valFindTheElement;
    }

    public WebElement findTheElementByTexts(String text) {
        BtLogger.info(String.format("%s%s", "Finding elements with text :", text));
        String locator = String.format("%s%s%s", "//*[text()='", text, "']");
        WebElement result = driver.get().findElement(By.xpath(locator));
        BtLogger.info(String.format("%s%s", "Found elements with text : ", text));
        return result;
    }

    public WebElement findTheElementByTextLike(String text) {
        BtLogger.info(String.format("%s%s", "Finding elements with text :", text));
        String locator = String.format("%s%s%s", "//*[contains(normalize-space() , '",
                text, "')]");
        WebElement result = driver.get().findElement(By.xpath(locator));
        BtLogger.info(String.format("%s%s", "Found elements with text : ", text));
        return result;
    }

    public void clearInputField(By locator) {
        // This method will clear the input field
        BtLogger.info(String.format("%s%s", "Clearing input field of :", locator));
        driver.get().findElement(locator).clear();
        Actions action = new Actions(driver.get());
        action.moveToElement(driver.get().findElement(locator))
            .doubleClick().click().sendKeys(Keys.DELETE).build().perform();
        BtLogger.info(String.format("%s%s", "Cleared input field of :", locator));
    }

    public void sendTheKeys(By locator, String value) {
        BtLogger.info(String.format("%s%s%s%s", "Sending ", value,
                " on element: ", locator));
        driver.get().findElement(locator).sendKeys(value);
        BtLogger.info(String.format("%s%s%s%s", "Sent ", value,
                " on element: ", locator));
    }

    public void sendTheKeys(By locator, Keys value) {
        BtLogger.info(String.format("%s%s%s%s", "Sending keys", value, " on element: ", locator));
        driver.get().findElement(locator).sendKeys(value);
        BtLogger.info(String.format("%s%s%s%s", "Sent keys", value, " on element: ", locator));
    }

    public String getTheText(By locator) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            BtLogger.info(e);
        }
        BtLogger.info(String.format("%s%s", "Fetching the text of element :", locator));
        String valGetTheText = driver.get().findElement(locator).getText();
        BtLogger.info(String.format("%s%s%s%s", "Text fetched of element :", locator,
                " : ", valGetTheText));
        return valGetTheText;
    }

    public String getTheText(WebElement wbE) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            BtLogger.info(e);
        }
        BtLogger.info(String.format("%s%s", "Fetching the text of element :", wbE));
        String valGetTheText = wbE.getText();
        BtLogger.info(String.format("%s%s", "Text fetched of element :", valGetTheText));
        return valGetTheText;
    }

    public String getTheFontColour(By locator) {
        // This method will return the font colour of the webelement
        BtLogger.info(String.format("%s%s", "Fetching colour of element: ", locator));
        WebElement wbE = driver.get().findElement(locator);
        String fontColor = Color.fromString(wbE.getCssValue("color")).asRgb();
        BtLogger.info(String.format("%s%s%s%s", "Colour of element :", locator,
                " is :", fontColor));
        return fontColor;
    }

    public String getTheFontColour(WebElement wbE) {
        // This method will return the font colour of the webelement
        BtLogger.info(String.format("%s%s", "Fetching colour of element: ", wbE));
        String fontColor = Color.fromString(wbE.getCssValue("color")).asRgb();
        BtLogger.info(String.format("%s%s%s%s", "Colour of element :", wbE,
                " is :", fontColor));
        return fontColor;
    }

    public void untillTextVisible(By locator, String value) {
        BtLogger.info(String.format("%s%s%s", "Waiting for the text: ", value,
                " to be visible"));
        new WebDriverWait(driver.get(), Duration.ofSeconds(30))
            .until(ExpectedConditions.textToBe(locator, value));
        BtLogger.info("Text visible");
    }

    public void untillNoOfElementPresent(By locator, int value) {
        BtLogger.info(String.format("%s%s%s%s",
                "Waiting for the number of elements by locator : ", locator,
                " to be : ", value));
        new WebDriverWait(driver.get(), Duration.ofSeconds(15))
                .until(ExpectedConditions.numberOfElementsToBe(locator, value));
        BtLogger.info(String.format("%s%s%s%s",
                "Number of elements by locator : ", locator,
                " are : ", value));
    }

    public void untillUrlToBe(String value) {
        BtLogger.info(String.format("%s%s", "Waiting for the url to be: ", value));
        new WebDriverWait(driver.get(), Duration.ofSeconds(30))
            .until(ExpectedConditions.urlToBe(value));
        BtLogger.info(String.format("%s%s", "URL is now: ", value));
    }

    public void untillPresentOfAllElement(By locator) {
        BtLogger.info(String.format("%s%s", "Waiting for all the element to be present : ",
                locator));
        new WebDriverWait(driver.get(), Duration.ofSeconds(30))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        BtLogger.info(String.format("%s%s", "Element located : ", locator));
    }

    public void untillElementCLickable(By locator) {
        BtLogger.info(String.format("%s%s%s", "Waiting for the element: ", locator,
                " to be clickable"));
        new WebDriverWait(driver.get(), Duration.ofSeconds(30))
            .until(ExpectedConditions.elementToBeClickable(locator));
        BtLogger.info("Element clickable");
    }

    public void untillElementPresent(By locator) {
        BtLogger.info(String.format("%s%s%s", "Waiting for the element: ", locator,
                " to be present"));
        new WebDriverWait(driver.get(), Duration.ofSeconds(30))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
        BtLogger.info("Element present");
    }
    
    public void untillElementRefreshed(By locator) {
        BtLogger.info(String.format("%s%s", "Waiting for the element: ", locator));
        new WebDriverWait(driver.get(), Duration.ofSeconds(30)).until(ExpectedConditions
                .refreshed(ExpectedConditions.stalenessOf(driver.get().findElement(locator))));
        BtLogger.info("Element found");
    }
    
    public void untillElementRefreshed(WebElement wbE) {
        BtLogger.info(String.format("%s%s", "Waiting for the element: ", wbE));
        new WebDriverWait(driver.get(), Duration.ofSeconds(30)).until(ExpectedConditions
                .refreshed(ExpectedConditions.stalenessOf(wbE)));
        BtLogger.info("Element found");
    }

    public void untillElementVisible(By locator) {
        BtLogger.info(String.format("%s%s%s", "Waiting for the element : ", locator,
                " to be visible"));
        new WebDriverWait(driver.get(), Duration.ofSeconds(30)).until(ExpectedConditions
                .visibilityOfElementLocated(locator));
        BtLogger.info("Element visible");
    }
    
    public void untillElementInvisible(By locator) {
        BtLogger.info(String.format("%s%s%s", "Waiting for the element : ", locator,
                " to be invisible"));
        new WebDriverWait(driver.get(), Duration.ofSeconds(30)).until(ExpectedConditions
                .invisibilityOfElementLocated(locator));
        BtLogger.info("Element invisible");
    }
    
    public void untillElementInvisibleByText(By locator, String text) {
        BtLogger.info(String.format("%s%s%s", "Waiting for the element : ", locator,
                " to be invisible"));
        new WebDriverWait(driver.get(), Duration.ofSeconds(30)).until(ExpectedConditions
                .invisibilityOfElementWithText(locator, text));
        BtLogger.info("Element invisible");
    }

    public void untillElementSelectionStateToBe(By locator, boolean state) {
        BtLogger.info(String.format("%s%s%s%s", "Waiting for the element : ",
                locator, " to have state :", state));
        new WebDriverWait(driver.get(), Duration.ofSeconds(30)).until(ExpectedConditions
                .elementSelectionStateToBe(locator, state));
        BtLogger.info(String.format("%s%s%s%s", "Element : ", locator, " state is now :", state));
    }

    public void untillUrlToHave(String url) {
        BtLogger.info(String.format("%s%s%s", "Waiting for URL : ",
                " to have String :", url));
        new WebDriverWait(driver.get(), Duration.ofSeconds(30)).until(ExpectedConditions
                .urlContains(url));
        BtLogger.info(String.format("%s%s", "URL now contains ", url));
    }

    public void untilChangesSaved() {
        //This method will wait until changes are saved on UI
        untillElementVisible(By.xpath("//*[normalize-space()='Saving...']"));
        untillElementVisible(By.xpath("//*[normalize-space()='Successful']"));
        threadSleep(1);
    }
    
    public void letPageLoad() {
        BtLogger.info("Waiting for the page to load");
        try {
            new WebDriverWait(driver.get(), Duration.ofSeconds(30)).until(webDriver ->
                    ((JavascriptExecutor) webDriver).executeScript("return document.readyState")
                            .equals("complete"));
        } catch (Exception e) {
            BtLogger.trace("Error", e);
        }
        BtLogger.info("Page loaded");
    }

    public void assertEqual(String str1, String str2) {
        BtLogger.info(String.format("%s%s%s%s", "Asserting equal ", str1, " ", str2));
        Assert.assertEquals(str1, str2);
        BtLogger.info(String.format("%s%s%s%s", "Asserted equal ", str1, " ", str2));
    }

    public void assertEqual(double str1, double str2) {
        BtLogger.info(String.format("%s%s%s%s", "Asserting equal ", str1, " ", str2));
        Assert.assertEquals(str1, str2);
        BtLogger.info(String.format("%s%s%s%s", "Asserted equal ", str1, " ", str2));
    }

    public void assertNotEqual(String str1, String str2) {
        BtLogger.info(String.format("%s%s%s%s", "Asserting not equal ", str1, " ", str2));
        Assert.assertNotEquals(str1, str2);
        BtLogger.info(String.format("%s%s%s%s", "Asserted not equal ", str1, " ", str2));
    }

    public void assertNotEqual(double num1, double num2) {
        BtLogger.info(String.format("%s%s%s%s", "Asserting not equal ", num1, " ", num2));
        Assert.assertNotEquals(num1, num2);
        BtLogger.info(String.format("%s%s%s%s", "Asserted not equal ", num1, " ", num2));
    }

    public void assertTrue(boolean b) {
        BtLogger.info(String.format("%s%s", "Asserting true :", b));
        Assert.assertTrue(b);
        BtLogger.info(String.format("%s%s", "Asserted true :", b));
    }

    public void assertFalse(boolean b) {
        BtLogger.info(String.format("%s%s", "Asserting false :", b));
        Assert.assertFalse(b);
        BtLogger.info(String.format("%s%s", "Asserted false :", b));
    }

    public String[] downloadFile(String fileUrl) {
        /*
         * This method will download the file from the URL passed as an arguemnt and
         * save the file temporarily for futher process
         */
        BtLogger.info("Trying to save image file ar URL: " + fileUrl);
        String[] fileDetail = new String[2];
        try {
            URL imageUrl = new URL(fileUrl);
            BufferedImage saveImage = ImageIO.read(imageUrl);
            Graphics2D editImage = saveImage.createGraphics();
            int x1 = (int) (Math.random() * 1000);
            int y1 = (int) (Math.random() * 1000);
            int x2 = (int) (Math.random() * 1000);
            int y2 = (int) (Math.random() * 1000);
            editImage.drawLine(x1, y1, x2, y2);
            editImage.dispose();
            fileDetail[0] = "test" + randomGenerator(4) + ".png";
            fileDetail[1] = String.format("%s%s%s", System.getProperty("java.io.tmpdir"),
                    "/", fileDetail[0]);
            BtLogger.info(saveImage);
            ImageIO.write(saveImage, "png", new File(fileDetail[1]));
            BtLogger.info(String.format("%s%s", "File saved at path: ", fileDetail[1]));

        } catch (Exception e) {
            BtLogger.info(e.toString());
        }
        return fileDetail;
    }

    public String[] downloadFileWithSpecialCharacter(String fileUrl) {
        /*
         * This method will download the file from the URL passed as an argument and
         * save the file temporarily for futher process with special characters in name
         */
        BtLogger.info("Trying to save image file ar URL: " + fileUrl);
        String[] fileDetail = new String[2];
        try {
            URL imageUrl = new URL(fileUrl);
            BufferedImage saveImage = ImageIO.read(imageUrl);
            fileDetail[0] = "test!@#" + randomGenerator(4) + ".png";
            fileDetail[1] = String.format("%s%s%s", System.getProperty("java.io.tmpdir"),
                    "/", fileDetail[0]);
            BtLogger.info(saveImage);
            ImageIO.write(saveImage, "png", new File(fileDetail[1]));
            BtLogger.info("File saved at path: " + fileDetail[1]);

        } catch (Exception e) {
            BtLogger.info(e.toString());
        }
        return fileDetail;
    }

    public void reload() {
        // This method will reload the page
        BtLogger.info("Reloading page");
        driver.get().navigate().refresh();
        letPageLoad();
        BtLogger.info("Reloaded page");
    }

    public String getSrc(By locator) {
        // This method will return the src of the image element
        BtLogger.info(String.format("%s%s", "Fetching SRC of element :", locator));
        String src = driver.get().findElement(locator).getAttribute("src");
        BtLogger.info(String.format("%s%s", "Fetched SRC of element :", src));
        return src;
    }

    public String getAttribute(By locator, String attribute) {
        // This method will return the src of the image element
        BtLogger.info(String.format("%s%s%s%s", "Fetching attribute ", attribute,
                " of element : ", locator));
        String att = driver.get().findElement(locator).getAttribute(attribute);
        BtLogger.info(String.format("%s%s%s%s", "Fetched attribute ", attribute,
                " of element : ", locator));
        return att;
    }

    public String getAttribute(WebElement wbE, String attribute) {
        // This method will return the src of the image element
        BtLogger.info(String.format("%s%s%s", "Fetching attribute ",
                " of element : ", wbE.toString()));
        String att = wbE.getAttribute(attribute);
        BtLogger.info(String.format("%s%s%s%s", "Fetched attribute ", att,
                " of element : ", wbE.toString()));
        return att;
    }

    public void switchToActiveElement() {
        BtLogger.info("Switching driver focus to element to active element");
        driver.get().switchTo().activeElement();
        BtLogger.info("Switched driver focus to element to active element");
    }
}
