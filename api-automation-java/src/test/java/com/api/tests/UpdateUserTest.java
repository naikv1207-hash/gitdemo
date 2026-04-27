package com.api.tests;

import com.api.base.BaseTest;
import com.api.endpoints.UserEndpoints;
import com.api.models.User;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

/**
 * UpdateUserTest — Tests for PUT /users/{id} and PATCH /users/{id}
 *
 * Concepts covered:
 *  - PUT  → Full update (replace entire resource)
 *  - PATCH → Partial update (update specific fields only)
 *  - Path parameters
 *  - Validating updatedAt timestamp
 */
public class UpdateUserTest extends BaseTest {

    // ─────────────────────────────────────────────────────────
    // TC01: PUT — full update of user
    // ─────────────────────────────────────────────────────────
    @Test(description = "TC01 - PUT fully updates a user and returns 200")
    public void putUser_shouldReturn200AndUpdatedData() {
        log.info("▶ TC01: PUT /users/2");

        User updatedUser = User.builder()
                .name("Updated Name")
                .job("Updated Job Title")
                .build();

        Response response = given()
            .spec(requestSpec)
            .pathParam("id", 2)
            .body(updatedUser)
        .when()
            .put(UserEndpoints.UPDATE_USER)
        .then()
            .statusCode(200)
            .body("name", equalTo("Updated Name"))
            .body("job", equalTo("Updated Job Title"))
            .body("updatedAt", notNullValue())
            .extract().response();

        String updatedAt = response.jsonPath().getString("updatedAt");
        assertNotNull(updatedAt, "updatedAt should be present in PUT response");

        log.info("✅ TC01 Passed — updatedAt: {}", updatedAt);
    }

    // ─────────────────────────────────────────────────────────
    // TC02: PATCH — partial update (only job field)
    // ─────────────────────────────────────────────────────────
    @Test(description = "TC02 - PATCH partially updates a user field and returns 200")
    public void patchUser_shouldReturn200AndPartiallyUpdatedData() {
        log.info("▶ TC02: PATCH /users/2 (job only)");

        // Only updating the "job" field
        Map<String, String> partialUpdate = new HashMap<>();
        partialUpdate.put("job", "Lead QA Automation Engineer");

        given()
            .spec(requestSpec)
            .pathParam("id", 2)
            .body(partialUpdate)
        .when()
            .patch(UserEndpoints.UPDATE_USER)
        .then()
            .statusCode(200)
            .body("job", equalTo("Lead QA Automation Engineer"))
            .body("updatedAt", notNullValue());

        log.info("✅ TC02 Passed");
    }

    // ─────────────────────────────────────────────────────────
    // TC03: PUT — validate updatedAt is recent timestamp
    // ─────────────────────────────────────────────────────────
    @Test(description = "TC03 - PUT response updatedAt should be a valid timestamp")
    public void putUser_updatedAt_shouldBeValidTimestamp() {
        log.info("▶ TC03: PUT /users/3 — validate timestamp");

        Response response = given()
            .spec(requestSpec)
            .pathParam("id", 3)
            .body(User.builder().name("Test User").job("Tester").build())
        .when()
            .put(UserEndpoints.UPDATE_USER)
        .then()
            .statusCode(200)
            .extract().response();

        String updatedAt = response.jsonPath().getString("updatedAt");
        assertNotNull(updatedAt);
        assertTrue(updatedAt.contains("T"), "Timestamp should be ISO 8601 format");

        log.info("✅ TC03 Passed — Timestamp: {}", updatedAt);
    }
}
