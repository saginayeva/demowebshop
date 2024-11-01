package pages;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import static constants.Constants.RegistrationLocators.*;

@Slf4j
public class RegistrationPage extends BasePage {

    private static final Faker faker = new Faker();

    public RegistrationPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("Click register link")
    public void clickRegisterLink() {
        clickElement(REGISTER_LINK);
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
}