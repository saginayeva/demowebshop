package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config/credentials.properties")
public interface CredentialsConfig extends Config {
    String login();
    String password();

    @Key("baseUrl")
    @DefaultValue("https://demowebshop.tricentis.com/")
    String baseUrl();

    @Key("remoteUrl")
    String getRemoteUrl();

    @Key("browserName")
    @DefaultValue("chrome")
    String getBrowserName();

    @Key("browserSize")
    @DefaultValue("1920x1080")
    String getBrowserSize();
}
