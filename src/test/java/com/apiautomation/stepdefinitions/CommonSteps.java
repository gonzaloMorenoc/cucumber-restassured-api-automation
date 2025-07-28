package com.apiautomation.stepdefinitions;

import com.apiautomation.client.UserClient;
import com.apiautomation.context.TestContext;
import com.apiautomation.models.User;
import com.apiautomation.utils.TestDataGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;

public class CommonSteps {
    
    private final TestContext testContext;
    private final UserClient userClient;

    public CommonSteps(TestContext testContext) {
        this.testContext = testContext;
        this.userClient = new UserClient();
    }

    @Given("the GoRest API is available")
    public void theGoRestAPIIsAvailable() {
        Response response = userClient.getAllUsers();
        assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 401, 
                  "API should be available");
    }

    @Given("there is an existing user in the system")
    public void thereIsAnExistingUserInTheSystem() {
        Response response = userClient.getAllUsers(1, 1);
        assertEquals(200, response.getStatusCode());
        
        User[] users = response.getBody().as(User[].class);
        if (users.length > 0) {
            testContext.setUserId(users[0].getId());
            testContext.setUser(users[0]);
        } else {
            User newUser = TestDataGenerator.generateRandomUser();
            Response createResponse = userClient.createUser(newUser);
            if (createResponse.getStatusCode() == 201) {
                User createdUser = createResponse.getBody().as(User.class);
                testContext.setUserId(createdUser.getId());
                testContext.setUser(createdUser);
            }
        }
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        Response response = testContext.getResponse();
        assertNotNull(response, "Response should not be null");
        assertEquals(expectedStatusCode, response.getStatusCode(), 
                    "Expected status code: " + expectedStatusCode + " but got: " + response.getStatusCode());
    }

    @Then("the response should contain an error message")
    public void theResponseShouldContainAnErrorMessage() {
        Response response = testContext.getResponse();
        assertNotNull(response, "Response should not be null");
        String responseBody = response.getBody().asString();
        assertFalse(responseBody.isEmpty(), "Response body should contain error message");
    }

    @Then("the response body should be empty")
    public void theResponseBodyShouldBeEmpty() {
        Response response = testContext.getResponse();
        assertNotNull(response, "Response should not be null");
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.isEmpty() || responseBody.trim().isEmpty(), 
                  "Response body should be empty");
    }

    @Then("the response should contain authentication error")
    public void theResponseShouldContainAuthenticationError() {
        Response response = testContext.getResponse();
        assertNotNull(response, "Response should not be null");
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("Authentication failed") || 
                  responseBody.contains("Unauthorized") ||
                  responseBody.contains("Invalid token"), 
                  "Response should contain authentication error");
    }
}