package cucumber.stepBase;

import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import static cucumber.config.TestConfig.*;

public class LoginStepBase {
    protected void loginUser(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(DRIVER_TIMEOUT_IN_SECOND, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(DRIVER_TIMEOUT_IN_SECOND, TimeUnit.SECONDS);

        driver.navigate().to(BASE_URL);
        driver.findElement(USERNAME_LOCATOR_CLASSNAME).sendKeys(TEST_USERNAME);
        driver.findElement(PASSWORD_LOCATOR_CLASSNAME).sendKeys(TEST_PASSWORD);
        driver.findElement(LOGIN_LOCATOR_ID).click();
    }
}
