package com.apiautomation.client;

import com.apiautomation.models.User;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient {
    
    private static final String USERS_ENDPOINT = "/users";

    public Response getAllUsers() {
        return given()
                .spec(BaseClient.getRequestSpecWithoutAuth())
                .when()
                .get(USERS_ENDPOINT);
    }

    public Response getAllUsers(int page, int perPage) {
        return given()
                .spec(BaseClient.getRequestSpecWithoutAuth())
                .queryParam("page", page)
                .queryParam("per_page", perPage)
                .when()
                .get(USERS_ENDPOINT);
    }

    public Response getUserById(int userId) {
        return given()
                .spec(BaseClient.getRequestSpecWithoutAuth())
                .when()
                .get(USERS_ENDPOINT + "/{id}", userId);
    }

    public Response createUser(User user) {
        return given()
                .spec(BaseClient.getRequestSpec())
                .body(user)
                .when()
                .post(USERS_ENDPOINT);
    }

    public Response updateUser(int userId, User user) {
        return given()
                .spec(BaseClient.getRequestSpec())
                .body(user)
                .when()
                .put(USERS_ENDPOINT + "/{id}", userId);
    }

    public Response deleteUser(int userId) {
        return given()
                .spec(BaseClient.getRequestSpec())
                .when()
                .delete(USERS_ENDPOINT + "/{id}", userId);
    }

    public Response searchUsersByName(String name) {
        return given()
                .spec(BaseClient.getRequestSpecWithoutAuth())
                .queryParam("name", name)
                .when()
                .get(USERS_ENDPOINT);
    }
}