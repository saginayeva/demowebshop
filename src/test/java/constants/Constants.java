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

    public class HeaderLinksLocators {
        public static final By SHOPPING_CART = By.id("topcartlink");
        public static final By LOGOUT_LINK = By.linkText("Log out");
        public static final By ACCOUNT_LINK = By.className("account");

    }
}
