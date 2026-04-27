import { defineConfig, devices } from '@playwright/test';

export default defineConfig({
  // Directory where tests are located
  testDir: './tests',

  // Run tests in files in parallel
  fullyParallel: true,

  // Fail the build on CI if you accidentally left test.only in the source code
  forbidOnly: !!process.env.CI,

  // Retry on CI only
  retries: process.env.CI ? 2 : 1,

  // Reporter to use
  reporter: [
    ['html', { outputFolder: 'reports/html-report', open: 'never' }],
    ['list'],
  ],

  // Global timeout for each test
  timeout: 30000,

  use: {
    // Base URL — change this to your app URL
    baseURL: 'https://practicetestautomation.com',

    // Collect trace when retrying the failed test
    trace: 'on-first-retry',

    // Take screenshot only on failure
    screenshot: 'only-on-failure',

    // Record video only on failure
    video: 'retain-on-failure',

    // Run in headless mode
    headless: true,
  },

  // Configure projects for major browsers
  projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    },
    {
      name: 'firefox',
      use: { ...devices['Desktop Firefox'] },
    },
    {
      name: 'webkit',
      use: { ...devices['Desktop Safari'] },
    },
    // Mobile viewports
    {
      name: 'Mobile Chrome',
      use: { ...devices['Pixel 5'] },
    },
  ],
});
