package com.amalitech.tests;

import com.amalitech.base.BaseTest;
import com.amalitech.constants.*;
import com.amalitech.pages.*;
import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Swag Labs Mobile Application")
@Feature("End-to-End User Journey")
public class EndToEndTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(EndToEndTest.class);

    @Test(description = "Complete user journey from login to logout")
    @Story("Complete E2E user flow")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test covers the complete user journey: login, browse products, add items to cart, checkout, and logout")
    public void testCompleteUserJourney() {

        logger.info("Starting complete user journey test");

        // Initialize page objects
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        MenuPage menuPage = new MenuPage(driver);

        // Step 1: Wait for application to load and perform login
        performLogin(loginPage, productsPage);

        // Step 2: Browse products and add items to cart
        addItemsToCart(productsPage);

        // Step 3: Navigate to cart and verify items
        navigateToCartAndVerifyItems(productsPage, cartPage);

        // Step 4: Remove one item from cart
        removeItemFromCart(cartPage);

        // Step 5: Proceed with checkout
        proceedWithCheckout(cartPage, checkoutPage);

        // Step 6: Fill checkout information and complete purchase
        completeCheckoutProcess(checkoutPage);

        // Step 7: Return to home and logout
        returnHomeAndLogout(checkoutPage, menuPage, loginPage);

        logger.info("Complete user journey test completed successfully");
    }

    @Step("Perform login with standard user")
    private void performLogin(LoginPage loginPage, ProductsPage productsPage) {
        logger.info("Step 1: Performing login");

        // Wait for login page and login
        loginPage.waitForLoginPageToLoad();
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");

        loginPage.loginWithStandardUser();

        // Verify successful login by checking products page
        productsPage.waitForProductsPageToLoad();
        Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Products page should be displayed after login");
        Assert.assertEquals(productsPage.getProductsPageTitle(), AppConstants.PRODUCTS_PAGE_TITLE,
                "Products page title should be correct");

        logger.info("Login successful - Products page displayed");
    }

    @Step("Add items to cart")
    private void addItemsToCart(ProductsPage productsPage) {
        logger.info("Step 2: Adding items to cart");

        // Get initial product count
        int initialProductCount = productsPage.getProductsCount();
        Assert.assertTrue(initialProductCount > 0, "Products should be available on the page");
        logger.info("Found " + initialProductCount + " products available");

        // Add first and second items to cart
        productsPage.addFirstItemToCart();
        logger.info("Added first item to cart");

        productsPage.addSecondItemToCart();
        logger.info("Added second item to cart");

        // Verify items are added (check remove buttons count)
        Assert.assertEquals(productsPage.getRemoveButtonsCount(), 2,
                "Should have 2 remove buttons indicating items in cart");

        logger.info("Successfully added 2 items to cart");
    }

    @Step("Navigate to cart and verify items")
    private void navigateToCartAndVerifyItems(ProductsPage productsPage, CartPage cartPage) {
        logger.info("Step 3: Navigating to cart and verifying items");

        // Click on cart icon
        productsPage.clickCartIcon();

        // Verify cart page is displayed
        cartPage.waitForCartPageToLoad();
        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page should be displayed");
        Assert.assertEquals(cartPage.getCartPageTitle(), AppConstants.CART_PAGE_TITLE,
                "Cart page title should be correct");

        // Verify cart has items
        int cartItemsCount = cartPage.getCartItemsCount();
        Assert.assertEquals(cartItemsCount, 2, "Cart should contain 2 items");

        // Verify remove button is visible
        Assert.assertTrue(cartPage.isRemoveButtonVisible(), "Remove button should be visible");

        logger.info("Cart page verified with " + cartItemsCount + " items");
    }

    @Step("Remove one item from cart")
    private void removeItemFromCart(CartPage cartPage) {
        logger.info("Step 4: Removing one item from cart");

        // Remove first item
        cartPage.removeFirstItem();

        // Verify item is removed
        Assert.assertEquals(cartPage.getCartItemsCount(), 1,
                "Cart should contain 1 item after removing one");

        logger.info("Successfully removed one item from cart");
    }

    @Step("Proceed with checkout")
    private void proceedWithCheckout(CartPage cartPage, CheckoutPage checkoutPage) {
        logger.info("Step 5: Proceeding with checkout");

        // Click checkout button
        Assert.assertTrue(cartPage.isCheckoutButtonDisplayed(), "Checkout button should be displayed");
        cartPage.clickCheckoutButton();

        // Verify checkout information page
        checkoutPage.waitForCheckoutInformationPageToLoad();
        Assert.assertTrue(checkoutPage.isCheckoutInformationPageDisplayed(),
                "Checkout information page should be displayed");
        Assert.assertEquals(checkoutPage.getCheckoutInformationTitle(),
                AppConstants.CHECKOUT_INFO_TITLE, "Checkout info title should be correct");

        logger.info("Checkout information page displayed");
    }

    @Step("Complete checkout process")
    private void completeCheckoutProcess(CheckoutPage checkoutPage) {
        logger.info("Step 6: Completing checkout process");

        // Fill checkout information
        Assert.assertTrue(checkoutPage.validateCheckoutInformationFields(),
                "Checkout form fields should be displayed");

        checkoutPage.fillCheckoutInformationWithDefaults();
        checkoutPage.clickContinueButton();

        // Verify checkout overview page
        Assert.assertTrue(checkoutPage.isCheckoutOverviewPageDisplayed(),
                "Checkout overview page should be displayed");

        // Scroll down and finish checkout
        checkoutPage.clickFinishButton();

        // Verify checkout complete page
        checkoutPage.waitForCheckoutCompletePageToLoad();
        Assert.assertTrue(checkoutPage.isCheckoutCompletePageDisplayed(),
                "Checkout complete page should be displayed");
        Assert.assertEquals(checkoutPage.getCheckoutCompleteTitle(),
                AppConstants.CHECKOUT_COMPLETE_TITLE, "Checkout complete title should be correct");

        // Verify thank you message or back home button
        Assert.assertTrue(checkoutPage.isBackHomeButtonDisplayed(),
                "Back home button should be displayed");

        logger.info("Checkout process completed successfully");
    }

    @Step("Return home and logout")
    private void returnHomeAndLogout(CheckoutPage checkoutPage, MenuPage menuPage, LoginPage loginPage) {
        logger.info("Step 7: Returning home and logging out");

        // Click back home button
        checkoutPage.clickBackHomeButton();

        // Verify we're back on products page (or home page)
        // Small wait to ensure page transition
        waitUtils.hardWait(2);

        // Open hamburger menu and logout
        Assert.assertTrue(menuPage.isHamburgerMenuDisplayed(), "Hamburger menu should be displayed");
        menuPage.performLogout();

        // Verify logout successful - should be back to login page
        loginPage.waitForLoginPageToLoad();
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Should be back to login page after logout");

        // Verify login page image is there (as per test requirement)
        String loginImageSelector = "new UiSelector().className(\"android.widget.ImageView\").instance(1)";
        Assert.assertTrue(loginPage.isElementDisplayedByUiSelector(loginImageSelector),
                "Login page image should be displayed");

        logger.info("Successfully logged out and returned to login page");
    }
}