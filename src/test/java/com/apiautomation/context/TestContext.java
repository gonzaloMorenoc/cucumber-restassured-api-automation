package com.apiautomation.context;

import com.apiautomation.models.User;
import io.restassured.response.Response;
import java.util.List;

public class TestContext {
    
    private Response response;
    private User user;
    private List<User> users;
    private int userId;
    private String searchName;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public void reset() {
        this.response = null;
        this.user = null;
        this.users = null;
        this.userId = 0;
        this.searchName = null;
    }
}