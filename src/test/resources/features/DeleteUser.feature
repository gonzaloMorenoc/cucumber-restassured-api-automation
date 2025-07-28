@api @users @delete
Feature: Delete User API
  As a user
  I want to delete users
  So that I can remove users from the system

  Background:
    Given the GoRest API is available

  @smoke @regression
  Scenario: Delete an existing user successfully
    Given I create a new user for deletion
    When I send a DELETE request to remove the user
    Then the response status code should be 204
    And the response body should be empty
    When I try to get the deleted user
    Then the response status code should be 404

  @regression @negative
  Scenario: Delete a non-existent user
    When I send a DELETE request to remove user with ID 999999
    Then the response status code should be 404
    And the response should contain an error message

  @smoke @negative @auth
  Scenario: Delete a user without authentication
    Given there is an existing user in the system
    When I send a DELETE request without authentication token
    Then the response status code should be 401
    And the response should contain authentication error

  @regression @bulk
  Scenario: Delete multiple users sequentially
    Given I create 3 new users for deletion
    When I delete each user one by one
    Then each deletion should return status code 204
    And all users should be successfully removed

  @regression @negative @idempotency
  Scenario: Attempt to delete the same user twice
    Given I create a new user for deletion
    When I send a DELETE request to remove the user
    Then the response status code should be 204
    When I send another DELETE request for the same user
    Then the response status code should be 404