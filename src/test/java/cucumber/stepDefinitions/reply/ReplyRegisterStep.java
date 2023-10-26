package cucumber.stepDefinitions.reply;

import cucumber.stepBase.LoginStepBase;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import static cucumber.config.TestConfig.*;

public class ReplyRegisterStep extends LoginStepBase {
    private WebDriver driver;
    @Before
    public void setUp() throws InterruptedException {
        driver = initializeWebDriver();
    }
    @Given("the user want to add a new reply to the second answer")
    public void the_user_want_to_add_a_new_reply_with_the_following_details() {
        System.out.println("the user want to add a new reply to the second answer");
        loginUser(driver);
        driver.navigate().to(ANSWER_URL);
    }
    @When("the user add the new reply to with the Reply1")
    public void the_user_add_the_new_reply(String description) throws InterruptedException {
        System.out.println("the user add the new reply to with the Reply1");
        driver.findElement(ANSWER_LOCATOR_CLASSNAME).sendKeys(description);

        Thread.sleep(TWO_SECOND_SLEEP);
        driver.findElement(REGISTER_LOCATOR_ID).click();
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @Then("the reply {string} should be added successfully")
    public void the_reply_should_be_added_successfully_and(String description) throws InterruptedException {
        System.out.println("the reply " + description + " should be added successfully");
        Assert.assertTrue(driver.getPageSource().contains(description));

        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
