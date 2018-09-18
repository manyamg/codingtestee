package io.ee.hotelbooking.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverBuilder {

    private static WebDriver driver;
    static void initialiseDriver(String driverType) {
        switch (driverType) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            default:
                throw new IllegalArgumentException("Incorrect driver type is requested " + driverType);
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    static void tearDownDriver() {
        getDriver().close();
        getDriver().quit();
    }


}
