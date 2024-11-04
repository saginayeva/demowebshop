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

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        isRemote = Boolean.parseBoolean(System.getProperty("isRemote", "false"));
    }

    @BeforeEach
    public void setUp() {
        if (isRemote) {
            ChromeOptions options = new ChromeOptions();
            options.setCapability("browserVersion", "100.0");
            options.setCapability("selenoid:options", new HashMap<String, Object>() {{
                /* How to enable video recording */
                put("enableVideo", true);
                put("enableVNC", true);
                put("name", "Test badge ");
            }});

            try {
                this.driver = new RemoteWebDriver(new URL("https://user1:1234@selenoid.autotests.cloud/wd/hub"), options);
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("Bad Selenoid URL");
            }

        } else {
            driver = new ChromeDriver();
        }

        DriverContainer.setDriver(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT));
        driver.get(REGISTRATION_URL);
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
