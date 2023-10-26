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

public class ReplyUpdateStep extends LoginStepBase {
    private WebDriver driver;
    @Before
    public void setUp() throws InterruptedException {
        driver = initializeWebDriver();
    }
    @Given("there is a reply of the user that the user want to update")
    public void there_is_a_reply_of_the_user_that_the_user_want_to_update() {
        System.out.println("there is a reply of the user that the user want to update");
        loginUser(driver);
        driver.navigate().to(ANSWER_URL);
    }
    @When("the user want to update the user's reply")
    public void the_user_want_to_update_the_users_reply() throws InterruptedException {
        System.out.println("the user want to update the user's reply");
        driver.findElement(UPDATE_LOCATOR_ID).click();
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @When("the user is the owner of the reply")
    public void the_user_is_the_owner_of_the_reply() {
        System.out.println("the user is the owner of the reply");
        driver.findElement(DESCRIPTION_UPDATE_LOCATOR_ID).isDisplayed();
    }
    @When("the user update the reply with updateText")
    public void the_user_update_the_reply(String text) {
        System.out.println("the user update the reply with updateText");
        driver.findElement(DESCRIPTION_UPDATE_LOCATOR_ID).sendKeys(text);
    }
    @Then("the reply should be updated with the text updateText")
    public void the_reply_should_be_updated(String text) throws InterruptedException {
        System.out.println("the reply should be updated with the text updateText");
        System.out.println("the user should navigated back to the answer");
        Assert.assertTrue(driver.getPageSource().contains(text));

        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
