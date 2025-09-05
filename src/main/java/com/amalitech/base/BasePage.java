package com.amalitech.base;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class BasePage {

    protected AndroidDriver driver;
    protected WebDriverWait wait;

    public BasePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Find element by accessibility ID with wait
     */
    public WebElement findByAccessibilityId(String accessibilityId) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.accessibilityId(accessibilityId)
            ));
        } catch (Exception e) {
            System.err.println("Element not found with accessibility ID: " + accessibilityId);
            throw e;
        }
    }

    /**
     * Find element by UIAutomator with wait
     */
    public WebElement findByUIAutomator(String uiAutomatorString) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.androidUIAutomator(uiAutomatorString)
            ));
        } catch (Exception e) {
            System.err.println("Element not found with UIAutomator: " + uiAutomatorString);
            throw e;
        }
    }

    /**
     * Find element by XPath with wait
     */
    public WebElement findByXPath(String xpath) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.xpath(xpath)
            ));
        } catch (Exception e) {
            System.err.println("Element not found with XPath: " + xpath);
            throw e;
        }
    }

    /**
     * Find element by ID with wait
     */
    public WebElement findById(String id) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.id(id)
            ));
        } catch (Exception e) {
            System.err.println("Element not found with ID: " + id);
            throw e;
        }
    }

    /**
     * Find element by class name with wait
     */
    public WebElement findByClassName(String className) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.className(className)
            ));
        } catch (Exception e) {
            System.err.println("Element not found with class name: " + className);
            throw e;
        }
    }

    /**
     * Wait for element to be clickable
     */
    public WebElement waitForElementToBeClickable(String accessibilityId) {
        return wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId(accessibilityId)
        ));
    }

    /**
     * Wait for element to be visible
     */
    public WebElement waitForElementToBeVisible(String accessibilityId) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.accessibilityId(accessibilityId)
        ));
    }

    /**
     * Check if element exists without throwing exception
     */
    public boolean isElementPresent(String accessibilityId) {
        try {
            driver.findElement(AppiumBy.accessibilityId(accessibilityId));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if element exists using UIAutomator without throwing exception
     */
    public boolean isElementPresentByUIAutomator(String uiAutomatorString) {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(uiAutomatorString));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Safe click method with retry
     */
    public void safeClick(WebElement element) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                element.click();
                break;
            } catch (Exception e) {
                attempts++;
                if (attempts >= 3) {
                    throw e;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Safe send keys method with retry
     */
    public void safeSendKeys(WebElement element, String text) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                element.clear();
                element.sendKeys(text);
                break;
            } catch (Exception e) {
                attempts++;
                if (attempts >= 3) {
                    throw e;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    // Methods to maintain compatibility with existing CartPage and MenuPage

    /**
     * Check if element is displayed by XPath
     */
    public boolean isElementDisplayedByXpath(String xpath) {
        try {
            WebElement element = findByXPath(xpath);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get text by XPath
     */
    public String getTextByXpath(String xpath) {
        try {
            WebElement element = findByXPath(xpath);
            return element.getText();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Click element by XPath
     */
    public void clickByXpath(String xpath) {
        try {
            WebElement element = findByXPath(xpath);
            safeClick(element);
        } catch (Exception e) {
            System.err.println("Failed to click element by XPath: " + xpath);
            throw e;
        }
    }

    /**
     * Check if element is displayed by UI Selector
     */
    public boolean isElementDisplayedByUiSelector(String uiSelector) {
        try {
            WebElement element = findByUIAutomator(uiSelector);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click element by UI Selector
     */
    public void clickByUiSelector(String uiSelector) {
        try {
            WebElement element = findByUIAutomator(uiSelector);
            safeClick(element);
        } catch (Exception e) {
            System.err.println("Failed to click element by UI Selector: " + uiSelector);
            throw e;
        }
    }

    /**
     * Wait for page to load (general purpose method)
     */
    public void waitForPageLoad() {
        try {
            Thread.sleep(2000); // Generic wait - can be customized per page
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}