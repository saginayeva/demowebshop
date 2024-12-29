package pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@Slf4j
public class LoginPage extends BasePage {
    private static final By accountElementLocator = By.cssSelector(".account");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openPage("/login");
    }

    public void login(String authCookieValue) {
        addAuthCookie(authCookieValue);
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
