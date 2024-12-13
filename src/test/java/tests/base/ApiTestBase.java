package tests.base;

import config.CredentialsConfig;
import helpers.CustomAllureListener;
import helpers.DriverContainer;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public class ApiTestBase {

    protected WebDriver driver;
    private static final CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);
    protected String login = config.login();
    protected String password = config.password();

    @BeforeAll
    static void setUpAllureListener() {
        RestAssured.filters(CustomAllureListener.withCustomTemplates());
    }

    @BeforeEach
    void setup() {
        if (driver == null) {
            driver = DriverContainer.getDriver();
        }
    }

    protected void openPage(String path) {
        if (driver != null) {
            String baseUrl = config.baseUrl();
            String fullUrl = path.startsWith("http") ? path : baseUrl + (path.startsWith("/") ? path : "/" + path);
            driver.get(fullUrl);
        }
    }

    @AfterEach
    void tearDown() {
        DriverContainer.closeDriver();
    }
}
