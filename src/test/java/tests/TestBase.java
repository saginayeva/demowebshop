package tests;

import helpers.Attach;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.RegistrationPage;
import pages.SearchPage;
import utils.TestData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import static constants.Constants.TimeoutVariable.IMPLICIT_WAIT;
import static constants.Constants.Url.REGISTRATION_URL;

public class TestBase {
    protected WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        boolean isRemote = Boolean.parseBoolean(System.getProperty("isRemote", "false"));

        if (isRemote) {
            ChromeOptions options = new ChromeOptions();
            options.setCapability("browserVersion", "100.0");
            options.setCapability("selenoid:options", new HashMap<String, Object>() {{
                /* How to enable video recording */
                put("enableVideo", true);
                put("enableVNC", true);
                put("name", "Test badge ");
            }});

            try {
                this.driver = new RemoteWebDriver(new URL("https://user1:1234@selenoid.autotests.cloud/wd/hub"), options);
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("Bad Selenoid URL");
            }

        } else {
            driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT));
        driver.get(REGISTRATION_URL);
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot", driver);
        Attach.pageSource(driver);
        Attach.browserConsoleLogs(driver);
        Attach.addVideo(driver);

        if (driver != null) {
            driver.quit();
        }
    }

    public void registerAndAddProductToCart() {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        TestData testData = new TestData();
        String firstName = testData.getFirstName();
        String lastName = testData.getLastName();
        String email = testData.getUserEmail();
        String password = testData.getPassword();

        registrationPage.clickRegisterLink();
        registrationPage.fillRegistrationForm(firstName, lastName, email, password);
        registrationPage.submitRegistration();

        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchForItem("computer");
        searchPage.findProductByName("Simple Computer");
        searchPage.findProductByPrice(BigDecimal.valueOf(800.00).setScale(2, RoundingMode.HALF_UP));
        searchPage.clickOnProductLink("Simple Computer");
        searchPage.clickRadioButton("Slow");
        searchPage.clickRadioButton("4 GB");
        searchPage.clickRadioButton("400 GB");
        searchPage.clickCheckbox("Office Suite");
        searchPage.verifyProductName("Simple Computer");
        searchPage.verifyProductPrice(BigDecimal.valueOf(800.00));
        searchPage.clickAddToCartButton();
    }
}