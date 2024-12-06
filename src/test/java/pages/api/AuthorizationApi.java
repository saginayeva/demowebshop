package pages.api;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

public class AuthorizationApi {
    private static final String LOGIN_PAGE = "/login";
    private final String baseUrl;
    private static final String authCookieKey = "NOPCOMMERCE.AUTH";

    public AuthorizationApi(String baseUrl) {
        this.baseUrl = baseUrl;
        RestAssured.baseURI = baseUrl;
    }

    public String loginAndGetAuthCookie(String login, String password) {
        return given()
                .baseUri(baseUrl)
                .contentType("application/x-www-form-urlencoded")
                .formParam("Email", login)
                .formParam("Password", password)
                .when()
                .post(LOGIN_PAGE)
                .then()
                .statusCode(302)
                .extract()
                .cookie(authCookieKey);
    }
}
