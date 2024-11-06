package helpers;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;


@Slf4j
public class Screenshot {
    public static final Path path = Paths.get("screenshots");

    public static void take(String fileName, WebDriver driver) {
        Path file = path.resolve(fileName);
        log.info("*** SCREENSHOT => file:///" + file.toAbsolutePath().toString().replace("\\", "/") + " ***");
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, file.toFile());
        } catch (IOException e) {
            log.error("Failed to save screenshot: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error during taking screenshot: {}", e.getMessage());
        }
    }

    public static void save(String fileName, WebDriver driver) {
        Path file = path.resolve(fileName);
        log.info("*** HTML => file:///{} ***", file.toAbsolutePath().toString().replace("\\", "/"));
        try (PrintWriter out = new PrintWriter(new FileWriter(file.toFile()))) {
            out.println(driver.getPageSource());
        } catch (IOException e) {
            log.error("Failed to save page source: {}", e.getMessage());
        }
    }

    public static void clearScreenshots() {
        try (Stream<Path> paths = Files.walk(path)) {
            paths.filter(Files::isRegularFile)
                    .filter(file -> !file.getFileName().toString().equals(".gitignore"))
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            log.error("Failed to clear screenshots: {}", e.getMessage());
        }
    }
}
