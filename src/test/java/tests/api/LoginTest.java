package tests.api;

import config.CredentialsConfig;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Test;
import tests.api.api.AuthorizationApi;
import pages.LoginPage;
import tests.base.ApiTestBase;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class LoginTest extends ApiTestBase {

    private static final CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);
    private static final String MAIN_PAGE = "/";

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

        openPage(MAIN_PAGE);
        LoginPage loginPage = new LoginPage();
        loginPage.login(authCookieValue);
        assertTrue(loginPage.isUserLoggedIn(login), "User should be logged in");
        log.info("Test completed: Successful Login with API - Passed");
    }
}
