package helpers;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebDriver;

public class DriverContainer {

    @Getter
    @Setter
    private static WebDriver driver = null;

}
