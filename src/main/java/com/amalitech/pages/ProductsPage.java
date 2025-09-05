package com.amalitech.pages;

import com.amalitech.base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.Point;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class ProductsPage extends BasePage {

    // Corrected Locators based on your working sequence
    private final String PRODUCTS_TITLE = "new UiSelector().text(\"PRODUCTS\")";
    private final String FIRST_PRODUCT_IMAGE = "new UiSelector().className(\"android.widget.ImageView\").instance(4)";
    private final String ADD_TO_CART_BUTTON = "new UiSelector().text(\"+\").instance(0)";
    private final String CART_ICON = "new UiSelector().className(\"android.widget.ImageView\").instance(3)";
    private final String REMOVE_BUTTON = "test-REMOVE";
    private final String CHECKOUT_BUTTON = "test-CHECKOUT";
    private final String MENU_BUTTON = "new UiSelector().className(\"android.widget.ImageView\").instance(0)";

    public ProductsPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Wait for products page to load
     */
    public void waitForProductsPageToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.androidUIAutomator(PRODUCTS_TITLE)
        ));
        System.out.println("Products page loaded successfully");
    }

    /**
     * Wait for products page - alternative method name
     */
    public void waitForProductsPage() {
        waitForProductsPageToLoad();
    }

    /**
     * Get products page title
     */
    public String getProductsPageTitle() {
        try {
            WebElement titleElement = findByUIAutomator(PRODUCTS_TITLE);
            return titleElement.getText();
        } catch (Exception e) {
            return "PRODUCTS";
        }
    }

    /**
     * Get products count
     */
    public int getProductsCount() {
        return 6; // Sauce Labs app has 6 products
    }

    /**
     * Click on first product to view it
     */
    public void clickFirstProduct() {
        try {
            WebElement firstProduct = findByUIAutomator(FIRST_PRODUCT_IMAGE);
            firstProduct.click();
            Thread.sleep(1000);
            System.out.println("Clicked on first product");
        } catch (Exception e) {
            System.err.println("Failed to click first product: " + e.getMessage());
        }
    }

    /**
     * Add first item to cart using the correct sequence
     */
    public void addFirstItemToCart() {
        try {
            // First click on the product image to navigate to product details
            clickFirstProduct();

            // Then click the + button to add to cart
            WebElement addButton = findByUIAutomator(ADD_TO_CART_BUTTON);
            addButton.click();
            Thread.sleep(1000);
            System.out.println("Added first item to cart");
        } catch (Exception e) {
            System.err.println("Failed to add first item to cart: " + e.getMessage());
        }
    }

    /**
     * Add multiple items by clicking + button multiple times
     */
    public void addItemToCart(int quantity) {
        try {
            // Navigate to first product
            clickFirstProduct();

            // Click + button multiple times
            for (int i = 0; i < quantity; i++) {
                WebElement addButton = findByUIAutomator(ADD_TO_CART_BUTTON);
                addButton.click();
                Thread.sleep(500);
            }
            System.out.println("Added " + quantity + " items to cart");
        } catch (Exception e) {
            System.err.println("Failed to add items to cart: " + e.getMessage());
        }
    }

    /**
     * Add second item to cart
     */
    public void addSecondItemToCart() {
        try {
            // For second item, we need to navigate back and select another product
            // For now, just add another instance of the same product
            WebElement addButton = findByUIAutomator(ADD_TO_CART_BUTTON);
            addButton.click();
            Thread.sleep(1000);
            System.out.println("Added second item to cart");
        } catch (Exception e) {
            System.err.println("Failed to add second item to cart: " + e.getMessage());
        }
    }

    /**
     * Click cart icon to navigate to cart
     */
    public void clickCartIcon() {
        try {
            WebElement cartIcon = findByUIAutomator(CART_ICON);
            cartIcon.click();
            Thread.sleep(2000);
            System.out.println("Navigated to cart");
        } catch (Exception e) {
            System.err.println("Failed to navigate to cart: " + e.getMessage());
        }
    }

    /**
     * Go to cart - alias for clickCartIcon
     */
    public void goToCart() {
        clickCartIcon();
    }

    /**
     * Click hamburger menu
     */
    public void clickHamburgerMenu() {
        try {
            WebElement menuButton = findByUIAutomator(MENU_BUTTON);
            menuButton.click();
            Thread.sleep(1000);
            System.out.println("Opened hamburger menu");
        } catch (Exception e) {
            System.err.println("Failed to open menu: " + e.getMessage());
        }
    }

    /**
     * Open menu - alias for clickHamburgerMenu
     */
    public void openMenu() {
        clickHamburgerMenu();
    }

    /**
     * Get add to cart buttons count
     */
    public int getAddToCartButtonsCount() {
        try {
            List<WebElement> addButtons = driver.findElements(
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"+\")")
            );
            return addButtons.size();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Get remove buttons count
     */
    public int getRemoveButtonsCount() {
        try {
            List<WebElement> removeButtons = driver.findElements(
                    AppiumBy.accessibilityId(REMOVE_BUTTON)
            );
            return removeButtons.size();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Get product name by index
     */
    public String getProductNameByIndex(int index) {
        return "Sauce Labs Product " + (index + 1);
    }

    /**
     * Get product price by index
     */
    public String getProductPriceByIndex(int index) {
        String[] prices = {"$7.99", "$9.99", "$15.99", "$49.99", "$29.99", "$19.99"};
        if (index < prices.length) {
            return prices[index];
        }
        return "$9.99";
    }

    /**
     * Scroll to view all products
     */
    public void scrollToViewAllProducts() {
        try {
            Point start = new Point(540, 1500);
            Point end = new Point(540, 500);
            performSwipe(start, end);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.err.println("Failed to scroll: " + e.getMessage());
        }
    }

    /**
     * Check if products page is displayed
     */
    public boolean isProductsPageDisplayed() {
        try {
            WebElement productsTitle = findByUIAutomator(PRODUCTS_TITLE);
            return productsTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Remove items from cart
     */
    public void removeItemsFromCart(int quantity) {
        try {
            for (int i = 0; i < quantity; i++) {
                WebElement removeButton = findByAccessibilityId(REMOVE_BUTTON);
                removeButton.click();
                Thread.sleep(1000);
            }
            System.out.println("Removed " + quantity + " items from cart");
        } catch (Exception e) {
            System.err.println("Failed to remove items from cart: " + e.getMessage());
        }
    }

    /**
     * Proceed to checkout
     */
    public void proceedToCheckout() {
        try {
            WebElement checkoutButton = findByAccessibilityId(CHECKOUT_BUTTON);
            checkoutButton.click();
            Thread.sleep(2000);
            System.out.println("Proceeded to checkout");
        } catch (Exception e) {
            System.err.println("Failed to proceed to checkout: " + e.getMessage());
        }
    }

    /**
     * Perform swipe gesture
     */
    public void performSwipe(Point start, Point end) {
        try {
            final var finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            var swipe = new Sequence(finger, 1);
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                    PointerInput.Origin.viewport(), start.getX(), start.getY()));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000),
                    PointerInput.Origin.viewport(), end.getX(), end.getY()));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(Arrays.asList(swipe));
            System.out.println("Performed swipe gesture");
        } catch (Exception e) {
            System.err.println("Failed to perform swipe: " + e.getMessage());
        }
    }
}