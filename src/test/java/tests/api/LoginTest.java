package tests.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static constants.Constants.Url.REGISTRATION_URL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class LoginTest extends ApiTestBase {

    private static final String MAIN_PAGE = "/";

    @Test
    void testHomePageTitle() {
        given().when()
                .get(REGISTRATION_URL)
                .then()
                .statusCode(200)
                .and()
                .body("html.head.title", containsString("Demo Web Shop"));
        log.info("Test completed: Home Page Title - Passed");
    }

    @Test
    void testSuccessfulLoginWithApi() {
        String authCookieKey = "NOPCOMMERCE.AUTH";
        String authCookieValue = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("Email", login)
                .formParam("Password", password)
                .when()
                .post(LOGIN_PAGE)
                .then()
                .log().all()
                .statusCode(302)
                .extract()
                .cookie(authCookieKey);

        assertNotNull(authCookieValue, "Authorization cookie should not be null");

        if (driver != null) {
            openPage(MAIN_PAGE);
            Cookie authCookie = new Cookie(authCookieKey, authCookieValue);
            driver.manage().addCookie(authCookie);
            driver.navigate().refresh();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement accountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".account")));
            assertTrue(accountElement.getText().contains(login), "Verify successful authorization");
            log.info("Successfully logged in and verified account page");
        }
        log.info("Test completed: Successful Login with API - Passed");
    }
}
