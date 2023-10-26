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

public class AnswerUpdateStep extends LoginStepBase {
    private WebDriver driver;
    @Before
    public void setUp() throws InterruptedException {
        driver = initializeWebDriver();
    }
    @Given("the user want to edit the user's answer for a selected question")
    public void the_user_want_to_edit_the_user_s_answer_for_a_selected_question() {
        System.out.println("the user want to edit the user's answer for a selected question");
        loginUser(driver);
    }
    @When("the user search for the answer to edit from the list of answers")
    public void the_user_search_for_the_answer_to_edit_from_the_list_of_answers() throws InterruptedException {
        System.out.println("the user search for the answer to edit from the list of answers");
        driver.navigate().to(QUESTION_URL);
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @When("the user click on the update button on the {string} answer")
    public void the_user_click_on_the_update_button_on_the_answer_and(String description) throws InterruptedException {
        System.out.println("the user click on the update button on an answer the user previously added");
        By answerUpdateName = By.name("update " + description);
        driver.findElement(answerUpdateName).click();
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @When("the user edit the content with {string} of the answer and submit it")
    public void the_user_edit_the_content_with_of_the_answer_and_submit_it_and(String description) throws InterruptedException {
        System.out.println("And the user edit the content with" + description + "of the answer and submit it");
        driver.findElement(DESCRIPTION_UPDATE_LOCATOR_ID).sendKeys(description);
        driver.findElement(UPDATE_LOCATOR_ID).click();
        Thread.sleep(TWO_SECOND_SLEEP);
        driver.navigate().to(QUESTION_URL);
    }
    @Then("the answer should be updated with the content {string}")
    public void the_answer_should_be_updated_with_the_content_and(String description) throws InterruptedException {
        System.out.println("the answer should be updated with the content" + description);
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
