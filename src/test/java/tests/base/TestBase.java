package tests.base;

import config.CredentialsConfig;
import helpers.Attach;
import helpers.DriverContainer;
import helpers.Screenshot;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.time.Duration;
import java.util.HashMap;

import static constants.Constants.TimeoutVariable.IMPLICIT_WAIT;

public class TestBase {
    protected WebDriver driver;
    protected static boolean isRemote;
    private static final CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);

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
        if (isRemote) {
            String remoteUrl = config.getRemoteUrl();
            ChromeOptions options = new ChromeOptions();
            options.setCapability("browserVersion", "100.0");
            options.setCapability("selenoid:options", new HashMap<String, Object>() {{
                put("enableVideo", true);
                put("enableVNC", true);

            }});
            try {
                this.driver = new RemoteWebDriver(new URL(remoteUrl), options);
                DriverContainer.setDriver(driver);
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("Bad Selenoid URL");
            }
        } else {
            driver = DriverContainer.getDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT));
        driver.get(config.baseUrl());
    }

    @AfterEach
    void tearDown() {
        attachAttachments();
        if (driver != null) {
            DriverContainer.closeDriver();
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
