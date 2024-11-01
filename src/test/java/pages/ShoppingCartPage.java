package pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static constants.Constants.HeaderLinksLocators.SHOPPING_CART;
import static constants.Constants.ShoppingCartLocators.*;

@Slf4j
public class ShoppingCartPage extends BasePage {

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    public ShoppingCartPage clickShoppingCartLink() {
        clickElement(SHOPPING_CART);
        log.info("Clicked on 'Shopping Cart' link");
        return this;
    }

    public ShoppingCartPage clickCheckbox() {
        jsClick(getElement(CHECKBOX_CART));
        log.info("Clicked on checkbox");
        return this;
    }

    public ShoppingCartPage selectCountry(String country) {
        selectOption(COUNTRY_DD, country);
        log.info("Selected country: " + country);
        return this;
    }

    public void selectState(String state) {
        selectOption(STATE_PROVINCE_DD, state);
        log.info("Selected state: " + state);
    }

    public ShoppingCartPage clickTermOfServiceAgreement() {
        jsClick(getElement(TERMS_OF_SERVICE_AGREEMENT));
        log.info("Clicked on terms of service agreement");
        return this;
    }

    public ShoppingCartPage clickCheckoutButton() {
        clickElement(CHECKOUT_BUTTON);
        log.info("Clicked on checkout button");
        return this;
    }

    protected By getInputLocator(String value) {
        return By.name("BillingNewAddress." + value);
    }

    public ShoppingCartPage setInputDD(String field, String value) {
        jsClick(getElement(getInputLocator(field)));
        selectOption(getInputLocator(field), value);
        log.info("Set input for " + value);
        return this;
    }

    public boolean isSelectedValue(String field, String value) {
        return getSelectedOption(getInputLocator(field)).equals(value);
    }

    public ShoppingCartPage setInputValue(String field, String value) {
        jsClick(getElement(getInputLocator(field)));
        setElementValue(getInputLocator(field), value);
        log.info("Set input for " + value);
        return this;
    }

    public ShoppingCartPage clickContinueButton() {
        clickElement(CONTINUE_BUTTON);
        log.info("Click Continue button");
        return this;
    }

    public ShoppingCartPage clickCheckboxInStorePickup() {
        jsClick(getElement(CHECKBOX_IN_STORE_PICKUP));
        log.info("Click Checkbox In Store Pickup");
        return this;
    }

    public ShoppingCartPage clickCheckboxPaymentMethodCheckMoneyOrder() {
        jsClick(getElement(PAYMENT_METHOD_CHECK_MONEY_ORDER));
        log.info("Click Checkbox Payment Method Check or Money Order");
        return this;
    }

    public ShoppingCartPage clickContinuePaymentMethodButton() {
        jsClick(getElement(CONTINUE_PAYMENT_METHOD_BUTTON));
        log.info("Click Continue Payment Method Button");
        return this;
    }

    public boolean isPaymentInformationDisplayed() {
        return getElement(CHECKOUT_DATA_TEXT).isDisplayed();
    }

    public ShoppingCartPage clickContinuePaymentInformationButton() {
        jsClick(getElement(CONTINUE_PAYMENT_INFORMATION_BUTTON));
        log.info("Click Continue Payment Information");
        return this;
    }

    public ShoppingCartPage clickConfirmButton() {
        clickElement(CONFIRM_BUTTON);
        log.info("Click Confirm Button");
        return this;
    }
}
