Feature: Login
Scenario: Verification of successful login page

Given Open the app	
When the client calls /login
And the username and password are correct
Then the client can access his information
