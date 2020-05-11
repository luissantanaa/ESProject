Feature: Login
Scenario: Verification of successful login page
    Given Open the app	
    When The client calls login
    And the username and password are correct
    Then Reset the credential
