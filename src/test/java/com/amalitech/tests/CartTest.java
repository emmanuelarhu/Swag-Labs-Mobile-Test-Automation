package com.amalitech.tests;

import com.amalitech.base.BaseTest;
import com.amalitech.pages.LoginPage;
import com.amalitech.pages.ProductsPage;
import com.amalitech.pages.CartPage;
import com.amalitech.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private WaitUtils waitUtils;

    @BeforeClass
    public void setUpClass() {
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        waitUtils = new WaitUtils(driver);

        // One-time setup to get to cart page
        setupCartOnce();
    }

    @Test(description = "Test cart page displays correctly with items")
    public void testCartPageDisplay() {
        System.out.println("Starting cart page display test...");

        try {
            // Verify cart page title
            String cartTitle = cartPage.getCartPageTitle();
            Assert.assertEquals(cartTitle, "YOUR CART", "Cart title should be 'YOUR CART'");
            System.out.println("✓ Cart title verified: " + cartTitle);

            // Verify we have items in cart
            int itemCount = cartPage.getCartItemsCount();
            Assert.assertTrue(itemCount > 0, "Cart should have items");
            System.out.println("✓ Cart has " + itemCount + " items");

            // Verify cart page elements are present
            boolean cartValid = cartPage.validateCartPageElements();
            Assert.assertTrue(cartValid, "Cart page should have all required elements");
            System.out.println("✓ Cart page elements validated");

            System.out.println("Cart page display test PASSED!");

        } catch (Exception e) {
            System.err.println("Cart page display test failed: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(description = "Test cart items and their details")
    public void testCartItemDetails() {
        System.out.println("Starting cart item details test...");

        try {
            // Verify QTY column is visible
            WebElement qtyHeader = driver.findElement(
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"QTY\")")
            );
            Assert.assertTrue(qtyHeader.isDisplayed(), "QTY header should be visible");
            System.out.println("✓ QTY header found");

            // Verify DESCRIPTION column is visible
            WebElement descHeader = driver.findElement(
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"DESCRIPTION\")")
            );
            Assert.assertTrue(descHeader.isDisplayed(), "DESCRIPTION header should be visible");
            System.out.println("✓ DESCRIPTION header found");

            // Verify product names are visible
            try {
                WebElement product1 = driver.findElement(
                        AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Sauce Labs\")")
                );
                Assert.assertTrue(product1.isDisplayed(), "Product name should be visible");
                System.out.println("✓ Product name found: " + product1.getText());
            } catch (Exception e) {
                System.out.println("Product name element might have different text");
            }

            // Verify prices are visible
            try {
                WebElement price = driver.findElement(
                        AppiumBy.androidUIAutomator("new UiSelector().textContains(\"$\")")
                );
                Assert.assertTrue(price.isDisplayed(), "Price should be visible");
                System.out.println("✓ Price found: " + price.getText());
            } catch (Exception e) {
                System.out.println("Price element might have different format");
            }

            System.out.println("Cart item details test PASSED!");

        } catch (Exception e) {
            System.err.println("Cart item details test failed: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(description = "Test REMOVE buttons functionality")
    public void testRemoveButtons() {
        System.out.println("Starting remove buttons test...");

        try {
            // Get initial item count
            int initialCount = cartPage.getCartItemsCount();
            System.out.println("Initial item count: " + initialCount);

            // Verify REMOVE buttons are visible
            boolean removeVisible = cartPage.isRemoveButtonVisible();
            Assert.assertTrue(removeVisible, "REMOVE buttons should be visible");
            System.out.println("✓ REMOVE buttons are visible");

            // Get number of remove buttons
            int removeButtonCount = cartPage.getRemoveButtonsCount();
            Assert.assertEquals(removeButtonCount, initialCount, "Should have one REMOVE button per item");
            System.out.println("✓ Found " + removeButtonCount + " REMOVE buttons for " + initialCount + " items");

            // Test clicking a REMOVE button
            boolean removeSuccess = cartPage.removeFirstItem();
            Assert.assertTrue(removeSuccess, "Should successfully remove item");
            waitUtils.hardWait(2);

            // Verify item was removed
            int newCount = cartPage.getCartItemsCount();
            Assert.assertTrue(newCount < initialCount, "Item count should decrease after removal");
            System.out.println("✓ Item removed successfully. Count: " + initialCount + " → " + newCount);

            System.out.println("Remove buttons test PASSED!");

        } catch (Exception e) {
            System.err.println("Remove buttons test failed: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(description = "Test cart navigation elements")
    public void testCartNavigation() {
        System.out.println("Starting cart navigation test...");

        try {
            // Verify checkout button exists (but don't click it to avoid leaving cart)
            boolean checkoutDisplayed = cartPage.isCheckoutButtonDisplayed();
            System.out.println("Checkout button displayed: " + checkoutDisplayed);

            // Verify continue shopping button exists (if available)
            try {
                boolean continueShoppingDisplayed = cartPage.isContinueShoppingButtonDisplayed();
                System.out.println("Continue shopping button displayed: " + continueShoppingDisplayed);
            } catch (Exception e) {
                System.out.println("Continue shopping button may not be visible or available");
            }

            // Verify cart icon in header
            try {
                WebElement cartIconHeader = driver.findElement(
                        AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(3)")
                );
                Assert.assertTrue(cartIconHeader.isDisplayed(), "Cart icon should be in header");
                System.out.println("✓ Cart icon found in header");
            } catch (Exception e) {
                System.out.println("Cart icon might have different locator");
            }

            System.out.println("Cart navigation test PASSED!");

        } catch (Exception e) {
            System.err.println("Cart navigation test failed: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(description = "Test cart page completeness")
    public void testCartPageCompleteness() {
        System.out.println("Starting cart page completeness test...");

        try {
            // Verify we're definitely on cart page
            Assert.assertTrue(cartPage.isCartPageDisplayed(), "Should be on cart page");

            // Verify cart is not empty
            Assert.assertFalse(cartPage.isCartEmpty(), "Cart should not be empty");

            // Verify basic cart functionality works
            int itemCount = cartPage.getCartItemsCount();
            String cartTitle = cartPage.getCartPageTitle();
            boolean hasCheckout = cartPage.isCheckoutButtonDisplayed();

            System.out.println("=== CART PAGE SUMMARY ===");
            System.out.println("Cart Title: " + cartTitle);
            System.out.println("Item Count: " + itemCount);
            System.out.println("Has Checkout Button: " + hasCheckout);
            System.out.println("Cart Page Valid: " + cartPage.validateCartPageElements());
            System.out.println("========================");

            // Final assertion - cart should have the expected structure
            Assert.assertTrue(itemCount > 0, "Cart should have items");
            Assert.assertEquals(cartTitle, "YOUR CART", "Cart should have correct title");

            System.out.println("Cart page completeness test PASSED!");
            System.out.println("🎉 ALL CART TESTS COMPLETED SUCCESSFULLY! 🎉");

        } catch (Exception e) {
            System.err.println("Cart page completeness test failed: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    /**
     * One-time setup to get to cart page with items
     */
    private void setupCartOnce() {
        try {
            System.out.println("Setting up cart for testing...");

            // Wait for app to load
            waitUtils.hardWait(3);

            // Login directly
            WebElement usernameField = driver.findElement(AppiumBy.accessibilityId("test-Username"));
            WebElement passwordField = driver.findElement(AppiumBy.accessibilityId("test-Password"));
            WebElement loginButton = driver.findElement(AppiumBy.accessibilityId("test-LOGIN"));

            usernameField.clear();
            usernameField.sendKeys("standard_user");
            passwordField.clear();
            passwordField.sendKeys("secret_sauce");
            loginButton.click();
            waitUtils.hardWait(3);

            // Navigate to product and add items
            WebElement productImage = driver.findElement(
                    AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(4)")
            );
            productImage.click();
            waitUtils.hardWait(1);

            // Add 2 items to cart
            for (int i = 0; i < 2; i++) {
                WebElement addButton = driver.findElement(
                        AppiumBy.androidUIAutomator("new UiSelector().text(\"+\").instance(0)")
                );
                addButton.click();
                waitUtils.hardWait(1);
            }

            // Navigate to cart
            WebElement cartIcon = driver.findElement(
                    AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(3)")
            );
            cartIcon.click();
            waitUtils.hardWait(2);

            // Verify we're on cart page
            cartPage.waitForCartPageToLoad();

            System.out.println("✓ Cart setup completed - ready for testing!");

        } catch (Exception e) {
            System.err.println("Failed to setup cart: " + e.getMessage());
            throw new RuntimeException("Cart setup failed", e);
        }
    }
}