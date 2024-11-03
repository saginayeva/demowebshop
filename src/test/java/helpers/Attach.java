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
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.openqa.selenium.logging.LogType.BROWSER;

@Slf4j
public class Attach {
    @Attachment(value = "{attachName}", type = "image/png")
    public static byte[] screenshotAs(String attachName) {
        return ((TakesScreenshot) DriverContainer.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page source", type = "text/plain")
    public static byte[] pageSource() {
        return DriverContainer.getDriver().getPageSource().getBytes(StandardCharsets.UTF_8);
    }

    @Attachment(value = "{attachName}", type = "text/plain")
    public static String attachAsText(String attachName, String message) {
        return message;
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
    public static String addVideo() {
        return "<html><body><video width='100%' height='100%' controls autoplay><source src='"
                + getVideoUrl()
                + "' type='video/mp4'></video></body></html>";
    }

    public static URL getVideoUrl() {
        String videoUrl = "https://selenoid.autotests.cloud/video/" + getSessionId() + ".mp4";
        try {
            return new URL(videoUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSessionId() {
        return ((RemoteWebDriver) DriverContainer.getDriver()).getSessionId().toString();
    }

    public static void logException(Exception e) {
        log.info(e.getMessage());
        e.printStackTrace();
    }

}
