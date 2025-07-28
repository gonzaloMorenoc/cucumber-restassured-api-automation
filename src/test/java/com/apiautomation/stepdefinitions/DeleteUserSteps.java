package com.apiautomation.stepdefinitions;

import com.apiautomation.client.BaseClient;
import com.apiautomation.client.UserClient;
import com.apiautomation.context.TestContext;
import com.apiautomation.models.User;
import com.apiautomation.utils.TestDataGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class DeleteUserSteps {
    
    private final TestContext testContext;
    private final UserClient userClient;
    private List<Integer> createdUserIds = new ArrayList<>();

    public DeleteUserSteps(TestContext testContext) {
        this.testContext = testContext;
        this.userClient = new UserClient();
    }

    @Given("I create a new user for deletion")
    public void iCreateANewUserForDeletion() {
        User newUser = TestDataGenerator.generateRandomUser();
        Response createResponse = userClient.createUser(newUser);
        assertEquals(201, createResponse.getStatusCode(), "User should be created successfully");
        
        User createdUser = createResponse.getBody().as(User.class);
        testContext.setUserId(createdUser.getId());
        testContext.setUser(createdUser);
    }

    @Given("I create {int} new users for deletion")
    public void iCreateNewUsersForDeletion(int count) {
        createdUserIds.clear();
        
        for (int i = 0; i < count; i++) {
            User newUser = TestDataGenerator.generateRandomUser();
            Response createResponse = userClient.createUser(newUser);
            assertEquals(201, createResponse.getStatusCode(), 
                        "User " + (i + 1) + " should be created successfully");
            
            User createdUser = createResponse.getBody().as(User.class);
            createdUserIds.add(createdUser.getId());
        }
    }

    @When("I send a DELETE request to remove the user")
    public void iSendADELETERequestToRemoveTheUser() {
        int userId = testContext.getUserId();
        Response response = userClient.deleteUser(userId);
        testContext.setResponse(response);
    }

    @When("I send a DELETE request to remove user with ID {int}")
    public void iSendADELETERequestToRemoveUserWithID(int userId) {
        Response response = userClient.deleteUser(userId);
        testContext.setResponse(response);
    }

    @When("I send a DELETE request without authentication token")
    public void iSendADELETERequestWithoutAuthenticationToken() {
        int userId = testContext.getUserId();
        Response response = given()
                .spec(BaseClient.getRequestSpecWithoutAuth())
                .when()
                .delete("/users/{id}", userId);
        testContext.setResponse(response);
    }

    @When("I delete each user one by one")
    public void iDeleteEachUserOneByOne() {
        for (int userId : createdUserIds) {
            Response response = userClient.deleteUser(userId);
            assertEquals(204, response.getStatusCode(), 
                        "User with ID " + userId + " should be deleted successfully");
        }
        
        if (!createdUserIds.isEmpty()) {
            testContext.setResponse(userClient.deleteUser(createdUserIds.get(0)));
        }
    }

    @When("I send another DELETE request for the same user")
    public void iSendAnotherDELETERequestForTheSameUser() {
        int userId = testContext.getUserId();
        Response response = userClient.deleteUser(userId);
        testContext.setResponse(response);
    }

    @Then("each deletion should return status code {int}")
    public void eachDeletionShouldReturnStatusCode(int expectedStatusCode) {
        for (int userId : createdUserIds) {
            Response response = userClient.deleteUser(userId);
            assertEquals(expectedStatusCode, response.getStatusCode(),
                        "Deletion of user " + userId + " should return status " + expectedStatusCode);
        }
    }

    @Then("all users should be successfully removed")
    public void allUsersShouldBeSuccessfullyRemoved() {
        for (int userId : createdUserIds) {
            Response response = userClient.getUserById(userId);
            assertEquals(404, response.getStatusCode(),
                        "User with ID " + userId + " should not exist after deletion");
        }
    }
}