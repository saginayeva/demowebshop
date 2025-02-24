package tests.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import tests.api.api.AuthorizationApi;
import pages.LoginPage;
import tests.base.TestBase;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class LoginTest extends TestBase {

    @Test
    void testHomePageTitle() {
        given().when()
                .get(config.baseUrl())
                .then()
                .statusCode(200)
                .and()
                .body("html.head.title", containsString("Demo Web Shop"));
        log.info("Test completed: Home Page Title - Passed");
    }

    @Test
    void testSuccessfulLoginWithApi() {
        AuthorizationApi authApi = new AuthorizationApi(config.baseUrl());
        String authCookieValue = authApi.loginAndGetAuthCookie(login, password);

        assertNotNull(authCookieValue, "Authorization cookie should not be null");
        log.info("Authorization cookie retrieved successfully");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(authCookieValue);
        assertTrue(loginPage.isUserLoggedIn(login), "User should be logged in");
        log.info("Test completed: Successful Login with API - Passed");
    }
}
