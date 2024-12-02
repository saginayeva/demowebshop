package tests.api;

import static io.restassured.RestAssured.given;

public class AuthApi {
    private static final String LOGIN_PAGE = "/login";
    String authCookieKey = "NOPCOMMERCE.AUTH";
    private final String baseUrl;

    public AuthApi(String baseUrl) {
        this.baseUrl = baseUrl;
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
