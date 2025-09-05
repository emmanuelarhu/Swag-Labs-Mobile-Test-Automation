package com.amalitech.pages;

import com.amalitech.base.BasePage;
import com.amalitech.utils.WaitUtils;
import io.appium.java_client.android.AndroidDriver;

public class MenuPage extends BasePage {

    private WaitUtils waitUtils;

    // UI Selector locators for menu elements
    private String menuButton = "new UiSelector().description(\"test-Menu\")";
    private String allItemsOption = "new UiSelector().description(\"test-ALL ITEMS\")";
    private String aboutOption = "new UiSelector().description(\"test-ABOUT\")";
    private String logoutOption = "new UiSelector().description(\"test-LOGOUT\")";
    private String resetAppStateOption = "new UiSelector().description(\"test-RESET APP STATE\")";
    private String webViewOption = "new UiSelector().description(\"test-WEBVIEW\")";
    private String closeMenuButton = "new UiSelector().description(\"test-Close Menu\")";

    public MenuPage(AndroidDriver driver) {
        super(driver);
        this.waitUtils = new WaitUtils(driver);
    }

    /**
     * Check if hamburger menu is displayed - compatibility method
     */
    public boolean isHamburgerMenuDisplayed() {
        return isElementDisplayedByUiSelector(menuButton);
    }

    /**
     * Perform logout - compatibility method
     */
    public void performLogout() {
        logout();
    }

    public void openMenu() {
        clickByUiSelector(menuButton);
    }

    public void selectAllItems() {
        try {
            clickByUiSelector(allItemsOption);
            waitForPageLoad();
        } catch (Exception e) {
            System.err.println("Failed to select All Items: " + e.getMessage());
        }
    }

    public void selectAbout() {
        try {
            clickByUiSelector(aboutOption);
            waitForPageLoad();
        } catch (Exception e) {
            System.err.println("Failed to select About: " + e.getMessage());
        }
    }

    public void logout() {
        try {
            clickByUiSelector(logoutOption);
            waitForPageLoad();
        } catch (Exception e) {
            System.err.println("Failed to logout: " + e.getMessage());
        }
    }

    public void resetAppState() {
        try {
            clickByUiSelector(resetAppStateOption);
            waitForPageLoad();
        } catch (Exception e) {
            System.err.println("Failed to reset app state: " + e.getMessage());
        }
    }

    public void selectWebView() {
        try {
            clickByUiSelector(webViewOption);
            waitForPageLoad();
        } catch (Exception e) {
            System.err.println("Failed to select WebView: " + e.getMessage());
        }
    }

    public void closeMenu() {
        try {
            clickByUiSelector(closeMenuButton);
            waitForPageLoad();
        } catch (Exception e) {
            System.err.println("Failed to close menu: " + e.getMessage());
        }
    }

    public boolean isAllItemsOptionDisplayed() {
        return isElementDisplayedByUiSelector(allItemsOption);
    }

    public boolean isAboutOptionDisplayed() {
        return isElementDisplayedByUiSelector(aboutOption);
    }

    public boolean isLogoutOptionDisplayed() {
        return isElementDisplayedByUiSelector(logoutOption);
    }

    public boolean isResetAppStateOptionDisplayed() {
        return isElementDisplayedByUiSelector(resetAppStateOption);
    }

    public boolean isWebViewOptionDisplayed() {
        return isElementDisplayedByUiSelector(webViewOption);
    }

    public void waitForMenuToLoad() {
        try {
            waitUtils.waitForPageLoad();
        } catch (Exception e) {
            System.err.println("Menu failed to load: " + e.getMessage());
        }
    }
}