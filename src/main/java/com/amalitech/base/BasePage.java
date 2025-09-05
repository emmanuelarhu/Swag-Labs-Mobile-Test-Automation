package com.amalitech.base;

import com.amalitech.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected AndroidDriver driver;
    protected WaitUtils waitUtils;
    private static final Logger logger = LogManager.getLogger(BasePage.class);

    public BasePage(AndroidDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Find element by accessibility id
     */
    protected WebElement findByAccessibilityId(String accessibilityId) {
        logger.debug("Finding element by accessibility id: " + accessibilityId);
        return driver.findElement(AppiumBy.accessibilityId(accessibilityId));
    }

    /**
     * Find elements by accessibility id
     */
    protected List<WebElement> findElementsByAccessibilityId(String accessibilityId) {
        logger.debug("Finding elements by accessibility id: " + accessibilityId);
        return driver.findElements(AppiumBy.accessibilityId(accessibilityId));
    }

    /**
     * Find element by xpath
     */
    protected WebElement findByXpath(String xpath) {
        logger.debug("Finding element by xpath: " + xpath);
        return driver.findElement(By.xpath(xpath));
    }

    /**
     * Find element by UiSelector
     */
    protected WebElement findByUiSelector(String uiSelector) {
        logger.debug("Finding element by UiSelector: " + uiSelector);
        return driver.findElement(AppiumBy.androidUIAutomator(uiSelector));
    }

    /**
     * Wait and click element by accessibility id
     */
    protected void clickByAccessibilityId(String accessibilityId) {
        logger.info("Clicking element by accessibility id: " + accessibilityId);
        WebElement element = waitUtils.waitForElementToBeClickable(AppiumBy.accessibilityId(accessibilityId));
        element.click();
    }

    /**
     * Wait and click element by xpath
     */
    protected void clickByXpath(String xpath) {
        logger.info("Clicking element by xpath: " + xpath);
        WebElement element = waitUtils.waitForElementToBeClickable(By.xpath(xpath));
        element.click();
    }

    /**
     * Wait and click element by UiSelector
     */
    protected void clickByUiSelector(String uiSelector) {
        logger.info("Clicking element by UiSelector: " + uiSelector);
        WebElement element = waitUtils.waitForElementToBeClickable(AppiumBy.androidUIAutomator(uiSelector));
        element.click();
    }

    /**
     * Send keys to element by accessibility id
     */
    protected void sendKeysByAccessibilityId(String accessibilityId, String text) {
        logger.info("Sending keys to element by accessibility id: " + accessibilityId + " with text: " + text);
        WebElement element = waitUtils.waitForElementToBeVisible(AppiumBy.accessibilityId(accessibilityId));
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Get text from element by accessibility id
     */
    protected String getTextByAccessibilityId(String accessibilityId) {
        logger.debug("Getting text from element by accessibility id: " + accessibilityId);
        WebElement element = waitUtils.waitForElementToBeVisible(AppiumBy.accessibilityId(accessibilityId));
        return element.getText();
    }

    /**
     * Get text from element by xpath
     */
    protected String getTextByXpath(String xpath) {
        logger.debug("Getting text from element by xpath: " + xpath);
        WebElement element = waitUtils.waitForElementToBeVisible(By.xpath(xpath));
        return element.getText();
    }

    /**
     * Check if element is displayed by accessibility id
     */
    public boolean isElementDisplayedByAccessibilityId(String accessibilityId) {
        try {
            WebElement element = waitUtils.waitForElementToBeVisible(AppiumBy.accessibilityId(accessibilityId), 5);
            return element.isDisplayed();
        } catch (Exception e) {
            logger.debug("Element not found or not displayed: " + accessibilityId);
            return false;
        }
    }

    /**
     * Check if element is displayed by xpath
     */
    public boolean isElementDisplayedByXpath(String xpath) {
        try {
            WebElement element = waitUtils.waitForElementToBeVisible(By.xpath(xpath), 5);
            return element.isDisplayed();
        } catch (Exception e) {
            logger.debug("Element not found or not displayed: " + xpath);
            return false;
        }
    }

    /**
     * Check if element is displayed by UiSelector - MADE PUBLIC
     */
    public boolean isElementDisplayedByUiSelector(String uiSelector) {
        try {
            WebElement element = waitUtils.waitForElementToBeVisible(AppiumBy.androidUIAutomator(uiSelector), 5);
            return element.isDisplayed();
        } catch (Exception e) {
            logger.debug("Element not found or not displayed: " + uiSelector);
            return false;
        }
    }

    /**
     * Scroll down on the screen
     */
    protected void scrollDown() {
        logger.info("Scrolling down");
        Dimension size = driver.manage().window().getSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence scroll = new Sequence(finger, 1);
        scroll.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        scroll.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        scroll.addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), startX, endY));
        scroll.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(List.of(scroll));
    }

    /**
     * Scroll to element and click
     */
    protected void scrollToElementAndClick(By by) {
        logger.info("Scrolling to element and clicking: " + by.toString());
        WebElement element = waitUtils.waitForElementToBePresent(by);
        scrollToElement(element);
        element.click();
    }

    /**
     * Scroll to specific element
     */
    private void scrollToElement(WebElement element) {
        Point location = element.getLocation();
        Dimension size = driver.manage().window().getSize();

        if (location.getY() > size.getHeight() * 0.8) {
            scrollDown();
        }
    }

    /**
     * Wait for page to load
     */
    protected void waitForPageLoad() {
        logger.debug("Waiting for page to load");
        try {
            Thread.sleep(2000); // Basic wait - can be enhanced with better logic
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted during wait", e);
        }
    }
}