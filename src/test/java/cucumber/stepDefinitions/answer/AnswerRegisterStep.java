package cucumber.stepDefinitions.answer;

import cucumber.stepBase.LoginStepBase;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import static cucumber.config.TestConfig.*;

public class AnswerRegisterStep extends LoginStepBase {
    private WebDriver driver;
    @Before
    public void setUp() throws InterruptedException {
        driver = initializeWebDriver();
    }
    @Given("the user want to answer a selected question")
    public void the_user_want_to_answer_a_selected_question() throws InterruptedException {
        System.out.println("the user want to answer a selected question");
        loginUser(driver);
        driver.navigate().to(QUESTION_URL);
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @When("the user provide the {string} answer to the question")
    public void the_user_provide_the_answer_to_the_question_and(String description) {
        System.out.println("the user provide the " + description + "answer to the question");
        driver.findElement(ANSWER_LOCATOR_CLASSNAME).sendKeys(description);
    }
    @When("the user submit the answer by using the Reply button")
    public void the_user_submit_the_answer_by_using_the_Reply_button() throws InterruptedException {
        System.out.println("the user submit the answer by using the Reply button");
        driver.findElement(REPLY_LOCATOR_ID).click();
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @Then("{string} should be added to the list of answers")
    public void should_be_added_to_the_list_of_answers_and(String description) throws InterruptedException {
        System.out.println(description + "should be added to the list of answers");
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
