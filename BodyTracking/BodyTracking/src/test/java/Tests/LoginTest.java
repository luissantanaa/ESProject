package Tests;

import java.lang.*;
import cucumber.api.CucumberOptions;
import org.springframework.boot.test.context.SpringBootTest;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

/**
 *
 * @author santananas
 */
@SpringBootTest
public class LoginTest {

    WebDriver driver;

    @Given("^Open the app$")
    public void open_the_app() throws Throwable {
        System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver");

        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.get("http://localhost:21999/");
        System.out.println("App opened!");
    }

    @When("^The client calls login$")
    public void calls_login() throws Throwable {
        driver.findElement(By.id("login")).click();
    }

    @And("^the username and password are correct$")
    public void enter_the_Username_and_Password(String username, String password) throws Throwable {
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
    }

    @Then("^Reset the credential$")
    public void Reset_the_credential() throws Throwable {
        driver.findElement(By.name("btnReset")).click();
    }
}
