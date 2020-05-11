Feature: Login
Scenario: Login
	Given Open the app	
	When the client calls /login
	And the username and password are correct
	Then the client can access his information
