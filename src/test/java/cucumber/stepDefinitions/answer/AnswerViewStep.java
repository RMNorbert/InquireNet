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

public class AnswerViewStep extends LoginStepBase {
    private WebDriver driver;
    @Before
    public void setUp() throws InterruptedException {
        driver = initializeWebDriver();
    }
    @Given("the user want to view the answers of a question")
    public void the_user_want_to_view_the_answers_of_a_question() {
        System.out.println("the user want to view the answers of a question");
        loginUser(driver);
    }
    @When("the user click on the {string} question")
    public void the_user_click_on_the_question_and(String title) {
        System.out.println("the user click on the" + title + "question");
        By questionName = By.name(title);
        driver.findElement(questionName).click();
    }
    @Then("the user should see a list of answers")
    public void the_user_should_see_a_list_of_answers() throws InterruptedException {
        System.out.println("the user should see a list of answers");
        By answerListName = By.name("answer-list");
        Assert.assertTrue(driver.findElement(answerListName).isDisplayed());
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
