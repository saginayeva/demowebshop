package tests.base;

import config.CredentialsConfig;
import helpers.CustomAllureListener;
import helpers.DriverContainer;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class ApiTestBase {

    private static final CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);
    protected String login = config.login();
    protected String password = config.password();

    @BeforeAll
    static void setUpAllureListener() {
        RestAssured.filters(CustomAllureListener.withCustomTemplates());
    }

    @BeforeEach
    void initRestAssured() {
        RestAssured.baseURI = ConfigFactory.create(CredentialsConfig.class).baseUrl();
    }

    @AfterEach
    void tearDown() {
        DriverContainer.closeDriver();
    }
}
