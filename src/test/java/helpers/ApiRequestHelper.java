package helpers;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiRequestHelper {
    private final String baseUrl;
    String authCookieKey = "NOPCOMMERCE.AUTH";

    public ApiRequestHelper(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Response post(String url, String contentType, String body) {
        return given()
                .baseUri(baseUrl)
                .contentType(contentType)
                .body(body)
                .post(url);
    }

    public Response get(String url, String authCookie) {
        return given()
                .cookie(authCookieKey, authCookie)
                .get(url);
    }

    public String loginAndGetAuthCookie(String login, String password) {
        String loginUrl = "/login";
        return post(loginUrl, "application/x-www-form-urlencoded",
                "Email=" + login + "&Password=" + password)
                .then()
                .statusCode(302)
                .extract()
                .cookie(authCookieKey);
    }
}
