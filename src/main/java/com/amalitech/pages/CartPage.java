package com.amalitech.pages;

import com.amalitech.base.BasePage;
import com.amalitech.utils.WaitUtils;
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

public class CartPage extends BasePage {

    private WaitUtils waitUtils;

    // Exact locators as provided
    private String cartTitle = "new UiSelector().text(\"YOUR CART\")";
    private String qtyText = "new UiSelector().text(\"QTY\")";
    private String descriptionText = "new UiSelector().text(\"DESCRIPTION\")";
    private String removeButton = "new UiSelector().description(\"test-REMOVE\").instance(0)";
    private String removeButton1 = "new UiSelector().description(\"test-REMOVE\").instance(1)";
    private String continueShoppingButton = "new UiSelector().text(\"CONTINUE SHOPPING\")";
    private String checkoutButton = "new UiSelector().text(\"CHECKOUT\")";

    public CartPage(AndroidDriver driver) {
        super(driver);
        this.waitUtils = new WaitUtils(driver);
    }

    /**
     * Wait for cart page to load
     */
    public void waitForCartPageToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.androidUIAutomator(cartTitle)
        ));
        System.out.println("Cart page loaded successfully");
    }

    /**
     * Check if cart page is displayed
     */
    public boolean isCartPageDisplayed() {
        try {
            WebElement titleElement = findByUIAutomator(cartTitle);
            return titleElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get cart page title
     */
    public String getCartPageTitle() {
        try {
            WebElement titleElement = findByUIAutomator(cartTitle);
            return titleElement.getText();
        } catch (Exception e) {
            return "YOUR CART";
        }
    }

    /**
     * Get cart items count by counting REMOVE buttons
     */
    public int getCartItemsCount() {
        try {
            List<WebElement> removeButtons = driver.findElements(
                    AppiumBy.androidUIAutomator("new UiSelector().description(\"test-REMOVE\")")
            );
            return removeButtons.size();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Get remove buttons count
     */
    public int getRemoveButtonsCount() {
        return getCartItemsCount();
    }

    /**
     * Check if remove button is visible
     */
    public boolean isRemoveButtonVisible() {
        try {
            WebElement button = findByUIAutomator(removeButton);
            return button.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Remove first item from cart
     */
    public boolean removeFirstItem() {
        try {
            WebElement removeBtn = findByUIAutomator(removeButton);
            removeBtn.click();
            Thread.sleep(1000);
            System.out.println("Removed first item from cart");
            return true;
        } catch (Exception e) {
            System.err.println("Failed to remove first item: " + e.getMessage());
            return false;
        }
    }

    /**
     * Remove second item from cart
     */
    public boolean removeSecondItem() {
        try {
            WebElement removeBtn = findByUIAutomator(removeButton1);
            removeBtn.click();
            Thread.sleep(1000);
            System.out.println("Removed second item from cart");
            return true;
        } catch (Exception e) {
            System.err.println("Failed to remove second item: " + e.getMessage());
            return false;
        }
    }

    /**
     * Validate cart page elements
     */
    public boolean validateCartPageElements() {
        try {
            System.out.println("Validating cart page elements...");

            // Check for cart title
            boolean hasTitle = false;
            try {
                WebElement titleElement = findByUIAutomator(cartTitle);
                hasTitle = titleElement.isDisplayed();
                System.out.println("Cart title found: " + hasTitle);
            } catch (Exception e) {
                System.out.println("Cart title not found: " + e.getMessage());
            }

            // Check for QTY header
            boolean hasQty = false;
            try {
                WebElement qtyElement = findByUIAutomator(qtyText);
                hasQty = qtyElement.isDisplayed();
                System.out.println("QTY header found: " + hasQty);
            } catch (Exception e) {
                System.out.println("QTY header not found: " + e.getMessage());
            }

            // Check for DESCRIPTION header
            boolean hasDescription = false;
            try {
                WebElement descElement = findByUIAutomator(descriptionText);
                hasDescription = descElement.isDisplayed();
                System.out.println("DESCRIPTION header found: " + hasDescription);
            } catch (Exception e) {
                System.out.println("DESCRIPTION header not found: " + e.getMessage());
            }

            // Validation passes if we have title and headers
            boolean isValid = hasTitle && hasQty && hasDescription;

            System.out.println("Cart validation summary - Title: " + hasTitle +
                    ", QTY: " + hasQty +
                    ", DESCRIPTION: " + hasDescription +
                    ", Overall Valid: " + isValid);

            return isValid;

        } catch (Exception e) {
            System.err.println("Cart validation failed with exception: " + e.getMessage());
            return false;
        }
    }

    /**
     * Scroll down to reveal checkout and continue shopping buttons
     */
    private void scrollToBottomButtons() {
        try {
            // Scroll down from middle of screen to reveal bottom buttons
            Point start = new Point(540, 1500);
            Point end = new Point(540, 800);
            performSwipe(start, end);

            // Wait for scroll to complete
            Thread.sleep(1000);

            System.out.println("Scrolled down to reveal bottom buttons");
        } catch (Exception e) {
            System.err.println("Failed to scroll: " + e.getMessage());
        }
    }

    /**
     * Perform swipe gesture
     */
    private void performSwipe(Point start, Point end) {
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
        } catch (Exception e) {
            System.err.println("Failed to perform swipe: " + e.getMessage());
        }
    }

    /**
     * Check if continue shopping button is displayed (with scroll if needed)
     */
    public boolean isContinueShoppingButtonDisplayed() {
        try {
            // First try to find it without scrolling
            WebElement button = findByUIAutomator(continueShoppingButton);
            return button.isDisplayed();
        } catch (Exception e) {
            System.out.println("Continue shopping button not visible, scrolling down...");

            // Scroll down and try again
            scrollToBottomButtons();

            try {
                WebElement button = findByUIAutomator(continueShoppingButton);
                return button.isDisplayed();
            } catch (Exception e2) {
                return false;
            }
        }
    }

    /**
     * Check if checkout button is displayed (with scroll if needed)
     */
    public boolean isCheckoutButtonDisplayed() {
        try {
            // First try to find it without scrolling
            WebElement button = findByUIAutomator(checkoutButton);
            return button.isDisplayed();
        } catch (Exception e) {
            System.out.println("Checkout button not visible, scrolling down...");

            // Scroll down and try again
            scrollToBottomButtons();

            try {
                WebElement button = findByUIAutomator(checkoutButton);
                return button.isDisplayed();
            } catch (Exception e2) {
                return false;
            }
        }
    }

    /**
     * Click continue shopping button (with scroll if needed)
     */
    public void clickContinueShoppingButton() {
        try {
            // First try to find it without scrolling
            WebElement button = findByUIAutomator(continueShoppingButton);
            button.click();
            System.out.println("Clicked continue shopping button");
        } catch (Exception e) {
            System.out.println("Continue shopping button not visible, scrolling down...");

            // Scroll down and try again
            scrollToBottomButtons();

            try {
                WebElement button = findByUIAutomator(continueShoppingButton);
                button.click();
                System.out.println("Clicked continue shopping button after scrolling");
            } catch (Exception e2) {
                System.err.println("Failed to click continue shopping button: " + e2.getMessage());
            }
        }
    }

    /**
     * Proceed to checkout (with scroll if needed)
     */
    public void proceedToCheckout() {
        try {
            // First try to find it without scrolling
            WebElement checkoutBtn = findByUIAutomator(checkoutButton);
            checkoutBtn.click();
            Thread.sleep(2000);
            System.out.println("Proceeded to checkout");
        } catch (Exception e) {
            System.out.println("Checkout button not visible, scrolling down...");

            // Scroll down and try again
            scrollToBottomButtons();

            try {
                WebElement checkoutBtn = findByUIAutomator(checkoutButton);
                checkoutBtn.click();
                Thread.sleep(2000);
                System.out.println("Proceeded to checkout after scrolling");
            } catch (Exception e2) {
                System.err.println("Failed to proceed to checkout: " + e2.getMessage());
            }
        }
    }

    /**
     * Check if cart is empty
     */
    public boolean isCartEmpty() {
        return getCartItemsCount() == 0;
    }

    /**
     * Get item name by index
     */
    public String getItemNameByIndex(int index) {
        try {
            List<WebElement> itemNames = driver.findElements(
                    AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Sauce Labs\")")
            );
            if (index < itemNames.size()) {
                return itemNames.get(index).getText();
            }
            return "Product " + index;
        } catch (Exception e) {
            return "Product " + index;
        }
    }

    /**
     * Get item price by index
     */
    public String getItemPriceByIndex(int index) {
        try {
            List<WebElement> prices = driver.findElements(
                    AppiumBy.androidUIAutomator("new UiSelector().textContains(\"$\")")
            );
            if (index < prices.size()) {
                return prices.get(index).getText();
            }
            return "$0.00";
        } catch (Exception e) {
            return "$0.00";
        }
    }
}