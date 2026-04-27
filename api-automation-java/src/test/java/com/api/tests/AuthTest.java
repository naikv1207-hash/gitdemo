package com.api.tests;

import com.api.base.BaseTest;
import com.api.endpoints.UserEndpoints;
import com.api.models.AuthRequest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

/**
 * AuthTest — Tests for POST /login and POST /register
 *
 * Concepts covered:
 *  - Successful login → extract token
 *  - Login with missing password → 400 error
 *  - Successful registration → extract id + token
 *  - Registration with missing fields → 400 error
 *  - Using token in subsequent requests (Bearer Auth)
 */
public class AuthTest extends BaseTest {

    // Shared token across tests
    private static String authToken;

    // ─────────────────────────────────────────────────────────
    // TC01: Successful login
    // ─────────────────────────────────────────────────────────
    @Test(priority = 1, description = "TC01 - POST login with valid credentials returns 200 and token")
    public void login_validCredentials_shouldReturn200AndToken() {
        log.info("▶ TC01: POST /login");

        AuthRequest loginRequest = AuthRequest.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();

        Response response = given()
            .spec(requestSpec)
            .body(loginRequest)
        .when()
            .post(UserEndpoints.LOGIN)
        .then()
            .statusCode(200)
            .body("token", notNullValue())
            .extract().response();

        authToken = response.jsonPath().getString("token");
        assertNotNull(authToken, "Token should be present in response");

        log.info("✅ TC01 Passed — Token: {}", authToken);
    }

    // ─────────────────────────────────────────────────────────
    // TC02: Login with missing password — expect 400
    // ─────────────────────────────────────────────────────────
    @Test(priority = 2, description = "TC02 - POST login with missing password returns 400")
    public void login_missingPassword_shouldReturn400() {
        log.info("▶ TC02: POST /login — missing password");

        AuthRequest loginRequest = AuthRequest.builder()
                .email("eve.holt@reqres.in")
                .build();  // no password!

        given()
            .spec(requestSpec)
            .body(loginRequest)
        .when()
            .post(UserEndpoints.LOGIN)
        .then()
            .statusCode(400)
            .body("error", equalTo("Missing password"));

        log.info("✅ TC02 Passed — 400 with error message received");
    }

    // ─────────────────────────────────────────────────────────
    // TC03: Successful registration
    // ─────────────────────────────────────────────────────────
    @Test(priority = 3, description = "TC03 - POST register with valid data returns 200 with id and token")
    public void register_validData_shouldReturn200() {
        log.info("▶ TC03: POST /register");

        AuthRequest registerRequest = AuthRequest.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();

        Response response = given()
            .spec(requestSpec)
            .body(registerRequest)
        .when()
            .post(UserEndpoints.REGISTER)
        .then()
            .statusCode(200)
            .body("id", notNullValue())
            .body("token", notNullValue())
            .extract().response();

        int id = response.jsonPath().getInt("id");
        String token = response.jsonPath().getString("token");

        assertTrue(id > 0, "Registered user ID should be > 0");
        assertNotNull(token, "Token should not be null");

        log.info("✅ TC03 Passed — Registered ID: {}, Token: {}", id, token);
    }

    // ─────────────────────────────────────────────────────────
    // TC04: Register with missing password — expect 400
    // ─────────────────────────────────────────────────────────
    @Test(priority = 4, description = "TC04 - POST register without password returns 400")
    public void register_missingPassword_shouldReturn400() {
        log.info("▶ TC04: POST /register — missing password");

        AuthRequest registerRequest = AuthRequest.builder()
                .email("sydney@fife")
                .build();  // no password

        given()
            .spec(requestSpec)
            .body(registerRequest)
        .when()
            .post(UserEndpoints.REGISTER)
        .then()
            .statusCode(400)
            .body("error", equalTo("Missing password"));

        log.info("✅ TC04 Passed");
    }

    // ─────────────────────────────────────────────────────────
    // TC05: Use auth token in GET request (Bearer Token)
    // ─────────────────────────────────────────────────────────
    @Test(priority = 5,
          dependsOnMethods = "login_validCredentials_shouldReturn200AndToken",
          description = "TC05 - Use Bearer token in GET request")
    public void useAuthToken_inGetRequest_shouldReturn200() {
        log.info("▶ TC05: GET /users with Bearer token");
        assertNotNull(authToken, "Token must be available from TC01");

        given()
            .spec(requestSpec)
            .header("Authorization", "Bearer " + authToken)
        .when()
            .get(UserEndpoints.GET_ALL_USERS)
        .then()
            .statusCode(200)
            .body("data", not(empty()));

        log.info("✅ TC05 Passed — Authenticated request successful");
    }
}
