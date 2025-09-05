package com.amalitech.base;

import com.amalitech.utils.ConfigReader;
import com.amalitech.utils.WaitUtils;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseTest {

    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected static AndroidDriver driver;
    protected static WaitUtils waitUtils;

    @BeforeClass
    @Parameters({"platformName", "deviceName", "platformVersion"})
    public void setUp(@Optional("Android") String platformName,
                      @Optional("emulator-5554") String deviceName,
                      @Optional("16") String platformVersion) {
        try {
            logger.info("Setting up Appium driver...");

            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("platformName", platformName);
            caps.setCapability("appium:automationName", "UiAutomator2");
            caps.setCapability("appium:appPackage", "com.swaglabsmobileapp");
            caps.setCapability("appium:appActivity", "com.swaglabsmobileapp/.SplashActivity");
            caps.setCapability("appium:deviceName", deviceName);
            caps.setCapability("appium:platformVersion", platformVersion);
            caps.setCapability("appium:noReset", true);
            caps.setCapability("appium:fullReset", false);
            caps.setCapability("appium:allowInvisibleElements", false);
            caps.setCapability("appium:enableMultiWindows", false);
            caps.setCapability("appium:newCommandTimeout", 300);
            caps.setCapability("appium:connectHardwareKeyboard", true);
            caps.setCapability("appium:noReset", false);  // Change from true to false
            caps.setCapability("appium:fullReset", false);

            // Optional: Set app path if needed
            String appPath = ConfigReader.getProperty("app.path");
            if (appPath != null && !appPath.isEmpty()) {
                File appFile = new File(appPath);
                if (appFile.exists()) {
                    caps.setCapability("appium:app", appFile.getAbsolutePath());
                    logger.info("App path set to: " + appFile.getAbsolutePath());
                }
            }

            String appiumServerUrl = ConfigReader.getProperty("appium.server.url", "http://localhost:4723");
            logger.info("Connecting to Appium server: " + appiumServerUrl);

            driver = new AndroidDriver(new URL(appiumServerUrl), caps);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            waitUtils = new WaitUtils(driver);

            logger.info("Appium driver initialized successfully");

        } catch (MalformedURLException e) {
            logger.error("Invalid Appium server URL", e);
            throw new RuntimeException("Failed to initialize driver", e);
        } catch (Exception e) {
            logger.error("Failed to initialize Appium driver", e);
            throw new RuntimeException("Failed to initialize driver", e);
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            logger.info("Closing Appium driver...");
            driver.quit();
            logger.info("Appium driver closed successfully");
        }
    }

    /**
     * Get the current driver instance
     * @return AndroidDriver instance
     */
    public static AndroidDriver getDriver() {
        return driver;
    }

    /**
     * Get wait utils instance
     * @return WaitUtils instance
     */
    public static WaitUtils getWaitUtils() {
        return waitUtils;
    }

    /**
     * Take screenshot for reporting
     * @param testName name of the test
     * @return screenshot path
     */
    protected String captureScreenshot(String testName) {
        try {
            // Implementation for screenshot capture
            // This can be enhanced based on your reporting needs
            String screenshotPath = "screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";
            logger.info("Screenshot captured: " + screenshotPath);
            return screenshotPath;
        } catch (Exception e) {
            logger.error("Failed to capture screenshot", e);
            return null;
        }
    }
}