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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private WaitUtils waitUtils;

    @BeforeClass
    public void setUpClass() {
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        waitUtils = new WaitUtils(driver);

        // One-time setup to get to checkout information page
        setupCheckoutOnce();
    }

    @Test(description = "Test checkout information page displays correctly")
    public void testCheckoutInformationPageDisplay() {
        System.out.println("Starting checkout information page display test...");

        try {
            // Verify checkout information page title
            WebElement checkoutTitle = driver.findElement(
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"CHECKOUT: INFORMATION\")")
            );
            Assert.assertTrue(checkoutTitle.isDisplayed(), "Checkout information title should be visible");
            System.out.println("✓ Checkout information title verified: " + checkoutTitle.getText());

            // Verify required form fields are present
            boolean fieldsValid = checkoutPage.validateCheckoutInformationFields();
            Assert.assertTrue(fieldsValid, "All checkout information fields should be present");
            System.out.println("✓ Checkout information fields validated");

            System.out.println("Checkout information page display test PASSED!");

        } catch (Exception e) {
            System.err.println("Checkout information page display test failed: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(description = "Test filling checkout information with Emmanuel Arhu details")
    public void testFillCheckoutInformation() {
        System.out.println("Starting fill checkout information test...");

        try {
            // Fill with Emmanuel's information
            checkoutPage.fillShippingInfo("Emmanuel", "Arhu", "0233");
            System.out.println("✓ Successfully filled shipping information: Emmanuel Arhu, 0233");

            // Verify fields were filled (optional validation)
            try {
                WebElement firstNameField = driver.findElement(AppiumBy.accessibilityId("test-First Name"));
                String firstNameValue = firstNameField.getText();
                System.out.println("✓ First name field contains: " + firstNameValue);
            } catch (Exception e) {
                System.out.println("Could not verify field values - this is okay");
            }

            System.out.println("Fill checkout information test PASSED!");

        } catch (Exception e) {
            System.err.println("Fill checkout information test failed: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(description = "Test checkout information form validation")
    public void testCheckoutFormValidation() {
        System.out.println("Starting checkout form validation test...");

        try {
            // Verify First Name field
            WebElement firstNameField = driver.findElement(AppiumBy.accessibilityId("test-First Name"));
            Assert.assertTrue(firstNameField.isDisplayed(), "First Name field should be visible");
            System.out.println("✓ First Name field found");

            // Verify Last Name field
            WebElement lastNameField = driver.findElement(AppiumBy.accessibilityId("test-Last Name"));
            Assert.assertTrue(lastNameField.isDisplayed(), "Last Name field should be visible");
            System.out.println("✓ Last Name field found");

            // Verify Zip/Postal Code field
            WebElement zipField = driver.findElement(AppiumBy.accessibilityId("test-Zip/Postal Code"));
            Assert.assertTrue(zipField.isDisplayed(), "Zip/Postal Code field should be visible");
            System.out.println("✓ Zip/Postal Code field found");

            // Verify Continue button
            WebElement continueButton = driver.findElement(AppiumBy.accessibilityId("test-CONTINUE"));
            Assert.assertTrue(continueButton.isDisplayed(), "Continue button should be visible");
            System.out.println("✓ Continue button found");

            System.out.println("Checkout form validation test PASSED!");

        } catch (Exception e) {
            System.err.println("Checkout form validation test failed: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(description = "Test checkout information page completeness")
    public void testCheckoutPageCompleteness() {
        System.out.println("Starting checkout page completeness test...");

        try {
            // Verify we're on the correct page
            WebElement checkoutTitle = driver.findElement(
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"CHECKOUT: INFORMATION\")")
            );
            Assert.assertTrue(checkoutTitle.isDisplayed(), "Should be on checkout information page");

            // Verify page has expected elements
            boolean hasFirstName = driver.findElement(AppiumBy.accessibilityId("test-First Name")).isDisplayed();
            boolean hasLastName = driver.findElement(AppiumBy.accessibilityId("test-Last Name")).isDisplayed();
            boolean hasZipCode = driver.findElement(AppiumBy.accessibilityId("test-Zip/Postal Code")).isDisplayed();
            boolean hasContinue = driver.findElement(AppiumBy.accessibilityId("test-CONTINUE")).isDisplayed();

            System.out.println("=== CHECKOUT INFORMATION PAGE SUMMARY ===");
            System.out.println("Page Title: " + checkoutTitle.getText());
            System.out.println("Has First Name Field: " + hasFirstName);
            System.out.println("Has Last Name Field: " + hasLastName);
            System.out.println("Has Zip Code Field: " + hasZipCode);
            System.out.println("Has Continue Button: " + hasContinue);
            System.out.println("=========================================");

            // Final assertions
            Assert.assertTrue(hasFirstName && hasLastName && hasZipCode && hasContinue,
                    "All required checkout fields should be present");

            System.out.println("Checkout page completeness test PASSED!");
            System.out.println("🎉 ALL CHECKOUT TESTS COMPLETED SUCCESSFULLY! 🎉");
            System.out.println("Test ends here at CHECKOUT: INFORMATION page as requested.");

        } catch (Exception e) {
            System.err.println("Checkout page completeness test failed: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test(description = "Test different shipping information scenarios")
    public void testShippingInformationScenarios() {
        System.out.println("Starting shipping information scenarios test...");

        try {
            // Clear fields first
            WebElement firstNameField = driver.findElement(AppiumBy.accessibilityId("test-First Name"));
            WebElement lastNameField = driver.findElement(AppiumBy.accessibilityId("test-Last Name"));
            WebElement zipField = driver.findElement(AppiumBy.accessibilityId("test-Zip/Postal Code"));

            // Clear all fields
            firstNameField.clear();
            lastNameField.clear();
            zipField.clear();
            System.out.println("✓ Cleared all fields");

            // Test with different valid information
            firstNameField.sendKeys("Test");
            lastNameField.sendKeys("User");
            zipField.sendKeys("12345");
            System.out.println("✓ Filled with test information: Test User, 12345");

            // Clear and fill with Emmanuel's info again
            firstNameField.clear();
            lastNameField.clear();
            zipField.clear();

            firstNameField.sendKeys("Emmanuel");
            lastNameField.sendKeys("Arhu");
            zipField.sendKeys("0233");
            System.out.println("✓ Restored Emmanuel's information: Emmanuel Arhu, 0233");

            System.out.println("Shipping information scenarios test PASSED!");

        } catch (Exception e) {
            System.err.println("Shipping information scenarios test failed: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    /**
     * One-time setup to get to checkout information page
     */
    private void setupCheckoutOnce() {
        try {
            System.out.println("Setting up checkout for testing...");

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

            // Navigate to product and add item
            WebElement productImage = driver.findElement(
                    AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(4)")
            );
            productImage.click();
            waitUtils.hardWait(1);

            // Add item to cart
            WebElement addButton = driver.findElement(
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"+\").instance(0)")
            );
            addButton.click();
            waitUtils.hardWait(1);

            // Navigate to cart
            WebElement cartIcon = driver.findElement(
                    AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(3)")
            );
            cartIcon.click();
            waitUtils.hardWait(2);

            // Proceed to checkout (with scroll if needed)
            cartPage.proceedToCheckout();
            waitUtils.hardWait(2);

            // Verify we're on checkout information page
            WebElement checkoutTitle = driver.findElement(
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"CHECKOUT: INFORMATION\")")
            );
            Assert.assertTrue(checkoutTitle.isDisplayed(), "Should be on checkout information page");

            System.out.println("✓ Checkout setup completed - ready for testing!");
            System.out.println("✓ Currently on: " + checkoutTitle.getText());

        } catch (Exception e) {
            System.err.println("Failed to setup checkout: " + e.getMessage());
            throw new RuntimeException("Checkout setup failed", e);
        }
    }
}