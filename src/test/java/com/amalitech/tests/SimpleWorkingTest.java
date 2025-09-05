package com.amalitech.tests;

import com.amalitech.base.BaseTest;
import com.amalitech.pages.LoginPage;
import com.amalitech.pages.ProductsPage;
import com.amalitech.utils.WaitUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SimpleWorkingTest extends BaseTest {

    private LoginPage loginPage;
    private ProductsPage productsPage;
    private WaitUtils waitUtils;

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        waitUtils = new WaitUtils(driver);

        // Wait for app to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test(description = "Simple login test that should work")
    public void testSimpleLogin() {
        System.out.println("Starting simple login test...");

        try {
            // Navigate to login if needed
            loginPage.navigateToLoginIfNeeded();

            // Clear any existing data
            loginPage.clearAllFields();

            // Perform login
            loginPage.loginWithStandardUser();

            // Wait for products page
            waitUtils.hardWait(3);

            // Simple verification - just check if we can find the products title
            boolean productsPageDisplayed = productsPage.isProductsPageDisplayed();
            Assert.assertTrue(productsPageDisplayed, "Should be on products page after login");

            System.out.println("Simple login test passed!");

        } catch (Exception e) {
            System.err.println("Simple login test failed: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Simple login test failed: " + e.getMessage());
        }
    }
}