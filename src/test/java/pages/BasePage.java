package pages;

import config.CredentialsConfig;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static constants.Constants.TimeoutVariable.EXPLICIT_WAIT;

@Slf4j
public class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait driverWait;
    protected final JavascriptExecutor jsExecutor;
    protected static final CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);
    protected final String baseUrl = config.baseUrl();
    protected static final By SHOPPING_CART = By.id("topcartlink");
    protected static final By REGISTER_LINK = By.linkText("Register");
    protected static final By LOG_IN_LINK = By.linkText("Log in");
    protected static final By LOADING_IMAGE = By.className("loading-image");
    protected static final By NOTIFICATION_BAR = By.id("bar-notification");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        driverWait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));
        jsExecutor = (JavascriptExecutor) driver;
    }

    protected WebElement getElement(By locator) {
        return driver.findElement(locator);
    }

    protected List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    protected void clickElement(By locator) {
        getElement(locator).click();
    }

    protected void setElementValue(By locator, String value) {
        getElement(locator).clear();
        getElement(locator).sendKeys(value);
    }

    protected String getElementInputValue(By locator) {
        return getElement(locator).getAttribute("value");
    }

    protected void waitElementVisible(By locator) {
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitElementDisappear(By locator) {
        driverWait.until(wd -> ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void waitElementClickable(By locator) {
        driverWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForTextInElement(By locator, String text) {
        driverWait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    protected void waitElementRefreshed(By locator) {
        driverWait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(getElement(locator))));
    }

    protected void handleAlert() {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
            log.info("Alert was successfully accepted.");
        } catch (NoAlertPresentException e) {
            log.warn("No alert was present to accept.", e);
        }
    }

    protected void selectOption(By locator, String value) {
        Actions actions = new Actions(driver);
        actions.moveToElement(getElement(locator)).click().sendKeys(value, Keys.ENTER).perform();
    }

    protected String getSelectedOption(By locator) {
        return getElement(locator).getAttribute("value");
    }

    protected static By locateFromDataTestId(String id) {
        return By.cssSelector("[data-testid='" + id + "']");
    }

    protected By getCheckboxLocator(String containerId) {
        return locateFromDataTestId("container_" + containerId + ";");
    }

    protected void jsClick(WebElement webElement) {
        jsExecutor.executeScript("arguments[0].click();", webElement);
    }

    protected boolean elementExists(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    protected void scrollToElement(WebElement element) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(false);", element);
    }

    public void openPage(String path) {
        String fullUrl = baseUrl + (path.startsWith("/") ? path : "/" + path);
        driver.get(fullUrl);
    }

    public void addAuthCookie(String authCookieValue) {
        driver.manage().addCookie(new Cookie("NOPCOMMERCE.AUTH", authCookieValue));
        driver.navigate().refresh();
    }
}
