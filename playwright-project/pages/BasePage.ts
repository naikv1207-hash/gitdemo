import { Page, Locator, expect } from '@playwright/test';

/**
 * BasePage — parent class for all Page Objects
 * Contains common reusable methods
 */
export class BasePage {
  readonly page: Page;

  constructor(page: Page) {
    this.page = page;
  }

  // ─── Navigation ───────────────────────────────────────────

  async goto(path: string = '/') {
    await this.page.goto(path);
  }

  async getTitle(): Promise<string> {
    return await this.page.title();
  }

  async getCurrentURL(): Promise<string> {
    return this.page.url();
  }

  // ─── Actions ──────────────────────────────────────────────

  async click(locator: Locator) {
    await locator.waitFor({ state: 'visible' });
    await locator.click();
  }

  async fill(locator: Locator, text: string) {
    await locator.waitFor({ state: 'visible' });
    await locator.clear();
    await locator.fill(text);
  }

  async selectDropdown(locator: Locator, value: string) {
    await locator.selectOption(value);
  }

  // ─── Assertions ───────────────────────────────────────────

  async assertVisible(locator: Locator) {
    await expect(locator).toBeVisible();
  }

  async assertText(locator: Locator, expectedText: string) {
    await expect(locator).toHaveText(expectedText);
  }

  async assertURL(expectedURL: string) {
    await expect(this.page).toHaveURL(expectedURL);
  }

  // ─── Utilities ────────────────────────────────────────────

  async takeScreenshot(name: string) {
    await this.page.screenshot({ path: `reports/screenshots/${name}.png` });
  }

  async waitForPageLoad() {
    await this.page.waitForLoadState('networkidle');
  }
}
