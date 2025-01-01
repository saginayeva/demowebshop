package tests.ui;

import helpers.MyTestWatcher;
import helpers.ShoppingFlowHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.BasePage;
import pages.ShoppingCartPage;
import tests.base.TestBase;
import utils.TestData;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Tag("web")
@ExtendWith(MyTestWatcher.class)
public class ShoppingCartTest extends TestBase {

    private TestData testData;
    private BasePage basePage;
    private ShoppingCartPage shoppingCartPage;
    private ShoppingFlowHelper shoppingFlowHelper;
    private static final String DATA = "product_attribute_72_5_18=52" +
            "&product_attribute_72_6_19=54" +
            "&product_attribute_72_3_20=58" +
            "&addtocart_72.EnteredQuantity=1";

    @BeforeEach
    public void setUp() {
        super.setUp();
        testData = new TestData();
        basePage = new BasePage(driver);
        shoppingCartPage = new ShoppingCartPage(driver);
        shoppingFlowHelper = new ShoppingFlowHelper(driver);
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
}
