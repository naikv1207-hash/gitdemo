# 🚀 REST API Test Automation Framework
### Java + RestAssured + TestNG + ExtentReports + Postman

---

## 📁 Project Structure

```
api-automation-java/
│
├── src/test/java/com/api/
│   ├── base/
│   │   └── BaseTest.java              ← RequestSpec, ResponseSpec setup
│   ├── config/
│   │   └── ConfigManager.java         ← Reads config.properties (Singleton)
│   ├── endpoints/
│   │   └── UserEndpoints.java         ← All API endpoint constants
│   ├── models/
│   │   ├── User.java                  ← POJO with Lombok
│   │   └── AuthRequest.java           ← Auth POJO
│   ├── tests/
│   │   ├── GetUserTest.java           ← GET tests (6 test cases)
│   │   ├── CreateUserTest.java        ← POST tests (4 test cases)
│   │   ├── UpdateUserTest.java        ← PUT/PATCH tests (3 test cases)
│   │   ├── DeleteUserTest.java        ← DELETE tests (2 test cases)
│   │   ├── AuthTest.java              ← Login/Register tests (5 test cases)
│   │   └── SchemaValidationTest.java  ← JSON Schema tests (2 test cases)
│   └── utils/
│       ├── TestDataGenerator.java     ← Random data with JavaFaker
│       ├── TestDataProvider.java      ← TestNG @DataProvider sets
│       └── ExtentReportListener.java  ← HTML report generator
│
├── src/test/resources/
│   ├── config.properties              ← Base URLs, API keys, env config
│   ├── testng.xml                     ← Test suite configuration
│   ├── log4j2.xml                     ← Logging configuration
│   └── schemas/
│       ├── users-list-schema.json     ← JSON Schema for GET /users
│       └── user-schema.json           ← JSON Schema for GET /users/{id}
│
├── postman/
│   └── Reqres_API_Collection.json     ← Import this into Postman
│
└── pom.xml                            ← Maven dependencies
```

---

## 🛠️ Prerequisites

| Tool | Version |
|---|---|
| Java JDK | 11 or higher |
| Maven | 3.8+ |
| IDE | IntelliJ IDEA (recommended) |
| Postman | Latest |

---

## 🚀 Quick Start

### Step 1 — Clone / Extract the project
```bash
cd api-automation-java
```

### Step 2 — Install dependencies
```bash
mvn clean install -DskipTests
```

### Step 3 — Run all tests
```bash
mvn test
```

### Step 4 — View HTML Report
```
Open: test-output/reports/ExtentReport_<timestamp>.html
```

---

## 🧪 Running Specific Tests

```bash
# Run only GET tests
mvn test -Dtest=GetUserTest

# Run only Auth tests
mvn test -Dtest=AuthTest

# Run all tests via TestNG suite
mvn test -DsuiteXmlFile=src/test/resources/testng.xml

# Run with specific group tag
mvn test -Dgroups=smoke
```

---

## 📬 Postman Setup

1. Open Postman
2. Click **Import**
3. Select `postman/Reqres_API_Collection.postman_collection.json`
4. Click the **▶ Run** button or use **Collection Runner**
5. All tests include pre-written test scripts!

---

## 📚 Concepts Covered

| Concept | Where |
|---|---|
| GET / POST / PUT / PATCH / DELETE | All test classes |
| RequestSpecification & ResponseSpecification | BaseTest.java |
| Path Parameters | GetUserTest, UpdateUserTest |
| Query Parameters | GetUserTest (pagination) |
| Request Body (Map + POJO) | CreateUserTest |
| JSON Deserialization (POJO) | GetUserTest, CreateUserTest |
| JSONPath Extraction | All test classes |
| Status Code Validation | All test classes |
| Response Time Validation | GetUserTest TC06 |
| Header Validation | GetUserTest TC05 |
| JSON Schema Validation | SchemaValidationTest |
| Authentication (Bearer Token) | AuthTest TC05 |
| Data-Driven (@DataProvider) | CreateUserTest TC04 |
| Random Data (JavaFaker) | CreateUserTest TC03 |
| Extent HTML Reports | ExtentReportListener |
| Logging (Log4j2) | All classes |
| Singleton Config | ConfigManager |
| Postman with Test Scripts | postman/ folder |

---

## 🌐 API Under Test

**Reqres.in** — Free sandbox REST API  
URL: `https://reqres.in`

No signup needed. Perfect for practice!

---

## 📊 Test Summary

| Suite | Tests |
|---|---|
| GET User Tests | 6 |
| POST Create Tests | 4 |
| PUT/PATCH Update Tests | 3 |
| DELETE Tests | 2 |
| Auth Tests | 5 |
| Schema Validation Tests | 2 |
| **Total** | **22** |
