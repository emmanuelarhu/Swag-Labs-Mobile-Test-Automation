package com.amalitech.tests;

import com.amalitech.base.BaseTest;
import com.amalitech.pages.LoginPage;
import com.amalitech.pages.ProductsPage;
import com.amalitech.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.Point;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.Arrays;

public class ProductTest extends BaseTest {

    private LoginPage loginPage;
    private ProductsPage productsPage;
    private WaitUtils waitUtils;

    @BeforeClass
    public void setUpClass() {
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        waitUtils = new WaitUtils(driver);

        // One-time setup to login and get to products page
        setupOnce();
    }

    @Test(description = "Verify products are visible on the product page and scroll to view all products")
    public void testProductsVisibilityOnProductPage() {
        System.out.println("Starting products visibility test on product page...");

        try {
            // Verify we're on products page
            Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Should be on products page");
            System.out.println("✓ Verified we're on products page");

            // Test Product 1: Sauce Labs Backpack
            verifyProduct1();

            // Test Product 2: Sauce Labs Bike Light
            verifyProduct2();

            // Scroll down to view more products
            scrollToViewMoreProducts();

            // Test Product 3: Sauce Labs Bolt T-Shirt
            verifyProduct3();

            // Test Product 4: Sauce Labs Fleece Jacket
            verifyProduct4();

            System.out.println("🎉 Products visibility test PASSED! 🎉");
            System.out.println("Test ends here at products page as requested");

        } catch (Exception e) {
            System.err.println("Products visibility test failed: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    // Helper Methods

    private void setupOnce() {
        try {
            System.out.println("Setting up test environment...");

            // Wait for app to load
            waitUtils.hardWait(3);

            // Check if we're already logged in by looking for products page
            if (productsPage.isProductsPageDisplayed()) {
                System.out.println("✓ Already on products page");
                return;
            }

            // If not on products page, perform login
            System.out.println("Performing login...");

            // Direct login without navigation
            WebElement usernameField = driver.findElement(AppiumBy.accessibilityId("test-Username"));
            WebElement passwordField = driver.findElement(AppiumBy.accessibilityId("test-Password"));
            WebElement loginButton = driver.findElement(AppiumBy.accessibilityId("test-LOGIN"));

            usernameField.clear();
            usernameField.sendKeys("standard_user");
            passwordField.clear();
            passwordField.sendKeys("secret_sauce");
            loginButton.click();
            waitUtils.hardWait(3);

            // Verify we're on products page
            productsPage.waitForProductsPageToLoad();
            System.out.println("✓ Setup completed - on products page");

        } catch (Exception e) {
            System.err.println("Failed to setup: " + e.getMessage());
            throw new RuntimeException("Setup failed", e);
        }
    }

    private void verifyProduct1() {
        System.out.println("=== Verifying Product 1: Sauce Labs Backpack ===");

        try {
            // Verify product image (instance 6 as per your requirement)
            boolean productImageVisible = isElementVisible("new UiSelector().className(\"android.widget.ImageView\").instance(6)");
            System.out.println("Product 1 image visible: " + productImageVisible);

            // Verify product name
            boolean productNameVisible = isElementVisible("new UiSelector().text(\"Sauce Labs Backpack\")");
            System.out.println("Product 1 name 'Sauce Labs Backpack' visible: " + productNameVisible);
            Assert.assertTrue(productNameVisible, "Sauce Labs Backpack name should be visible");

            // Verify product price
            boolean productPriceVisible = isElementVisible("new UiSelector().text(\"$29.99\")");
            System.out.println("Product 1 price '$29.99' visible: " + productPriceVisible);
            Assert.assertTrue(productPriceVisible, "Sauce Labs Backpack price should be visible");

            // Verify ADD TO CART button (instance 0)
            boolean addToCartVisible = isElementVisible("new UiSelector().description(\"test-ADD TO CART\").instance(0)");
            System.out.println("Product 1 ADD TO CART button visible: " + addToCartVisible);
            Assert.assertTrue(addToCartVisible, "ADD TO CART button should be visible for Sauce Labs Backpack");

            System.out.println("✓ Product 1 (Sauce Labs Backpack) verification completed successfully");

        } catch (Exception e) {
            System.err.println("Failed to verify Product 1: " + e.getMessage());
            throw e;
        }
    }

    private void verifyProduct2() {
        System.out.println("=== Verifying Product 2: Sauce Labs Bike Light ===");

        try {
            // Verify product name
            boolean productNameVisible = isElementVisible("new UiSelector().text(\"Sauce Labs Bike Light\")");
            System.out.println("Product 2 name 'Sauce Labs Bike Light' visible: " + productNameVisible);
            Assert.assertTrue(productNameVisible, "Sauce Labs Bike Light name should be visible");

            // Verify product price
            boolean productPriceVisible = isElementVisible("new UiSelector().text(\"$9.99\")");
            System.out.println("Product 2 price '$9.99' visible: " + productPriceVisible);
            Assert.assertTrue(productPriceVisible, "Sauce Labs Bike Light price should be visible");

            // Verify ADD TO CART button (instance 1)
            boolean addToCartVisible = isElementVisible("new UiSelector().description(\"test-ADD TO CART\").instance(1)");
            System.out.println("Product 2 ADD TO CART button visible: " + addToCartVisible);
            Assert.assertTrue(addToCartVisible, "ADD TO CART button should be visible for Sauce Labs Bike Light");

            System.out.println("✓ Product 2 (Sauce Labs Bike Light) verification completed successfully");

        } catch (Exception e) {
            System.err.println("Failed to verify Product 2: " + e.getMessage());
            throw e;
        }
    }

    private void scrollToViewMoreProducts() {
        System.out.println("=== Scrolling to view Sauce Labs Bolt T-Shirt ===");

        try {
            // Scroll down to reveal more products
            Point start = new Point(540, 1500);
            Point end = new Point(540, 800);
            performSwipe(start, end);
            waitUtils.hardWait(2);

            System.out.println("✓ Scrolled down to view more products");

        } catch (Exception e) {
            System.err.println("Failed to scroll: " + e.getMessage());
            throw e;
        }
    }

    private void verifyProduct3() {
        System.out.println("=== Verifying Product 3: Sauce Labs Bolt T-Shirt ===");

        try {
            // Verify product name
            boolean productNameVisible = isElementVisible("new UiSelector().text(\"Sauce Labs Bolt T-Shirt\")");
            System.out.println("Product 3 name 'Sauce Labs Bolt T-Shirt' visible: " + productNameVisible);
            Assert.assertTrue(productNameVisible, "Sauce Labs Bolt T-Shirt name should be visible");

            // Verify product price
            boolean productPriceVisible = isElementVisible("new UiSelector().text(\"$15.99\")");
            System.out.println("Product 3 price '$15.99' visible: " + productPriceVisible);
            Assert.assertTrue(productPriceVisible, "Sauce Labs Bolt T-Shirt price should be visible");

            // Verify ADD TO CART button (instance 2)
            boolean addToCartVisible = isElementVisible("new UiSelector().description(\"test-ADD TO CART\").instance(2)");
            System.out.println("Product 3 ADD TO CART button visible: " + addToCartVisible);
            Assert.assertTrue(addToCartVisible, "ADD TO CART button should be visible for Sauce Labs Bolt T-Shirt");

            System.out.println("✓ Product 3 (Sauce Labs Bolt T-Shirt) verification completed successfully");

        } catch (Exception e) {
            System.err.println("Failed to verify Product 3: " + e.getMessage());
            throw e;
        }
    }

    private void verifyProduct4() {
        System.out.println("=== Verifying Product 4: Sauce Labs Fleece Jacket ===");

        try {
            // Verify product name
            boolean productNameVisible = isElementVisible("new UiSelector().text(\"Sauce Labs Fleece Jacket\")");
            System.out.println("Product 4 name 'Sauce Labs Fleece Jacket' visible: " + productNameVisible);
            Assert.assertTrue(productNameVisible, "Sauce Labs Fleece Jacket name should be visible");

            // Verify product price
            boolean productPriceVisible = isElementVisible("new UiSelector().text(\"$49.99\")");
            System.out.println("Product 4 price '$49.99' visible: " + productPriceVisible);
            Assert.assertTrue(productPriceVisible, "Sauce Labs Fleece Jacket price should be visible");

            // Verify ADD TO CART button (instance 3)
            boolean addToCartVisible = isElementVisible("new UiSelector().description(\"test-ADD TO CART\").instance(3)");
            System.out.println("Product 4 ADD TO CART button visible: " + addToCartVisible);
            Assert.assertTrue(addToCartVisible, "ADD TO CART button should be visible for Sauce Labs Fleece Jacket");

            System.out.println("✓ Product 4 (Sauce Labs Fleece Jacket) verification completed successfully");

        } catch (Exception e) {
            System.err.println("Failed to verify Product 4: " + e.getMessage());
            throw e;
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
            System.out.println("✓ Performed swipe gesture");
        } catch (Exception e) {
            System.err.println("Failed to perform swipe: " + e.getMessage());
        }
    }

    private boolean isElementVisible(String uiSelector) {
        try {
            WebElement element = driver.findElement(AppiumBy.androidUIAutomator(uiSelector));
            boolean isDisplayed = element.isDisplayed();
            return isDisplayed;
        } catch (Exception e) {
            return false;
        }
    }
}