package cucumber.stepDefinitions.user;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;
import static cucumber.config.TestConfig.*;

public class UserRegisterStep {
    private WebDriver driver;
    @Before
    public void setUp() throws InterruptedException {
        driver = initializeWebDriver();
    }
    @Given("the user is on the register page and want to register with an existing username:")
    public void the_user_is_on_the_register_page_and_want_to_register_with_an_existing_username() {
        System.out.println("the user is on the register page and want to register with an existing username:");

        driver.manage().timeouts().implicitlyWait(DRIVER_TIMEOUT_IN_SECOND, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(DRIVER_TIMEOUT_IN_SECOND, TimeUnit.SECONDS);

        driver.navigate().to(BASE_URL);
        driver.findElement(REGISTER_LOCATOR_ID).click();
    }
    @When("the user try to registers with the existing {string} and {string}")
    public void the_user_try_to_registers_with_the_existing_user_and_password_and(String username,
                                                                              String password)
                                                                              throws InterruptedException {
        System.out.println("the user try to registers with the existing user adn password");
        driver.findElement(USERNAME_LOCATOR_CLASSNAME).sendKeys(username);
        driver.findElement(PASSWORD_LOCATOR_CLASSNAME).sendKeys(password);

        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @Then("the registration should fail")
    public void the_registration_should_fail() throws InterruptedException {
        driver.findElement(REGISTER_LOCATOR_ID).click();
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @Then("the user should stay on the register page")
    public void the_user_should_stay_on_the_register_page() throws InterruptedException {
        System.out.println("the user should navigated to the login page");
        Assert.assertTrue(driver.findElement(REGISTER_LOCATOR_ID).isDisplayed());

        Thread.sleep(TWO_SECOND_SLEEP);
    }

    @Given("a user is on the register page and want to register:")
    public void a_user_is_on_the_register_page_and_want_to_register() throws InterruptedException {
        driver.navigate().to(BASE_URL);
        Thread.sleep(TWO_SECOND_SLEEP);
        driver.findElement(REGISTER_LOCATOR_ID).click();
    }
    @When("the user try to register by entering {string} and {string}")
    public void the_user_try_to_register_by_entering_username_and_password(String username,
                                                                           String password)
            throws InterruptedException {
        System.out.println("the user try to register by entering username and password");
        driver.findElement(USERNAME_LOCATOR_CLASSNAME).sendKeys(username);
        driver.findElement(PASSWORD_LOCATOR_CLASSNAME).sendKeys(password);

        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @Then("the registration should be successful")
    public void the_registration_should_be_successful() throws InterruptedException {
        driver.findElement(REGISTER_LOCATOR_ID).click();
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @Then("the user should navigated to the login page")
    public void the_user_should_navigated_to_the_login_page() throws InterruptedException {
        System.out.println("the user should navigated to the login page");
        Assert.assertTrue(driver.findElement(LOGIN_LOCATOR_ID).isDisplayed());

        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
