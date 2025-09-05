package com.amalitech.utils;

import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WaitUtils {

    private final AndroidDriver driver;
    private final WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(WaitUtils.class);
    private static final int DEFAULT_TIMEOUT = 15;

    public WaitUtils(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    /**
     * Wait for element to be visible
     */
    public WebElement waitForElementToBeVisible(By locator) {
        return waitForElementToBeVisible(locator, DEFAULT_TIMEOUT);
    }

    /**
     * Wait for element to be visible with custom timeout
     */
    public WebElement waitForElementToBeVisible(By locator, int timeoutInSeconds) {
        logger.debug("Waiting for element to be visible: " + locator.toString() + " with timeout: " + timeoutInSeconds);
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be clickable
     */
    public WebElement waitForElementToBeClickable(By locator) {
        return waitForElementToBeClickable(locator, DEFAULT_TIMEOUT);
    }

    /**
     * Wait for element to be clickable with custom timeout
     */
    public WebElement waitForElementToBeClickable(By locator, int timeoutInSeconds) {
        logger.debug("Waiting for element to be clickable: " + locator.toString() + " with timeout: " + timeoutInSeconds);
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return customWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait for element to be present
     */
    public WebElement waitForElementToBePresent(By locator) {
        return waitForElementToBePresent(locator, DEFAULT_TIMEOUT);
    }

    /**
     * Wait for element to be present with custom timeout
     */
    public WebElement waitForElementToBePresent(By locator, int timeoutInSeconds) {
        logger.debug("Waiting for element to be present: " + locator.toString() + " with timeout: " + timeoutInSeconds);
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return customWait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Wait for elements to be visible
     */
    public List<WebElement> waitForElementsToBeVisible(By locator) {
        return waitForElementsToBeVisible(locator, DEFAULT_TIMEOUT);
    }

    /**
     * Wait for elements to be visible with custom timeout
     */
    public List<WebElement> waitForElementsToBeVisible(By locator, int timeoutInSeconds) {
        logger.debug("Waiting for elements to be visible: " + locator.toString() + " with timeout: " + timeoutInSeconds);
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return customWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /**
     * Wait for text to be present in element
     */
    public boolean waitForTextToBePresentInElement(By locator, String text) {
        return waitForTextToBePresentInElement(locator, text, DEFAULT_TIMEOUT);
    }

    /**
     * Wait for text to be present in element with custom timeout
     */
    public boolean waitForTextToBePresentInElement(By locator, String text, int timeoutInSeconds) {
        logger.debug("Waiting for text '" + text + "' to be present in element: " + locator.toString());
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return customWait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    /**
     * Wait for element to be invisible
     */
    public boolean waitForElementToBeInvisible(By locator) {
        return waitForElementToBeInvisible(locator, DEFAULT_TIMEOUT);
    }

    /**
     * Wait for element to be invisible with custom timeout
     */
    public boolean waitForElementToBeInvisible(By locator, int timeoutInSeconds) {
        logger.debug("Waiting for element to be invisible: " + locator.toString() + " with timeout: " + timeoutInSeconds);
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return customWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Wait for element attribute to contain specific value
     */
    public boolean waitForElementAttributeToContain(By locator, String attribute, String value) {
        return waitForElementAttributeToContain(locator, attribute, value, DEFAULT_TIMEOUT);
    }

    /**
     * Wait for element attribute to contain specific value with custom timeout
     */
    public boolean waitForElementAttributeToContain(By locator, String attribute, String value, int timeoutInSeconds) {
        logger.debug("Waiting for element attribute '" + attribute + "' to contain '" + value + "' in element: " + locator.toString());
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return customWait.until(ExpectedConditions.attributeContains(locator, attribute, value));
    }

    /**
     * Hard wait - use sparingly
     */
    public void hardWait(int seconds) {
        try {
            logger.debug("Hard wait for " + seconds + " seconds");
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted during hard wait", e);
        }
    }

    /**
     * Wait with custom condition
     */
    public <T> T waitUntil(java.util.function.Function<? super org.openqa.selenium.WebDriver, T> condition) {
        return waitUntil(condition, DEFAULT_TIMEOUT);
    }

    /**
     * Wait with custom condition and timeout
     */
    public <T> T waitUntil(java.util.function.Function<? super org.openqa.selenium.WebDriver, T> condition, int timeoutInSeconds) {
        logger.debug("Waiting with custom condition, timeout: " + timeoutInSeconds);
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return customWait.until(condition);
    }

    /**
     * Wait with custom condition specific to AndroidDriver
     */
    public <T> T waitUntilAndroid(java.util.function.Function<AndroidDriver, T> condition) {
        return waitUntilAndroid(condition, DEFAULT_TIMEOUT);
    }

    /**
     * Wait with custom condition specific to AndroidDriver with timeout
     */
    public <T> T waitUntilAndroid(java.util.function.Function<AndroidDriver, T> condition, int timeoutInSeconds) {
        logger.debug("Waiting with Android-specific custom condition, timeout: " + timeoutInSeconds);
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return customWait.until(webDriver -> condition.apply(driver));
    }
}