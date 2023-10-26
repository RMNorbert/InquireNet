package cucumber.config;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestConfig {
    public final static String SELENIUM_DRIVER = "webdriver.gecko.driver";
    public final static String DRIVER_PATH = "/snap/bin/firefox.geckodriver";
    public final static int DRIVER_TIMEOUT_IN_SECOND = 30;
    public final static By USERNAME_LOCATOR_CLASSNAME = By.className("name");
    public final static By PASSWORD_LOCATOR_CLASSNAME = By.className("password");
    public final static By QUESTION_UPDATE_LOCATOR_ID = By.id("title");
    public final static By DESCRIPTION_UPDATE_LOCATOR_ID = By.id("desc");
    public final static By AI_LOCATOR_NAME = By.name("ai");
    public final static By ICON_LOCATOR_NAME = By.name("icon");
    public final static By PROFILE_LOCATOR_NAME = By.name("profile");
    public final static String FORUM_URL_LOCATOR = "forum";
    public final static By LOGIN_LOCATOR_ID = By.id("login");
    public final static By REGISTER_LOCATOR_ID = By.id("register");
    public final static By REPLY_LOCATOR_ID = By.id("reply");
    public final static By ANSWER_LOCATOR_ID = By.id("answer");
    public final static By ANSWER_LOCATOR_CLASSNAME = By.className("answer");
    public final static By QUESTION_LOCATOR_ID = By.id("question-card");
    public final static By UPDATE_LOCATOR_ID = By.id("update");
    public final static By DELETE_LOCATOR_ID = By.id("delete");

    public final static String BASE_URL = "http://localhost:5173/";
    public final static String QUESTION_URL = "http://localhost:5173/question/1";
    public final static String ANSWER_URL = "http://localhost:5173/answer/1";
    public final static int TWO_SECOND_SLEEP = 2000;

    public final static String TEST_USERNAME = "user";
    public final static String TEST_PASSWORD = "password";

    public static WebDriver initializeWebDriver(){
        String mozDevToolsSetting = "MOZ_REMOTE_SETTINGS_DEVTOOLS";
        String enableRemoteSettingsDevtoolsOption = "1";
        System.setProperty(mozDevToolsSetting, enableRemoteSettingsDevtoolsOption);
        System.setProperty(SELENIUM_DRIVER, DRIVER_PATH);
        return new FirefoxDriver();
    }
}
