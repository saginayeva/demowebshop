package tests.api;

import helpers.ApiHelper;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import pages.api.AuthorizationApi;
import tests.base.ApiTestBase;

import static constants.Constants.Url.REGISTRATION_URL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class CartTest extends ApiTestBase {

    private static final String CART_PAGE = "/cart";
    private static final String ADD_TO_CART_URL = "/addproducttocart/details/72/1";
    private static final String AUTH_COOKIE_KEY = "NOPCOMMERCE.AUTH";
    private static final String DATA = "product_attribute_72_5_18=52" +
            "&product_attribute_72_6_19=54" +
            "&product_attribute_72_3_20=58" +
            "&addtocart_72.EnteredQuantity=1";

    @Test
    void testAddProductToCartAsAuthorizedUser() {
        AuthorizationApi authApi = new AuthorizationApi(REGISTRATION_URL);
        String authCookieValue = authApi.loginAndGetAuthCookie(login, password);
        if (authCookieValue == null) {
            log.error("Authentication failed, cannot proceed with the test.");
            return;
        }

        String data = "product_attribute_72_5_18=52" +
                "&product_attribute_72_6_19=54" +
                "&product_attribute_72_3_20=58" +
                "&addtocart_72.EnteredQuantity=1";

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie(AUTH_COOKIE_KEY, authCookieValue)
                .body(data)
                .when()
                .post(ADD_TO_CART_URL)
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"));

        String htmlResponse = given()
                .cookie(AUTH_COOKIE_KEY, authCookieValue)
                .when()
                .get(CART_PAGE)
                .then()
                .statusCode(200)
                .extract()
                .asString();
        log.debug("HTML Response from cart page: {}", htmlResponse);

        Document doc = Jsoup.parse(htmlResponse);
        String cartItemCount = doc.select(".cart-qty").text();
        log.info("Items in cart: " + cartItemCount);

        int initialItemCount = extractItemCount(cartItemCount);
        int expectedItemCount = initialItemCount + 1;
        log.info("Initial item count: {}, Expected item count after adding: {}", initialItemCount, expectedItemCount);
        assertEquals(expectedItemCount, initialItemCount + 1, "Verify the cart has the expected number of items after adding");
    }

    private int extractItemCount(String cartItemCountText) {
        String countText = cartItemCountText.replaceAll("[^0-9]", "");
        log.debug("Extracted item count text: {}", countText);
        return Integer.parseInt(countText);
    }

    @Test
    void testAddProductToCartAsAnonymousUser() {
        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body(DATA)
                .when()
                .post(ADD_TO_CART_URL)
                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                .body("updatetopcartsectionhtml", is("(2)"));
        log.debug("Response after adding product to cart: {}", DATA);
    }

    @Test  //что-то сломано
    void testCartAsAuthorizedUser() {
        ApiHelper apiHelper = new ApiHelper(REGISTRATION_URL);
        String authCookieValue = apiHelper.loginAndGetAuthCookie(login, password);

        if (authCookieValue == null) {
            log.error("Authentication failed, cannot proceed with the test.");
            return;
        }

        Response addItemResponse = apiHelper.addItemToCart(
                "application/x-www-form-urlencoded; charset=UTF-8",
                authCookieValue, ADD_TO_CART_URL, DATA);

        addItemResponse.then()
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"));

        String cartHtml = apiHelper.getCartPage(authCookieValue);
        Document doc = Jsoup.parse(cartHtml);
        String cartItemCount = doc.select(".cart-qty").text();
        log.info("Items in cart: {}", cartItemCount);

        int initialItemCount = extractItemCount(cartItemCount);
        int expectedItemCount = initialItemCount + 1;

        assertEquals(expectedItemCount, initialItemCount + 1,
                "Verify the cart has the expected number of items after adding");
    }
}
