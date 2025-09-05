package com.amalitech.pages;

import com.amalitech.base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class LoginPage extends BasePage {

    // Locators
    private final String USERNAME_FIELD = "test-Username";
    private final String PASSWORD_FIELD = "test-Password";
    private final String LOGIN_BUTTON = "test-LOGIN";
    private final String ERROR_MESSAGE = "test-Error message";

    // Alternative locators using UiAutomator
    private final String USERNAME_FIELD_ALT = "new UiSelector().className(\"android.widget.EditText\").instance(0)";
    private final String PASSWORD_FIELD_ALT = "new UiSelector().className(\"android.widget.EditText\").instance(1)";
    private final String LOGIN_BUTTON_ALT = "new UiSelector().text(\"LOGIN\")";

    public LoginPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Wait for login page to load - for compatibility with existing tests
     */
    public void waitForLoginPageToLoad() {
        waitForLoginPage();
    }

    /**
     * Wait for login page to be loaded
     */
    public void waitForLoginPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId(USERNAME_FIELD)),
                    ExpectedConditions.presenceOfElementLocated(AppiumBy.androidUIAutomator(USERNAME_FIELD_ALT))
            ));
        } catch (Exception e) {
            System.out.println("Login page elements not found, might need to navigate to login screen");
        }
    }

    /**
     * Check if login page is displayed - for compatibility
     */
    public boolean isLoginPageDisplayed() {
        return isOnLoginPage();
    }

    /**
     * Enhanced method to find username field with fallback locators
     */
    public WebElement getUsernameField() {
        try {
            return findByAccessibilityId(USERNAME_FIELD);
        } catch (Exception e) {
            System.out.println("Primary username locator failed, trying alternative...");
            return findByUIAutomator(USERNAME_FIELD_ALT);
        }
    }

    /**
     * Enhanced method to find password field with fallback locators
     */
    public WebElement getPasswordField() {
        try {
            return findByAccessibilityId(PASSWORD_FIELD);
        } catch (Exception e) {
            System.out.println("Primary password locator failed, trying alternative...");
            return findByUIAutomator(PASSWORD_FIELD_ALT);
        }
    }

    /**
     * Enhanced method to find login button with fallback locators
     */
    public WebElement getLoginButton() {
        try {
            return findByAccessibilityId(LOGIN_BUTTON);
        } catch (Exception e) {
            System.out.println("Primary login button locator failed, trying alternative...");
            return findByUIAutomator(LOGIN_BUTTON_ALT);
        }
    }

    /**
     * Enter username with error handling
     */
    public void enterUsername(String username) {
        try {
            WebElement usernameField = getUsernameField();
            usernameField.clear();
            usernameField.sendKeys(username);
            System.out.println("Username entered successfully: " + username);
        } catch (Exception e) {
            System.err.println("Failed to enter username: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Enter password with error handling
     */
    public void enterPassword(String password) {
        try {
            WebElement passwordField = getPasswordField();
            passwordField.clear();
            passwordField.sendKeys(password);
            System.out.println("Password entered successfully");
        } catch (Exception e) {
            System.err.println("Failed to enter password: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Click login button with error handling
     */
    public void clickLoginButton() {
        try {
            WebElement loginButton = getLoginButton();
            loginButton.click();
            System.out.println("Login button clicked successfully");
        } catch (Exception e) {
            System.err.println("Failed to click login button: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Complete login process
     */
    public void login(String username, String password) {
        waitForLoginPage();
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();

        // Wait a moment for login to process
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Compatibility methods for existing tests
    public void loginWithStandardUser() {
        login("standard_user", "secret_sauce");
    }

    public void loginWithLockedOutUser() {
        login("locked_out_user", "secret_sauce");
    }

    public void loginWithProblemUser() {
        login("problem_user", "secret_sauce");
    }

    public void loginWithInvalidCredentials() {
        login("invalid_user", "wrong_password");
    }

    /**
     * Check if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        try {
            WebElement errorElement = findByAccessibilityId(ERROR_MESSAGE);
            return errorElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get error message text
     */
    public String getErrorMessageText() {
        try {
            WebElement errorElement = findByAccessibilityId(ERROR_MESSAGE);
            return errorElement.getText();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Get error message - compatibility method
     */
    public String getErrorMessage() {
        return getErrorMessageText();
    }

    /**
     * Check if username field is displayed
     */
    public boolean isUsernameFieldDisplayed() {
        try {
            getUsernameField();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if password field is displayed
     */
    public boolean isPasswordFieldDisplayed() {
        try {
            getPasswordField();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if login button is displayed
     */
    public boolean isLoginButtonDisplayed() {
        try {
            getLoginButton();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Clear all input fields safely
     */
    public void clearAllFields() {
        try {
            WebElement usernameField = getUsernameField();
            if (usernameField != null) {
                usernameField.clear();
            }
        } catch (Exception e) {
            System.out.println("Could not clear username field: " + e.getMessage());
        }

        try {
            WebElement passwordField = getPasswordField();
            if (passwordField != null) {
                passwordField.clear();
            }
        } catch (Exception e) {
            System.out.println("Could not clear password field: " + e.getMessage());
        }
    }

    /**
     * Check if we're on the login page
     */
    public boolean isOnLoginPage() {
        try {
            getUsernameField();
            getPasswordField();
            getLoginButton();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Navigate to login page if not already there
     */
    public void navigateToLoginIfNeeded() {
        if (!isOnLoginPage()) {
            System.out.println("Not on login page, attempting to navigate...");
            // Try to find and click menu/logout button if available
            try {
                WebElement menuButton = findByUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(0)");
                menuButton.click();
                Thread.sleep(1000);

                // Look for logout option
                WebElement logoutButton = findByAccessibilityId("test-LOGOUT");
                logoutButton.click();
                Thread.sleep(2000);

                waitForLoginPage();
            } catch (Exception e) {
                System.out.println("Could not navigate to login page: " + e.getMessage());
            }
        }
    }
}