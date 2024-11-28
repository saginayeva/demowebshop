package tests.api;

import helpers.DriverContainer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import static constants.Constants.Url.REGISTRATION_URL;
import static io.restassured.RestAssured.given;

public class ApiTestBase {

    protected WebDriver driver;
    protected String login = "admin@a.cz";
    protected String password = "admin123";
    protected static final String LOGIN_PAGE = "/login";

    @BeforeEach
    void setup() {
        RestAssured.baseURI = REGISTRATION_URL;
        if (driver == null) {
            driver = DriverContainer.getDriver();
        }
    }

    protected void openPage(String path) {
        if (driver != null) {
            String fullUrl = path.startsWith("http") ? path : REGISTRATION_URL + (path.startsWith("/") ? path : "/" + path);
            System.out.println("Opening URL: " + fullUrl);
            driver.get(fullUrl);
        }
    }

    protected String loginAndGetAuthCookie() {
        return given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("Email", login)
                .formParam("Password", password)
                .when()
                .post(LOGIN_PAGE)
                .then()
                .statusCode(302)
                .extract()
                .cookie("NOPCOMMERCE.AUTH");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
