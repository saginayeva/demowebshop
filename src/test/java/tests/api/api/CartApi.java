package tests.api.api;

import helpers.CustomAllureListener;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class CartApi {

    private static final String ADD_TO_CART_URL = "/addproducttocart/details/72/1";
    private static final String CONTENT_TYPE_FORM_URLENCODED = "application/x-www-form-urlencoded; charset=UTF-8";

    public CartApi() {
        RestAssured.filters(CustomAllureListener.withCustomTemplates());
    }

    public void addProductToCartAsAuthorizedUser(String authCookieValue, String productData) {
        given()
                .contentType(CONTENT_TYPE_FORM_URLENCODED)
                .cookie("NOPCOMMERCE.AUTH", authCookieValue)
                .body(productData)
                .when()
                .post(ADD_TO_CART_URL)
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", containsString("The product has been added to your <a href=\"/cart\">shopping cart</a>"));
    }

    public void addProductToCartAsAnonymousUser(String productData) {
        given()
                .contentType(CONTENT_TYPE_FORM_URLENCODED)
                .body(productData)
                .when()
                .post(ADD_TO_CART_URL)
                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                .body("updatetopcartsectionhtml", is("(2)"));
    }

    public void addProductToCart(String authCookieValue, String productData) {
        given()
                .contentType(CONTENT_TYPE_FORM_URLENCODED)
                .cookie("NOPCOMMERCE.AUTH", authCookieValue)
                .body(productData)
                .when()
                .post(ADD_TO_CART_URL)
                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("message", containsString("The product has been added to your <a href=\"/cart\">shopping cart</a>"));
    }
}
