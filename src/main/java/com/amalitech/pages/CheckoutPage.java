package com.amalitech.pages;

import com.amalitech.base.BasePage;
import com.amalitech.constants.AppConstants;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class CheckoutPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(CheckoutPage.class);

    // Page elements
    private final String CHECKOUT_INFO_TITLE_XPATH = "//android.widget.TextView[@text='CHECKOUT: INFORMATION']";
    private final String CHECKOUT_OVERVIEW_TITLE_XPATH = "//android.widget.TextView[@text='CHECKOUT: OVERVIEW']";
    private final String CHECKOUT_COMPLETE_TITLE_XPATH = "//android.widget.TextView[@text='CHECKOUT: COMPLETE!']";
    private final String FINISH_BUTTON_XPATH = "//android.widget.TextView[@text='FINISH']";
    private final String BACK_HOME_BUTTON_XPATH = "//android.widget.TextView[@text='BACK HOME']";
    private final String CANCEL_BUTTON_XPATH = "//android.widget.TextView[@text='CANCEL']";
    private final String THANK_YOU_MESSAGE_XPATH = "//android.widget.TextView[contains(@text, 'THANK YOU FOR YOU ORDER')]";

    public CheckoutPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Fill in checkout information
     */
    public CheckoutPage fillCheckoutInformation(String firstName, String lastName, String zipCode) {
        logger.info("Filling checkout information - First Name: " + firstName + ", Last Name: " + lastName + ", Zip: " + zipCode);

        sendKeysByAccessibilityId(AppConstants.TEST_FIRST_NAME, firstName);
        sendKeysByAccessibilityId(AppConstants.TEST_LAST_NAME, lastName);
        sendKeysByAccessibilityId(AppConstants.TEST_ZIP_CODE, zipCode);

        return this;
    }

    /**
     * Fill checkout information with default test data
     */
    public CheckoutPage fillCheckoutInformationWithDefaults() {
        logger.info("Filling checkout information with default test data");
        return fillCheckoutInformation(AppConstants.FIRST_NAME, AppConstants.LAST_NAME, AppConstants.ZIP_CODE);
    }

    /**
     * Click continue button
     */
    public CheckoutPage clickContinueButton() {
        logger.info("Clicking continue button");
        clickByAccessibilityId(AppConstants.TEST_CONTINUE);
        waitForPageLoad();
        return this;
    }

    /**
     * Click finish button
     */
    public CheckoutPage clickFinishButton() {
        logger.info("Clicking finish button");
        scrollDown(); // Scroll to make sure finish button is visible
        clickByXpath(FINISH_BUTTON_XPATH);
        waitForPageLoad();
        return this;
    }

    /**
     * Click back home button
     */
    public void clickBackHomeButton() {
        logger.info("Clicking back home button");
        clickByXpath(BACK_HOME_BUTTON_XPATH);
        waitForPageLoad();
    }

    /**
     * Click cancel button
     */
    public void clickCancelButton() {
        logger.info("Clicking cancel button");
        clickByXpath(CANCEL_BUTTON_XPATH);
        waitForPageLoad();
    }

    /**
     * Check if checkout information page is displayed
     */
    public boolean isCheckoutInformationPageDisplayed() {
        logger.debug("Checking if checkout information page is displayed");
        return isElementDisplayedByXpath(CHECKOUT_INFO_TITLE_XPATH);
    }

    /**
     * Check if checkout overview page is displayed
     */
    public boolean isCheckoutOverviewPageDisplayed() {
        logger.debug("Checking if checkout overview page is displayed");
        return isElementDisplayedByXpath(CHECKOUT_OVERVIEW_TITLE_XPATH);
    }

    /**
     * Check if checkout complete page is displayed
     */
    public boolean isCheckoutCompletePageDisplayed() {
        logger.debug("Checking if checkout complete page is displayed");
        return isElementDisplayedByXpath(CHECKOUT_COMPLETE_TITLE_XPATH);
    }

    /**
     * Wait for checkout information page to load
     */
    public CheckoutPage waitForCheckoutInformationPageToLoad() {
        logger.info("Waiting for checkout information page to load");
        waitUtils.waitForElementToBeVisible(By.xpath(CHECKOUT_INFO_TITLE_XPATH));
        return this;
    }

    /**
     * Wait for checkout overview page to load
     */
    public CheckoutPage waitForCheckoutOverviewPageToLoad() {
        logger.info("Waiting for checkout overview page to load");
        waitUtils.waitForElementToBeVisible(By.xpath(CHECKOUT_OVERVIEW_TITLE_XPATH));
        return this;
    }

    /**
     * Wait for checkout complete page to load
     */
    public CheckoutPage waitForCheckoutCompletePageToLoad() {
        logger.info("Waiting for checkout complete page to load");
        waitUtils.waitForElementToBeVisible(By.xpath(CHECKOUT_COMPLETE_TITLE_XPATH));
        return this;
    }

    /**
     * Get checkout information page title
     */
    public String getCheckoutInformationTitle() {
        logger.debug("Getting checkout information title");
        return getTextByXpath(CHECKOUT_INFO_TITLE_XPATH);
    }

    /**
     * Get checkout overview page title
     */
    public String getCheckoutOverviewTitle() {
        logger.debug("Getting checkout overview title");
        return getTextByXpath(CHECKOUT_OVERVIEW_TITLE_XPATH);
    }

    /**
     * Get checkout complete page title
     */
    public String getCheckoutCompleteTitle() {
        logger.debug("Getting checkout complete title");
        return getTextByXpath(CHECKOUT_COMPLETE_TITLE_XPATH);
    }

    /**
     * Check if finish button is displayed
     */
    public boolean isFinishButtonDisplayed() {
        logger.debug("Checking if finish button is displayed");
        return isElementDisplayedByXpath(FINISH_BUTTON_XPATH);
    }

    /**
     * Check if back home button is displayed
     */
    public boolean isBackHomeButtonDisplayed() {
        logger.debug("Checking if back home button is displayed");
        return isElementDisplayedByXpath(BACK_HOME_BUTTON_XPATH);
    }

    /**
     * Check if continue button is displayed
     */
    public boolean isContinueButtonDisplayed() {
        logger.debug("Checking if continue button is displayed");
        return isElementDisplayedByAccessibilityId(AppConstants.TEST_CONTINUE);
    }

    /**
     * Get thank you message text
     */
    public String getThankYouMessage() {
        logger.debug("Getting thank you message");
        try {
            return getTextByXpath(THANK_YOU_MESSAGE_XPATH);
        } catch (Exception e) {
            logger.warn("Thank you message not found");
            return "";
        }
    }

    /**
     * Check if thank you message is displayed
     */
    public boolean isThankYouMessageDisplayed() {
        logger.debug("Checking if thank you message is displayed");
        return isElementDisplayedByXpath(THANK_YOU_MESSAGE_XPATH);
    }

    /**
     * Complete entire checkout process
     */
    public CheckoutPage completeCheckoutProcess(String firstName, String lastName, String zipCode) {
        logger.info("Completing entire checkout process");

        // Fill information and continue
        fillCheckoutInformation(firstName, lastName, zipCode)
                .clickContinueButton();

        // Finish the order
        clickFinishButton();

        return this;
    }

    /**
     * Complete checkout with default data
     */
    public CheckoutPage completeCheckoutWithDefaults() {
        logger.info("Completing checkout with default test data");
        return completeCheckoutProcess(AppConstants.FIRST_NAME, AppConstants.LAST_NAME, AppConstants.ZIP_CODE);
    }

    /**
     * Validate checkout information form fields
     */
    public boolean validateCheckoutInformationFields() {
        logger.info("Validating checkout information form fields");
        return isElementDisplayedByAccessibilityId(AppConstants.TEST_FIRST_NAME) &&
                isElementDisplayedByAccessibilityId(AppConstants.TEST_LAST_NAME) &&
                isElementDisplayedByAccessibilityId(AppConstants.TEST_ZIP_CODE) &&
                isContinueButtonDisplayed();
    }

    /**
     * Get error message (if any validation fails)
     */
    public String getErrorMessage() {
        logger.debug("Getting error message from checkout page");
        try {
            return getTextByAccessibilityId(AppConstants.TEST_ERROR_MESSAGE);
        } catch (Exception e) {
            logger.debug("No error message found on checkout page");
            return "";
        }
    }
}