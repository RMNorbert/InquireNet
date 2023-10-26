package cucumber.stepDefinitions.question;

import cucumber.stepBase.LoginStepBase;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import static cucumber.config.TestConfig.*;

public class QuestionDeleteStep extends LoginStepBase {
    private WebDriver driver;
    @Before
    public void setUp() throws InterruptedException {
        driver = initializeWebDriver();
    }
    @Given("the user want to delete a question that belongs to the user")
    public void the_user_want_to_delete_a_question_that_belongs_to_the_user() {
        System.out.println("the user want to delete a question that belongs to the user");
        loginUser(driver);
        driver.getCurrentUrl().contains(FORUM_URL_LOCATOR);
    }
    @When("the user delete the {string} question")
    public void the_user_delete_the_question_and(String title) {
        By deleteName = By.name("delete " + title);
        System.out.println("the user delete the question");
        driver.findElement(deleteName).click();
    }
    @Then("the {string} question and its associated answers should be deleted")
    public void the_question_and_its_associated_answers_should_be_deleted_and(String title) throws InterruptedException {
        System.out.println("the question and its associated answers should be deleted");
        try {
            System.out.println("the reply should be deleted");
            Assert.assertFalse(driver.getPageSource().contains(title));
        } catch (NoSuchElementException e) {
            Thread.sleep(TWO_SECOND_SLEEP);
            driver.close();
            driver.quit();
            System.out.println("Element not found: " + e.getMessage());
        }
    }
}
