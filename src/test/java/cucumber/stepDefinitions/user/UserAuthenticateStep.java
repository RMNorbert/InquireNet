package cucumber.stepDefinitions.user;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;
import static cucumber.config.TestConfig.*;


public class UserAuthenticateStep {
    private WebDriver driver;
    @Before
    public void setUp() {
        driver = initializeWebDriver();
    }
    @Given("the user want to authenticate with an invalid password:")
    public void the_user_want_to_authenticate_with_an_invalid_password() throws InterruptedException {
        System.out.println("the user want to authenticate with an invalid password:");
        driver.manage().timeouts().implicitlyWait(DRIVER_TIMEOUT_IN_SECOND, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(DRIVER_TIMEOUT_IN_SECOND, TimeUnit.SECONDS);
        Thread.sleep(TWO_SECOND_SLEEP);
        driver.navigate().to(BASE_URL);
    }
    @When("the user attempts to authenticate with invalid {string} and {string}")
    public void the_user_attempts_to_authenticate_with_invalid_and(String string, String string2)
                                                                throws InterruptedException {
        System.out.println("the user attempts to authenticate with invalid username and password");
        driver.findElement(USERNAME_LOCATOR_CLASSNAME).sendKeys(string);
        driver.findElement(PASSWORD_LOCATOR_CLASSNAME).sendKeys(string2);

        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @Then("the authentication should fail with the invalid password")
    public void the_authentication_should_fail_with_the_invalid_password() throws InterruptedException {
        driver.findElement(LOGIN_LOCATOR_ID).click();
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @Then("the user should stay on the login page")
    public void the_user_should_stay_on_the_login_page() throws InterruptedException {
        System.out.println("the user should navigated to the forum page");
        Assert.assertTrue(driver.findElement(LOGIN_LOCATOR_ID).isDisplayed());
        Thread.sleep(TWO_SECOND_SLEEP);
    }

    @Given("the user is on the login page and want to login")
    public void the_user_is_on_the_login_page_and_want_to_login() {
        System.out.println("the user is on the login page and want to login");
        driver.navigate().to(BASE_URL);
    }
    @When("the user enters {string} and {string}")
    public void the_user_enters_user_and_password_and(String string, String string2)
                                                      throws InterruptedException {
        System.out.println("the user attempts to authenticate");
        driver.findElement(USERNAME_LOCATOR_CLASSNAME).sendKeys(string);
        driver.findElement(PASSWORD_LOCATOR_CLASSNAME).sendKeys(string2);

        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @When("user click on login")
    public void user_click_on_login() {
        driver.findElement(By.id("login")).click();
    }
    @Then("the user should navigated to the forum page")
    public void the_user_should_navigated_to_the_forum_page() throws InterruptedException {
        System.out.println("the user should navigated to the forum page");
        Assert.assertTrue(driver.getCurrentUrl().contains(FORUM_URL_LOCATOR));

        Thread.sleep(TWO_SECOND_SLEEP);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
