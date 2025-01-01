package pages;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;

import java.util.List;

@Slf4j
public class ShoppingCartPage extends BasePage {

    private static final By TABLE = By.xpath("//table[@class='cart']");
    private static final By TABLE_HEADERS = By.xpath("//table[@class='cart']/thead/tr");
    private static final By TABLE_ROWS = By.xpath("//table[@class='cart']/tbody/tr");
    private static final By REMOVE_CHECKBOX = By.cssSelector("input[type='checkbox'][name='removefromcart']");
    private static final By COUNTRY_DD = By.name("CountryId");
    private static final By STATE_PROVINCE_DD = By.name("StateProvinceId");
    private static final By CHECKOUT_BUTTON = By.name("checkout");
    private static final By TERMS_OF_SERVICE_AGREEMENT = By.name("termsofservice");
    private static final By CHECKBOX_IN_STORE_PICKUP = By.id("PickUpInStore");
    private static final By PAYMENT_METHOD_CHECK_MONEY_ORDER = By.id("paymentmethod_1");
    private static final By CONTINUE_BILLING_BUTTON = By.cssSelector("#billing-buttons-container .new-address-next-step-button");
    private static final By CONTINUE_SHIPPING_BUTTON = By.cssSelector("#shipping-buttons-container .new-address-next-step-button");
    private static final By CONTINUE_PAYMENT_METHOD_BUTTON = By.cssSelector(".payment-method-next-step-button");
    private static final By CONTINUE_PAYMENT_INFORMATION_BUTTON = By.cssSelector(".payment-info-next-step-button");
    private static final By CHECKOUT_DATA_TEXT = By.className("checkout-data");
    private static final By CONFIRM_BUTTON = By.cssSelector(".button-1.confirm-order-next-step-button");
    private static final By CHECKBOX_CART = By.cssSelector("table.cart input[type='checkbox'][name='removefromcart']");
    private static final By TERMS_WARNING_MODAL = By.id("terms-of-service-warning-box");
    private static final By MODAL_CLOSE_BUTTON = By.cssSelector("#terms-of-service-warning-box .ui-dialog-titlebar-close");
    private static final By CONFIRMATION = By.cssSelector(".page.checkout-page");

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click on shopping cart button")
    public ShoppingCartPage clickShoppingCartLink() {
        waitElementVisible(SHOPPING_CART);
        waitElementDisappear(NOTIFICATION_BAR);
        scrollToElement(getElement(SHOPPING_CART));
        clickElement(SHOPPING_CART);
        waitElementDisappear(LOADING_IMAGE);
        log.info("Clicked on 'Shopping Cart' link");
        return this;
    }

    @Step("Click on checkbox cart")
    public ShoppingCartPage clickCheckboxShoppingCart(String productName) {
        waitElementClickable(CHECKBOX_CART);
        clickCheckboxForProduct(productName);
        log.info("Clicked on checkbox");
        return this;
    }

