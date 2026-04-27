package com.api.utils;

import com.github.javafaker.Faker;

/**
 * TestDataGenerator — Generates random realistic test data using JavaFaker.
 *
 * ✅ Why Faker?
 *   - Avoids hardcoded test data
 *   - Each test run uses unique data
 *   - Closer to real-world scenarios
 */
public class TestDataGenerator {

    private static final Faker faker = new Faker();

    private TestDataGenerator() {}

    public static String getRandomName() {
        return faker.name().fullName();
    }

    public static String getRandomFirstName() {
        return faker.name().firstName();
    }

    public static String getRandomLastName() {
        return faker.name().lastName();
    }

    public static String getRandomEmail() {
        return faker.internet().emailAddress();
    }

    public static String getRandomJobTitle() {
        return faker.job().title();
    }

    public static String getRandomPassword() {
        return faker.internet().password(8, 16, true, true);
    }

    public static String getRandomPhoneNumber() {
        return faker.phoneNumber().phoneNumber();
    }

    public static String getRandomCity() {
        return faker.address().city();
    }
}
