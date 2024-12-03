package helpers;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Objects;

@Slf4j
public class DriverContainer {

    private static WebDriver driver = null;
    public static WebDriver getDriver() {
        if (Objects.isNull(driver)) {
            driver = new ChromeDriver();
            log.debug("Created driver");
        }
        log.info("Returned driver");
        return driver;
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            log.info("Disposed driver");
        }
    }

    public static void setDriver(WebDriver driver) {
        DriverContainer.driver = driver;
    }
}
