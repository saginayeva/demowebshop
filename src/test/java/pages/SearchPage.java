package pages;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class SearchPage extends BasePage {

    private static final By SEARCH_INPUT = By.id("small-searchterms");
    private static final By SEARCH_BUTTON = By.cssSelector("input[value='Search']");
    private static final By SEARCH_RESULTS = By.className("search-results");
    private static final By NO_RESULTS_MESSAGE = By.className("search-results");
    private static final By SIMPLE_COMPUTER_LINK = By.linkText("Simple Computer");
    private static final By SIMPLE_COMPUTER_PRICE = By.className("prices");
    private static final By ADD_TO_CART_BUTTON = By.xpath("//input[@id='add-to-cart-button-75']");
    private static final By PRODUCT_NAME_HEADER_DETAIL_PAGE = By.className("product-name");
    private static final By PRODUCT_NAME_HEADER = By.className("product-title");
    private static final By PRODUCT_PRICE = By.className("product-price");
    private static final By NOTIFICATION_BAR = By.id("bar-notification");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    @Step("Search for item '{query}'")
    public void searchForItem(String query) {
        setElementValue(SEARCH_INPUT, query);
        clickElement(SEARCH_BUTTON);
        log.info("Searching for item: {}", query);
    }

    @Step("Find product by price '{price}'")
    public void findProductByPrice(BigDecimal price) {
        List<WebElement> products = getElements(SIMPLE_COMPUTER_PRICE);
        for (WebElement product : products) {
            BigDecimal actualPrice = new BigDecimal(product.getText()).setScale(2, RoundingMode.HALF_UP);
            if (actualPrice.compareTo(price.setScale(2, RoundingMode.HALF_UP)) == 0) {
                return;
            }
        }
        throw new NoSuchElementException("Product with expected price not found: " + price);
    }

    @Step("Find product by name '{title}'")
    public void findProductByName(String title) {
        List<WebElement> products = getElements(PRODUCT_NAME_HEADER);
        for (WebElement product : products) {
            String productName = product.getText().trim();
            if (productName.equalsIgnoreCase(title)) {
                return;
            }
        }
        throw new NoSuchElementException("Product with title '" + title + "' not found.");
    }

    @Step("Click on product link '{title}'")
    public void clickOnProductLink(String title) {
        getElement(SIMPLE_COMPUTER_LINK).click();
        log.info("Clicked on product link: {}", title);
    }

    @Step("Check if search result is displayed")
    public boolean isSearchResultDisplayed() {
        handleUnexpectedAlert();
        waitElementVisible(SEARCH_RESULTS);
        return getElement(SEARCH_RESULTS).isDisplayed();
    }

    @Step("Check if no result message is displayed")
    public boolean isNoResultsMessageDisplayed() {
        try {
            return getElement(NO_RESULTS_MESSAGE).isDisplayed();
        } catch (NoSuchElementException e) {
            log.warn("No results message not found.");
            return false;
        }
    }

    @Step("Handle unexpected alert if present")
    public boolean handleUnexpectedAlert() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            log.warn("Unexpected alert encountered: " + alertText);
            alert.accept();
            return alertText.contains("Please enter some search keyword");
        } catch (NoAlertPresentException e) {
            log.info("No unexpected alert present.");
            return false;
        }
    }

    @Step("Verify that product name is visible: '{nameProduct}'")
    public void verifyProductName(String nameProduct) {
        waitElementVisible(PRODUCT_NAME_HEADER_DETAIL_PAGE);
        String actualName = getElement(PRODUCT_NAME_HEADER_DETAIL_PAGE).getText().trim();
        assertEquals(nameProduct, actualName, "Expected product name to be: " + nameProduct);
    }

    @Step("Verify that the product price is correct: '{expectedPrice}'")
    public void verifyProductPrice(BigDecimal expectedPrice) {
        BigDecimal actualPrice = new BigDecimal(getElement(PRODUCT_PRICE).getText()).setScale(
                2, RoundingMode.HALF_UP);
        assertEquals(expectedPrice.setScale(2, RoundingMode.HALF_UP), actualPrice,
                "Expected product price to be: " + expectedPrice + " but was: " + actualPrice);
    }

    protected By getRadioButtonLocator(String value) {
        return By.xpath("//label[contains(text(),'" + value + "')]/preceding-sibling::input[@type='radio']");
    }

    protected By getCheckboxLocator(String value) {
        return By.xpath("//label[contains(text(),'" + value + "')]/preceding-sibling::input[@type='checkbox']");
    }

    @Step("Click on the checkbox with value '{value}'")
    public void clickCheckboxSetSoftware(String value) {
        jsClick(getElement(getCheckboxLocator(value)));
        log.info("Clicked checkbox with value: " + value);
    }

    @Step("Click radio button with value '{value}'")
    public void clickRadioButton(String value) {
        jsClick(getElement(getRadioButtonLocator(value)));
        log.info("Clicked radio button with value: " + value);
    }

    @Step("Verify if the checkbox with value '{value}' is selected")
    public boolean isCheckboxSoftwareChecked(String value) {
        return getElement(getCheckboxLocator(value)).isSelected();
    }

    @Step("Verify if the radio button with value '{value}' is selected")
    public boolean isRadioButtonSelected(String value) {
        return getElement(getRadioButtonLocator(value)).isSelected();
    }

    @Step("Add product to the shopping cart")
    public void clickAddToCartButton() {
        clickElement(ADD_TO_CART_BUTTON);
        waitElementDisappear(LOADING_IMAGE);
        waitElementDisappear(NOTIFICATION_BAR);
        log.info("Clicked on 'Add to Cart' button");
    }

    @Step("Check if the product added to cart message is displayed")
    public boolean isProductAddedToCartMessageDisplayed() {
        waitElementVisible(NOTIFICATION_BAR);
        return getElement(NOTIFICATION_BAR).isDisplayed() &&
                getElement(NOTIFICATION_BAR).getText().contains("The product has been added to your shopping cart");
    }
}
