package cucumber.stepDefinitions.reply;

import cucumber.stepBase.LoginStepBase;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static cucumber.config.TestConfig.*;

public class ReplyDeleteStep extends LoginStepBase {
    private WebDriver driver;
    @Before
    public void setUp() throws InterruptedException {
        driver = initializeWebDriver();
    }
    @Given("there is a reply of the user that the user want to delete")
    public void there_is_a_reply_of_the_user_that_the_user_want_to_delete() {
        System.out.println("there is a reply of the user that the user want to delete");
        loginUser(driver);
        driver.navigate().to(ANSWER_URL);
    }
    @When("the user delete the reply the user's reply")
    public void the_user_delete_the_reply() throws InterruptedException {
        System.out.println("the user delete the reply the user's reply");

        driver.findElement(DELETE_LOCATOR_ID).click();
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @Then("the reply should be deleted")
    public void the_reply_should_be_deleted() throws InterruptedException {
        try {
            System.out.println("the reply should be deleted");
            driver.findElement(DELETE_LOCATOR_ID);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            Thread.sleep(TWO_SECOND_SLEEP);
            driver.close();
            driver.quit();
            driver = null;
            System.out.println("Element not found: " + e.getMessage());
        }
    }
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
