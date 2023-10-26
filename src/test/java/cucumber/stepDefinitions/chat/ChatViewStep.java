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
import org.openqa.selenium.firefox.FirefoxDriver;

import static cucumber.config.TestConfig.*;
import static cucumber.config.TestConfig.TWO_SECOND_SLEEP;

public class ChatViewStep extends LoginStepBase {
    private WebDriver driver;
    @Before
    public void setUp() throws InterruptedException {
        System.setProperty(SELENIUM_DRIVER, DRIVER_PATH);
        driver = new FirefoxDriver();
    }
    @Given("the user want to get all previous chats that belongs to the user")
    public void the_user_want_to_get_all_previous_chats_that_belongs_to_the_user() {
        System.out.println("the user want to get all previous chats that belongs to the user");
        loginUser(driver);
    }
    @When("the user clicks on the Chat with AI button")
    public void the_user_clicks_on_the_Chat_with_AI_button() throws InterruptedException {
        System.out.println("the user clicks on the Chat with AI button");
        driver.findElement(AI_LOCATOR_NAME).click();
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @Then("the user should receive a list of chat information")
    public void the_user_should_receive_a_list_of_chat_information() throws InterruptedException {
        System.out.println("the chat with the title should be deleted");
        By chatName = By.name("previous-chat");
        Assert.assertTrue(driver.findElement(chatName).isDisplayed());
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
