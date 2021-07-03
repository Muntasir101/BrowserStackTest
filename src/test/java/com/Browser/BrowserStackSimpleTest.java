package com.Browser;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

public class BrowserStackSimpleTest {
    public static final String USERNAME = "muntasir2";
    public static final String AUTOMATE_KEY = "NcKxkDU3bChUX2wrzxLD";
    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

    public static void main(String[] args) throws MalformedURLException {

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("os", "Window");
        caps.setCapability("os_version", "10");
        caps.setCapability("browser", "chrome");
        caps.setCapability("browser_version", "latest");
        caps.setCapability("build", "browserstack-build-1");
        caps.setCapability("name", "Intellij  Test");

        WebDriver driver;
        try {
            driver = new RemoteWebDriver(new URL(URL), caps);
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            // Searching for 'BrowserStack' on google.com
            driver.get("https://www.google.com");
            WebElement element = driver.findElement(By.name("q"));
            element.sendKeys("BrowserStack");
            element.submit();
            // Setting the status of test as 'passed' or 'failed' based on the condition; if title of the web page contains 'BrowserStack'
            WebDriverWait wait = new WebDriverWait(driver, 5);
            try {
                wait.until(ExpectedConditions.titleContains("BrowserStack"));
                jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"Title matched!\"}}");
            } catch (Exception e) {
                jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \"Title not matched\"}}");
            }
            System.out.println(driver.getTitle());
            driver.quit();
        } catch (MalformedURLException e) {
            e.printStackTrace();

        }
    }
}
