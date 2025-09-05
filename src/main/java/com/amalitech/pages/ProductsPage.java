package com.amalitech.pages;

import com.amalitech.base.BasePage;
import com.amalitech.constants.AppConstants;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductsPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(ProductsPage.class);

    // Page elements
    private final String PRODUCTS_TITLE_XPATH = "//android.widget.TextView[@text='PRODUCTS']";
    private final String FIRST_ITEM_XPATH = "(//android.view.ViewGroup[@content-desc='test-Item'])[1]/android.view.ViewGroup";
    private final String SECOND_ITEM_XPATH = "(//android.view.ViewGroup[@content-desc='test-Item'])[2]/android.view.ViewGroup";
    private final String CART_ICON_SELECTOR = "new UiSelector().className(\"android.widget.ImageView\").instance(3)";
    private final String HAMBURGER_MENU_SELECTOR = "new UiSelector().className(\"android.widget.ImageView\").instance(1)";
    private final String ADD_TO_CART_BUTTONS_XPATH = "//android.widget.TextView[@text='ADD TO CART']";
    private final String REMOVE_BUTTONS_XPATH = "//android.widget.TextView[@text='REMOVE']";

    public ProductsPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Check if products page is displayed
     */
    public boolean isProductsPageDisplayed() {
        logger.debug("Checking if products page is displayed");
        try {
            return isElementDisplayedByXpath(PRODUCTS_TITLE_XPATH);
        } catch (Exception e) {
            logger.warn("Products page title not found", e);
            return false;
        }
    }

    /**
     * Wait for products page to load
     */
    public ProductsPage waitForProductsPageToLoad() {
        logger.info("Waiting for products page to load");
        waitUtils.waitForElementToBeVisible(By.xpath(PRODUCTS_TITLE_XPATH));
        return this;
    }

    /**
     * Get products page title text
     */
    public String getProductsPageTitle() {
        logger.debug("Getting products page title");
        return getTextByXpath(PRODUCTS_TITLE_XPATH);
    }

    /**
     * Add first item to cart
     */
    public ProductsPage addFirstItemToCart() {
        logger.info("Adding first item to cart");
        clickByXpath(FIRST_ITEM_XPATH);
        return this;
    }

    /**
     * Add second item to cart
     */
    public ProductsPage addSecondItemToCart() {
        logger.info("Adding second item to cart");
        clickByXpath(SECOND_ITEM_XPATH);
        return this;
    }

    /**
     * Click on cart icon
     */
    public void clickCartIcon() {
        logger.info("Clicking cart icon");
        clickByUiSelector(CART_ICON_SELECTOR);
    }

    /**
     * Click on hamburger menu
     */
    public void clickHamburgerMenu() {
        logger.info("Clicking hamburger menu");
        clickByUiSelector(HAMBURGER_MENU_SELECTOR);
    }

    /**
     * Get list of all products
     */
    public List<WebElement> getAllProducts() {
        logger.debug("Getting all products");
        return driver.findElements(By.xpath("//android.view.ViewGroup[@content-desc='test-Item']"));
    }

    /**
     * Get count of products
     */
    public int getProductsCount() {
        int count = getAllProducts().size();
        logger.info("Found " + count + " products on the page");
        return count;
    }

    /**
     * Get list of all "Add to Cart" buttons
     */
    public List<WebElement> getAllAddToCartButtons() {
        logger.debug("Getting all 'Add to Cart' buttons");
        return driver.findElements(By.xpath(ADD_TO_CART_BUTTONS_XPATH));
    }

    /**
     * Get count of "Add to Cart" buttons
     */
    public int getAddToCartButtonsCount() {
        int count = getAllAddToCartButtons().size();
        logger.info("Found " + count + " 'Add to Cart' buttons");
        return count;
    }

    /**
     * Get list of all "Remove" buttons
     */
    public List<WebElement> getAllRemoveButtons() {
        logger.debug("Getting all 'Remove' buttons");
        return driver.findElements(By.xpath(REMOVE_BUTTONS_XPATH));
    }

    /**
     * Get count of "Remove" buttons (indicates items in cart)
     */
    public int getRemoveButtonsCount() {
        int count = getAllRemoveButtons().size();
        logger.info("Found " + count + " 'Remove' buttons (items in cart)");
        return count;
    }

    public String getProductNameByIndex(int index) {
        logger.debug("Getting product name at index: " + index);
        String productNameXpath = String.format(
                "(//android.view.ViewGroup[@content-desc='test-Item'])[%d]//android.widget.TextView[1]",
                index
        );
        return getTextByXpath(productNameXpath);
    }

    public String getProductPriceByIndex(int index) {
        logger.debug("Getting product price at index: " + index);
        String productPriceXpath = String.format(
                "(//android.view.ViewGroup[@content-desc='test-Item'])[%d]//android.widget.TextView[contains(@text, '$')]",
                index
        );
        return getTextByXpath(productPriceXpath);
    }

    public ProductsPage scrollToViewAllProducts() {
        logger.info("Scrolling to view all products");
        scrollDown();
        return this;
    }
}