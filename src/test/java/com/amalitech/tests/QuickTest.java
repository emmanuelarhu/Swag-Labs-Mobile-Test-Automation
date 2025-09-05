package com.amalitech.tests;

import com.amalitech.base.BaseTest;
import com.amalitech.constants.AppConstants;
import com.amalitech.pages.LoginPage;
import org.testng.annotations.Test;

public class QuickTest extends BaseTest {

    @Test
    public void testBasicSetup() {
        LoginPage loginPage = new LoginPage(driver);

        // Wait for app to load
        waitUtils.hardWait(3);

        // Basic verification that everything is working
        System.out.println("Driver initialized: " + (driver != null));
        System.out.println("WaitUtils initialized: " + (waitUtils != null));
        System.out.println("Constants accessible: " + AppConstants.STANDARD_USER);

        // Try basic page interaction
        boolean isLoginPageDisplayed = loginPage.isLoginPageDisplayed();
        System.out.println("Login page displayed: " + isLoginPageDisplayed);
    }
}