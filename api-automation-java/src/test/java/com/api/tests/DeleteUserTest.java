package com.api.tests;

import com.api.base.BaseTest;
import com.api.endpoints.UserEndpoints;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

/**
 * DeleteUserTest — Tests for DELETE /users/{id}
 *
 * Concepts covered:
 *  - DELETE returns 204 No Content
 *  - Response body is empty
 *  - Validating resource is gone after delete
 */
public class DeleteUserTest extends BaseTest {

    // ─────────────────────────────────────────────────────────
    // TC01: DELETE user — returns 204 No Content
    // ─────────────────────────────────────────────────────────
    @Test(description = "TC01 - DELETE user returns 204 No Content")
    public void deleteUser_shouldReturn204() {
        log.info("▶ TC01: DELETE /users/2");

        given()
            .spec(requestSpec)
            .pathParam("id", 2)
        .when()
            .delete(UserEndpoints.DELETE_USER)
        .then()
            .statusCode(204);

        log.info("✅ TC01 Passed — 204 No Content received");
    }

    // ─────────────────────────────────────────────────────────
    // TC02: DELETE response body should be empty
    // ─────────────────────────────────────────────────────────
    @Test(description = "TC02 - DELETE response body should be empty")
    public void deleteUser_responseBodyShouldBeEmpty() {
        log.info("▶ TC02: DELETE /users/3 — validate empty body");

        String responseBody = given()
            .spec(requestSpec)
            .pathParam("id", 3)
        .when()
            .delete(UserEndpoints.DELETE_USER)
        .then()
            .statusCode(204)
            .extract().response().asString();

        assertTrue(responseBody.isEmpty(), "Response body should be empty for 204");

        log.info("✅ TC02 Passed — Response body is empty as expected");
    }
}
