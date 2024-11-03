package helpers;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Attach {
    @Attachment(value = "{attachName}", type = "image/png")
    public static byte[] screenshotAs(String attachName, WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page source", type = "text/plain")
    public static byte[] pageSource(WebDriver driver) {
        return driver.getPageSource().getBytes(StandardCharsets.UTF_8);
    }

    @Attachment(value = "{attachName}", type = "text/plain")
    public static String attachAsText(String attachName, String message) {
        return message;
    }

    public static void browserConsoleLogs(WebDriver driver) {
        LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
        String logMessages = logs.getAll().stream()
                .map(LogEntry::getMessage)
                .collect(Collectors.joining("\n"));
        attachAsText("Browser console logs", logMessages);
    }

    @Attachment(value = "Video", type = "text/html", fileExtension = ".html")
    public static String addVideo(WebDriver driver) {
        return "<html><body><video width='100%' height='100%' controls autoplay><source src='"
                + getVideoUrl(driver)
                + "' type='video/mp4'></video></body></html>";
    }

    public static URL getVideoUrl(WebDriver driver) {
        String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
        String videoUrl = "https://selenoid.autotests.cloud/video/" + sessionId + ".mp4";
        try {
            return new URL(videoUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}