package com.api.base;

import com.api.config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

/**
 * BaseTest — Parent class for all test classes.
 *
 * Sets up:
 *  - RequestSpecification  (base URL, headers, content type)
 *  - ResponseSpecification (common response assertions)
 *  - Logging (log all request/response details)
 */
public class BaseTest {

    protected static final Logger log = LogManager.getLogger(BaseTest.class);

    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;

    @BeforeSuite(alwaysRun = true)
    public void globalSetup() {
        log.info("========== 🚀 Starting API Test Suite ==========");
    }

    @BeforeClass(alwaysRun = true)
    public void setup() {
        ConfigManager config = ConfigManager.getInstance();

        // ── Build Request Specification ───────────────────────
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(config.getBaseUrl())
                .setContentType(ContentType.JSON)
                .addHeader("x-api-key", config.getApiKey())
                .log(LogDetail.ALL)           // log every request
                .build();

        // ── Build Response Specification ──────────────────────
        responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL)           // log every response
                .build();

        // ── Global RestAssured Config ─────────────────────────
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        log.info("✅ BaseTest setup complete. BaseURL: {}", config.getBaseUrl());
    }
}
