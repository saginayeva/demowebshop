package constants;

import org.openqa.selenium.By;

public class Constants {

    public static class TimeoutVariable {

        public static final int IMPLICIT_WAIT = 5;
        public static final int EXPLICIT_WAIT = 10;
    }

    public class HeaderLinksLocators {
        public static final By SHOPPING_CART = By.id("topcartlink");
        public static final By REGISTER_LINK = By.linkText("Register");
        public static final By LOG_IN_LINK = By.linkText("Log in");
        public static final By LOGOUT_LINK = By.linkText("Log out");
        public static final By ACCOUNT_LINK = By.className("account");

    }

    public class LoadingLocators {
        public static final By LOADING_INDICATOR = By.className("loading-indicator");
        public static final By LOADING_IMAGE = By.className("loading-image");
    }
}
