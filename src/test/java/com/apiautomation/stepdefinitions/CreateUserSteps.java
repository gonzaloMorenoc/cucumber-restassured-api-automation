package com.apiautomation.stepdefinitions;

import com.apiautomation.client.BaseClient;
import com.apiautomation.client.UserClient;
import com.apiautomation.context.TestContext;
import com.apiautomation.models.User;
import com.apiautomation.utils.TestDataGenerator;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class CreateUserSteps {
    
    private final TestContext testContext;
    private final UserClient userClient;

    public CreateUserSteps(TestContext testContext) {
        this.testContext = testContext;
        this.userClient = new UserClient();
    }

    @Given("I have valid user data")
    public void iHaveValidUserData() {
        User user = TestDataGenerator.generateRandomUser();
        testContext.setUser(user);
    }

    @Given("I have user data with the following details:")
    public void iHaveUserDataWithTheFollowingDetails(DataTable dataTable) {
        Map<String, String> userData = dataTable.asMap();
        User user = new User(
            userData.get("name"),
            userData.get("email"),
            userData.get("gender"),
            userData.get("status")
        );
        testContext.setUser(user);
    }

    @Given("I have user data with missing name")
    public void iHaveUserDataWithMissingName() {
        User user = TestDataGenerator.generateRandomUser();
        user.setName(null);
        testContext.setUser(user);
    }

    @Given("I have user data with invalid email {string}")
    public void iHaveUserDataWithInvalidEmail(String invalidEmail) {
        User user = TestDataGenerator.generateRandomUser();
        user.setEmail(invalidEmail);
        testContext.setUser(user);
    }

    @Given("there is an existing user with email {string}")
    public void thereIsAnExistingUserWithEmail(String email) {
        User existingUser = TestDataGenerator.generateUserWithEmail(email);
        Response createResponse = userClient.createUser(existingUser);
        if (createResponse.getStatusCode() == 201) {
            User createdUser = createResponse.getBody().as(User.class);
            testContext.setUserId(createdUser.getId());
        }
    }

    @Given("I have user data with email {string}")
    public void iHaveUserDataWithEmail(String email) {
        User user = TestDataGenerator.generateUserWithEmail(email);
        testContext.setUser(user);
    }

    @When("I send a POST request to create the user")
    public void iSendAPOSTRequestToCreateTheUser() {
        User user = testContext.getUser();
        System.out.println("Creating user: " + user);
        
        Response response = userClient.createUser(user);
        
        if (response.getStatusCode() >= 400) {
            System.out.println("Request failed with status: " + response.getStatusCode());
            System.out.println("Response body: " + response.getBody().asString());
            System.out.println("Request headers: " + response.getHeaders());
        }
        
        testContext.setResponse(response);
        
        if (response.getStatusCode() == 201) {
            User createdUser = response.getBody().as(User.class);
            testContext.setUserId(createdUser.getId());
        }
    }

    @When("I send a POST request without authentication token")
    public void iSendAPOSTRequestWithoutAuthenticationToken() {
        User user = testContext.getUser();
        Response response = given()
                .spec(BaseClient.getRequestSpecWithoutAuth())
                .body(user)
                .when()
                .post("/users");
        testContext.setResponse(response);
    }

    @Then("the response should contain the created user details")
    public void theResponseShouldContainTheCreatedUserDetails() {
        Response response = testContext.getResponse();
        User createdUser = response.getBody().as(User.class);
        User originalUser = testContext.getUser();
        
        assertNotNull(createdUser, "Created user should not be null");
        assertNotNull(createdUser.getId(), "Created user should have an ID");
        assertEquals(originalUser.getName(), createdUser.getName(), "Name should match");
        assertEquals(originalUser.getEmail(), createdUser.getEmail(), "Email should match");
        assertEquals(originalUser.getGender(), createdUser.getGender(), "Gender should match");
        assertEquals(originalUser.getStatus(), createdUser.getStatus(), "Status should match");
    }

    @Then("the user should have a generated ID")
    public void theUserShouldHaveAGeneratedID() {
        Response response = testContext.getResponse();
        User createdUser = response.getBody().as(User.class);
        
        assertNotNull(createdUser.getId(), "User should have a generated ID");
        assertTrue(createdUser.getId() > 0, "User ID should be positive");
    }

    @Then("the response should contain the user with name {string}")
    public void theResponseShouldContainTheUserWithName(String expectedName) {
        Response response = testContext.getResponse();
        User createdUser = response.getBody().as(User.class);
        
        assertEquals(expectedName, createdUser.getName(), "User name should match");
    }

    @Then("the response should contain the user with email {string}")
    public void theResponseShouldContainTheUserWithEmail(String expectedEmail) {
        Response response = testContext.getResponse();
        User createdUser = response.getBody().as(User.class);
        
        assertEquals(expectedEmail, createdUser.getEmail(), "User email should match");
    }

    @Then("the response should contain validation errors")
    public void theResponseShouldContainValidationErrors() {
        Response response = testContext.getResponse();
        String responseBody = response.getBody().asString();
        
        assertTrue(responseBody.contains("field") || 
                  responseBody.contains("error") ||
                  responseBody.contains("invalid") ||
                  responseBody.contains("required"),
                  "Response should contain validation errors");
    }

    @Then("the response should contain email validation error")
    public void theResponseShouldContainEmailValidationError() {
        Response response = testContext.getResponse();
        String responseBody = response.getBody().asString();
        
        assertTrue(responseBody.toLowerCase().contains("email") && 
                  (responseBody.toLowerCase().contains("invalid") || 
                   responseBody.toLowerCase().contains("format")),
                  "Response should contain email validation error");
    }

    @Then("the response should contain duplicate email error")
    public void theResponseShouldContainDuplicateEmailError() {
        Response response = testContext.getResponse();
        String responseBody = response.getBody().asString();
        
        assertTrue(responseBody.toLowerCase().contains("email") && 
                  (responseBody.toLowerCase().contains("taken") || 
                   responseBody.toLowerCase().contains("already") ||
                   responseBody.toLowerCase().contains("exists")),
                  "Response should contain duplicate email error");
    }
}