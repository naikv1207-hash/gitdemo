package com.api.tests;

import com.api.base.BaseTest;
import com.api.endpoints.UserEndpoints;
import com.api.models.User;
import com.api.utils.TestDataGenerator;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

/**
 * CreateUserTest — Tests for POST /users
 *
 * Concepts covered:
 *  - POST with JSON body (Map)
 *  - POST with POJO body
 *  - Asserting 201 Created
 *  - Extracting created resource ID
 *  - Data-driven testing with @DataProvider
 *  - Negative test: missing required fields
 */
public class CreateUserTest extends BaseTest {

    // ─────────────────────────────────────────────────────────
    // TC01: Create user using Map as request body
    // ─────────────────────────────────────────────────────────
    @Test(description = "TC01 - POST create user with Map body returns 201")
    public void createUser_withMap_shouldReturn201() {
        log.info("▶ TC01: POST /users with Map body");

        // ── Request body using HashMap ─────────────────────
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", "John Doe");
        requestBody.put("job", "QA Engineer");

        given()
            .spec(requestSpec)
            .body(requestBody)
        .when()
            .post(UserEndpoints.CREATE_USER)
        .then()
            .statusCode(201)
            .body("name", equalTo("John Doe"))
            .body("job", equalTo("QA Engineer"))
            .body("id", notNullValue())
            .body("createdAt", notNullValue());

        log.info("✅ TC01 Passed");
    }

    // ─────────────────────────────────────────────────────────
    // TC02: Create user using POJO as request body
    // ─────────────────────────────────────────────────────────
    @Test(description = "TC02 - POST create user with POJO body and validate response")
    public void createUser_withPOJO_shouldReturn201AndMatchFields() {
        log.info("▶ TC02: POST /users with POJO body");

        // ── Build User using Lombok @Builder ──────────────────
        User userRequest = User.builder()
                .name("Jane Smith")
                .job("Senior Developer")
                .build();

        Response response = given()
            .spec(requestSpec)
            .body(userRequest)
        .when()
            .post(UserEndpoints.CREATE_USER)
        .then()
            .statusCode(201)
            .extract().response();

        // ── Deserialize response → User POJO ──────────────────
        User createdUser = response.as(User.class);

        assertNotNull(createdUser.getId(), "Created user ID should not be null");
        assertEquals(createdUser.getName(), "Jane Smith");
        assertEquals(createdUser.getJob(), "Senior Developer");

        log.info("✅ TC02 Passed — Created user ID: {}", createdUser.getId());
    }

    // ─────────────────────────────────────────────────────────
    // TC03: Create user with random data (using Faker)
    // ─────────────────────────────────────────────────────────
    @Test(description = "TC03 - POST create user with random Faker data")
    public void createUser_withRandomData_shouldReturn201() {
        log.info("▶ TC03: POST /users with random Faker data");

        String randomName = TestDataGenerator.getRandomName();
        String randomJob  = TestDataGenerator.getRandomJobTitle();

        log.info("Creating user: name={}, job={}", randomName, randomJob);

        Response response = given()
            .spec(requestSpec)
            .body(User.builder().name(randomName).job(randomJob).build())
        .when()
            .post(UserEndpoints.CREATE_USER)
        .then()
            .statusCode(201)
            .body("name", equalTo(randomName))
            .body("job", equalTo(randomJob))
            .extract().response();

        String createdId = response.jsonPath().getString("id");
        assertNotNull(createdId, "Created user ID should be returned");

        log.info("✅ TC03 Passed — Random user created with ID: {}", createdId);
    }

    // ─────────────────────────────────────────────────────────
    // TC04: Data-driven — create multiple users via @DataProvider
    // ─────────────────────────────────────────────────────────
    @Test(dataProvider = "userData",
          dataProviderClass = com.api.utils.TestDataProvider.class,
          description = "TC04 - POST create user with multiple data sets")
    public void createUser_datadriven_shouldReturn201(String name, String job) {
        log.info("▶ TC04: Creating user — name={}, job={}", name, job);

        given()
            .spec(requestSpec)
            .body(User.builder().name(name).job(job).build())
        .when()
            .post(UserEndpoints.CREATE_USER)
        .then()
            .statusCode(201)
            .body("name", equalTo(name))
            .body("job", equalTo(job));

        log.info("✅ TC04 Passed for: {} / {}", name, job);
    }
}
