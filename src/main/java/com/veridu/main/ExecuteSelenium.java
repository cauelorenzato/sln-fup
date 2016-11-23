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

    public static void waitFor(long millisecs) {
        try {
            Thread.sleep(millisecs);
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException, ScriptError {
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

        try {
            /**
             * Sleeps for one second to be sure everything is ready before opening the first page.
             */
            waitFor(1000);

            /**
             * Login into developers facebook
             */
            driver.get("https://developers.facebook.com/apps/316846622010320/roles/");
            waitFor(1000);
            driver.findElement(By.id("email")).sendKeys(settings.get("email").getAsString());
            driver.findElement(By.id("pass")).sendKeys(settings.get("password").getAsString());
            driver.findElement(By.id("loginbutton")).click();
            /**
             * Sleeps for 3 seconds to be sure the whole page is loaded
             */
            int times = 0;
            while ((times < 3) || (driver.findElement(By.xpath("//a[text()='Add Testers']")).getSize().equals(0))){
                waitFor(3000);
                if (!driver.findElement(By.xpath("//a[text()='Add Testers']")).getSize().equals(0)) {
                    driver.findElement(By.xpath("//a[text()='Add Testers']")).click();
                    times = 3;
                }
                times++;
            }

            times = 0;
            while ((times < 3) || (driver.findElement(By.className("_58al")).getSize().equals(0))) {
                waitFor(3000);
                if (!driver.findElement(By.className("_58al")).getSize().equals(0)) {
                    driver.findElement(By.className("_58al")).sendKeys(run.facebookId);
                    times = 3;
                }
                times++;
            }

            times = 0;
            while ((times < 3) || (driver.findElement(By.xpath("//div[@id='user_id_or_vanitys']")).isEnabled() == false)){
                waitFor(3000);
                if (driver.findElement(By.xpath("//div[@id='user_id_or_vanitys']")).isEnabled()) {
                    driver.findElement(By.xpath("//div[@id='user_id_or_vanitys']")).click();
                    times = 3;
                }
                times++;
            }

            times = 0;
            while ((times < 3) || (driver.findElement(By.xpath("//button[text()='Submit']")).isEnabled() == false)){
                waitFor(3000);
                if (driver.findElement(By.xpath("//button[text()='Submit']")).isEnabled()) {
                    driver.findElement(By.xpath("//button[text()='Submit']")).click();
                    times = 3;
                }
                times++;
            }
            waitFor(2000);
            driver.close();
            driver.quit();
        } catch (Exception ex) {
            driver.close();
            driver.quit();
            throw new ScriptError("Unable to add a new tester: " + ex.getMessage());
        }
    }

    /**
     * Constructor class
     * @param facebookId the facebook id to be added as a tester on facebook.
     */
    public ExecuteSelenium(String facebookId) {
        this.facebookId = facebookId;
    }
}