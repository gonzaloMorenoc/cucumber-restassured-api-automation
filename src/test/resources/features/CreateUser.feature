@api @users @post
Feature: Create User API
  As a user
  I want to create new users
  So that I can add users to the system

  Background:
    Given the GoRest API is available

  @smoke @regression
  Scenario: Create a user successfully
    Given I have valid user data
    When I send a POST request to create the user
    Then the response status code should be 201
    And the response should contain the created user details
    And the user should have a generated ID

  @regression @data-driven
  Scenario: Create a user with specific data
    Given I have user data with the following details:
      | name         | John Doe              |
      | email        | john.doe@testmail.com |
      | gender       | male                  |
      | status       | active                |
    When I send a POST request to create the user
    Then the response status code should be 201
    And the response should contain the user with name "John Doe"
    And the response should contain the user with email "john.doe@testmail.com"

  @regression @negative @validation
  Scenario: Create a user with missing required fields
    Given I have user data with missing name
    When I send a POST request to create the user
    Then the response status code should be 422
    And the response should contain validation errors

  @regression @negative @validation
  Scenario: Create a user with invalid email format
    Given I have user data with invalid email "invalid-email"
    When I send a POST request to create the user
    Then the response status code should be 422
    And the response should contain email validation error

  @regression @negative @validation
  Scenario: Create a user with duplicate email
    Given there is an existing user with email "duplicate@testmail.com"
    And I have user data with email "duplicate@testmail.com"
    When I send a POST request to create the user
    Then the response status code should be 422
    And the response should contain duplicate email error

  @smoke @negative @auth
  Scenario: Create a user without authentication
    Given I have valid user data
    When I send a POST request without authentication token
    Then the response status code should be 401
    And the response should contain authentication error