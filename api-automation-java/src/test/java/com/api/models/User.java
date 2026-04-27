package com.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User — POJO model representing the User entity from Reqres API.
 *
 * Lombok annotations used:
 *   @Data        → generates getters, setters, toString, equals, hashCode
 *   @Builder     → enables builder pattern: User.builder().name("John").build()
 *   @NoArgsConstructor → generates no-arg constructor (required by Jackson)
 *   @AllArgsConstructor → generates all-args constructor
 *
 * @JsonIgnoreProperties(ignoreUnknown = true) → ignores extra JSON fields
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private Integer id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String email;
    private String avatar;

    // ─── For Create/Update requests ───────────────────────────
    private String name;
    private String job;
}
