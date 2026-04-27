import { test, expect } from '@playwright/test';

/**
 * API Test Suite
 * Tests against JSONPlaceholder — a free fake REST API
 * URL: https://jsonplaceholder.typicode.com
 */
test.describe('API Tests - JSONPlaceholder', () => {

  const BASE_URL = 'https://jsonplaceholder.typicode.com';

  // ─── GET Requests ─────────────────────────────────────────

  test('TC01 - GET all posts returns 200 and 100 posts', async ({ request }) => {
    const response = await request.get(`${BASE_URL}/posts`);

    expect(response.status()).toBe(200);

    const posts = await response.json();
    expect(posts).toHaveLength(100);
  });

  test('TC02 - GET single post by ID returns correct data', async ({ request }) => {
    const response = await request.get(`${BASE_URL}/posts/1`);

    expect(response.status()).toBe(200);

    const post = await response.json();
    expect(post.id).toBe(1);
    expect(post.userId).toBeDefined();
    expect(post.title).toBeDefined();
    expect(post.body).toBeDefined();
  });

  // ─── POST Request ─────────────────────────────────────────

  test('TC03 - POST creates a new post and returns 201', async ({ request }) => {
    const newPost = {
      title: 'Playwright API Test',
      body: 'This is a test post created by Playwright',
      userId: 1,
    };

    const response = await request.post(`${BASE_URL}/posts`, {
      data: newPost,
    });

    expect(response.status()).toBe(201);

    const created = await response.json();
    expect(created.title).toBe(newPost.title);
    expect(created.id).toBeDefined();
  });

  // ─── PUT Request ──────────────────────────────────────────

  test('TC04 - PUT updates an existing post', async ({ request }) => {
    const updatedPost = {
      id: 1,
      title: 'Updated Title',
      body: 'Updated body content',
      userId: 1,
    };

    const response = await request.put(`${BASE_URL}/posts/1`, {
      data: updatedPost,
    });

    expect(response.status()).toBe(200);

    const result = await response.json();
    expect(result.title).toBe('Updated Title');
  });

  // ─── DELETE Request ───────────────────────────────────────

  test('TC05 - DELETE a post returns 200', async ({ request }) => {
    const response = await request.delete(`${BASE_URL}/posts/1`);
    expect(response.status()).toBe(200);
  });

  // ─── Response Header Check ────────────────────────────────

  test('TC06 - Response has correct Content-Type header', async ({ request }) => {
    const response = await request.get(`${BASE_URL}/posts/1`);
    const contentType = response.headers()['content-type'];
    expect(contentType).toContain('application/json');
  });

});
