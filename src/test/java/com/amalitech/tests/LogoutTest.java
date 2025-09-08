package com.amalitech.tests;

import com.amalitech.base.BaseTest;
import com.amalitech.pages.LoginPage;
import com.amalitech.pages.ProductsPage;
import com.amalitech.pages.MenuPage;
import com.amalitech.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LogoutTest extends BaseTest {

    private LoginPage loginPage;
    private ProductsPage productsPage;
    private MenuPage menuPage;
    private WaitUtils waitUtils;

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        menuPage = new MenuPage(driver);
        waitUtils = new WaitUtils(driver);

        // Wait for app to load
        waitUtils.hardWait(3);
    }

    @Test(description = "Test logout functionality after login")
    public void testLogoutAfterLogin() {
        System.out.println("Starting logout test...");

        try {
            // Step 1: Perform login
            performLogin();

            // Step 2: Verify we're on products page and can see price
            verifyProductsPageAfterLogin();

            // Step 3: Open hamburger menu
            openHamburgerMenu();

            // Step 4: Click logout
            performLogout();

            // Step 5: Verify we're back on login page
            verifyBackOnLoginPage();

            System.out.println("🎉 Logout test PASSED! 🎉");
            System.out.println("Successfully logged out and returned to login page");

        } catch (Exception e) {
            System.err.println("Logout test failed: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    // Helper Methods

    private void performLogin() {
        System.out.println("=== Performing Login ===");

        try {
            // Check if we need to navigate to login page
            if (!loginPage.isLoginPageDisplayed()) {
                System.out.println("Not on login page, trying to navigate...");
                loginPage.navigateToLoginIfNeeded();
            }

            // Verify login page elements are visible
            Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Should be on login page");

            // Perform login with standard user
            loginPage.login("standard_user", "secret_sauce");
            waitUtils.hardWait(3);

            System.out.println("✓ Login completed successfully");

        } catch (Exception e) {
            System.err.println("Failed to perform login: " + e.getMessage());
            throw e;
        }
    }

    private void verifyProductsPageAfterLogin() {
        System.out.println("=== Verifying Products Page After Login ===");

        try {
            // Wait for products page to load
            productsPage.waitForProductsPageToLoad();

            // Verify we're on products page
            Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Should be on products page after login");

            // Verify specific price is visible as mentioned in your requirements
            boolean priceVisible = isElementVisible("new UiSelector().text(\"$29.99\")");
            System.out.println("Price '$29.99' visible on products page: " + priceVisible);
            Assert.assertTrue(priceVisible, "Price '$29.99' should be visible on products page");

            System.out.println("✓ Products page verified after login");

        } catch (Exception e) {
            System.err.println("Failed to verify products page: " + e.getMessage());
            throw e;
        }
    }

    private void openHamburgerMenu() {
        System.out.println("=== Opening Hamburger Menu ===");

        try {
            // Click hamburger menu using the selector you provided
            WebElement menuButton = driver.findElement(
                    AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(1)")
            );
            menuButton.click();
            waitUtils.hardWait(2);

            // Verify menu opened by checking for menu items
            boolean allItemsVisible = isElementVisible("new UiSelector().text(\"ALL ITEMS\")");
            boolean logoutVisible = isElementVisible("new UiSelector().text(\"LOGOUT\")");

            System.out.println("Menu opened - ALL ITEMS visible: " + allItemsVisible);
            System.out.println("Menu opened - LOGOUT visible: " + logoutVisible);

            Assert.assertTrue(logoutVisible, "LOGOUT option should be visible in menu");
            System.out.println("✓ Hamburger menu opened successfully");

        } catch (Exception e) {
            System.err.println("Failed to open hamburger menu: " + e.getMessage());
            throw e;
        }
    }

    private void performLogout() {
        System.out.println("=== Performing Logout ===");

        try {
            // Click on LOGOUT option
            WebElement logoutButton = driver.findElement(
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"LOGOUT\")")
            );
            logoutButton.click();
            waitUtils.hardWait(3);

            System.out.println("✓ Logout button clicked");

        } catch (Exception e) {
            System.err.println("Failed to perform logout: " + e.getMessage());
            throw e;
        }
    }

    private void verifyBackOnLoginPage() {
        System.out.println("=== Verifying Back On Login Page ===");

        try {
            // Wait for login page to load
            loginPage.waitForLoginPageToLoad();

            // Verify we're back on login page
            Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Should be back on login page after logout");

            // Verify specific login page elements
            boolean usernameFieldVisible = loginPage.isUsernameFieldDisplayed();
            boolean passwordFieldVisible = loginPage.isPasswordFieldDisplayed();
            boolean loginButtonVisible = loginPage.isLoginButtonDisplayed();

            System.out.println("Login page elements after logout:");
            System.out.println("- Username field visible: " + usernameFieldVisible);
            System.out.println("- Password field visible: " + passwordFieldVisible);
            System.out.println("- Login button visible: " + loginButtonVisible);

            Assert.assertTrue(usernameFieldVisible, "Username field should be visible");
            Assert.assertTrue(passwordFieldVisible, "Password field should be visible");
            Assert.assertTrue(loginButtonVisible, "Login button should be visible");

            // Verify LOGIN button accessibility ID as mentioned in your requirements
            boolean loginButtonAccessible = isElementVisibleByAccessibilityId("test-LOGIN");
            System.out.println("LOGIN button accessible (test-LOGIN): " + loginButtonAccessible);
            Assert.assertTrue(loginButtonAccessible, "LOGIN button should be accessible via test-LOGIN");

            System.out.println("✓ Successfully verified back on login page");

        } catch (Exception e) {
            System.err.println("Failed to verify login page: " + e.getMessage());
            throw e;
        }
    }

    private boolean isElementVisible(String uiSelector) {
        try {
            WebElement element = driver.findElement(AppiumBy.androidUIAutomator(uiSelector));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isElementVisibleByAccessibilityId(String accessibilityId) {
        try {
            WebElement element = driver.findElement(AppiumBy.accessibilityId(accessibilityId));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}