# 🎭 Playwright Automation Framework

A beginner-friendly, production-ready Playwright test automation project using **TypeScript** and **Page Object Model (POM)**.

---

## 📁 Project Structure

```
playwright-project/
│
├── tests/                     ← Test spec files
│   ├── login.spec.ts          ← UI login tests
│   └── api.spec.ts            ← API tests
│
├── pages/                     ← Page Object Model classes
│   ├── BasePage.ts            ← Common methods (parent class)
│   └── LoginPage.ts           ← Login page actions & locators
│
├── utils/                     ← Reusable helper functions
│   └── helpers.ts
│
├── test-data/                 ← Test input data (JSON)
│   └── loginData.json
│
├── reports/                   ← Auto-generated test reports
│
├── playwright.config.ts       ← Main Playwright configuration
├── tsconfig.json              ← TypeScript configuration
└── package.json               ← Project dependencies & scripts
```

---

## 🚀 Getting Started

### Step 1 — Install dependencies
```bash
npm install
```

### Step 2 — Install Playwright browsers
```bash
npx playwright install
```

### Step 3 — Run all tests
```bash
npm test
```

---

## 🧪 Running Tests

| Command | Description |
|---|---|
| `npm test` | Run all tests (headless) |
| `npm run test:headed` | Run with browser visible |
| `npm run test:ui` | Open visual UI mode |
| `npm run test:debug` | Step-through debugger |
| `npm run test:chrome` | Run only on Chrome |
| `npm run test:firefox` | Run only on Firefox |
| `npm run test:report` | View HTML report |
| `npm run codegen` | Auto-generate test code |

---

## 🌐 Test Sites Used

| Suite | URL |
|---|---|
| UI Login Tests | https://practicetestautomation.com/practice-test-login/ |
| API Tests | https://jsonplaceholder.typicode.com |

---

## 📊 Reports

After running tests, view the HTML report:
```bash
npm run test:report
```

Screenshots and videos are saved automatically on failure inside `reports/`.

---

## 🧩 Adding a New Page Object

1. Create a new file in `pages/`, e.g. `pages/DashboardPage.ts`
2. Extend `BasePage`
3. Define locators in the constructor
4. Add action and getter methods
5. Import and use in your test spec

```typescript
import { Page, Locator } from '@playwright/test';
import { BasePage } from './BasePage';

export class DashboardPage extends BasePage {
  readonly heading: Locator;

  constructor(page: Page) {
    super(page);
    this.heading = page.locator('h1');
  }

  async getHeadingText(): Promise<string> {
    return (await this.heading.textContent()) || '';
  }
}
```

---

## ✅ Best Practices Followed

- ✅ Page Object Model (POM) pattern
- ✅ Centralized test data in JSON
- ✅ Base class for common utilities
- ✅ Descriptive test case IDs (TC01, TC02...)
- ✅ Screenshot & video on failure
- ✅ HTML report generation
- ✅ TypeScript for type safety
