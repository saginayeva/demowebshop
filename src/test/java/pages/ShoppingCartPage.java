package pages;

import io.qameta.allure.Step;
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

    @Step("Click on shopping cart button")
    public ShoppingCartPage clickShoppingCartLink() {
        waitElementVisible(SHOPPING_CART);
        clickElement(SHOPPING_CART);
        log.info("Clicked on 'Shopping Cart' link");
        return this;
    }

    @Step("Click on checkbox cart")
    public ShoppingCartPage clickCheckbox() {
        waitElementClickable(CHECKBOX_CART);
        getElement(CHECKBOX_CART).click();
        log.info("Clicked on checkbox");
        return this;
    }

    public boolean isCheckboxSelected() {
        return getElement(CHECKBOX_CART).isSelected();
    }

    @Step("Select country '{}'")
    public ShoppingCartPage selectCountry(String country) {
        scrollToElement(getElement(COUNTRY_DD));
        selectOption(COUNTRY_DD, country);
        log.info("Selected country: " + country);
        return this;
    }

    @Step("Select state/province '{}'")
    public void selectState(String state) {
        selectOption(STATE_PROVINCE_DD, state);
        log.info("Selected state: " + state);
    }

    @Step("Click on terms of service agreement")
    public ShoppingCartPage clickTermOfServiceAgreement() {
        jsClick(getElement(TERMS_OF_SERVICE_AGREEMENT));
        log.info("Clicked on terms of service agreement");
        return this;
    }

    @Step("Click on checkout button")
    public ShoppingCartPage clickCheckoutButton() {
        clickElement(CHECKOUT_BUTTON);
        log.info("Clicked on checkout button");
        return this;
    }

    protected By getInputLocator(String value) {
        return By.name("BillingNewAddress." + value);
    }

    @Step("Set input for value '{}'")
    public ShoppingCartPage setInputDD(String field, String value) {
        jsClick(getElement(getInputLocator(field)));
        selectOption(getInputLocator(field), value);
        log.info("Set input for " + value);
        return this;
    }

    public boolean isSelectedValue(String field, String value) {
        return getSelectedOption(getInputLocator(field)).equals(value);
    }

    @Step("Set input for value '{}'")
    public ShoppingCartPage setInputValue(String field, String value) {
        jsClick(getElement(getInputLocator(field)));
        setElementValue(getInputLocator(field), value);
        log.info("Set input for " + value);
        return this;
    }

    @Step("Click Continue button")
    public ShoppingCartPage clickContinueButton() {
        clickElement(CONTINUE_BUTTON);
        log.info("Click Continue button");
        return this;
    }

    @Step("Click checkbox In Store Pickup")
    public ShoppingCartPage clickCheckboxInStorePickup() {
        jsClick(getElement(CHECKBOX_IN_STORE_PICKUP));
        log.info("Click Checkbox In Store Pickup");
        return this;
    }

    @Step("Click checkbox on Payment method check/money order")
    public ShoppingCartPage clickCheckboxPaymentMethodCheckMoneyOrder() {
        jsClick(getElement(PAYMENT_METHOD_CHECK_MONEY_ORDER));
        log.info("Click Checkbox Payment Method Check or Money Order");
        return this;
    }

    @Step("Click Continue button on Payment method")
    public ShoppingCartPage clickContinuePaymentMethodButton() {
        jsClick(getElement(CONTINUE_PAYMENT_METHOD_BUTTON));
        log.info("Click Continue button Payment Method");
        return this;
    }

    @Step("Check if data text is valid")
    public boolean isPaymentInformationDisplayed() {
        return getElement(CHECKOUT_DATA_TEXT).isDisplayed();
    }

    @Step("Click Continue button on Payment Information")
    public ShoppingCartPage clickContinuePaymentInformationButton() {
        jsClick(getElement(CONTINUE_PAYMENT_INFORMATION_BUTTON));
        log.info("Click Continue button Payment Information");
        return this;
    }

    @Step("Click Confirm button")
    public ShoppingCartPage clickConfirmButton() {
        clickElement(CONFIRM_BUTTON);
        log.info("Click Confirm Button");
        return this;
    }
}
