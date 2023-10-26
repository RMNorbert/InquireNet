package cucumber.stepDefinitions.question;

import cucumber.stepBase.LoginStepBase;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import static cucumber.config.TestConfig.*;
import static cucumber.config.TestConfig.REGISTER_LOCATOR_ID;

public class QuestionRegisterStep extends LoginStepBase {
    private WebDriver driver;
    @Before
    public void setUp() throws InterruptedException {
        driver = initializeWebDriver();
    }
    @Given("the user want to add a new question")
    public void the_user_want_to_add_a_new_question() {
        System.out.println("the user want to add a new question");
        loginUser(driver);
        driver.getCurrentUrl().contains(FORUM_URL_LOCATOR);
    }
    @When("the user use the create new question button")
    public void the_user_use_the_create_new_question_button() {
        driver.manage().timeouts().pageLoadTimeout(DRIVER_TIMEOUT_IN_SECOND, TimeUnit.SECONDS);
        System.out.println("the user use the create new question button");

        driver.findElement(REGISTER_LOCATOR_ID).click();
    }

    @When("the user add the new question with {string} and {string}")
    public void the_user_add_the_new_question_with_and(String title, String content) throws InterruptedException {
        System.out.println("the user use the create new question button");
        driver.findElement(QUESTION_UPDATE_LOCATOR_ID).sendKeys(title);
        driver.findElement(DESCRIPTION_UPDATE_LOCATOR_ID).sendKeys(content);

        Thread.sleep(TWO_SECOND_SLEEP);
        driver.findElement(REGISTER_LOCATOR_ID).click();
    }
    @Then("the question with {string} should be added successfully")
    public void the_question_with_should_be_added_successfully_and(String title) throws InterruptedException {
        System.out.println("the question should be added successfully");
        Assert.assertTrue(driver.getPageSource().contains(title));
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
