package cucumber.stepDefinitions.answer;

import cucumber.stepBase.LoginStepBase;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import static cucumber.config.TestConfig.*;

public class AnswerDeleteStep extends LoginStepBase {
    private WebDriver driver;
    @Before
    public void setUp() throws InterruptedException {
        driver = initializeWebDriver();
    }
    @Given("the user want to delete the user's answer for the selected question")
    public void the_user_want_to_delete_the_user_s_answer_for_the_selected_question() throws InterruptedException {
        System.out.println("the user want to delete the user's answer for the selected question");
        loginUser(driver);
        driver.navigate().to(QUESTION_URL);
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @When("the user click on the delete button on the {string} answer")
    public void the_user_click_on_the_delete_button_on_the_answer_and(String description) {
        System.out.println("the user click on the delete button on the" + description + " answer");
        By answerDeleteName = By.name("delete-"+ description);
        driver.findElement(answerDeleteName).click();
    }
    @Then("the {string} answer should be removed from the list")
    public void the_answer_should_be_removed_from_the_list_and(String description) throws InterruptedException {
        System.out.println("the " + description + " answer should be removed from the list");
        try {
            Assert.assertFalse(driver.getPageSource().contains(description));
        } catch (NoSuchElementException e) {
                Thread.sleep(TWO_SECOND_SLEEP);
                driver.close();
                driver.quit();
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
