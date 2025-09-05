package com.amalitech.pages;

import com.amalitech.base.BasePage;
import com.amalitech.constants.AppConstants;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    public LoginPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Enter username
     */
    public LoginPage enterUsername(String username) {
        logger.info("Entering username: " + username);
        sendKeysByAccessibilityId(AppConstants.TEST_USERNAME, username);
        return this;
    }

    /**
     * Enter password
     */
    public LoginPage enterPassword(String password) {
        logger.info("Entering password");
        sendKeysByAccessibilityId(AppConstants.TEST_PASSWORD, password);
        return this;
    }

    /**
     * Click login button
     */
    public void clickLoginButton() {
        logger.info("Clicking login button");
        clickByAccessibilityId(AppConstants.TEST_LOGIN);
    }

    /**
     * Perform login with username and password
     */
    public void login(String username, String password) {
        logger.info("Logging in with username: " + username);
        enterUsername(username)
                .enterPassword(password)
                .clickLoginButton();

        // Wait for navigation
        waitForPageLoad();
    }

    /**
     * Perform login with standard user credentials
     */
    public void loginWithStandardUser() {
        login(AppConstants.STANDARD_USER, AppConstants.PASSWORD);
    }

    /**
     * Perform login with locked out user credentials
     */
    public void loginWithLockedOutUser() {
        login(AppConstants.LOCKED_OUT_USER, AppConstants.PASSWORD);
    }

    /**
     * Get error message text
     */
    public String getErrorMessage() {
        logger.debug("Getting error message");
        try {
            return getTextByAccessibilityId(AppConstants.TEST_ERROR_MESSAGE);
        } catch (Exception e) {
            logger.warn("Error message not found");
            return "";
        }
    }

    /**
     * Check if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        logger.debug("Checking if error message is displayed");
        return isElementDisplayedByAccessibilityId(AppConstants.TEST_ERROR_MESSAGE);
    }

    /**
     * Check if login button is displayed
     */
    public boolean isLoginButtonDisplayed() {
        logger.debug("Checking if login button is displayed");
        return isElementDisplayedByAccessibilityId(AppConstants.TEST_LOGIN);
    }

    /**
     * Check if username field is displayed
     */
    public boolean isUsernameFieldDisplayed() {
        logger.debug("Checking if username field is displayed");
        return isElementDisplayedByAccessibilityId(AppConstants.TEST_USERNAME);
    }

    /**
     * Check if password field is displayed
     */
    public boolean isPasswordFieldDisplayed() {
        logger.debug("Checking if password field is displayed");
        return isElementDisplayedByAccessibilityId(AppConstants.TEST_PASSWORD);
    }

    /**
     * Check if login page is displayed
     */
    public boolean isLoginPageDisplayed() {
        logger.debug("Checking if login page is displayed");
        return isUsernameFieldDisplayed() && isPasswordFieldDisplayed() && isLoginButtonDisplayed();
    }

    /**
     * Clear username field
     */
    public LoginPage clearUsername() {
        logger.info("Clearing username field");
        findByAccessibilityId(AppConstants.TEST_USERNAME).clear();
        return this;
    }

    /**
     * Clear password field
     */
    public LoginPage clearPassword() {
        logger.info("Clearing password field");
        findByAccessibilityId(AppConstants.TEST_PASSWORD).clear();
        return this;
    }

    /**
     * Clear all fields
     */
    public LoginPage clearAllFields() {
        logger.info("Clearing all login fields");
        return clearUsername().clearPassword();
    }

    public LoginPage waitForLoginPageToLoad() {
        logger.info("Waiting for login page to load");
        waitUtils.waitForElementToBeVisible(
                io.appium.java_client.AppiumBy.accessibilityId(AppConstants.TEST_USERNAME)
        );
        return this;
    }

    public void loginWithProblemUser() {
        login(AppConstants.PROBLEM_USER, AppConstants.PASSWORD);
    }
}