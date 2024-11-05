package tests;

import helpers.ShoppingFlowHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.ShoppingCartPage;
import utils.TestData;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShoppingCartTest extends TestBase {
    private ShoppingCartPage shoppingCartPage;
    private ShoppingFlowHelper shoppingFlowHelper;
    private TestData testData;

    @BeforeEach
    public void setUp() {
        super.setUp();
        testData = new TestData();
        shoppingCartPage = new ShoppingCartPage(driver);
        shoppingFlowHelper = new ShoppingFlowHelper(driver);
    }

    @Test
    void testShoppingCart() {
        shoppingFlowHelper.registerAndAddProductToCart();
        shoppingCartPage.clickShoppingCartLink()
                .clickCheckboxShoppingCart("Simple Computer")
                .selectCountry("United States")
                .selectState("California")
                .clickTermOfServiceAgreement()
                .clickCheckoutButton();
//        assertTrue(shoppingCartPage.isCheckoutPageDisplayed(), "Expected to be on checkout page");
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
}
