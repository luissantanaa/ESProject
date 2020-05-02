/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StepDefinitions;

/**
 *
 * @author santananas
 */
import org.springframework.boot.test.context.SpringBootTest;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 *
 * @author santananas
 */
@SpringBootTest
public class Steps {
	
	@Given("^Open the app$")
	public void open_the_app() throws Throwable {
		System.out.println("Given");
	}

	@When("^the client calls /login$")
	public void the_client_calls_login() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("When");
	}

	@When("^the username and password are correct$")
	public void the_username_and_password_are_correct() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("When");
	}

	@Then("^the client can access his information$")
	public void the_client_can_access_his_information() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Then");
	}
}