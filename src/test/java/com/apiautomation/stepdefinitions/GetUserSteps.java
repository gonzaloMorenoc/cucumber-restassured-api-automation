package com.apiautomation.stepdefinitions;

import com.apiautomation.client.UserClient;
import com.apiautomation.context.TestContext;
import com.apiautomation.models.User;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;

public class GetUserSteps {
    
    private final TestContext testContext;
    private final UserClient userClient;

    public GetUserSteps(TestContext testContext) {
        this.testContext = testContext;
        this.userClient = new UserClient();
    }

    @When("I send a GET request to retrieve all users")
    public void iSendAGETRequestToRetrieveAllUsers() {
        Response response = userClient.getAllUsers();
        testContext.setResponse(response);
    }

    @When("I send a GET request to retrieve users with page {int} and per_page {int}")
    public void iSendAGETRequestToRetrieveUsersWithPageAndPerPage(int page, int perPage) {
        Response response = userClient.getAllUsers(page, perPage);
        testContext.setResponse(response);
    }

    @When("I send a GET request to retrieve the user by ID")
    public void iSendAGETRequestToRetrieveTheUserByID() {
        int userId = testContext.getUserId();
        Response response = userClient.getUserById(userId);
        testContext.setResponse(response);
    }

    @When("I send a GET request to retrieve user with ID {int}")
    public void iSendAGETRequestToRetrieveUserWithID(int userId) {
        Response response = userClient.getUserById(userId);
        testContext.setResponse(response);
    }

    @When("I search for users with name {string}")
    public void iSearchForUsersWithName(String name) {
        testContext.setSearchName(name);
        Response response = userClient.searchUsersByName(name);
        testContext.setResponse(response);
    }

    @When("I try to get the deleted user")
    public void iTryToGetTheDeletedUser() {
        int userId = testContext.getUserId();
        Response response = userClient.getUserById(userId);
        testContext.setResponse(response);
    }

    @Then("the response should contain a list of users")
    public void theResponseShouldContainAListOfUsers() {
        Response response = testContext.getResponse();
        assertNotNull(response, "Response should not be null");
        
        User[] users = response.getBody().as(User[].class);
        assertNotNull(users, "Users array should not be null");
        assertTrue(users.length >= 0, "Should return valid users array");
    }

    @Then("each user should have valid properties")
    public void eachUserShouldHaveValidProperties() {
        Response response = testContext.getResponse();
        User[] users = response.getBody().as(User[].class);
        
        for (User user : users) {
            assertNotNull(user.getId(), "User ID should not be null");
            assertNotNull(user.getName(), "User name should not be null");
            assertNotNull(user.getEmail(), "User email should not be null");
            assertNotNull(user.getGender(), "User gender should not be null");
            assertNotNull(user.getStatus(), "User status should not be null");
            assertTrue(user.getEmail().contains("@"), "Email should be valid format");
        }
    }

    @Then("the response should contain maximum {int} users")
    public void theResponseShouldContainMaximumUsers(int maxUsers) {
        Response response = testContext.getResponse();
        User[] users = response.getBody().as(User[].class);
        assertTrue(users.length <= maxUsers, 
                  "Should contain maximum " + maxUsers + " users, but got " + users.length);
    }

    @Then("the response should contain pagination information")
    public void theResponseShouldContainPaginationInformation() {
        Response response = testContext.getResponse();
        assertNotNull(response.getHeader("X-Pagination-Page"), "Should contain pagination page header");
        assertNotNull(response.getHeader("X-Pagination-Pages"), "Should contain pagination pages header");
    }

    @Then("the response should contain the user details")
    public void theResponseShouldContainTheUserDetails() {
        Response response = testContext.getResponse();
        User user = response.getBody().as(User.class);
        
        assertNotNull(user, "User should not be null");
        assertEquals(testContext.getUserId(), user.getId(), "User ID should match");
        assertNotNull(user.getName(), "User name should not be null");
        assertNotNull(user.getEmail(), "User email should not be null");
    }

    @Then("the user should have all required properties")
    public void theUserShouldHaveAllRequiredProperties() {
        Response response = testContext.getResponse();
        User user = response.getBody().as(User.class);
        
        assertNotNull(user.getId(), "User ID should not be null");
        assertNotNull(user.getName(), "User name should not be null");
        assertNotNull(user.getEmail(), "User email should not be null");
        assertNotNull(user.getGender(), "User gender should not be null");
        assertNotNull(user.getStatus(), "User status should not be null");
    }

    @Then("the response should contain users with matching names")
    public void theResponseShouldContainUsersWithMatchingNames() {
        Response response = testContext.getResponse();
        User[] users = response.getBody().as(User[].class);
        String searchName = testContext.getSearchName().toLowerCase();
        
        for (User user : users) {
            assertTrue(user.getName().toLowerCase().contains(searchName),
                      "User name should contain search term: " + searchName);
        }
    }
}