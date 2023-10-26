package cucumber.stepDefinitions.chat;

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

public class ChatDeleteStep extends LoginStepBase {
    private WebDriver driver;
    @Before
    public void setUp() throws InterruptedException {
        driver = initializeWebDriver();
    }
    @Given("the user want to delete a chat")
    public void the_user_want_to_delete_a_chat() throws InterruptedException {
        System.out.println("the user want to delete the Title1 chat");
        loginUser(driver);
        driver.findElement(ICON_LOCATOR_NAME).click();
        Thread.sleep(TWO_SECOND_SLEEP);
        driver.findElement(PROFILE_LOCATOR_NAME).click();
    }
    @When("the user delete the {string} chat")
    public void the_user_delete_the_chat_and(String title) throws InterruptedException {
        System.out.println("the user delete the question");
        By deleteName = By.name(title);
        driver.findElement(deleteName).click();
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @Then("the chat with the title {string} should be deleted")
    public void the_chat_with_the_title_should_be_deleted_and(String title) throws InterruptedException {
        System.out.println("the chat with the title should be deleted");
        try {
            System.out.println("the chat should be deleted");
            Assert.assertFalse(driver.getPageSource().contains(title));
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
