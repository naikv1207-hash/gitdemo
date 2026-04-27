package com.api.endpoints;

/**
 * UserEndpoints — Central store for all API endpoint paths.
 *
 * ✅ Benefit: If endpoints change, update here only — not in every test.
 *
 * Base URL comes from config.properties → base.url
 * Example: base.url=https://reqres.in/api
 */
public class UserEndpoints {

    private UserEndpoints() {}  // Prevent instantiation

    // ─── User Endpoints ───────────────────────────────────────
    public static final String GET_ALL_USERS    = "/users";
    public static final String GET_USER_BY_ID   = "/users/{id}";
    public static final String CREATE_USER      = "/users";
    public static final String UPDATE_USER      = "/users/{id}";
    public static final String DELETE_USER      = "/users/{id}";

    // ─── Auth Endpoints ───────────────────────────────────────
    public static final String REGISTER         = "/register";
    public static final String LOGIN            = "/login";

    // ─── Resource Endpoints ───────────────────────────────────
    public static final String GET_ALL_RESOURCES = "/unknown";
    public static final String GET_RESOURCE_BY_ID = "/unknown/{id}";
}
