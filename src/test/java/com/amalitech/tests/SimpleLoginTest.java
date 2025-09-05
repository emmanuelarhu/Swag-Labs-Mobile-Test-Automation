package com.amalitech.tests;

import com.amalitech.base.BaseTest;
import com.amalitech.constants.AppConstants;
import com.amalitech.pages.LoginPage;
import com.amalitech.pages.ProductsPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SimpleLoginTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(SimpleLoginTest.class);
    private LoginPage loginPage;
    private ProductsPage productsPage;

    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);

        // Wait for login page to load
        waitUtils.hardWait(3); // Initial wait for app to load
    }

    @Test(priority = 1, description = "Verify successful login with valid credentials")
    public void testValidLogin() {

        logger.info("Starting valid login test");

        // Verify login page is displayed
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Login page should be displayed");

        // Perform login with valid credentials
        loginPage.loginWithStandardUser();

        // Wait for navigation
        waitUtils.hardWait(3);

        // Verify successful login by checking products page
        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "Products page should be displayed after successful login");

        logger.info("Valid login test completed successfully");
    }

    @Test(priority = 2, description = "Verify login with locked out user")
    public void testLockedOutUserLogin() {

        logger.info("Starting locked out user login test");

        // Clear any existing data and perform login with locked user
        loginPage.clearAllFields();
        loginPage.loginWithLockedOutUser();

        // Wait for error message
        waitUtils.hardWait(2);

        // Verify error message is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed for locked out user");

        // Verify still on login page
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Should still be on login page after failed login");

        logger.info("Locked out user login test completed successfully");
    }
}