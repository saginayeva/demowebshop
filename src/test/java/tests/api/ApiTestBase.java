package tests.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;

import static constants.Constants.Url.REGISTRATION_URL;

public class ApiTestBase {

    protected static WebDriver driver;
    protected String login = "admin@a.cz";
    protected String password = "admin123";
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = REGISTRATION_URL;
    }

    protected void openPage(String path) {
        driver.get(REGISTRATION_URL + path);
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
