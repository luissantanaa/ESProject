Feature: Record Exercise
Scenario: Verification of successful login page
    Given the user wants to record his exercise session for further analysis	
    When the user accesses the Real Time endpoint and proceeds to connect
    Then after he is done he presses to disconnect
    Then user's session is stored in the database
