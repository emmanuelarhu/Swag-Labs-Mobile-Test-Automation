package com.amalitech.pages;

import com.amalitech.base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.Point;
import java.time.Duration;
import java.util.Arrays;

public class CheckoutPage extends BasePage {

    // Locators
    private final String FIRST_NAME_FIELD = "test-First Name";
    private final String LAST_NAME_FIELD = "test-Last Name";
    private final String ZIP_CODE_FIELD = "test-Zip/Postal Code";
    private final String CONTINUE_BUTTON = "test-CONTINUE";
    private final String FINISH_BUTTON = "test-FINISH";
    private final String BACK_HOME_BUTTON = "test-BACK HOME";
    private final String CHECKOUT_INFO_TITLE = "new UiSelector().text(\"CHECKOUT: YOUR INFORMATION\")";
    private final String CHECKOUT_OVERVIEW_TITLE = "new UiSelector().text(\"CHECKOUT: OVERVIEW\")";
    private final String CHECKOUT_COMPLETE_TITLE = "new UiSelector().text(\"CHECKOUT: COMPLETE!\")";
    private final String FINISH_BUTTON_TEXT = "new UiSelector().text(\"FINISH\")";
    private final String CANCEL_BUTTON_TEXT = "new UiSelector().text(\"CANCEL\")";

    public CheckoutPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Wait for checkout page to load - compatibility method
     */
    public void waitForCheckoutInformationPageToLoad() {
        waitForCheckoutPage();
    }

