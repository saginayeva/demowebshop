package tests.base;

import helpers.DriverContainer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import static constants.Constants.Url.REGISTRATION_URL;

public class ApiTestBase {

    protected WebDriver driver;
    protected String login = "admin@a.cz";
    protected String password = "admin123";

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
