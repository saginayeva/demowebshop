package helpers;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiHelper {
    private final String baseUrl;
    private static final String AUTH_COOKIE_KEY = "NOPCOMMERCE.AUTH";

    public ApiHelper(String baseUrl) {
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
                .cookie(AUTH_COOKIE_KEY, authCookie)
                .get(url);
    }

    public String loginAndGetAuthCookie(String login, String password) {
        String loginUrl = "/login";
        return post(loginUrl, "application/x-www-form-urlencoded",
                "Email=" + login + "&Password=" + password)
                .then()
                .statusCode(302)
                .extract()
                .cookie(AUTH_COOKIE_KEY);
    }

    public Response addItemToCart(String contentType, String authCookie, String addToCartUrl, String data) {
        return given()
                .contentType(contentType)
                .cookie(AUTH_COOKIE_KEY, authCookie)
                .body(data)
                .post(addToCartUrl)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
    }

    public String getCartPage(String authCookie) {
        return get("/cart", authCookie)
                .then()
                .statusCode(200)
                .extract()
                .asString();
    }
}
