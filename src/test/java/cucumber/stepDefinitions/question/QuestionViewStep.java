package cucumber.stepDefinitions.question;

import cucumber.stepBase.LoginStepBase;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static cucumber.config.TestConfig.*;

public class QuestionViewStep extends LoginStepBase {
    private WebDriver driver;
    @Before
    public void setUp() throws InterruptedException {
        driver = initializeWebDriver();
    }
    @Given("there are questions in the system")
    public void there_are_questions_in_the_system() {
        System.out.println("there are questions in the system");
        loginUser(driver);
    }
    @When("the user is on the forum page")
    public void the_user_is_on_the_forum_page() throws InterruptedException {
        System.out.println("the user is on the forum page");
        Assert.assertTrue(driver.getCurrentUrl().contains(FORUM_URL_LOCATOR));
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @Then("the user should receive a list of question information")
    public void the_user_should_receive_a_list_of_question_information() throws InterruptedException {
        System.out.println("the user should receive a list of question information");
        Assert.assertTrue(driver.findElement(QUESTION_LOCATOR_ID).isDisplayed());
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @Given("there is a question the user want to interact with")
    public void there_is_a_question_the_user_want_to_interact_with() throws InterruptedException {
        System.out.println("there is a question the user want to interact with");
        Assert.assertTrue(driver.findElement(QUESTION_LOCATOR_ID).isDisplayed());
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @When("the user requests the {string} question by clicking on it")
    public void the_user_requests_the_question_by_clicking_on_it(String question)
                                                      throws InterruptedException {
        System.out.println("the user requests the question by clicking on it");
        driver.findElement(By.name(question)).click();
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @Then("the user should get the question's information by navigate to {string}")
    public void the_user_should_get_the_question_s_information_by_navigate_to(String url)
                                                    throws InterruptedException {
        System.out.println("the user should receive the question's information");
        Assert.assertTrue(driver.getCurrentUrl().contains(url));
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
