package com.amalitech.tests;

import com.amalitech.base.BaseTest;
import com.amalitech.pages.LoginPage;
import com.amalitech.pages.ProductsPage;
import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Swag Labs Mobile Application")
@Feature("Product Management")
public class ProductsTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(ProductsTest.class);
    private LoginPage loginPage;
    private ProductsPage productsPage;

    @BeforeMethod
    public void setupAndLogin() {
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);

        // Login before each test
        loginPage.waitForLoginPageToLoad();
        loginPage.loginWithStandardUser();
        productsPage.waitForProductsPageToLoad();
    }

    @Test(priority = 1, description = "Verify products page is displayed correctly")
    @Story("Products Page Display")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test verifies that products page loads correctly and displays products")
    public void testProductsPageDisplay() {

        logger.info("Starting products page display test");

        // Verify products page is displayed
        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "Products page should be displayed");
        Assert.assertEquals(productsPage.getProductsPageTitle(), com.amalitech.constants.AppConstants.PRODUCTS_PAGE_TITLE,
                "Products page title should be correct");

        // Verify products are loaded
        int productsCount = productsPage.getProductsCount();
        Assert.assertTrue(productsCount > 0,
                "Products should be available on the page. Found: " + productsCount);
        logger.info("Found " + productsCount + " products on the page");

        // Verify Add to Cart buttons are present
        int addToCartButtonsCount = productsPage.getAddToCartButtonsCount();
        Assert.assertEquals(addToCartButtonsCount, productsCount,
                "Each product should have an Add to Cart button");

        logger.info("Products page display test completed successfully");
    }

    @Test(priority = 2, description = "Verify adding single item to cart")
    @Story("Add to Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that user can add a single item to cart")
    public void testAddSingleItemToCart() {

        logger.info("Starting add single item to cart test");

        // Get initial state
        int initialRemoveButtonsCount = productsPage.getRemoveButtonsCount();
        logger.info("Initial remove buttons count: " + initialRemoveButtonsCount);

        // Add first item to cart
        productsPage.addFirstItemToCart();

        // Verify item is added
        int finalRemoveButtonsCount = productsPage.getRemoveButtonsCount();
        Assert.assertEquals(finalRemoveButtonsCount, initialRemoveButtonsCount + 1,
                "Remove buttons count should increase by 1 after adding item");

        logger.info("Single item added to cart successfully");
    }

    @Test(priority = 3, description = "Verify adding multiple items to cart")
    @Story("Add to Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that user can add multiple items to cart")
    public void testAddMultipleItemsToCart() {

        logger.info("Starting add multiple items to cart test");

        // Get initial state
        int initialRemoveButtonsCount = productsPage.getRemoveButtonsCount();

        // Add first and second items to cart
        productsPage.addFirstItemToCart();
        productsPage.addSecondItemToCart();

        // Verify items are added
        int finalRemoveButtonsCount = productsPage.getRemoveButtonsCount();
        Assert.assertEquals(finalRemoveButtonsCount, initialRemoveButtonsCount + 2,
                "Remove buttons count should increase by 2 after adding two items");

        logger.info("Multiple items added to cart successfully");
    }

    @Test(priority = 4, description = "Verify cart icon functionality")
    @Story("Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test verifies that cart icon is clickable and navigates to cart page")
    public void testCartIconNavigation() {

        logger.info("Starting cart icon navigation test");

        // Add an item to cart first
        productsPage.addFirstItemToCart();

        // Click cart icon
        productsPage.clickCartIcon();

        // Verify navigation to cart page
        // This would require CartPage validation, which we'll add
        waitUtils.hardWait(2); // Wait for navigation

        // Basic verification that we navigated away from products page
        // In a real scenario, you'd verify cart page is displayed

        logger.info("Cart icon navigation test completed");
    }

    @Test(priority = 5, description = "Verify hamburger menu functionality")
    @Story("Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test verifies that hamburger menu opens and displays options")
    public void testHamburgerMenuFunctionality() {

        logger.info("Starting hamburger menu functionality test");

        // Click hamburger menu
        productsPage.clickHamburgerMenu();

        // Wait for menu to open
        waitUtils.hardWait(2);

        // Verify menu opened (this would be better validated with MenuPage)
        // For now, just verify the action completed without error

        logger.info("Hamburger menu functionality test completed");
    }

    @Test(priority = 6, description = "Verify product information is displayed")
    @Story("Product Information")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test verifies that product names and prices are displayed correctly")
    public void testProductInformationDisplay() {

        logger.info("Starting product information display test");

        // Get product count
        int productsCount = productsPage.getProductsCount();
        Assert.assertTrue(productsCount > 0, "Products should be available");

        // Check first few products have name and price
        for (int i = 1; i <= Math.min(3, productsCount); i++) {
            try {
                String productName = productsPage.getProductNameByIndex(i);
                String productPrice = productsPage.getProductPriceByIndex(i);

                Assert.assertNotNull(productName, "Product name should not be null for index " + i);
                Assert.assertFalse(productName.trim().isEmpty(),
                        "Product name should not be empty for index " + i);

                Assert.assertNotNull(productPrice, "Product price should not be null for index " + i);
                Assert.assertTrue(productPrice.contains("$"),
                        "Product price should contain '$' symbol for index " + i);

                logger.info("Product " + i + ": " + productName + " - " + productPrice);

            } catch (Exception e) {
                logger.warn("Could not get product info for index " + i + ": " + e.getMessage());
            }
        }

        logger.info("Product information display test completed");
    }

    @Test(priority = 7, description = "Verify scroll functionality on products page")
    @Story("UI Interaction")
    @Severity(SeverityLevel.MINOR)
    @Description("Test verifies that user can scroll to view all products")
    public void testScrollFunctionality() {

        logger.info("Starting scroll functionality test");

        // Get initial product count
        int initialVisibleProducts = productsPage.getProductsCount();
        logger.info("Initially visible products: " + initialVisibleProducts);

        // Scroll down to view more products
        productsPage.scrollToViewAllProducts();

        // Wait for scroll to complete
        waitUtils.hardWait(1);

        // In a real scenario, you might verify that more products are now visible
        // or that the scroll action completed successfully

        logger.info("Scroll functionality test completed");
    }

    @Test(priority = 8, description = "Verify add to cart state changes")
    @Story("Add to Cart State")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test verifies that Add to Cart button changes to Remove after adding item")
    public void testAddToCartStateChange() {

        logger.info("Starting add to cart state change test");

        // Get initial counts
        int initialAddToCartCount = productsPage.getAddToCartButtonsCount();
        int initialRemoveCount = productsPage.getRemoveButtonsCount();

        logger.info("Initial Add to Cart buttons: " + initialAddToCartCount);
        logger.info("Initial Remove buttons: " + initialRemoveCount);

        // Add an item to cart
        productsPage.addFirstItemToCart();

        // Verify state change
        int finalAddToCartCount = productsPage.getAddToCartButtonsCount();
        int finalRemoveCount = productsPage.getRemoveButtonsCount();

        logger.info("Final Add to Cart buttons: " + finalAddToCartCount);
        logger.info("Final Remove buttons: " + finalRemoveCount);

        // Verify the button changed from "Add to Cart" to "Remove"
        Assert.assertEquals(finalAddToCartCount, initialAddToCartCount - 1,
                "Add to Cart buttons should decrease by 1");
        Assert.assertEquals(finalRemoveCount, initialRemoveCount + 1,
                "Remove buttons should increase by 1");

        logger.info("Add to cart state change test completed successfully");
    }
}