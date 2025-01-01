package tests.ui;

import helpers.MyTestWatcher;
import helpers.ShoppingFlowHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;
import pages.ShoppingCartPage;
import tests.api.api.AuthorizationApi;
import tests.api.api.CartApi;
import tests.base.TestBase;
import utils.TestData;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Tag("web")
@ExtendWith(MyTestWatcher.class)
public class ShoppingCartTest extends TestBase {

    private ShoppingCartPage shoppingCartPage;
    private ShoppingFlowHelper shoppingFlowHelper;
    private TestData testData;
    private CartApi cartApi;
    private String authCookieValue;
    private BasePage basePage;
    private static final String DATA = "product_attribute_72_5_18=52" +
            "&product_attribute_72_6_19=54" +
            "&product_attribute_72_3_20=58" +
            "&addtocart_72.EnteredQuantity=1";
    private static final String CART_PAGE = "/cart";

    @BeforeEach
    public void setUp(TestInfo testInfo) {
        super.setUp();
        testData = new TestData();
        cartApi = new CartApi();
        basePage = new BasePage(driver);
        AuthorizationApi authApi = new AuthorizationApi(baseUrl);
        shoppingCartPage = new ShoppingCartPage(driver);
        shoppingFlowHelper = new ShoppingFlowHelper(driver);

        if (testInfo.getTags().contains("auth")) {
            authCookieValue = authApi.loginAndGetAuthCookie(login, password);
            basePage.openPage("/");
            basePage.addAuthCookie(authCookieValue);
        }
    }

    @Test
    void testShoppingCart() {
        basePage.openPage("/");
        shoppingFlowHelper.registerAndAddProductToCart();
        assertEquals("https://demowebshop.tricentis.com/simple-computer", driver.getCurrentUrl(),
                "Shopping cart page URL does not match");

        shoppingCartPage
                .clickShoppingCartLink()
                .selectCountry("United States")
                .selectState("California")
                .clickTermOfServiceAgreement()
                .clickCheckoutButton();
        assertEquals("https://demowebshop.tricentis.com/onepagecheckout", driver.getCurrentUrl(),
                "Checkout page URL does not match");

    }

    @Test
    void testCheckoutPage() {
        String address = testData.getStreetAddress();
        String phoneNumber = testData.getPhoneNumber();

        testShoppingCart();
        shoppingCartPage.setInputDD("CountryId", "United States")
                .setInputDD("StateProvinceId", "California")
                .setInputValue("City", "Los Angeles")
                .setInputValue("Address1", address)
                .setInputValue("ZipPostalCode", "1234")
                .setInputValue("PhoneNumber", phoneNumber)
                .clickContinueButton()
                .clickCheckboxInStorePickup()
                .clickContinueShippingAddressButton()
                .clickCheckboxPaymentMethodCheckMoneyOrder()
                .clickContinuePaymentMethodButton();
        assertTrue(shoppingCartPage.isPaymentInformationDisplayed());

        shoppingCartPage.clickContinuePaymentInformationButton()
                .clickConfirmButton();
        assertTrue(shoppingCartPage.isOrderConfirmationDisplayed(),
                "Expected 'Your order has been successfully processed!' message on the final page");
    }

    @Test
    @Tag("auth")
    @DisplayName("Full E2E: Add product via API and complete checkout via UI")
    public void testFullE2ECheckout() {
        authenticateAndAddProductToCart();
        openCartAndProceedToCheckout();
        fillShippingAndPaymentInfo();
        confirmOrder();
    }

    private void authenticateAndAddProductToCart() {
        AuthorizationApi authApi = new AuthorizationApi(config.baseUrl());
        String authCookieValue = authApi.loginAndGetAuthCookie(login, password);
        assertNotNull(authCookieValue, "Authorization cookie should not be null");

        cartApi.addProductToCart(authCookieValue, DATA);
    }

    private void openCartAndProceedToCheckout() {
        basePage.openPage("/cart");
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
        String address = testData.getStreetAddress();
        String phoneNumber = testData.getPhoneNumber();

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
