package tests.api.specs;

import helpers.DriverContainer;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import tests.api.ApiTestBase;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends ApiTestBase {

    @Test
    void apiLoginTest() {
        WebDriver driver = DriverContainer.getDriver();
        step("Get authorization cookie by api and set it to browser", () -> {
            String authCookieKey = "NOPCOMMERCE.AUTH";
            String authCookieValue = given()
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("Email", login)
                    .formParam("Password", password)
                    .when()
                    .post("/login")
                    .then()
                    .log().all()
                    .statusCode(302)
                    .extract()
                    .cookie(authCookieKey);

            step("Open main page");
            openPage("/Themes/DefaultClean/Content/images/logo.png");
            Cookie authCookie = new Cookie(authCookieKey, authCookieValue);
            driver.manage().addCookie(authCookie);
        });

        driver.navigate().refresh();

        step("Verify successful authorization");
        WebElement accountElement = driver.findElement(By.cssSelector(".account"));
        assertTrue(accountElement.getText().contains(login), "Verify successful authorization");
    }
}
