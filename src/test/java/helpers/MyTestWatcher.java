package helpers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.WebDriver;


@Slf4j
public class MyTestWatcher implements TestWatcher, AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isPresent()) {
            String testName = context.getTestMethod().get().getName();
            WebDriver driver = DriverContainer.getDriver();
            if (driver != null) {
                try {
                    Screenshot.take(testName + ".png", driver);
                    Screenshot.save(testName + ".html", driver);
                } catch (Exception e) {
                    log.error("Error while saving screenshot: {}", e.getMessage());
                }
            } else {
                log.error("Driver is null, cannot take screenshot for failed test: {}", testName);
            }
        }
    }
}
