package tests.ui;

import helpers.MyTestWatcher;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.SearchPage;
import tests.base.TestBase;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@Tag("web")
@ExtendWith(MyTestWatcher.class)
public class SearchTest extends TestBase {

    private SearchPage searchPage;

    @BeforeEach
    public void setUp() {
        super.setUp();
        searchPage = new SearchPage(driver);
    }

    @DisplayName("Verify search results are displayed for a valid query")
    @Test
    void testSearchWithResults() {
        searchPage.searchForItem("computer");
        assertTrue(searchPage.isSearchResultDisplayed(), "Expected search results to be displayed");
        log.info("Search with results displayed successfully");
    }

    @DisplayName("Verify selecting a specific product")
    @Test
    void testSelectProduct() {
        searchPage.searchForItem("computer");
        searchPage.findProductByName("Simple Computer");
        searchPage.findProductByPrice(BigDecimal.valueOf(800.00).setScale(2, RoundingMode.HALF_UP));
        searchPage.clickOnProductLink("Simple Computer");
        log.info("Selected 'Simple Computer' product");

        String actualUrl = driver.getCurrentUrl();
        assertTrue(actualUrl.endsWith("/simple-computer"),
                "Expected the URL to end with '/simple-computer' but was: " + actualUrl);
    }

    @DisplayName("Verify adding product to cart and product details")
    @Test
    void testAddProductAndVerifyDetails() {
        testSelectProduct();
        searchPage.verifyProductName("Simple Computer");
        searchPage.verifyProductPrice(BigDecimal.valueOf(800.00));

        selectProductOptions();
        verifySelectedProductOptions();
        searchPage.clickAddToCartButton();
        assertTrue(searchPage.isProductAddedToCartMessageDisplayed(),
                "The product has been added to your shopping cart");
    }

    @DisplayName("Verify no results message for nonexistent item")
    @Test
    void testSearchWithoutResults() {
        searchPage.searchForItem("nonexistentitem");
        assertTrue(searchPage.isNoResultsMessageDisplayed(),
                "Expected no results message to be displayed for nonexistent items");
    }

    @DisplayName("Verify alert is displayed when search input is empty")
    @Test
    void testDisplayAlertWhenSearchIsEmpty() {
        searchPage.searchForItem("");
        assertTrue(searchPage.handleUnexpectedAlert(), "Expected alert to be displayed for empty search");
    }

    @Step("Select product options")
    private void selectProductOptions() {
        searchPage.clickRadioButton("Slow");
        searchPage.clickRadioButton("4 GB");
        searchPage.clickRadioButton("400 GB");
        searchPage.clickCheckboxSetSoftware("Office Suite");
        searchPage.clickCheckboxSetSoftware("Image Viewer");
    }

    @Step("Verify selected product options")
    private void verifySelectedProductOptions() {
        assertTrue(searchPage.isRadioButtonSelected("Slow"));
        assertTrue(searchPage.isRadioButtonSelected("4 GB"));
        assertTrue(searchPage.isRadioButtonSelected("400 GB"));
        assertTrue(searchPage.isCheckboxSoftwareChecked("Image Viewer"));
        assertTrue(searchPage.isCheckboxSoftwareChecked("Office Suite"));
    }
}
