package com.amalitech.tests;

import com.amalitech.base.BaseTest;
import com.amalitech.pages.LoginPage;
import com.amalitech.pages.ProductsPage;
import com.amalitech.pages.CartPage;
import com.amalitech.pages.CheckoutPage;
import com.amalitech.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EndToEndTest extends BaseTest {

    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private WaitUtils waitUtils;

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        waitUtils = new WaitUtils(driver);

        // Wait for app to load
        waitUtils.hardWait(3);
    }

    @Test(description = "Complete user journey from login to purchase")
    public void testCompleteUserJourney() {
        System.out.println("Starting complete user journey test...");

        try {
            // Step 1: Login
            performLogin();

            // Step 2: Add items to cart
            addItemsToCart();

            // Step 3: Navigate to cart and manage items
            navigateToCartAndManageItems();

            // Step 4: Complete checkout
            completeCheckoutProcess();

            System.out.println("Complete user journey test passed!");

        } catch (Exception e) {
            System.err.println("Complete user journey test failed: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    private void performLogin() {
        System.out.println("=== Step 1: Performing Login ===");

        // Navigate to login if needed
        loginPage.navigateToLoginIfNeeded();
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Should be on login page");

        // Login with standard user
        loginPage.loginWithStandardUser();

        // Verify we're on products page
        productsPage.waitForProductsPageToLoad();
        String title = productsPage.getProductsPageTitle();
        Assert.assertEquals(title, "PRODUCTS", "Should be on products page");

        System.out.println("Login completed successfully");
    }

    private void addItemsToCart() {
        System.out.println("=== Step 2: Adding Items to Cart ===");

        try {
            // Verify PRODUCTS title is visible
            WebElement productsTitle = driver.findElement(
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"PRODUCTS\")")
            );
            Assert.assertTrue(productsTitle.isDisplayed(), "Products title should be visible");

            // Click on product image (instance 4 as per your sequence)
            WebElement productImage = driver.findElement(
                    AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(4)")
            );
            productImage.click();
            waitUtils.hardWait(1);

            // Add items by clicking + button 3 times
            for (int i = 0; i < 3; i++) {
                WebElement addButton = driver.findElement(
                        AppiumBy.androidUIAutomator("new UiSelector().text(\"+\").instance(0)")
                );
                addButton.click();
                waitUtils.hardWait(1);
            }

            System.out.println("Successfully added 3 items to cart");

        } catch (Exception e) {
            System.err.println("Failed to add items to cart: " + e.getMessage());
            throw e;
        }
    }

    private void navigateToCartAndManageItems() {
        System.out.println("=== Step 3: Navigate to Cart and Manage Items ===");

        try {
            // Click cart icon (instance 3 as per your sequence)
            WebElement cartIcon = driver.findElement(
                    AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(3)")
            );
            cartIcon.click();
            waitUtils.hardWait(2);

            // Verify we're on cart page
            WebElement cartTitle = driver.findElement(
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"YOUR CART\")")
            );
            Assert.assertTrue(cartTitle.isDisplayed(), "Should be on cart page");

            // Verify description is visible
            WebElement description = driver.findElement(
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"DESCRIPTION\")")
            );
            Assert.assertTrue(description.isDisplayed(), "Description should be visible");

            // Remove 2 items (clicking REMOVE button twice)
            for (int i = 0; i < 2; i++) {
                WebElement removeButton = driver.findElement(
                        AppiumBy.androidUIAutomator("new UiSelector().description(\"test-REMOVE\").instance(0)")
                );
                removeButton.click();
                waitUtils.hardWait(1);
            }

            System.out.println("Successfully managed cart items");

        } catch (Exception e) {
            System.err.println("Failed to manage cart: " + e.getMessage());
            throw e;
        }
    }

    private void completeCheckoutProcess() {
        System.out.println("=== Step 4: Complete Checkout Process ===");

        try {
            // Click checkout button
            WebElement checkoutButton = driver.findElement(AppiumBy.accessibilityId("test-CHECKOUT"));
            checkoutButton.click();
            waitUtils.hardWait(2);

            // Fill checkout information
            checkoutPage.fillShippingInfo("Emmanuel", "Arhu", "0233");
            checkoutPage.clickContinue();
            waitUtils.hardWait(2);

            // Complete the purchase
            checkoutPage.clickFinish();
            waitUtils.hardWait(2);

            // Return to home
            checkoutPage.clickBackHome();
            waitUtils.hardWait(2);

            // Verify we're back on products page
            Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Should be back on products page");

            System.out.println("Checkout process completed successfully");

        } catch (Exception e) {
            System.err.println("Failed to complete checkout: " + e.getMessage());
            throw e;
        }
    }
}