package com.api.tests;

import com.api.base.BaseTest;
import com.api.endpoints.UserEndpoints;
import com.api.models.User;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

/**
 * GetUserTest — Tests for GET /users and GET /users/{id}
 *
 * Real-time endpoint: https://reqres.in/api/users
 *
 * Concepts covered:
 *  - Status code validation
 *  - Response body field validation
 *  - Deserializing JSON to POJO
 *  - JSONPath extraction
 *  - Query parameters
 *  - Negative tests (404)
 */
public class GetUserTest extends BaseTest {

    // ─────────────────────────────────────────────────────────
    // TC01: GET all users — verify status code and list size
    // ─────────────────────────────────────────────────────────
    @Test(description = "TC01 - GET all users returns 200 and non-empty list")
    public void getAllUsers_shouldReturn200() {
        log.info("▶ TC01: GET all users");

        given()
            .spec(requestSpec)
            .queryParam("page", 1)
        .when()
            .get(UserEndpoints.GET_ALL_USERS)
        .then()
            .statusCode(200)
            .body("page", equalTo(1))
            .body("data", not(empty()))
            .body("data.size()", greaterThan(0))
            .body("data[0].id", notNullValue())
            .body("data[0].email", containsString("@"));

        log.info("✅ TC01 Passed");
    }

    // ─────────────────────────────────────────────────────────
    // TC02: GET user by ID — validate specific user data
    // ─────────────────────────────────────────────────────────
    @Test(description = "TC02 - GET user by ID returns correct user")
    public void getUserById_shouldReturnCorrectUser() {
        log.info("▶ TC02: GET /users/2");

        Response response = given()
            .spec(requestSpec)
            .pathParam("id", 2)
        .when()
            .get(UserEndpoints.GET_USER_BY_ID)
        .then()
            .statusCode(200)
            .extract().response();

        // ── Deserialize JSON → User POJO ──────────────────────
        User user = response.jsonPath().getObject("data", User.class);

        assertNotNull(user, "User object should not be null");
        assertEquals(user.getId(), 2, "User ID should be 2");
        assertNotNull(user.getEmail(), "Email should not be null");
        assertTrue(user.getEmail().contains("@"), "Email should contain @");
        assertNotNull(user.getFirstName(), "First name should not be null");

        log.info("✅ TC02 Passed — User: {} {}", user.getFirstName(), user.getLastName());
    }

    // ─────────────────────────────────────────────────────────
    // TC03: GET user by invalid ID — expect 404
    // ─────────────────────────────────────────────────────────
    @Test(description = "TC03 - GET user with invalid ID returns 404")
    public void getUserById_invalidId_shouldReturn404() {
        log.info("▶ TC03: GET /users/9999 (invalid)");

        given()
            .spec(requestSpec)
            .pathParam("id", 9999)
        .when()
            .get(UserEndpoints.GET_USER_BY_ID)
        .then()
            .statusCode(404)
            .body(equalTo("{}"));

        log.info("✅ TC03 Passed — 404 received as expected");
    }

    // ─────────────────────────────────────────────────────────
    // TC04: GET users with pagination — page 2
    // ─────────────────────────────────────────────────────────
    @Test(description = "TC04 - GET users page 2 returns page 2 data")
    public void getAllUsers_page2_shouldReturnPage2() {
        log.info("▶ TC04: GET /users?page=2");

        Response response = given()
            .spec(requestSpec)
            .queryParam("page", 2)
        .when()
            .get(UserEndpoints.GET_ALL_USERS)
        .then()
            .statusCode(200)
            .body("page", equalTo(2))
            .extract().response();

        // Extract list of emails using JSONPath
        List<String> emails = response.jsonPath().getList("data.email");
        log.info("Emails on page 2: {}", emails);

        assertFalse(emails.isEmpty(), "Emails list should not be empty");
        emails.forEach(email -> assertTrue(email.contains("@"),
                "Each email should contain @: " + email));

        log.info("✅ TC04 Passed");
    }

    // ─────────────────────────────────────────────────────────
    // TC05: Validate response headers
    // ─────────────────────────────────────────────────────────
    @Test(description = "TC05 - GET users response has correct Content-Type header")
    public void getUsersResponse_shouldHaveCorrectHeaders() {
        log.info("▶ TC05: Validate response headers");

        given()
            .spec(requestSpec)
        .when()
            .get(UserEndpoints.GET_ALL_USERS)
        .then()
            .statusCode(200)
            .header("Content-Type", containsString("application/json"));

        log.info("✅ TC05 Passed");
    }

    // ─────────────────────────────────────────────────────────
    // TC06: Validate response time < 3000ms
    // ─────────────────────────────────────────────────────────
    @Test(description = "TC06 - GET users response time is under 3000ms")
    public void getUsersResponseTime_shouldBeFast() {
        log.info("▶ TC06: Validate response time");

        long responseTime = given()
            .spec(requestSpec)
        .when()
            .get(UserEndpoints.GET_ALL_USERS)
        .then()
            .statusCode(200)
            .extract().response().time();

        log.info("Response Time: {}ms", responseTime);
        assertTrue(responseTime < 3000, "Response time should be < 3000ms, was: " + responseTime);

        log.info("✅ TC06 Passed — Response time: {}ms", responseTime);
    }
}
