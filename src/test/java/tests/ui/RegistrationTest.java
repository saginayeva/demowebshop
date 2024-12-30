package tests.ui;

import helpers.MyTestWatcher;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.RegistrationPage;
import tests.base.TestBase;
import utils.TestData;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@Tag("web")
@ExtendWith(MyTestWatcher.class)
public class RegistrationTest extends TestBase {

    private RegistrationPage registrationPage;
    private TestData testData;

    @BeforeEach
    public void setUp() {
        super.setUp();
        testData = new TestData();
        registrationPage = new RegistrationPage(driver);
        registrationPage.openPage("/");
        registrationPage.clickRegisterLink();
    }

    @Test
    void testSuccessfulRegistration() {
        String gender = testData.randomGender();
        String firstName = testData.getFirstName();
        String lastName = testData.getLastName();
        String email = testData.getUserEmail();
        String password = testData.getPassword();

        registrationPage.fillRegistrationForm(gender, firstName, lastName, email, password);
        registrationPage.submitRegistration();

        String resultMessage = registrationPage.getRegistrationResult();
        assertTrue(resultMessage.contains("Your registration completed"),
                "Expected registration success message not found");
    }

    @Test
    void testMissingRequiredFieldError() {
        String gender = testData.randomGender();
        String firstName = "";
        String lastName = testData.getLastName();
        String email = testData.getUserEmail();
        String password = testData.getPassword();

        registrationPage.fillRegistrationForm(gender, firstName, lastName, email, password);
        registrationPage.submitRegistration();

        String errorMessage = registrationPage.getValidationErrorMessage();
        assertTrue(errorMessage.contains("First name is required"),
                "Expected validation error message for missing first name not found");
    }

    @Test
    void testInvalidEmailError() {
        String gender = testData.randomGender();
        String firstName = testData.getFirstName();
        String lastName = testData.getLastName();
        String email = "invalidEmailFormat";
        String password = testData.getPassword();

        registrationPage.fillRegistrationForm(gender, firstName, lastName, email, password);
        registrationPage.submitRegistration();

        String errorMessage = registrationPage.getValidationErrorMessage();
        assertTrue(errorMessage.contains("Wrong email"),
                "Expected validation error message for invalid email not found");
    }

    @Test
    @Tag("owner")
    void testWithCredentials() {
        String login = config.login();
        String password = config.password();

        registrationPage.clickLoginLink()
                .fillWithCredentials(login, password)
                .clickLogInButton();
    }
}
