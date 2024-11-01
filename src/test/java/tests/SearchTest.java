package tests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import pages.SearchPage;
import utils.TestData;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class SearchTest extends TestBase {

    private SearchPage searchPage;
    private TestData testData;

    @BeforeEach
    public void setUp() {
        super.setUp();
        testData = new TestData();
        searchPage = new SearchPage(driver);
    }

    @DisplayName("Search Results")
    @Test
    void testSearchWithResults() {
        searchPage.searchForItem("computer");
        assertTrue(searchPage.isSearchResultDisplayed(), "Expected search results to be displayed");
        log.info("Search with results displayed successfully");
    }

    @DisplayName("Select Product")
    @Test
    void testChooseToProduct() {
        searchPage.searchForItem("computer");
        searchPage.findProductByTitle("Simple Computer");
        searchPage.findByPrice(BigDecimal.valueOf(800.00).setScale(2, RoundingMode.HALF_UP));
        searchPage.clickOnProductLink("Simple Computer");
        log.info("Selected 'Simple Computer' product");

        String actualUrl = driver.getCurrentUrl();
        assertTrue(actualUrl.endsWith("/simple-computer"),
                "Expected the URL to end with '/simple-computer' but was: " + actualUrl);
    }

    @DisplayName("Set 'Simple Computer' and verify")
    @Test
    void testAddProductAndVerifyDetails() {
        testChooseToProduct();
        searchPage.verifyProductName("Simple Computer");
        searchPage.verifyProductPrice(BigDecimal.valueOf(800.00));

        selectProductOptions();
        verifySelectedProductOptions();
        searchPage.clickAddToCartButton();
        assertTrue(searchPage.isProductAddedToCartMessageDisplayed(),
                "The product has been added to your shopping cart");
        searchPage.clickShoppingCartLink();
    }

    @DisplayName("Search without results")
    @Test
    void testSearchWithoutResults() {
        searchPage.searchForItem("nonexistentitem");
        assertTrue(searchPage.isNoResultsMessageDisplayed(),
                "Expected no results message to be displayed for nonexistent items");
    }

    @DisplayName("Alert displayed when search fails")
    @Test
    void testDisplayAlertWhenSearchIsEmpty() {
        searchPage.searchForItem("");
        assertTrue(searchPage.handleUnexpectedAlert(), "Expected alert to be displayed for empty search");
    }

    private void selectProductOptions() {
        searchPage.clickRadioButton("Slow");
        searchPage.clickRadioButton("4 GB");
        searchPage.clickRadioButton("400 GB");
        searchPage.clickCheckbox("Office Suite");
        searchPage.clickCheckbox("Image Viewer");
    }

    private void verifySelectedProductOptions() {
        assertTrue(searchPage.isRadioButtonSelected("Slow"));
        assertTrue(searchPage.isRadioButtonSelected("4 GB"));
        assertTrue(searchPage.isRadioButtonSelected("400 GB"));
        assertTrue(searchPage.isCheckboxChecked("Image Viewer"));
        assertTrue(searchPage.isCheckboxChecked("Office Suite"));
    }
}
