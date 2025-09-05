package com.amalitech.pages;

import com.amalitech.base.BasePage;
import com.amalitech.constants.AppConstants;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MenuPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(MenuPage.class);

    // Page elements
    private final String HAMBURGER_MENU_SELECTOR = "new UiSelector().className(\"android.widget.ImageView\").instance(1)";
    private final String LOGOUT_SELECTOR = "new UiSelector().text(\"LOGOUT\")";
    private final String ALL_ITEMS_SELECTOR = "new UiSelector().text(\"ALL ITEMS\")";
    private final String ABOUT_SELECTOR = "new UiSelector().text(\"ABOUT\")";
    private final String RESET_APP_STATE_SELECTOR = "new UiSelector().text(\"RESET APP STATE\")";
    private final String CLOSE_MENU_SELECTOR = "new UiSelector().className(\"android.widget.ImageView\").instance(0)";

    public MenuPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Open hamburger menu
     */
    public MenuPage openHamburgerMenu() {
        logger.info("Opening hamburger menu");
        clickByUiSelector(HAMBURGER_MENU_SELECTOR);
        waitForMenuToOpen();
        return this;
    }

    /**
     * Close hamburger menu
     */
    public MenuPage closeHamburgerMenu() {
        logger.info("Closing hamburger menu");
        try {
            clickByUiSelector(CLOSE_MENU_SELECTOR);
            waitForPageLoad();
        } catch (Exception e) {
            logger.warn("Could not close menu using close button, trying alternative method");
            // Alternative: tap outside menu area or use back button
            driver.navigate().back();
        }
        return this;
    }

    /**
     * Click logout button
     */
    public void clickLogout() {
        logger.info("Clicking logout button");
        clickByUiSelector(LOGOUT_SELECTOR);
        waitForPageLoad();
    }

    /**
     * Click all items menu option
     */
    public void clickAllItems() {
        logger.info("Clicking all items menu option");
        clickByUiSelector(ALL_ITEMS_SELECTOR);
        waitForPageLoad();
    }

    /**
     * Click about menu option
     */
    public void clickAbout() {
        logger.info("Clicking about menu option");
        clickByUiSelector(ABOUT_SELECTOR);
        waitForPageLoad();
    }

    /**
     * Click reset app state menu option
     */
    public void clickResetAppState() {
        logger.info("Clicking reset app state menu option");
        clickByUiSelector(RESET_APP_STATE_SELECTOR);
        waitForPageLoad();
    }

    /**
     * Check if hamburger menu icon is displayed
     */
    public boolean isHamburgerMenuDisplayed() {
        logger.debug("Checking if hamburger menu icon is displayed");
        return isElementDisplayedByUiSelector(HAMBURGER_MENU_SELECTOR);
    }

    /**
     * Check if logout option is displayed in menu
     */
    public boolean isLogoutOptionDisplayed() {
        logger.debug("Checking if logout option is displayed");
        return isElementDisplayedByUiSelector(LOGOUT_SELECTOR);
    }

    /**
     * Check if all items option is displayed in menu
     */
    public boolean isAllItemsOptionDisplayed() {
        logger.debug("Checking if all items option is displayed");
        return isElementDisplayedByUiSelector(ALL_ITEMS_SELECTOR);
    }

    /**
     * Check if about option is displayed in menu
     */
    public boolean isAboutOptionDisplayed() {
        logger.debug("Checking if about option is displayed");
        return isElementDisplayedByUiSelector(ABOUT_SELECTOR);
    }

    /**
     * Check if reset app state option is displayed in menu
     */
    public boolean isResetAppStateOptionDisplayed() {
        logger.debug("Checking if reset app state option is displayed");
        return isElementDisplayedByUiSelector(RESET_APP_STATE_SELECTOR);
    }

    /**
     * Wait for menu to open
     */
    private void waitForMenuToOpen() {
        logger.debug("Waiting for menu to open");
        waitUtils.waitForElementToBeVisible(
                io.appium.java_client.AppiumBy.androidUIAutomator(LOGOUT_SELECTOR)
        );
    }

    /**
     * Validate menu options are displayed
     */
    public boolean validateMenuOptions() {
        logger.info("Validating menu options are displayed");
        return isLogoutOptionDisplayed() &&
                isAllItemsOptionDisplayed() &&
                isAboutOptionDisplayed() &&
                isResetAppStateOptionDisplayed();
    }

    /**
     * Perform complete logout flow
     */
    public void performLogout() {
        logger.info("Performing complete logout flow");
        openHamburgerMenu();
        clickLogout();
    }

    /**
     * Navigate to all items
     */
    public void navigateToAllItems() {
        logger.info("Navigating to all items");
        openHamburgerMenu();
        clickAllItems();
    }

    /**
     * Reset application state
     */
    public void resetApplicationState() {
        logger.info("Resetting application state");
        openHamburgerMenu();
        clickResetAppState();
        closeHamburgerMenu();
    }

    /**
     * Navigate to about page
     */
    public void navigateToAbout() {
        logger.info("Navigating to about page");
        openHamburgerMenu();
        clickAbout();
    }
}