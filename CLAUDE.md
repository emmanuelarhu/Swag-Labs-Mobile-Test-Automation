# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Java-based Appium mobile automation testing project for the Swag Labs mobile app. It uses Maven as the build system, TestNG as the testing framework, and follows the Page Object Model (POM) design pattern.

## Technology Stack

- **Java 21** - Programming language
- **Maven** - Build and dependency management
- **Appium 10.0.0** - Mobile automation framework
- **Selenium 4.35.0** - WebDriver implementation
- **TestNG 7.11.0** - Testing framework

## Commands

### Build and Test
- `mvn clean compile` - Clean and compile the project
- `mvn test` - Run all tests (uses testng.xml configuration)
- `mvn clean test` - Clean, compile and run tests
- `mvn test -Dtest=LoginTests` - Run specific test class
- `mvn test -Dtest=LoginTests#testValidLogin` - Run specific test method

### Dependencies
- `mvn dependency:resolve` - Download all dependencies
- `mvn clean install` - Full build with dependency installation

## Project Architecture

### Package Structure
```
src/main/java/com/amalitech/
├── pageObjects/android/     # Page Object classes for Android app
│   ├── BasePage.java       # Base page with common functionality
│   ├── LoginPage.java      # Login page interactions
│   ├── ProductsPage.java   # Products page interactions
│   ├── CartPage.java       # Shopping cart page
│   └── CheckoutPage.java   # Checkout flow page
├── utils/                  # Utility classes
│   ├── AndroidActions.java # Android-specific actions
│   ├── AppiumUtils.java    # Appium utility methods
│   ├── WaitUtils.java      # Wait strategies
│   └── TestUtils.java      # General test utilities
└── resources/
    └── data.properties     # Configuration properties

src/test/java/com/amalitech/
├── base/                   # Test foundation
│   ├── BaseTest.java      # Base test class with setup/teardown
│   └── DriverFactory.java # WebDriver instantiation
├── tests/                 # Test classes
│   ├── LoginTests.java    # Login functionality tests
│   ├── ProductsTests.java # Product browsing tests
│   ├── CartTests.java     # Shopping cart tests
│   └── EndToEndTests.java # Complete user journey tests
└── listeners/             # TestNG listeners
    ├── TestListener.java  # Test execution listener
    └── ScreenshotListener.java # Screenshot capture
```

### Key Design Patterns
- **Page Object Model (POM)**: Each page/screen is represented by a separate class
- **Base Classes**: `BaseTest` handles driver lifecycle, `BasePage` provides common page functionality
- **Factory Pattern**: `DriverFactory` creates and configures WebDriver instances
- **Inheritance**: All page objects extend `BasePage`, all tests extend `BaseTest`

### Configuration
- Device configuration is currently hardcoded in `DriverFactory.java`
- Test data stored in `src/main/java/com/amalitech/resources/data.properties`
- Appium server URL: `http://localhost:4723/wd/hub`
- Target app: Swag Labs mobile app (`com.swaglabsmobileapp`)

### Test Execution
- Tests use TestNG annotations (`@Test`, `@BeforeMethod`, `@AfterMethod`)
- Driver is initialized before each test method and quit after
- Tests are designed to run against Android devices/emulators
- Implicit wait of 10 seconds is configured for all elements

### Dependencies and Capabilities
The project requires:
- Appium server running on localhost:4723
- Android device/emulator with Swag Labs app installed
- Device name: DUK-AL20 (Android 9)
- UiAutomator2 automation engine