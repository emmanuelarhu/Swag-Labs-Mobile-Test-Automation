package com.amalitech.tests;

import com.amalitech.base.BaseTest;
import com.amalitech.constants.AppConstants;
import com.amalitech.pages.LoginPage;
import com.amalitech.pages.ProductsPage;
import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Swag Labs Mobile Application")
@Feature("User Authentication")
public class LoginTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(LoginTest.class);
    private LoginPage loginPage;
    private ProductsPage productsPage;

    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        loginPage.waitForLoginPageToLoad();
    }

    @Test(priority = 1, description = "Verify successful login with valid credentials")
    @Story("Valid Login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test verifies that user can successfully login with valid standard user credentials")
    public void testValidLogin() {

        logger.info("Starting valid login test");

        // Verify login page is displayed
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Login page should be displayed");

        // Perform login with valid credentials
        loginPage.loginWithStandardUser();

        // Verify successful login
        productsPage.waitForProductsPageToLoad();
        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "Products page should be displayed after successful login");
        Assert.assertEquals(productsPage.getProductsPageTitle(), AppConstants.PRODUCTS_PAGE_TITLE,
                "Products page title should match expected value");

        logger.info("Valid login test completed successfully");
    }

    @Test(priority = 2, description = "Verify login with locked out user")
    @Story("Locked User Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that locked out user cannot login and appropriate error message is displayed")
    public void testLockedOutUserLogin() {

        logger.info("Starting locked out user login test");

        // Clear any existing data and perform login with locked user
        loginPage.clearAllFields();
        loginPage.loginWithLockedOutUser();

        // Verify error message is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed for locked out user");

        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("locked out") || errorMessage.contains("Sorry"),
                "Error message should indicate user is locked out. Actual: " + errorMessage);

        // Verify still on login page
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Should still be on login page after failed login");

        logger.info("Locked out user login test completed successfully");
    }

    @Test(priority = 3, description = "Verify login with invalid credentials")
    @Story("Invalid Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test verifies that user cannot login with invalid credentials and error message is displayed")
    public void testInvalidCredentialsLogin() {

        logger.info("Starting invalid credentials login test");

        // Clear fields and enter invalid credentials
        loginPage.clearAllFields();
        loginPage.login("invalid_user", "invalid_password");

        // Verify error message is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed for invalid credentials");

        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("do not match") || errorMessage.contains("invalid"),
                "Error message should indicate credentials don't match. Actual: " + errorMessage);

        // Verify still on login page
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Should still be on login page after failed login");

        logger.info("Invalid credentials login test completed successfully");
    }

    @Test(priority = 4, description = "Verify login with empty username")
    @Story("Empty Field Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test verifies that appropriate error is shown when username field is empty")
    public void testEmptyUsernameLogin() {

        logger.info("Starting empty username login test");

        // Clear fields and enter only password
        loginPage.clearAllFields();
        loginPage.enterPassword(AppConstants.PASSWORD);
        loginPage.clickLoginButton();

        // Verify error message is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed for empty username");

        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("required") || errorMessage.contains("Username"),
                "Error message should indicate username is required. Actual: " + errorMessage);

        logger.info("Empty username login test completed successfully");
    }

    @Test(priority = 5, description = "Verify login with empty password")
    @Story("Empty Field Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test verifies that appropriate error is shown when password field is empty")
    public void testEmptyPasswordLogin() {

        logger.info("Starting empty password login test");

        // Clear fields and enter only username
        loginPage.clearAllFields();
        loginPage.enterUsername(AppConstants.STANDARD_USER);
        loginPage.clickLoginButton();

        // Verify error message is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed for empty password");

        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("required") || errorMessage.contains("Password"),
                "Error message should indicate password is required. Actual: " + errorMessage);

        logger.info("Empty password login test completed successfully");
    }

    @Test(priority = 6, description = "Verify login with problem user")
    @Story("Problem User Login")
    @Severity(SeverityLevel.MINOR)
    @Description("Test verifies that problem user can login but may have issues on products page")
    public void testProblemUserLogin() {

        logger.info("Starting problem user login test");

        // Clear fields and login with problem user
        loginPage.clearAllFields();
        loginPage.loginWithProblemUser();

        // Problem user should be able to login
        productsPage.waitForProductsPageToLoad();
        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "Products page should be displayed after problem user login");

        // Note: Problem user might have issues with product images or other functionality
        // This test verifies basic login capability

        logger.info("Problem user login test completed successfully");
    }

    @Test(priority = 7, description = "Verify login page elements are displayed correctly")
    @Story("UI Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test verifies all login page elements are present and visible")
    public void testLoginPageElements() {

        logger.info("Starting login page elements validation test");

        // Verify all login page elements
        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(),
                "Username field should be displayed");
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(),
                "Password field should be displayed");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(),
                "Login button should be displayed");

        // Verify login page image is present
        String loginImageSelector = "new UiSelector().className(\"android.widget.ImageView\").instance(1)";
        Assert.assertTrue(loginPage.isElementDisplayedByUiSelector(loginImageSelector),
                "Login page image should be displayed");

        logger.info("Login page elements validation completed successfully");
    }
}