import { Page, Locator } from '@playwright/test';
import { BasePage } from './BasePage';

/**
 * LoginPage — Page Object Model for the Login page
 * URL: https://practicetestautomation.com/practice-test-login/
 */
export class LoginPage extends BasePage {
  // ─── Locators ─────────────────────────────────────────────
  readonly usernameInput: Locator;
  readonly passwordInput: Locator;
  readonly submitButton: Locator;
  readonly errorMessage: Locator;
  readonly successMessage: Locator;
  readonly logoutButton: Locator;

  constructor(page: Page) {
    super(page);
    this.usernameInput  = page.locator('#username');
    this.passwordInput  = page.locator('#password');
    this.submitButton   = page.locator('#submit');
    this.errorMessage   = page.locator('#error');
    this.successMessage = page.locator('.post-title');
    this.logoutButton   = page.locator('.wp-block-button__link');
  }

  // ─── Actions ──────────────────────────────────────────────

  async navigateToLoginPage() {
    await this.goto('/practice-test-login/');
  }

  async enterUsername(username: string) {
    await this.fill(this.usernameInput, username);
  }

  async enterPassword(password: string) {
    await this.fill(this.passwordInput, password);
  }

  async clickSubmit() {
    await this.click(this.submitButton);
  }

  async login(username: string, password: string) {
    await this.enterUsername(username);
    await this.enterPassword(password);
    await this.clickSubmit();
  }

  async logout() {
    await this.click(this.logoutButton);
  }

  // ─── Getters ──────────────────────────────────────────────

  async getErrorMessage(): Promise<string> {
    return (await this.errorMessage.textContent()) || '';
  }

  async getSuccessHeading(): Promise<string> {
    return (await this.successMessage.textContent()) || '';
  }

  async isLogoutVisible(): Promise<boolean> {
    return await this.logoutButton.isVisible();
  }
}
