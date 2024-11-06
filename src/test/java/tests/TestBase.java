package tests;

import helpers.Attach;
import helpers.DriverContainer;
import helpers.Screenshot;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Map;

import static constants.Constants.TimeoutVariable.IMPLICIT_WAIT;
import static constants.Constants.Url.REGISTRATION_URL;

public class TestBase {
    protected WebDriver driver;
    protected static boolean isRemote;

    public static boolean isRunningInCICD() {
        return "true".equals(System.getenv("GITHUB_ACTIONS"));
    }

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        isRemote = Boolean.parseBoolean(System.getProperty("isRemote", "false"));
        if (!Files.exists(Screenshot.path)) {
            try {
                Files.createDirectories(Screenshot.path);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create screenshots directory", e);
            }
        }
        Screenshot.clearScreenshots();
    }

    @BeforeEach
    public void setUp() {
        driver = initializeDriver();
        DriverContainer.setDriver(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT));
        driver.get(REGISTRATION_URL);
    }

    private WebDriver initializeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage", "--disable-popup-blocking", "--remote-allow-origins=*", "--window-size=1920,1080");
        if (isRunningInCICD()) {
            options.addArguments("--headless", "--disable-gpu", "--disable-extensions", "--no-sandbox", "--disable-dev-shm-usage");
        }
        if (isRemote) {
            options.setCapability("browserVersion", "100.0");
            options.setCapability("selenoid:options", Map.of(
                    "enableVideo", true,
                    "enableVNC", true,
                    "name", "Test badge"
            ));
            try {
                return new RemoteWebDriver(new URL("https://user1:1234@selenoid.autotests.cloud/wd/hub"), options);
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("Bad Selenoid URL", e);
            }
        } else {
            return new ChromeDriver(options);
        }
    }

    @AfterEach
    void tearDown() {
        attachAttachments();
        if (driver != null) {
            driver.quit();
        }
    }

    void attachAttachments() {
        if (driver != null) {
            Attach.screenshotAs("Last screen");
            Attach.pageSource();
            Attach.browserConsoleLogs();
            Attach.addVideo();
        }
    }
}
