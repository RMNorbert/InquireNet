package cucumber.stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import static cucumber.config.TestConfig.*;

public class GoogleSearchSteps {
    private WebDriver driver;
    private final By denyButtonId = By.id("W0wltc");
    private final By searchbarName = By.name("q");
    @Before
    public void setUp() {
        driver = initializeWebDriver();
    }

    @Given("user is on google search page")
    public void user_is_on_google_search_page() throws InterruptedException {
        System.out.println("user is on google search page");
        String googleUrl = "https://google.com";
        driver.manage().timeouts().implicitlyWait(DRIVER_TIMEOUT_IN_SECOND, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(DRIVER_TIMEOUT_IN_SECOND, TimeUnit.SECONDS);

        Thread.sleep(TWO_SECOND_SLEEP);
        driver.navigate().to(googleUrl);
    }
    @When("user enters a text in search box")
    public void user_enters_a_text_in_search_box() throws InterruptedException {
        System.out.println("user enters a text in search box");
        driver.findElement(denyButtonId).click();
        Thread.sleep(TWO_SECOND_SLEEP);
        driver.findElement(searchbarName).sendKeys("Automation Step by Step");
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @When("hits enter")
    public void hits_enter() throws InterruptedException {
        System.out.println("hits enter");
        driver.findElement(searchbarName).sendKeys(Keys.ENTER);
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @Then("user is navigated to search results")
    public void user_is_navigated_to_search_results() throws InterruptedException {
        System.out.println("user is navigated to search results");
        String searchedText = "Online Courses";
        Assert.assertTrue(driver.getPageSource().contains(searchedText));
        Thread.sleep(TWO_SECOND_SLEEP);
    }
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
