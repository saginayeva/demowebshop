package pages.api;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@Slf4j
public class LoginPage {
    private WebDriver driver;
    private static final By accountElementLocator = By.cssSelector(".account");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String authCookieValue) {
        Cookie authCookie = new Cookie("NOPCOMMERCE.AUTH", authCookieValue);
        driver.manage().addCookie(authCookie);
        driver.navigate().refresh();
        log.info("Login process completed, page refreshed.");
    }

    public boolean isUserLoggedIn(String login) {
        log.info("Checking if user is logged in with login: {}", login);
        WebElement accountElement = driver.findElement(accountElementLocator);
        boolean isLoggedIn = accountElement.getText().contains(login);
        log.info("User logged in status: {}", isLoggedIn);
        return isLoggedIn;
    }
}
