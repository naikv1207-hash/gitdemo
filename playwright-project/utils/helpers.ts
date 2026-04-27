import { Page } from '@playwright/test';
import * as fs from 'fs';
import * as path from 'path';

/**
 * Utility helpers for common Playwright tasks
 */

// ─── Wait Helpers ─────────────────────────────────────────

export async function waitForSeconds(seconds: number) {
  await new Promise(resolve => setTimeout(resolve, seconds * 1000));
}

// ─── Screenshot Helpers ───────────────────────────────────

export async function takeScreenshot(page: Page, name: string) {
  const dir = 'reports/screenshots';
  if (!fs.existsSync(dir)) fs.mkdirSync(dir, { recursive: true });

  const timestamp = new Date().toISOString().replace(/[:.]/g, '-');
  const filePath = path.join(dir, `${name}-${timestamp}.png`);
  await page.screenshot({ path: filePath, fullPage: true });
  console.log(`📸 Screenshot saved: ${filePath}`);
}

// ─── Random Data Generators ───────────────────────────────

export function generateRandomEmail(): string {
  const timestamp = Date.now();
  return `testuser${timestamp}@example.com`;
}

export function generateRandomString(length: number = 8): string {
  const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
  return Array.from({ length }, () =>
    chars.charAt(Math.floor(Math.random() * chars.length))
  ).join('');
}

// ─── File Helpers ─────────────────────────────────────────

export function readJsonFile(filePath: string): Record<string, unknown> {
  const fullPath = path.resolve(filePath);
  const raw = fs.readFileSync(fullPath, 'utf-8');
  return JSON.parse(raw);
}

// ─── URL Helpers ──────────────────────────────────────────

export function buildURL(base: string, path: string): string {
  return `${base.replace(/\/$/, '')}/${path.replace(/^\//, '')}`;
}
