package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.ShoppingCartPage;
import utils.TestData;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShoppingCartTest extends TestBase {
    private ShoppingCartPage shoppingCartPage;
    private TestData testData;

    @BeforeEach
    public void setUp() {
        super.setUp();
        testData = new TestData();
        shoppingCartPage = new ShoppingCartPage(driver);
        registerAndAddProductToCart();
    }

    @Test
    void testShoppingCart() {
        shoppingCartPage.clickShoppingCartLink()
                .clickCheckbox()
                .selectCountry("United States")
                .selectCountry("California")
                .clickTermOfServiceAgreement()
                .clickCheckoutButton();
        //assert
//        assertTrue(shoppingCartPage.isCheckoutPageDisplayed(), "Expected to be on checkout page");
    }

    @Test
    void testCheckoutPage() {
        String address = testData.getStreetAddress();
        String phoneNumber = testData.getPhoneNumber();

        shoppingCartPage.setInputDD("CountryId", "United States")
                .setInputDD("StateProvinceId", "California")
                .setInputValue("City", "Los Angeles")
                .setInputValue("Address1", address)
                .setInputValue("ZipPostalCode", "1234")
                .setInputValue("PhoneNumber", phoneNumber)
                .clickContinueButton()
                .clickCheckboxInStorePickup()
                .clickContinueButton()
                .clickCheckboxPaymentMethodCheckMoneyOrder()
                .clickContinuePaymentMethodButton();
        assertTrue(shoppingCartPage.isPaymentInformationDisplayed());

        shoppingCartPage.clickContinuePaymentInformationButton()
                .clickConfirmButton();
        //assert
//        assertTrue(shoppingCartPage.isOrderConfirmationDisplayed(), "Expected order confirmation message");
    }
}
