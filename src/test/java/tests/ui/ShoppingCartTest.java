package tests.ui;

import config.CredentialsConfig;
import helpers.MyTestWatcher;
import helpers.ShoppingFlowHelper;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@Tag("web")
@ExtendWith(MyTestWatcher.class)
public class ShoppingCartTest extends TestBase {

    private static final CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);
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
        AuthorizationApi authApi = new AuthorizationApi(baseUrl);
        shoppingCartPage = new ShoppingCartPage(driver);
        shoppingFlowHelper = new ShoppingFlowHelper(driver);

        if (testInfo.getTags().contains("auth")) {
            authCookieValue = authApi.loginAndGetAuthCookie(login, password);
            basePage.addAuthCookie(authCookieValue);
        }
    }

    @Test
    void testShoppingCart() {
        shoppingFlowHelper.registerAndAddProductToCart();
        assertEquals("https://demowebshop.tricentis.com/simple-computer", driver.getCurrentUrl(), "Shopping cart page URL does not match");

        shoppingCartPage
                .clickShoppingCartLink()
                .selectCountry("United States")
                .selectState("California")
                .clickTermOfServiceAgreement()
                .clickCheckoutButton();
        assertEquals("https://demowebshop.tricentis.com/onepagecheckout", driver.getCurrentUrl(), "Checkout page URL does not match");

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
//        assertTrue(shoppingCartPage.isOrderConfirmationDisplayed(), "Expected order confirmation message");
    }

    @Test
    @Tag("auth")
    @DisplayName("Add product via API and complete checkout via UI")
    public void testAddProductToCartAndCheckout() {
        cartApi.addProductToCart(authCookieValue, DATA);

        basePage.openPage("/cart");
        assertTrue(shoppingCartPage.isProductInCart("Simple Computer"), "Product should be in cart");

        shoppingCartPage.clickTermOfServiceAgreement();
        shoppingCartPage.clickCheckoutButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/onepagecheckout"));
        String expectedUrl = config.baseUrl() + "onepagecheckout";
        assertEquals(expectedUrl, driver.getCurrentUrl(),
                "Checkout page URL does not match");
    }
}
