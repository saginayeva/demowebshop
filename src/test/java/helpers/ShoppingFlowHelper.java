package helpers;

import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import pages.BasePage;
import pages.RegistrationPage;
import pages.SearchPage;
import utils.TestData;

import java.math.BigDecimal;
import java.math.RoundingMode;

@ExtendWith(MyTestWatcher.class)
public class ShoppingFlowHelper extends BasePage {

    public ShoppingFlowHelper(WebDriver driver) {
        super(driver);
    }

    public void registerAndAddProductToCart() {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        TestData testData = new TestData();
        String gender = testData.randomGender();
        String firstName = testData.getFirstName();
        String lastName = testData.getLastName();
        String email = testData.getUserEmail();
        String password = testData.getPassword();

        registrationPage.clickRegisterLink();
        registrationPage.fillRegistrationForm(gender, firstName, lastName, email, password);
        registrationPage.submitRegistration();

        SearchPage searchPage = new SearchPage(driver);
        searchPage.searchForItem("computer");
        searchPage.findProductByName("Simple Computer");
        searchPage.findProductByPrice(BigDecimal.valueOf(800.00).setScale(2, RoundingMode.HALF_UP));
        searchPage.clickOnProductLink("Simple Computer");
        searchPage.clickRadioButton("Slow");
        searchPage.clickRadioButton("4 GB");
        searchPage.clickRadioButton("400 GB");
        searchPage.isCheckboxSoftwareChecked("Office Suite");
        searchPage.verifyProductName("Simple Computer");
        searchPage.verifyProductPrice(BigDecimal.valueOf(800.00));
        searchPage.clickAddToCartButton();
    }
}
