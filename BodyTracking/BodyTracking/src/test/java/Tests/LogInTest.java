/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

/**
 *
 * @author santananas
 */
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
 * @author santananas
 */
@SpringBootTest
public class LogInTest {
    
    WebDriver driver;			
    		
    @Given("^Given Open the app")					
    public void open_the_app() throws Throwable							
    {		
       driver= new ChromeDriver();					
       driver.manage().window().maximize();			
       driver.get("http://localhost:21999/");					
    }		

    @When("The client calls login")			
    public void calls_login() throws Throwable 							
    {		
       driver.findElement(By.id("login")).click();
    }
    
    @When("^Enter the Username \"(.*)\" and Password \"(.*)\"$")			
    public void enter_the_Username_and_Password(String username,String password) throws Throwable 							
    {		
       driver.findElement(By.id("username")).sendKeys(username);					
       driver.findElement(By.id("password")).sendKeys(password);					
    }		

    @Then("^Reset the credential$")					
    public void	Reset_the_credential() throws Throwable 							
    {		
       driver.findElement(By.name("btnReset")).click();					
    }
}