<div align="center">

# 📱 Swag Labs Mobile Test Automation

**Enterprise-grade Appium testing framework for comprehensive mobile app quality assurance**

[![Java](https://img.shields.io/badge/Java-11-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![Appium](https://img.shields.io/badge/Appium-10.0.0-662D91?style=for-the-badge&logo=appium&logoColor=white)](https://appium.io/)
[![TestNG](https://img.shields.io/badge/TestNG-7.8.0-FF6C37?style=for-the-badge&logo=testng&logoColor=white)](https://testng.org/)
[![Selenium](https://img.shields.io/badge/Selenium-4.15.0-43B02A?style=for-the-badge&logo=selenium&logoColor=white)](https://www.selenium.dev/)

![Test Status](https://img.shields.io/badge/Tests-Passing-success?style=flat-square)
![Coverage](https://img.shields.io/badge/Coverage-100%25-brightgreen?style=flat-square)
![Maintained](https://img.shields.io/badge/Maintained-Yes-blue?style=flat-square)

</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [️Tech Stack](#️-tech-stack)
- [Prerequisites](#-prerequisites)
- [Quick Start](#-quick-start)
- [️Running Tests](#️-running-tests)
- [Project Architecture](#️-project-architecture)
- [Test Reports](#-test-reports)
- [ Configuration](#️-configuration)

---

## 📖 Overview

A comprehensive **mobile test automation framework** built with **Appium** and **Java** for validating the Swag Labs Android application. This framework implements industry best practices including the **Page Object Model (POM)** design pattern, modular architecture, and multiple reporting frameworks for enhanced test visibility and maintainability.

###  Key Highlights

```
✅ 100% Coverage of critical user journeys
✅ Automated regression testing for faster releases
✅ Real-time test reporting with visual dashboards
✅ Modular & maintainable test architecture
✅ CI/CD ready with Maven integration
```

---

## ✨ Features

<table>
<tr>
<td width="50%">

### 🏛️ Architecture
- ✅ **Page Object Model (POM)** design
- ✅ **Modular & Reusable** components
- ✅ **Base Classes** for common functionality
- ✅ **Utility Methods** for DRY principles

</td>
<td width="50%">

### 🔧 Testing Capabilities
- ✅ **Comprehensive Test Coverage**
- ✅ **Parallel Test Execution**
- ✅ **Cross-device Testing Support**
- ✅ **Data-driven Testing**

</td>
</tr>
<tr>
<td width="50%">

### 📊 Reporting & Analytics
- ✅ **Dual Reporting** (Allure & Extent)
- ✅ **Screenshot on Failure**
- ✅ **Detailed Logging** with Log4j
- ✅ **Custom Test Listeners**

</td>
<td width="50%">

### ⚙️ Configuration
- ✅ **Properties-based Configuration**
- ✅ **Environment Profiles** (local/CI)
- ✅ **TestNG XML Suite** management
- ✅ **Maven Build Integration**

</td>
</tr>
</table>

---

## 🛠️ Tech Stack

<div align="center">

| Technology | Version | Purpose |
|:-----------|:-------:|:--------|
| ☕ **Java** | 11 | Core programming language |
| 📦 **Maven** | 3.6+ | Build automation & dependency management |
| 📱 **Appium** | 10.0.0 | Mobile automation framework |
| 🌐 **Selenium** | 4.15.0 | WebDriver implementation |
| 🧪 **TestNG** | 7.8.0 | Testing framework & test orchestration |
| 📊 **Allure** | 2.24.0 | Interactive test reporting |
| 📈 **ExtentReports** | 5.1.1 | HTML test reporting |
| 📝 **Log4j** | 2.25.1 | Logging framework |
| ✔️ **AssertJ** | 3.24.2 | Fluent assertions library |

</div>

---

## 📦 Prerequisites

### 🔧 Required Software

```bash
☑️ Java JDK 11 or higher
☑️ Maven 3.6+
☑️ Node.js & npm (for Appium)
☑️ Android SDK & Android Studio
☑️ Appium Server
☑️ Android Emulator or physical device
☑️ Swag Labs APK installed on test device
```

### 📲 Device Requirements

- **Platform**: Android 7.0 (API level 24) or higher
- **Automation Engine**: UiAutomator2
- **Connection**: USB debugging enabled or emulator running

---

## 🚀 Quick Start

### Step 1️⃣: Clone the Repository

```bash
git clone <repository-url>
cd AppiumTest
```

### Step 2️⃣: Install Dependencies

```bash
mvn clean install
```

### Step 3️⃣: Configure Test Environment

Update device capabilities in `src/test/resources/config.properties`:

```properties
platform.name=Android
device.name=your-device-name
platform.version=9
app.package=com.swaglabsmobileapp
appium.server=http://localhost:4723/wd/hub
```

### Step 4️⃣: Start Appium Server

```bash
appium
```

### Step 5️⃣: Run Tests

```bash
mvn test
```

---

## ▶️ Running Tests

###  Execute All Tests

```bash
mvn test
```

###  Run Specific Test Suite

```bash
# Login functionality tests
mvn test -Dtest=LoginTest

# Checkout workflow tests
mvn test -Dtest=CheckoutTest

# End-to-end scenarios
mvn test -Dtest=EndToEndTest
```

###  Run Specific Test Method

```bash
mvn test -Dtest=LoginTest#testValidLogin
```

###  Clean Build and Test

```bash
mvn clean test
```

###  Run with Profiles

```bash
# Local execution (default)
mvn test -Plocal

# CI/CD execution
mvn test -Pci
```

---

## 🏗️ Project Architecture

### 📂 Project Structure

```
AppiumTest/
├── 📁 src/
│   ├── 📁 main/java/com/amalitech/
│   │   ├── 📁 base/
│   │   │   ├── 📄 BaseTest.java          # Base test class with setup/teardown
│   │   │   └── 📄 BasePage.java          # Base page with common methods
│   │   ├── 📁 pages/
│   │   │   ├── 📄 LoginPage.java         # Login page objects
│   │   │   ├── 📄 ProductsPage.java      # Products page objects
│   │   │   ├── 📄 CartPage.java          # Cart page objects
│   │   │   ├── 📄 CheckoutPage.java      # Checkout page objects
│   │   │   └── 📄 MenuPage.java          # Menu page objects
│   │   ├── 📁 utils/
│   │   │   ├── 📄 ConfigReader.java      #️ Configuration reader
│   │   │   ├── 📄 WaitUtils.java         # Wait strategies
│   │   │   └── 📄 TestDataProvider.java  # Test data management
│   │   └── 📁 constants/
│   │       └── 📄 AppConstants.java      # Application constants
│   └── 📁 test/java/com/amalitech/
│       ├── 📁 tests/
│       │   ├── 📄 LoginTest.java         # 🧪 Login test cases
│       │   ├── 📄 ProductTest.java       # 🧪 Product test cases
│       │   ├── 📄 CartTest.java          # 🧪 Cart test cases
│       │   ├── 📄 CheckoutTest.java      # 🧪 Checkout test cases
│       │   ├── 📄 EndToEndTest.java      # 🧪 E2E test scenarios
│       │   └── 📄 LogoutTest.java        # 🧪 Logout test cases
│       └── 📁 listeners/
│           ├── 📄 TestListener.java      # Test execution listener
│           └── 📄 ScreenshotListener.java #Screenshot on failure
├── 📁 src/test/resources/
│   ├── 📄 config.properties              #️ Test configuration
│   ├── 📄 testng.xml                     # TestNG suite configuration
│   └── 📄 log4j2.xml                     # Logging configuration
├── 📄 pom.xml                            # Maven configuration
└── 📄 README.md                          # Project documentation
```

### 🎨 Design Patterns

<table>
<tr>
<td width="50%">

#### 🏛️ **Page Object Model (POM)**
Encapsulates page elements and actions for better maintainability

</td>
<td width="50%">

#### 🏭 **Factory Pattern**
Driver initialization and configuration management

</td>
</tr>
<tr>
<td width="50%">

#### 💎 **Singleton Pattern**
Single instance for configuration and utility classes

</td>
<td width="50%">

#### 🧬 **Inheritance**
Base classes provide common functionality to all tests

</td>
</tr>
</table>

### 🧪 Test Coverage

| Test Suite | Test Cases | Focus Area |
|:-----------|:----------:|:-----------|
| 🔐 **LoginTest** | 8+ | Authentication & security validation |
| 🛍️ **ProductTest** | 6+ | Product browsing & filtering |
| 🛒 **CartTest** | 7+ | Cart operations & calculations |
| 💳 **CheckoutTest** | 5+ | Checkout workflow validation |
| 🔄 **EndToEndTest** | 3+ | Complete user journey scenarios |
| 🚪 **LogoutTest** | 2+ | Session management |

---

## 📊 Test Reports

### 📈 Generate Allure Report

```bash
# Generate and open report in browser
mvn allure:serve

# Generate report in target directory
mvn allure:report
```

### 📋 View ExtentReports

ExtentReports are automatically generated after test execution:
```
📂 target/extent-reports/ExtentReport.html
```

### 📦 Test Artifacts

All test execution artifacts are stored in the `target/` directory:

```
target/
├── 📊 allure-results/          # Allure test results
├── 📈 extent-reports/          # ExtentReports HTML
├── 📸 screenshots/             # Failure screenshots
└── 📄 surefire-reports/        # TestNG XML reports
```

### 📊 Sample Report Metrics

```
┌──────────────────────────────────────┐
│  📊 Test Execution Summary           │
├──────────────────────────────────────┤
│  ✅ Total Tests: 30+                 │
│  ⏱️ Execution Time: ~5-10 minutes    │
│  ✔️ Pass Rate: 100%                  │
│  📱 Platforms: Android                │
│  🔄 CI/CD Ready: Yes                 │
└──────────────────────────────────────┘
```

---

## ⚙️ Configuration

### 📱 Appium Capabilities

Configure device settings in `src/test/resources/config.properties`:

```properties
# Platform configuration
platform.name=Android
platform.version=9
device.name=DUK-AL20

# App configuration
app.package=com.swaglabsmobileapp
app.activity=.MainActivity
automation.name=UiAutomator2

# Appium server
appium.server=http://localhost:4723/wd/hub

# Timeouts
implicit.wait=10
explicit.wait=20
```

### 👤 Test Users

Test credentials are defined in `AppConstants.java`:

| User Type | Username | Password | Purpose |
|:----------|:---------|:---------|:--------|
| ✅ **Standard User** | `standard_user` | `secret_sauce` | Normal user workflow testing |
| 🔒 **Locked Out User** | `locked_out_user` | `secret_sauce` | Security & error handling |
| ⚠️ **Problem User** | `problem_user` | `secret_sauce` | Edge case testing |

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/YourFeature`)
3. Commit your changes (`git commit -m 'Add some feature'`)
4. Push to the branch (`git push origin feature/YourFeature`)
5. Open a Pull Request

---

## 📝 License

This project is part of the **AmaliTech Training Program**.

---

## 📧 Contact & Support

<div align="center">

**Built with ❤️ by Emmanuel Arhu**

For questions or support, please reach out through the appropriate channels.

---

### 🌟 If you find this project helpful, consider giving it a star!

[![GitHub stars](https://img.shields.io/github/stars/emmanuelarhu/Swag-Labs-Mobile-Test-Automation?style=social)](https://github.com/emmanuelarhu/Swag-Labs-Mobile-Test-Automation/stargazers)

</div>
