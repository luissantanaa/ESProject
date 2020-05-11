package Tests;

import org.springframework.boot.test.context.SpringBootTest;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.openqa.selenium.By;		
import org.openqa.selenium.WebDriver;	
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author joao
 */
@SpringBootTest
public class RecordExercise {
    
    private WebDriver driver;
    
    @Given("^the user wants to record his exercise session for further analysis$")
    public void record_the_exercise_session() throws Throwable {
        
        System.out.println("Given");
    }

    @When("^the user accesses the \"Real Time\" endpoint and proceeds to connect$")
    public void connects_to_realtime_endpoint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("When");
    }

    @And("^after he is done he presses to disconnect$")
    public void disconnect() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("And");
    }

    @Then("^user's session is stored in the database$")
    public void store_session_in_database() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("Then");
    }

}
