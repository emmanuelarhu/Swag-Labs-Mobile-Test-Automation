package com.amalitech.pages;

import com.amalitech.base.BasePage;
import com.amalitech.constants.AppConstants;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(CartPage.class);

    // Page elements
    private final String CART_TITLE_XPATH = "//android.widget.TextView[@text='YOUR CART']";
    private final String REMOVE_BUTTONS_XPATH = "//android.widget.TextView[@text='REMOVE']";
    private final String CHECKOUT_BUTTON_XPATH = "//android.widget.TextView[@text='CHECKOUT']";
    private final String CONTINUE_SHOPPING_BUTTON_XPATH = "//android.widget.TextView[@text='CONTINUE SHOPPING']";
    private final String CART_ITEMS_XPATH = "//android.view.ViewGroup[@content-desc='test-Item']";
    private final String FIRST_REMOVE_BUTTON_XPATH = "(//android.widget.TextView[@text='REMOVE'])[1]";

    public CartPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Check if cart page is displayed
     */
    public boolean isCartPageDisplayed() {
        logger.debug("Checking if cart page is displayed");
        try {
            return isElementDisplayedByXpath(CART_TITLE_XPATH);
        } catch (Exception e) {
            logger.warn("Cart page title not found", e);
            return false;
        }
    }

    /**
     * Wait for cart page to load
     */
    public CartPage waitForCartPageToLoad() {
        logger.info("Waiting for cart page to load");
        waitUtils.waitForElementToBeVisible(By.xpath(CART_TITLE_XPATH));
        return this;
    }

    /**
     * Get cart page title text
     */
    public String getCartPageTitle() {
        logger.debug("Getting cart page title");
        return getTextByXpath(CART_TITLE_XPATH);
    }

    /**
     * Get list of all items in cart
     */
    public List<WebElement> getCartItems() {
        logger.debug("Getting all items in cart");
        return driver.findElements(By.xpath(CART_ITEMS_XPATH));
    }

    /**
     * Get count of items in cart
     */
    public int getCartItemsCount() {
        int count = getCartItems().size();
        logger.info("Found " + count + " items in cart");
        return count;
    }

    /**
     * Check if remove button is visible
     */
    public boolean isRemoveButtonVisible() {
        logger.debug("Checking if remove button is visible");
        return isElementDisplayedByXpath(REMOVE_BUTTONS_XPATH);
    }

    /**
     * Remove first item from cart
     */
    public CartPage removeFirstItem() {
        logger.info("Removing first item from cart");
        clickByXpath(FIRST_REMOVE_BUTTON_XPATH);
        return this;
    }

    /**
     * Click checkout button
     */
    public void clickCheckoutButton() {
        logger.info("Clicking checkout button");
        clickByXpath(CHECKOUT_BUTTON_XPATH);
    }

    /**
     * Click continue shopping button
     */
    public void clickContinueShoppingButton() {
        logger.info("Clicking continue shopping button");
        clickByXpath(CONTINUE_SHOPPING_BUTTON_XPATH);
    }

    /**
     * Check if checkout button is displayed
     */
    public boolean isCheckoutButtonDisplayed() {
        logger.debug("Checking if checkout button is displayed");
        return isElementDisplayedByXpath(CHECKOUT_BUTTON_XPATH);
    }

    /**
     * Check if continue shopping button is displayed
     */
    public boolean isContinueShoppingButtonDisplayed() {
        logger.debug("Checking if continue shopping button is displayed");
        return isElementDisplayedByXpath(CONTINUE_SHOPPING_BUTTON_XPATH);
    }

    /**
     * Check if cart is empty
     */
    public boolean isCartEmpty() {
        logger.debug("Checking if cart is empty");
        return getCartItemsCount() == 0;
    }

    /**
     * Get item name by index
     */
    public String getItemNameByIndex(int index) {
        logger.debug("Getting item name at index: " + index);
        String itemNameXpath = String.format(
                "(//android.view.ViewGroup[@content-desc='test-Item'])[%d]//android.widget.TextView[1]",
                index + 1
        );
        return getTextByXpath(itemNameXpath);
    }

    /**
     * Get item price by index
     */
    public String getItemPriceByIndex(int index) {
        logger.debug("Getting item price at index: " + index);
        String itemPriceXpath = String.format(
                "(//android.view.ViewGroup[@content-desc='test-Item'])[%d]//android.widget.TextView[contains(@text, '$')]",
                index + 1
        );
        return getTextByXpath(itemPriceXpath);
    }

    /**
     * Get list of all remove buttons
     */
    public List<WebElement> getRemoveButtons() {
        logger.debug("Getting all remove buttons");
        return driver.findElements(By.xpath(REMOVE_BUTTONS_XPATH));
    }

    /**
     * Get count of remove buttons
     */
    public int getRemoveButtonsCount() {
        int count = getRemoveButtons().size();
        logger.info("Found " + count + " remove buttons in cart");
        return count;
    }

    /**
     * Clear all items from cart
     */
    public CartPage clearCart() {
        logger.info("Clearing all items from cart");
        List<WebElement> removeButtons = getRemoveButtons();
        for (WebElement removeButton : removeButtons) {
            removeButton.click();
            waitForPageLoad(); // Wait for item to be removed
        }
        return this;
    }

    /**
     * Validate cart page elements
     */
    public boolean validateCartPageElements() {
        logger.info("Validating cart page elements");
        return isCartPageDisplayed() &&
                (isCheckoutButtonDisplayed() || getCartItemsCount() == 0) &&
                isContinueShoppingButtonDisplayed();
    }
}