package cucumber.stepDefinitions.reply;

import cucumber.stepBase.LoginStepBase;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static cucumber.config.TestConfig.*;

public class ReplyViewStep extends LoginStepBase {
    private WebDriver driver;
    @Before
    public void setUp() throws InterruptedException {
        driver = initializeWebDriver();
    }
    @Given("there is a reply the user want to interact with")
    public void there_is_a_reply_the_user_want_to_interact_with() {
        System.out.println("there is a reply the user want to interact with");
        loginUser(driver);
        driver.navigate().to(QUESTION_URL);
    }
    @When("the user requests the reply by clicking on it")
    public void the_user_requests_the_reply_by_clicking_on_it() throws InterruptedException {
        driver.findElement(ANSWER_LOCATOR_ID).click();
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @Then("the user should receive the reply's information")
    public void the_user_should_receive_the_reply_s_information() throws InterruptedException {
        System.out.println("the user should receive the reply's information");
        driver.findElement(REPLY_LOCATOR_ID).isDisplayed();

        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
