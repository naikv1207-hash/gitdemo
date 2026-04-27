import { test, expect } from '@playwright/test';
import { LoginPage } from '../pages/LoginPage';
import loginData from '../test-data/loginData.json';

/**
 * Login Test Suite
 * Tests against: https://practicetestautomation.com/practice-test-login/
 */
test.describe('Login Page Tests', () => {

  let loginPage: LoginPage;

  // Runs before each test
  test.beforeEach(async ({ page }) => {
    loginPage = new LoginPage(page);
    await loginPage.navigateToLoginPage();
  });

  // ─── Positive Tests ───────────────────────────────────────

  test('TC01 - Successful login with valid credentials', async ({ page }) => {
    await loginPage.login(
      loginData.validUser.username,
      loginData.validUser.password
    );

    // Assert successful login
    await expect(page).toHaveURL(/logged-in-successfully/);
    const heading = await loginPage.getSuccessHeading();
    expect(heading).toContain('Logged In Successfully');

    // Logout button should be visible
    expect(await loginPage.isLogoutVisible()).toBeTruthy();
  });

  test('TC02 - Successful logout after login', async ({ page }) => {
    await loginPage.login(
      loginData.validUser.username,
      loginData.validUser.password
    );

    await loginPage.logout();

    // Should redirect back to login
    await expect(page).toHaveURL(/practice-test-login/);
  });

  // ─── Negative Tests ───────────────────────────────────────

  test('TC03 - Login fails with invalid username', async () => {
    await loginPage.login(
      loginData.invalidUsers[0].username,
      loginData.invalidUsers[0].password
    );

    const error = await loginPage.getErrorMessage();
    expect(error).toContain(loginData.invalidUsers[0].expectedError);
  });

  test('TC04 - Login fails with invalid password', async () => {
    await loginPage.login(
      loginData.invalidUsers[1].username,
      loginData.invalidUsers[1].password
    );

    const error = await loginPage.getErrorMessage();
    expect(error).toContain(loginData.invalidUsers[1].expectedError);
  });

  test('TC05 - Login fails with empty credentials', async () => {
    await loginPage.clickSubmit();

    const error = await loginPage.getErrorMessage();
    expect(error).toContain(loginData.invalidUsers[2].expectedError);
  });

  // ─── UI Validation Tests ──────────────────────────────────

  test('TC06 - Login page title is correct', async ({ page }) => {
    await expect(page).toHaveTitle(/Practice Test Login/);
  });

  test('TC07 - Username and password fields are visible', async () => {
    await expect(loginPage.usernameInput).toBeVisible();
    await expect(loginPage.passwordInput).toBeVisible();
    await expect(loginPage.submitButton).toBeVisible();
  });

});
