@api @users @get
Feature: Get Users API
  As a user
  I want to retrieve user information
  So that I can view user details

  Background:
    Given the GoRest API is available

  @smoke @regression
  Scenario: Get all users successfully
    When I send a GET request to retrieve all users
    Then the response status code should be 200
    And the response should contain a list of users
    And each user should have valid properties

  @regression @pagination
  Scenario: Get users with pagination
    When I send a GET request to retrieve users with page 1 and per_page 5
    Then the response status code should be 200
    And the response should contain maximum 5 users
    And the response should contain pagination information

  @smoke @regression
  Scenario: Get a specific user by ID
    Given there is an existing user in the system
    When I send a GET request to retrieve the user by ID
    Then the response status code should be 200
    And the response should contain the user details
    And the user should have all required properties

  @regression @negative
  Scenario: Get a non-existent user
    When I send a GET request to retrieve user with ID 999999
    Then the response status code should be 404
    And the response should contain an error message

  @regression @search
  Scenario: Search users by name
    When I search for users with name "John"
    Then the response status code should be 200
    And the response should contain users with matching names