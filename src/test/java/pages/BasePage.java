package pages;

import lombok.extern.slf4j.Slf4j;
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

    protected WebElement waitElementClickable(By locator) {
        return driverWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitTextToBePresent(By locator, String text) {
        driverWait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    protected void waitElementRefreshed(By locator) {
        driverWait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(getElement(locator))));
    }

    protected void waitPageLoaded() {
        driverWait.until(driver -> getElements(locateFromDataTestId("overlay")).isEmpty());
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

    protected void clickCheckbox(String containerId) {
        jsClick(getElement(getCheckboxLocator(containerId)));
    }

    protected boolean isCheckboxChecked(String containerId) {
        return Boolean.parseBoolean(getElement(getCheckboxLocator(containerId)).getAttribute("value"));
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
}
