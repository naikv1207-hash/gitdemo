package com.api.tests;

import com.api.base.BaseTest;
import com.api.endpoints.UserEndpoints;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * SchemaValidationTest — Validates API responses match a JSON Schema.
 *
 * ✅ Why Schema Validation?
 *   - Ensures the API contract hasn't changed
 *   - Catches missing/renamed fields early
 *   - Independent of actual values (structural check)
 *
 * Schema files are in: src/test/resources/schemas/
 */
public class SchemaValidationTest extends BaseTest {

    // ─────────────────────────────────────────────────────────
    // TC01: Validate GET /users response matches JSON schema
    // ─────────────────────────────────────────────────────────
    @Test(description = "TC01 - GET /users response matches users-list schema")
    public void getUsers_shouldMatchJsonSchema() {
        log.info("▶ TC01: Schema validation for GET /users");

        given()
            .spec(requestSpec)
        .when()
            .get(UserEndpoints.GET_ALL_USERS)
        .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/users-list-schema.json"));

        log.info("✅ TC01 Passed — Schema matches");
    }

    // ─────────────────────────────────────────────────────────
    // TC02: Validate GET /users/{id} response matches schema
    // ─────────────────────────────────────────────────────────
    @Test(description = "TC02 - GET /users/{id} response matches single user schema")
    public void getUserById_shouldMatchJsonSchema() {
        log.info("▶ TC02: Schema validation for GET /users/2");

        given()
            .spec(requestSpec)
            .pathParam("id", 2)
        .when()
            .get(UserEndpoints.GET_USER_BY_ID)
        .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));

        log.info("✅ TC02 Passed — Schema matches");
    }
}