    /**
     * Wait for checkout complete page to load - compatibility method
     */
    public void waitForCheckoutCompletePageToLoad() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.androidUIAutomator(CHECKOUT_COMPLETE_TITLE)
            ));
        } catch (Exception e) {
            System.err.println("Checkout complete page failed to load: " + e.getMessage());
        }
    }

    /**
     * Check if checkout information page is displayed - compatibility method
     */
    public boolean isCheckoutInformationPageDisplayed() {
        try {
            WebElement titleElement = findByUIAutomator(CHECKOUT_INFO_TITLE);
            return titleElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if checkout overview page is displayed - compatibility method
     */
    public boolean isCheckoutOverviewPageDisplayed() {
        try {
            WebElement titleElement = findByUIAutomator(CHECKOUT_OVERVIEW_TITLE);
            return titleElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if checkout complete page is displayed - compatibility method
     */
    public boolean isCheckoutCompletePageDisplayed() {
        try {
            WebElement titleElement = findByUIAutomator(CHECKOUT_COMPLETE_TITLE);
            return titleElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get checkout information title - compatibility method
     */
    public String getCheckoutInformationTitle() {
        try {
            WebElement titleElement = findByUIAutomator(CHECKOUT_INFO_TITLE);
            return titleElement.getText();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Get checkout complete title - compatibility method
     */
    public String getCheckoutCompleteTitle() {
        try {
            WebElement titleElement = findByUIAutomator(CHECKOUT_COMPLETE_TITLE);
            return titleElement.getText();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Check if back home button is displayed - compatibility method
     */
    public boolean isBackHomeButtonDisplayed() {
        try {
            WebElement button = findByAccessibilityId(BACK_HOME_BUTTON);
            return button.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Validate checkout information fields - compatibility method
     */
    public boolean validateCheckoutInformationFields() {
        try {
            findByAccessibilityId(FIRST_NAME_FIELD);
            findByAccessibilityId(LAST_NAME_FIELD);
            findByAccessibilityId(ZIP_CODE_FIELD);
            findByAccessibilityId(CONTINUE_BUTTON);
            System.out.println("All checkout information fields validated successfully");
            return true;
        } catch (Exception e) {
            System.err.println("Failed to validate checkout information fields: " + e.getMessage());
            return false;
        }
    }

    /**
     * Fill checkout information with defaults - compatibility method
     */
    public boolean fillCheckoutInformationWithDefaults() {
        try {
            fillShippingInfo("John", "Doe", "12345");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click continue button - compatibility method
     */
    public void clickContinueButton() {
        clickContinue();
    }

    /**
     * Click finish button - compatibility method
     */
    public void clickFinishButton() {
        clickFinish();
    }

    /**
     * Click back home button - compatibility method
     */
    public void clickBackHomeButton() {
        clickBackHome();
    }

    /**
     * Wait for checkout page to load
     */
    public void waitForCheckoutPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.accessibilityId(FIRST_NAME_FIELD)
        ));
    }

    /**
     * Fill shipping information
     */
    public void fillShippingInfo(String firstName, String lastName, String zipCode) {
        try {
            waitForCheckoutPage();

            WebElement firstNameField = findByAccessibilityId(FIRST_NAME_FIELD);
            firstNameField.clear();
            firstNameField.sendKeys(firstName);

            WebElement lastNameField = findByAccessibilityId(LAST_NAME_FIELD);
            lastNameField.clear();
            lastNameField.sendKeys(lastName);

            WebElement zipCodeField = findByAccessibilityId(ZIP_CODE_FIELD);
            zipCodeField.clear();
            zipCodeField.sendKeys(zipCode);

            System.out.println("Filled shipping info: " + firstName + " " + lastName + ", " + zipCode);
        } catch (Exception e) {
            System.err.println("Failed to fill shipping info: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Click continue button
     */
    public void clickContinue() {
        try {
            WebElement continueButton = findByAccessibilityId(CONTINUE_BUTTON);
            continueButton.click();
            System.out.println("Clicked continue button");
        } catch (Exception e) {
            System.err.println("Failed to click continue: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Scroll down to find and click finish button
     */
    public void clickFinish() {
        try {
            // First try to find the finish button without scrolling
            try {
                WebElement finishButton = findByAccessibilityId(FINISH_BUTTON);
                finishButton.click();
                System.out.println("Clicked finish button");
                return;
            } catch (Exception e) {
                System.out.println("Finish button not visible, scrolling down...");
            }

            // If not found, scroll down to reveal the finish button
            scrollToFinishButton();

            // Try again after scrolling
            WebElement finishButton = findByAccessibilityId(FINISH_BUTTON);
            finishButton.click();
            System.out.println("Clicked finish button after scrolling");

        } catch (Exception e) {
            System.err.println("Failed to click finish button: " + e.getMessage());

            // Try alternative approach - look for FINISH text
            try {
                WebElement finishByText = findByUIAutomator(FINISH_BUTTON_TEXT);
                finishByText.click();
                System.out.println("Clicked finish button using text locator");
            } catch (Exception e2) {
                System.err.println("Failed with text locator too: " + e2.getMessage());
                throw e;
            }
        }
    }

    /**
     * Scroll down to reveal the finish button
     */
    private void scrollToFinishButton() {
        try {
            // Scroll down from middle of screen to reveal finish button
            Point start = new Point(540, 1500);
            Point end = new Point(540, 800);
            performSwipe(start, end);

            // Wait a moment for the scroll to complete
            Thread.sleep(1000);

            System.out.println("Scrolled down to reveal finish button");
        } catch (Exception e) {
            System.err.println("Failed to scroll: " + e.getMessage());
        }
    }

    /**
     * Perform swipe gesture
     */
    private void performSwipe(Point start, Point end) {
        try {
            final var finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            var swipe = new Sequence(finger, 1);
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                    PointerInput.Origin.viewport(), start.getX(), start.getY()));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000),
                    PointerInput.Origin.viewport(), end.getX(), end.getY()));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(Arrays.asList(swipe));
        } catch (Exception e) {
            System.err.println("Failed to perform swipe: " + e.getMessage());
        }
    }

    /**
     * Click back home button
     */
    public void clickBackHome() {
        try {
            WebElement backHomeButton = findByAccessibilityId(BACK_HOME_BUTTON);
            backHomeButton.click();
            System.out.println("Clicked back home button");
        } catch (Exception e) {
            System.err.println("Failed to click back home: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Complete checkout process
     */
    public void completeCheckout(String firstName, String lastName, String zipCode) {
        fillShippingInfo(firstName, lastName, zipCode);
        clickContinue();

        // Wait for overview page and then finish
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        clickFinish();

        // Wait for completion page
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        clickBackHome();
    }
}