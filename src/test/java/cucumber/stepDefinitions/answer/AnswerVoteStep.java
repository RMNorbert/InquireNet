package cucumber.stepDefinitions.answer;

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

public class AnswerVoteStep extends LoginStepBase {
    private WebDriver driver;
    @Before
    public void setUp() throws InterruptedException {
        driver = initializeWebDriver();
    }
    @Given("the user want to vote on an answer in the user's question")
    public void the_user_want_to_vote_on_an_answer_in_the_user_s_question() {
        System.out.println("the user want to vote on an answer in the user's question");
        loginUser(driver);

    }
    @When("the user want to vote on an answer in the user's question from the list of answers")
    public void the_user_want_to_vote_on_an_answer_in_the_user_s_question_from_the_list_of_answers() throws InterruptedException {
        System.out.println("the user want to vote on an answer in the user's question from the list of answers");
        driver.navigate().to(QUESTION_URL);
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @When("the user click on one of the vote button on the {string} answer")//Rit
    public void the_user_click_on_one_of_the_vote_button_on_the_answer_and(String description) {
        System.out.println("the user click on one of the vote button on an answer");
        By answerToVoteName = By.name("upvote " + description);
        driver.findElement(answerToVoteName).click();
    }
    @Then("the user should see that the answer card background changes")
    public void the_user_should_see_that_the_answer_card_background_changes() throws InterruptedException {
        System.out.println("the user should see that the answer card background changes");
        By voteId = By.id("upVoted");
        Assert.assertTrue(driver.findElement(voteId).isDisplayed());
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
