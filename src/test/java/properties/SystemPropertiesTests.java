package properties;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class SystemPropertiesTests {

    @Test
    void systemPropertiesTest() {
        String browser = System.getProperty("browser", "defaultBrowser");
        System.out.println(browser);
    }

    @Test
    void systemPropertiesSetAndGetTest() {
        System.setProperty("browser", "chrome");
        String browser = System.getProperty("browser");
        System.out.println(browser); // chrome
    }

    @Test
    @Tag("property")
    void systemPropertiesWithDefaultTest() {
        String browser = System.getProperty("browser", "mozilla");
        System.out.println(browser);
    }

    @Test
    @Tag("hello")
    void greetingWithSystemProperty() {
        String name = System.getProperty("name", "default");
        String message = String.format("Hello, %s!", name);
        System.out.println(message);
    }

//    public class OwnerTests {
//        CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);
//
//        @Test
//        @Tag("owner")
//        void loginTest() {
//            String login = config.login();
//            String password = config.password();
//        }
//    }
}
