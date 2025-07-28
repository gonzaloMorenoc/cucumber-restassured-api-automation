package com.apiautomation.hooks;

import com.apiautomation.client.UserClient;
import com.apiautomation.context.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;

public class Hooks {
    
    private final TestContext testContext;
    private final UserClient userClient;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
        this.userClient = new UserClient();
    }

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("Starting scenario: " + scenario.getName());
        testContext.reset();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            System.out.println("Scenario failed: " + scenario.getName());
            
            Response response = testContext.getResponse();
            if (response != null) {
                System.out.println("Response Status: " + response.getStatusCode());
                System.out.println("Response Body: " + response.getBody().asString());
            }
        }

        cleanupCreatedUser();
        System.out.println("Finished scenario: " + scenario.getName());
    }

    private void cleanupCreatedUser() {
        try {
            int userId = testContext.getUserId();
            if (userId > 0) {
                Response deleteResponse = userClient.deleteUser(userId);
                if (deleteResponse.getStatusCode() == 204) {
                    System.out.println("Cleaned up user with ID: " + userId);
                }
            }
        } catch (Exception e) {
            System.out.println("Cleanup failed: " + e.getMessage());
        }
    }
}