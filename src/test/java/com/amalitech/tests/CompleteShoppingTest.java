package com.amalitech.tests;

import com.amalitech.base.BaseTest;
import com.amalitech.pages.LoginPage;
import com.amalitech.pages.ProductsPage;
import com.amalitech.pages.CheckoutPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class CompleteShoppingTest extends BaseTest {

    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CheckoutPage checkoutPage;

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        checkoutPage = new CheckoutPage(driver);

        // Wait for app to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test(description = "Complete shopping flow from login to purchase")
    public void testCompleteShoppingFlow() {
        System.out.println("Starting complete shopping flow test...");

        try {
            // Step 1: Login
            loginPage.navigateToLoginIfNeeded();
            Assert.assertTrue(loginPage.isOnLoginPage(), "Should be on login page");
            loginPage.login("standard_user", "secret_sauce");

            // Step 2: Verify we're on products page
            productsPage.waitForProductsPage();
            Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Should be on products page");

            // Step 3: Add items to cart (3 items)
            productsPage.addItemToCart(3);

            // Step 4: Go to cart
            productsPage.goToCart();

            // Step 5: Remove some items (2 items)
            productsPage.removeItemsFromCart(2);

            // Step 6: Proceed to checkout
            productsPage.proceedToCheckout();

            // Step 7: Fill checkout information
            checkoutPage.fillShippingInfo("Emmanuel", "Arhu", "0233");
            checkoutPage.clickContinue();

            // Step 8: Perform swipe gesture on overview page
            Point start = new Point(844, 2013);
            Point end = new Point(941, 2347);
            productsPage.performSwipe(start, end);

            // Step 9: Complete purchase
            checkoutPage.clickFinish();

            // Step 10: Return to home
            checkoutPage.clickBackHome();

            // Step 11: Verify we're back on products page
            Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Should be back on products page");

            // Step 12: Verify product elements are visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Check for product image
            WebElement productImage = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(1)")
            ));
            Assert.assertTrue(productImage.isDisplayed(), "Product image should be visible");

            // Check for product description
            WebElement productDescription = wait.until(ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"Get your testing superhero on with the Sauce Labs bolt T-shirt. From American Apparel, 100% ringspun combed cotton, heather gray with red bolt.\")")
            ));
            Assert.assertTrue(productDescription.isDisplayed(), "Product description should be visible");

            System.out.println("Complete shopping flow test passed!");

        } catch (Exception e) {
            System.err.println("Complete shopping flow test failed: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Complete shopping flow test failed: " + e.getMessage());
        }
    }

    @Test(description = "Test adding and removing items from cart")
    public void testCartOperations() {
        System.out.println("Starting cart operations test...");

        try {
            // Login first
            loginPage.navigateToLoginIfNeeded();
            loginPage.login("standard_user", "secret_sauce");

            // Verify on products page
            productsPage.waitForProductsPage();
            Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Should be on products page");

            // Add multiple items
            productsPage.addItemToCart(5);

            // Go to cart
            productsPage.goToCart();

            // Remove some items
            productsPage.removeItemsFromCart(3);

            // Verify we can still proceed to checkout
            productsPage.proceedToCheckout();

            // Verify checkout page loaded
            checkoutPage.waitForCheckoutPage();

            System.out.println("Cart operations test passed!");

        } catch (Exception e) {
            System.err.println("Cart operations test failed: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Cart operations test failed: " + e.getMessage());
        }
    }
}