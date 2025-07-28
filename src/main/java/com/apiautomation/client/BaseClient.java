package com.apiautomation.client;

import com.apiautomation.config.Config;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;

public class BaseClient {
    
    private static final Config config = ConfigFactory.create(Config.class);
    private static RequestSpecification requestSpec;

    static {
        initializeRequestSpec();
    }

    private static void initializeRequestSpec() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(config.baseUrl())
                .setContentType(ContentType.JSON)
                .addHeader("Accept", ContentType.JSON.toString())
                .addHeader("Authorization", "Bearer " + config.accessToken())
                .build();
        
        RestAssured.requestSpecification = requestSpec;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    public static RequestSpecification getRequestSpec() {
        return requestSpec;
    }

    public static RequestSpecification getRequestSpecWithoutAuth() {
        return new RequestSpecBuilder()
                .setBaseUri(config.baseUrl())
                .setContentType(ContentType.JSON)
                .addHeader("Accept", ContentType.JSON.toString())
                .build();
    }
}