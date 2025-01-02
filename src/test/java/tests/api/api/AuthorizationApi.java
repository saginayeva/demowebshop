package tests.api.api;

import helpers.CustomAllureListener;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static tests.api.api.specs.ReqResSpec.requestSpec;
import static tests.api.api.specs.ReqResSpec.responseSpec;

public class AuthorizationApi {
    private static final String LOGIN_PAGE = "/login";
    private static final String authCookieKey = "NOPCOMMERCE.AUTH";

    public AuthorizationApi(String baseUrl) {
        RestAssured.baseURI = baseUrl;
        RestAssured.filters(CustomAllureListener.withCustomTemplates());
    }

    public String loginAndGetAuthCookie(String login, String password) {
        return given(requestSpec)
                .contentType("application/x-www-form-urlencoded")
                .formParam("Email", login)
                .formParam("Password", password)
                .when()
                .post(LOGIN_PAGE)
                .then()
                .spec(responseSpec)
                .statusCode(302)
                .extract()
                .cookie(authCookieKey);
    }
}
