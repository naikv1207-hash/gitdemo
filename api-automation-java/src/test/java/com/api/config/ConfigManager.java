package com.api.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigManager — Singleton to load and provide config.properties values
 *
 * Usage:
 *   String baseUrl = ConfigManager.getInstance().getProperty("base.url");
 */
public class ConfigManager {

    private static ConfigManager instance;
    private final Properties properties = new Properties();

    private static final String CONFIG_PATH =
            "src/test/resources/config.properties";

    private ConfigManager() {
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            properties.load(fis);
            System.out.println("✅ Config loaded successfully.");
        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to load config.properties: " + e.getMessage());
        }
    }

    // ─── Singleton accessor ───────────────────────────────────
    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null) instance = new ConfigManager();
            }
        }
        return instance;
    }

    // ─── Getters ──────────────────────────────────────────────
    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) throw new RuntimeException("❌ Property not found: " + key);
        return value.trim();
    }

    public String getBaseUrl() {
        return getProperty("base.url");
    }

    public String getApiKey() {
        return getProperty("api.key");
    }
}
