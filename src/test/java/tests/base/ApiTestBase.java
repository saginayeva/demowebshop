package tests.base;

import config.CredentialsConfig;
import helpers.DriverContainer;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import static constants.Constants.Url.REGISTRATION_URL;

public class ApiTestBase {

    protected WebDriver driver;
    private static final CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);
    protected String login = config.login();
    protected String password = config.password();

    @BeforeEach
    void setup() {
        if (driver == null) {
            driver = DriverContainer.getDriver();
        }
    }

    protected void openPage(String path) {
        if (driver != null) {
            String fullUrl = path.startsWith("http") ? path : REGISTRATION_URL + (path.startsWith("/") ? path : "/" + path);
            driver.get(fullUrl);
        }
    }

    @AfterEach
    void tearDown() {
        DriverContainer.closeDriver();
    }
}