    @Step("Click on checkbox for product {productName} in the cart")
    public void clickCheckboxForProduct(String productName) {
        WebElement cartTable = getElement(TABLE);
        List<WebElement> tableRows = cartTable.findElements(TABLE_ROWS);
        for (WebElement row : tableRows) {
            WebElement productCell = row.findElement(By.cssSelector("td.product"));
            String productText = productCell.getText();
            if (productText.contains(productName)) {
                row.findElement(REMOVE_CHECKBOX).click();
                log.info("Clicked on checkbox for product: " + productName);
                return;
            }
        }
        throw new NoSuchElementException("Checkbox for product '" + productName + "' not found in the cart");
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
    public ShoppingCartPage selectState(String state) {
        selectOption(STATE_PROVINCE_DD, state);
        log.info("Selected state: " + state);
        return this;
    }

    @Step("Click on terms of service agreement")
    public ShoppingCartPage clickTermOfServiceAgreement() {
        jsClick(getElement(TERMS_OF_SERVICE_AGREEMENT));
        log.info("Clicked on terms of service agreement");
        return this;
    }

    @Step("Click on checkout button")
    public void clickCheckoutButton() {
        waitElementClickable(CHECKOUT_BUTTON);
        clickElement(CHECKOUT_BUTTON);
        log.info("Clicked on checkout button");
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
        waitElementClickable(CONTINUE_BILLING_BUTTON);
        clickElement(CONTINUE_BILLING_BUTTON);
        log.info("Click Continue button");
        return this;
    }

    @Step("Click Continue Shipping address button")
    public ShoppingCartPage clickContinueShippingAddressButton() {
        waitElementVisible(CONTINUE_SHIPPING_BUTTON);
        clickElement(CONTINUE_SHIPPING_BUTTON);
        log.info("Click Continue button");
        return this;
    }

    @Step("Click checkbox In Store Pickup")
    public ShoppingCartPage clickCheckboxInStorePickup() {
        waitElementVisible(CHECKBOX_IN_STORE_PICKUP);
        jsClick(getElement(CHECKBOX_IN_STORE_PICKUP));
        log.info("Click Checkbox In Store Pickup");
        return this;
    }

    @Step("Handle Terms of Service modal if present")
    public ShoppingCartPage handleTermsOfServiceModal() {
        try {
            waitElementVisible(TERMS_WARNING_MODAL);
            WebElement modalElement = getElement(TERMS_WARNING_MODAL);
            String modalText = modalElement.getText();
            log.info("Terms of Service modal appeared with message: " + modalText);
            WebElement closeButton = getElement(MODAL_CLOSE_BUTTON);
            closeButton.click();
            log.info("Closed Terms of Service modal");
        } catch (TimeoutException e) {
            log.info("Terms of Service modal did not appear");
        }
        return this;
    }

    @Step("Click checkbox on Payment method check/money order")
    public ShoppingCartPage clickCheckboxPaymentMethodCheckMoneyOrder() {
        waitElementClickable(PAYMENT_METHOD_CHECK_MONEY_ORDER);
        jsClick(getElement(PAYMENT_METHOD_CHECK_MONEY_ORDER));
        log.info("Click Checkbox Payment Method Check or Money Order");
        return this;
    }

    @Step("Click Continue button on Payment method")
    public void clickContinuePaymentMethodButton() {
        waitElementVisible(CONTINUE_PAYMENT_METHOD_BUTTON);
        jsClick(getElement(CONTINUE_PAYMENT_METHOD_BUTTON));
        log.info("Click Continue button Payment Method");
    }

    @Step("Check if data text is valid")
    public boolean isPaymentInformationDisplayed() {
        return getElement(CHECKOUT_DATA_TEXT).isDisplayed();
    }

    @Step("Click Continue button on Payment Information")
    public ShoppingCartPage clickContinuePaymentInformationButton() {
        waitElementClickable(CONTINUE_PAYMENT_INFORMATION_BUTTON);
        jsClick(getElement(CONTINUE_PAYMENT_INFORMATION_BUTTON));
        log.info("Click Continue button Payment Information");
        return this;
    }

    @Step("Click Confirm button")
    public void clickConfirmButton() {
        waitElementVisible(CONFIRM_BUTTON);
        clickElement(CONFIRM_BUTTON);
        log.info("Click Confirm Button");
    }

    public boolean isProductInCart(String productName) {
        return driver.findElements(By.cssSelector(".product-name"))
                .stream()
                .anyMatch(element -> element.getText().contains(productName));
    }

    private void closeNotificationBar() {
        By notificationBarClose = By.cssSelector("#bar-notification .close");
        if (elementExists(notificationBarClose)) {
            clickElement(notificationBarClose);
            waitElementDisappear(NOTIFICATION_BAR);
        }
    }

    public boolean isOrderConfirmationDisplayed() {
        waitForTextInElement(CONFIRMATION, "Your order has been successfully processed!");
        return getElement(CONFIRMATION).getText().contains("Your order has been successfully processed!");
    }
}
