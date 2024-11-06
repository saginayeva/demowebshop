package helpers;

import io.qameta.allure.Attachment;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import static org.openqa.selenium.logging.LogType.BROWSER;

@Slf4j
public class Attach {
    @Attachment(value = "{attachName}", type = "image/png")
    public static void screenshotAs(String attachName) {
        ((TakesScreenshot) DriverContainer.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page source", type = "text/plain")
    public static void pageSource() {
        DriverContainer.getDriver().getPageSource();
    }

    @Attachment(value = "{attachName}", type = "text/plain")
    public static void attachAsText(String attachName, String message) {
    }

    public static void browserConsoleLogs() {
        LogEntries logEntries = DriverContainer.getDriver().manage().logs().get(BROWSER);
        StringBuilder string4LogEntries = new StringBuilder();
        for (LogEntry entry : logEntries) {
            string4LogEntries.append(new Date(entry.getTimestamp()));
            string4LogEntries.append(" ");
            string4LogEntries.append(entry.getLevel());
            string4LogEntries.append(" ");
            string4LogEntries.append(entry.getMessage());
            string4LogEntries.append("\n");
        }

        attachAsText("Browser console logs",
                String.join("\n", string4LogEntries.toString())
        );
    }

    @Attachment(value = "Video", type = "text/html", fileExtension = ".html")
    public static void addVideo() {
        getVideoUrl();
    }

    public static void getVideoUrl() {
        String videoUrl = "https://selenoid.autotests.cloud/video/" + getSessionId() + ".mp4";
        try {
            new URL(videoUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static String getSessionId() {
        return ((RemoteWebDriver) DriverContainer.getDriver()).getSessionId().toString();
    }

    public static void logException(Exception e) {
        log.info(e.getMessage());
        e.printStackTrace();
    }
}
