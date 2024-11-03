package constants;

import org.openqa.selenium.By;

public class Constants {

    public static class Url {
        public static final String REGISTRATION_URL = "https://demowebshop.tricentis.com/";
    }

    public static class TimeoutVariable {

        public static final int IMPLICIT_WAIT = 5;
        public static final int EXPLICIT_WAIT = 10;
    }

    public class RegistrationLocators {
        public static final By REGISTER_LINK = By.linkText("Register");
        public static final By GENDER_MALE = By.id("gender-male");
        public static final By GENDER_FEMALE = By.id("gender-female");
        public static final By FIRST_NAME_INPUT = By.id("FirstName");
        public static final By LAST_NAME_INPUT = By.id("LastName");
        public static final By EMAIL_INPUT = By.id("Email");
        public static final By PASSWORD_INPUT = By.id("Password");
        public static final By CONFIRM_PASSWORD_INPUT = By.id("ConfirmPassword");
        public static final By REGISTER_BUTTON = By.id("register-button");
        public static final By REGISTRATION_RESULT_MESSAGE = By.className("page-body");
        public static final By VALIDATION_ERROR_MESSAGE = By.className("field-validation-error");
    }

    public class SearchLocators {
        public static final By SEARCH_INPUT = By.id("small-searchterms");
        public static final By SEARCH_BOX = By.className("search-box");
        public static final By SEARCH_BUTTON = By.cssSelector("input[value='Search']");
        public static final By SEARCH_RESULTS = By.className("search-results");
        public static final By NO_RESULTS_MESSAGE = By.className("search-results");
        public static final By SIMPLE_COMPUTER_LINK = By.linkText("Simple Computer");
        public static final By SIMPLE_COMPUTER_PRICE = By.className("prices");
        public static final By ADD_TO_CART_BUTTON = By.id("add-to-cart-button-75");
        public static final By CART_QUANTITY = By.className("cart-qty");
        public static final By PRODUCT_NAME_HEADER_DETAIL_PAGE = By.className("product-name");
        public static final By PRODUCT_NAME_HEADER = By.className("product-title");
        public static final By PRODUCT_PRICE = By.className("product-price");
        public static final By NOTIFICATION_BAR = By.id("bar-notification"); //class"content
        public static final By LOADING_IMAGE = By.className("loading-image");
    }
    public class HeaderLinksLocators {
        public static final By SHOPPING_CART = By.id("topcartlink");
        public static final By LOGOUT_LINK = By.linkText("Log out");
        public static final By ACCOUNT_LINK = By.className("account");

    }
    public class ShoppingCartLocators {
        public static final By CHECKBOX_CART = By.name("removefromcart");
        public static final By COUNTRY_DD = By.name("CountryId");
        public static final By STATE_PROVINCE_DD = By.name("StateProvinceId");
        public static final By CHECKOUT_BUTTON = By.name("checkout");
        public static final By TERMS_OF_SERVICE_AGREEMENT = By.name("termsofservice");
        public static final By CHECKBOX_IN_STORE_PICKUP = By.id("PickUpInStore");
        public static final By PAYMENT_METHOD_CHECK_MONEY_ORDER = By.id("paymentmethod_1");
        public static final By CONTINUE_BUTTON = By.className("button-1 new-address-next-step-button");
        public static final By CONTINUE_PAYMENT_METHOD_BUTTON = By.className("button-1 payment-method-next-step-button");
        public static final By CONTINUE_PAYMENT_INFORMATION_BUTTON = By.className("button-1 payment-info-next-step-button");
        public static final By CHECKOUT_DATA_TEXT = By.className("checkout-data");
        public static final By CONFIRM_BUTTON = By.className("button-1 confirm-order-next-step-button");
    }
}
