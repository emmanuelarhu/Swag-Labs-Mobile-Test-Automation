package com.amalitech.tests;

import com.amalitech.base.BaseTest;
import com.amalitech.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class SimpleLoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(driver);

        // Wait for app to load and navigate to login if needed
        try {
            Thread.sleep(3000); // Give app time to load
            loginPage.navigateToLoginIfNeeded();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test(priority = 1, description = "Test login with valid credentials")
    public void testValidUserLogin() {
        System.out.println("Starting valid user login test...");

        try {
            // Ensure we're on login page
            Assert.assertTrue(loginPage.isOnLoginPage(), "Should be on login page");

            // Clear any existing data
            loginPage.clearAllFields();

            // Perform login
            loginPage.login("standard_user", "secret_sauce");

            // Wait for products page to load
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement productsTitle = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"PRODUCTS\")")
            ));

            Assert.assertTrue(productsTitle.isDisplayed(), "Should be on products page after successful login");
            System.out.println("Valid user login test passed!");

        } catch (Exception e) {
            System.err.println("Valid user login test failed: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Valid user login test failed: " + e.getMessage());
        }
    }

    @Test(priority = 2, description = "Test login with locked out user")
    public void testLockedOutUserLogin() {
        System.out.println("Starting locked out user login test...");

        try {
            // Navigate back to login if needed (in case previous test succeeded)
            loginPage.navigateToLoginIfNeeded();

            // Ensure we're on login page
            Assert.assertTrue(loginPage.isOnLoginPage(), "Should be on login page");

            // Clear any existing data
            loginPage.clearAllFields();

            // Attempt login with locked out user
            loginPage.login("locked_out_user", "secret_sauce");

            // Wait a moment for error to appear
            Thread.sleep(2000);

            // Check that error message is displayed
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed for locked out user");

            String errorMessage = loginPage.getErrorMessageText();
            Assert.assertTrue(errorMessage.contains("locked out") || errorMessage.contains("locked"),
                    "Error message should indicate user is locked out. Actual message: " + errorMessage);

            System.out.println("Locked out user login test passed!");

        } catch (Exception e) {
            System.err.println("Locked out user login test failed: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Locked out user login test failed: " + e.getMessage());
        }
    }

    @Test(priority = 3, description = "Test login with invalid credentials")
    public void testInvalidUserLogin() {
        System.out.println("Starting invalid user login test...");

        try {
            // Navigate back to login if needed
            loginPage.navigateToLoginIfNeeded();

            // Ensure we're on login page
            Assert.assertTrue(loginPage.isOnLoginPage(), "Should be on login page");

            // Clear any existing data
            loginPage.clearAllFields();

            // Attempt login with invalid credentials
            loginPage.login("invalid_user", "wrong_password");

            // Wait a moment for error to appear
            Thread.sleep(2000);

            // Check that error message is displayed
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed for invalid credentials");

            String errorMessage = loginPage.getErrorMessageText();
            Assert.assertTrue(errorMessage.contains("Username and password do not match") ||
                            errorMessage.contains("invalid") ||
                            errorMessage.contains("not match"),
                    "Error message should indicate invalid credentials. Actual message: " + errorMessage);

            System.out.println("Invalid user login test passed!");

        } catch (Exception e) {
            System.err.println("Invalid user login test failed: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Invalid user login test failed: " + e.getMessage());
        }
    }
}