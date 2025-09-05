package com.amalitech.constants;

public class AppConstants {

    // Test users
    public static final String STANDARD_USER = "standard_user";
    public static final String LOCKED_OUT_USER = "locked_out_user";
    public static final String PROBLEM_USER = "problem_user";
    public static final String PASSWORD = "secret_sauce";

    // Test data for checkout
    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Doe";
    public static final String ZIP_CODE = "12345";

    // Page titles
    public static final String PRODUCTS_PAGE_TITLE = "PRODUCTS";
    public static final String CART_PAGE_TITLE = "YOUR CART";
    public static final String CHECKOUT_INFO_TITLE = "CHECKOUT: INFORMATION";
    public static final String CHECKOUT_OVERVIEW_TITLE = "CHECKOUT: OVERVIEW";
    public static final String CHECKOUT_COMPLETE_TITLE = "CHECKOUT: COMPLETE!";

    // Accessibility IDs for login
    public static final String TEST_USERNAME = "test-Username";
    public static final String TEST_PASSWORD = "test-Password";
    public static final String TEST_LOGIN = "test-LOGIN";
    public static final String TEST_ERROR_MESSAGE = "test-Error message";

    // Accessibility IDs for checkout
    public static final String TEST_FIRST_NAME = "test-First Name";
    public static final String TEST_LAST_NAME = "test-Last Name";
    public static final String TEST_ZIP_CODE = "test-Zip/Postal Code";
    public static final String TEST_CONTINUE = "test-CONTINUE";
    public static final String TEST_FINISH = "test-FINISH";
    public static final String TEST_BACK_HOME = "test-BACK HOME";
    public static final String TEST_CHECKOUT = "test-CHECKOUT";

    // Private constructor to prevent instantiation
    private AppConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}