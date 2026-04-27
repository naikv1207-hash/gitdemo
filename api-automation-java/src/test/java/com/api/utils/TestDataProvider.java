package com.api.utils;

import org.testng.annotations.DataProvider;

/**
 * TestDataProvider — Provides data sets for @DataProvider driven tests.
 *
 * Usage in test:
 *   @Test(dataProvider = "userData", dataProviderClass = TestDataProvider.class)
 *   public void myTest(String name, String job) { ... }
 */
public class TestDataProvider {

    @DataProvider(name = "userData")
    public static Object[][] userData() {
        return new Object[][] {
            { "Alice Johnson",   "QA Engineer"           },
            { "Bob Williams",    "Senior Developer"      },
            { "Carol Martinez",  "Product Manager"       },
            { "David Lee",       "DevOps Engineer"       },
            { "Emma Wilson",     "Data Scientist"        },
        };
    }

    @DataProvider(name = "invalidLoginData")
    public static Object[][] invalidLoginData() {
        return new Object[][] {
            { "",                   "password123",  400 },
            { "notanemail",         "password123",  400 },
            { "valid@reqres.in",    "",             400 },
        };
    }
}
