package com.veridu.main;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.google.gson.JsonObject;
import exceptions.ScriptError;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class ExecuteSelenium {

    /**
     * Facebook id provided by the fakeus portal
     */
    private String facebookId;

    /**
     * Constructor class
     * @param facebookId the facebook id to be added as a tester on facebook.
     */
    public ExecuteSelenium(String facebookId) {
        this.facebookId = facebookId;
    }

    public static void main(String[] args) throws InterruptedException, IOException, ScriptError {
        try {
            ExecuteSelenium run = new ExecuteSelenium("100014015832537");

            /**
             * Reads environment settings from json file.
             */
            JsonObject settings = JsonReaderHelper.create().read();
            /**
             * Sets the driver to be used and the path.
             */
            System.setProperty("webdriver.gecko.driver", "geckodriver");
            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability("marionette", true);
            WebDriver driver = new FirefoxDriver(capabilities);
            /**
             * Sleeps for one second to be sure everything is ready before opening the first page.
             */
            driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

            /**
             * Login into developers facebook
             */
            driver.get("https://developers.facebook.com/apps/316846622010320/roles/");
            driver.findElement(By.id("email")).sendKeys(settings.get("email").getAsString());
            driver.findElement(By.id("pass")).sendKeys(settings.get("password").getAsString());
            driver.findElement(By.id("loginbutton")).click();
            /**
             * Sleeps for 5 seconds to be sure the whole page is loaded
             */
            Thread.sleep(3000);
            driver.findElement(By.xpath("//a[text()='Add Testers']")).click();
            Thread.sleep(3000);
            WebElement element = driver.findElement(By.className("_58al"));
            element.sendKeys(run.facebookId);
            Thread.sleep(3000);
            driver.findElement(By.xpath("//div[@id='user_id_or_vanitys']")).click();
            Thread.sleep(3000);
            driver.findElement(By.xpath("//button[text()='Submit']")).click();
            driver.close();
        } catch (Exception ex) {
            throw new ScriptError("Unable to add a new tester: " + ex.getMessage());
        }
    }
}