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

public class QuestionUpdateStep extends LoginStepBase {
    private WebDriver driver;
    @Before
    public void setUp() throws InterruptedException {
        driver = initializeWebDriver();
    }
    @Given("there is a question the user want to update")
    public void there_is_a_question_the_user_want_to_update() {
        System.out.println("there is a question the user want to update");
        loginUser(driver);
    }
    @When("the user want to update {string} question that is belong to the user")
    public void the_user_want_to_update_question_that_is_belong_to_the_user_and(String title) {
        System.out.println("the user want to update a question that is belong to the user");
        By updateName = By.name("update " + title);
        driver.findElement(updateName).click();
    }
    @When("the user update the question with {string}")
    public void the_user_update_the_question_with_and(String title) throws InterruptedException {
        System.out.println("the user update the question");
        driver.findElement(DESCRIPTION_UPDATE_LOCATOR_ID).sendKeys(title);
        driver.findElement(UPDATE_LOCATOR_ID).click();
        Thread.sleep(TWO_SECOND_SLEEP);
        driver.navigate().to(FORUM_URL_LOCATOR);
    }
    @Then("the question should be updated with {string}")
    public void the_question_should_be_updated_with_and(String title) {
        System.out.println("the question should be updated");
        Assert.assertTrue(driver.getPageSource().contains(title));
    }
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
