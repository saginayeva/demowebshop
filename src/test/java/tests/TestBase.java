package tests;

import helpers.Attach;
import helpers.DriverContainer;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

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
    }

    @BeforeEach
    public void setUp() {
        this.driver = initializeDriver();
        DriverContainer.setDriver(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT));
        driver.get(REGISTRATION_URL);
    }

    private WebDriver initializeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--disable-popup-blocking", "--remote-allow-origins=*", "--window-size=1920,1080");

        if (isRunningInCICD()) {
            options.addArguments("--headless", "--disable-gpu", "--disable-extensions");
        }

        if (isRemote) {
            options.setCapability("browserVersion", "100.0");
            options.setCapability("selenoid:options", new HashMap<String, Object>() {{
                put("enableVideo", true);
                put("enableVNC", true);
                put("name", "Test badge ");
            }});

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
    void addAttachments() {
        attachAttachments();
        if (driver != null) {
            driver.quit();
        }
    }

    void attachAttachments() {
        Attach.screenshotAs("Last screen");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }
}
