Feature: Team management
  As a user of the system
  user want to manage teams
  So that user can add, update, search and delete teams

  
  Scenario: Add a new team
    Given user have a team JSON
    When user send a POST request to "/team/add"
    Then the response status should be 200
    And the response should contain the team details

 
