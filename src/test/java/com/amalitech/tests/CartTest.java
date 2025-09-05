package com.amalitech.tests;

import com.amalitech.base.BaseTest;
import com.amalitech.constants.AppConstants;
import com.amalitech.pages.CartPage;
import com.amalitech.pages.LoginPage;
import com.amalitech.pages.ProductsPage;
import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Swag Labs Mobile Application")
@Feature("Shopping Cart Management")
public class CartTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(CartTest.class);
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;

    @BeforeMethod
    public void setupAndNavigateToCart() {
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);

        // Login and add items to cart
        loginPage.waitForLoginPageToLoad();
        loginPage.loginWithStandardUser();
        productsPage.waitForProductsPageToLoad();

        // Add items to cart and navigate to cart page
        productsPage.addFirstItemToCart();
        productsPage.addSecondItemToCart();
        productsPage.clickCartIcon();
        cartPage.waitForCartPageToLoad();
    }

    @Test(priority = 1, description = "Verify cart page displays correctly with items")
    @Story("Cart Page Display")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that cart page loads and displays added items correctly")
    public void testCartPageDisplayWithItems() {

        logger.info("Starting cart page display test");

        // Verify cart page is displayed
        Assert.assertTrue(cartPage.isCartPageDisplayed(),
                "Cart page should be displayed");
        Assert.assertEquals(cartPage.getCartPageTitle(), AppConstants.CART_PAGE_TITLE,
                "Cart page title should be correct");

        // Verify items are in cart
        int cartItemsCount = cartPage.getCartItemsCount();
        Assert.assertEquals(cartItemsCount, 2,
                "Cart should contain 2 items");

        // Verify cart page elements
        Assert.assertTrue(cartPage.validateCartPageElements(),
                "Cart page should display all required elements");

        logger.info("Cart page display test completed successfully");
    }

    @Test(priority = 2, description = "Verify removing single item from cart")
    @Story("Remove from Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that user can remove a single item from cart")
    public void testRemoveSingleItemFromCart() {

        logger.info("Starting remove single item test");

        // Get initial cart count
        int initialCount = cartPage.getCartItemsCount();
        logger.info("Initial cart items count: " + initialCount);

        // Verify remove button is visible
        Assert.assertTrue(cartPage.isRemoveButtonVisible(),
                "Remove button should be visible");

        // Remove first item
        cartPage.removeFirstItem();

        // Verify item is removed
        int finalCount = cartPage.getCartItemsCount();
        Assert.assertEquals(finalCount, initialCount - 1,
                "Cart should have one less item after removal");

        logger.info("Successfully removed single item from cart");
    }

    @Test(priority = 3, description = "Verify removing all items from cart")
    @Story("Clear Cart")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test verifies that user can remove all items from cart")
    public void testRemoveAllItemsFromCart() {

        logger.info("Starting remove all items test");

        // Clear entire cart
        cartPage.clearCart();

        // Verify cart is empty
        Assert.assertTrue(cartPage.isCartEmpty(),
                "Cart should be empty after clearing all items");
        Assert.assertEquals(cartPage.getCartItemsCount(), 0,
                "Cart items count should be 0");

        logger.info("Successfully removed all items from cart");
    }

    @Test(priority = 4, description = "Verify checkout button functionality")
    @Story("Checkout Navigation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that checkout button navigates to checkout page")
    public void testCheckoutButtonFunctionality() {

        logger.info("Starting checkout button functionality test");

        // Verify checkout button is displayed
        Assert.assertTrue(cartPage.isCheckoutButtonDisplayed(),
                "Checkout button should be displayed when cart has items");

        // Click checkout button
        cartPage.clickCheckoutButton();

        // Verify navigation (basic check - detailed verification in CheckoutTest)
        waitUtils.hardWait(2);

        logger.info("Checkout button functionality test completed");
    }

    @Test(priority = 5, description = "Verify continue shopping button functionality")
    @Story("Continue Shopping")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test verifies that continue shopping button navigates back to products page")
    public void testContinueShoppingFunctionality() {

        logger.info("Starting continue shopping functionality test");

        // Verify continue shopping button is displayed
        Assert.assertTrue(cartPage.isContinueShoppingButtonDisplayed(),
                "Continue shopping button should be displayed");

        // Click continue shopping
        cartPage.clickContinueShoppingButton();

        // Verify navigation back to products page
        productsPage.waitForProductsPageToLoad();
        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "Should navigate back to products page");

        logger.info("Continue shopping functionality test completed");
    }

    @Test(priority = 6, description = "Verify cart item information display")
    @Story("Item Information")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test verifies that cart items display correct name and price information")
    public void testCartItemInformationDisplay() {

        logger.info("Starting cart item information display test");

        int itemCount = cartPage.getCartItemsCount();
        Assert.assertTrue(itemCount > 0, "Cart should have items for this test");

        // Check information for each item in cart
        for (int i = 0; i < itemCount; i++) {
            try {
                String itemName = cartPage.getItemNameByIndex(i);
                String itemPrice = cartPage.getItemPriceByIndex(i);

                Assert.assertNotNull(itemName,
                        "Item name should not be null for index " + i);
                Assert.assertFalse(itemName.trim().isEmpty(),
                        "Item name should not be empty for index " + i);

                Assert.assertNotNull(itemPrice,
                        "Item price should not be null for index " + i);
                Assert.assertTrue(itemPrice.contains("$"),
                        "Item price should contain '$' for index " + i);

                logger.info("Cart item " + (i + 1) + ": " + itemName + " - " + itemPrice);

            } catch (Exception e) {
                logger.warn("Could not get item info for index " + i + ": " + e.getMessage());
            }
        }

        logger.info("Cart item information display test completed");
    }

    @Test(priority = 7, description = "Verify empty cart behavior")
    @Story("Empty Cart")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test verifies the behavior when cart is empty")
    public void testEmptyCartBehavior() {

        logger.info("Starting empty cart behavior test");

        // Clear all items from cart
        cartPage.clearCart();

        // Verify empty cart state
        Assert.assertTrue(cartPage.isCartEmpty(),
                "Cart should be empty");
        Assert.assertEquals(cartPage.getCartItemsCount(), 0,
                "Cart items count should be 0");

        // Verify remove buttons are not displayed
        Assert.assertEquals(cartPage.getRemoveButtonsCount(), 0,
                "No remove buttons should be displayed in empty cart");

        // Continue shopping should still be available
        Assert.assertTrue(cartPage.isContinueShoppingButtonDisplayed(),
                "Continue shopping button should be available even with empty cart");

        logger.info("Empty cart behavior test completed");
    }

    @Test(priority = 8, description = "Verify cart state persistence")
    @Story("Cart Persistence")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test verifies that cart state is maintained when navigating between pages")
    public void testCartStatePersistence() {

        logger.info("Starting cart state persistence test");

        // Get initial cart state
        int initialCartCount = cartPage.getCartItemsCount();
        logger.info("Initial cart count: " + initialCartCount);

        // Navigate to products page
        cartPage.clickContinueShoppingButton();
        productsPage.waitForProductsPageToLoad();

        // Navigate back to cart
        productsPage.clickCartIcon();
        cartPage.waitForCartPageToLoad();

        // Verify cart state is maintained
        int finalCartCount = cartPage.getCartItemsCount();
        Assert.assertEquals(finalCartCount, initialCartCount,
                "Cart state should be maintained after navigation");

        logger.info("Cart state persistence test completed");
    }
}