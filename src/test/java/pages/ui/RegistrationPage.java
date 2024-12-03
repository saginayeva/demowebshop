package pages.ui;

import com.github.javafaker.Faker;
import constants.Constants;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import pages.BasePage;

@Slf4j
public class RegistrationPage extends BasePage {

    private static final By GENDER_MALE = By.id("gender-male");
    private static final By GENDER_FEMALE = By.id("gender-female");
    private static final By FIRST_NAME_INPUT = By.id("FirstName");
    private static final By LAST_NAME_INPUT = By.id("LastName");
    private static final By EMAIL_INPUT = By.id("Email");
    private static final By PASSWORD_INPUT = By.id("Password");
    private static final By CONFIRM_PASSWORD_INPUT = By.id("ConfirmPassword");
    private static final By REGISTER_BUTTON = By.id("register-button");
    private static final By REGISTRATION_RESULT_MESSAGE = By.className("page-body");
    private static final By VALIDATION_ERROR_MESSAGE = By.className("field-validation-error");
    private static final By LOG_IN_BUTTON = By.cssSelector(".login-button");
    private static final Faker faker = new Faker();

    public RegistrationPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("Click register link")
    public void clickRegisterLink() {
        clickElement(Constants.HeaderLinksLocators.REGISTER_LINK);
    }

    @Step("Click log in link")
    public RegistrationPage clickLoginLink() {
        clickElement(Constants.HeaderLinksLocators.LOG_IN_LINK);
        return this;
    }

    @Step("Click submit button")
    public void submitRegistration() {
        clickElement(REGISTER_BUTTON);
    }

    @Step("Check if registration result message is visible")
    public String getRegistrationResult() {
        waitElementVisible(REGISTRATION_RESULT_MESSAGE);
        return getElement(REGISTRATION_RESULT_MESSAGE).getText();
    }

    @Step("Check if validation message is visible")
    public String getValidationErrorMessage() {
        waitElementVisible(VALIDATION_ERROR_MESSAGE);
        return getElement(VALIDATION_ERROR_MESSAGE).getText();
    }

    public void selectGender(String gender) {
        if ("Male".equalsIgnoreCase(gender)) {
            clickElement(GENDER_MALE);
        } else {
            clickElement(GENDER_FEMALE);
        }
    }

    @Step("Fill registration form")
    public void fillRegistrationForm(String firstName, String lastName, String email, String password) {
        selectGender(faker.options().option("Male", "Female"));
        getElement(FIRST_NAME_INPUT).sendKeys(firstName);
        getElement(LAST_NAME_INPUT).sendKeys(lastName);
        getElement(EMAIL_INPUT).sendKeys(email);
        getElement(PASSWORD_INPUT).sendKeys(password);
        getElement(CONFIRM_PASSWORD_INPUT).sendKeys(password);
    }

    @Step("Enter email and password with credentials")
    public RegistrationPage fillWithCredentials(String login, String password) {
        getElement(EMAIL_INPUT).sendKeys(login);
        getElement(PASSWORD_INPUT).sendKeys(password);
        return this;
    }

    @Step("Click on the log in button")
    public void clickLogInButton() {
        clickElement(LOG_IN_BUTTON);
        log.info("Clicked log in button");
    }
}
