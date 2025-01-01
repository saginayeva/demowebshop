package tests.e2e;

import helpers.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.*;
import tests.api.api.AuthorizationApi;
import tests.api.api.CartApi;
import tests.base.TestBase;
import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@Tag("web")
@ExtendWith(MyTestWatcher.class)
public class FullE2ETest extends TestBase {

    private CartApi cartApi;
    private BasePage basePage;
    private ShoppingCartPage shoppingCartPage;
    private String authCookieValue;
    private static final String CART_PAGE = "/cart";
    private static final String DATA = "product_attribute_72_5_18=52" +
            "&product_attribute_72_6_19=54" +
            "&product_attribute_72_3_20=58" +
            "&addtocart_72.EnteredQuantity=1";

    @BeforeEach
    public void setUp(TestInfo testInfo) {
        super.setUp();
        cartApi = new CartApi();
        basePage = new BasePage(driver);
        AuthorizationApi authApi = new AuthorizationApi(baseUrl);
        shoppingCartPage = new ShoppingCartPage(driver);
        if (testInfo.getTags().contains("auth")) {
            authCookieValue = authApi.loginAndGetAuthCookie(login, password);
            basePage.openPage("/");
            basePage.addAuthCookie(authCookieValue);
        }
    }

    @Test
    @Tag("auth")
    @DisplayName("E2E test: login, add product, and complete order")
    public void testFullE2ECheckout() {
        authenticateAndAddProductToCart();
        openCartAndProceedToCheckout();
        fillShippingAndPaymentInfo();
        confirmOrder();
    }

    private void authenticateAndAddProductToCart() {
        AuthorizationApi authApi = new AuthorizationApi(config.baseUrl());
        String authCookieValue = authApi.loginAndGetAuthCookie(login, password);
        assertNotNull(authApi.loginAndGetAuthCookie(login, password),
                "Authorization cookie should not be null");

        cartApi.addProductToCart(authCookieValue, DATA);
    }

    private void openCartAndProceedToCheckout() {
        basePage.openPage(CART_PAGE);
        assertTrue(shoppingCartPage.isProductInCart("Build your own cheap computer"),
                "Product should be in cart");

        shoppingCartPage
                .clickShoppingCartLink()
                .selectCountry("United States")
                .selectState("California")
                .clickTermOfServiceAgreement()
                .clickCheckoutButton();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/onepagecheckout"));
        String expectedUrl = config.baseUrl() + "onepagecheckout";
        assertEquals(expectedUrl, driver.getCurrentUrl(),
                "Checkout page URL does not match");
    }

    private void fillShippingAndPaymentInfo() {
        shoppingCartPage
                .clickContinueButton()
                .clickCheckboxInStorePickup()
                .clickContinueShippingAddressButton()
                .clickCheckboxPaymentMethodCheckMoneyOrder()
                .clickContinuePaymentMethodButton();

        assertTrue(shoppingCartPage.isPaymentInformationDisplayed(),
                "Payment information should be displayed");
    }

    private void confirmOrder() {
        shoppingCartPage.clickContinuePaymentInformationButton()
                .clickConfirmButton();
        assertTrue(shoppingCartPage.isOrderConfirmationDisplayed(),
                "Expected 'Your order has been successfully processed!' message on the final page");
    }
}
