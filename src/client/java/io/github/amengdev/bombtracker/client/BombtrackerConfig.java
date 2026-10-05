package io.github.amengdev.bombtracker.client;

import io.github.amengdev.bombtracker.Bombtracker;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public record BombtrackerConfig(String backendUrl) {
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("bombtracker.properties");

    public static BombtrackerConfig load() {
        Properties properties = new Properties();
        try {
            if (!Files.exists(PATH)) {
                properties.setProperty("backend_url", "");
                try (var out = Files.newOutputStream(PATH)) {
                    properties.store(out, "Bombtracker config");
                }
                Bombtracker.LOGGER.warn("Created {} Fill in your settings there.", PATH);
            } else {
                try (var in = Files.newInputStream(PATH)) {
                    properties.load(in);
                }
            }
        } catch (IOException e) {
            Bombtracker.LOGGER.error("Error loading Bombtracker config", e);
        }
        return new BombtrackerConfig(clean(properties.getProperty("backend_url")));
    }

    private static String clean(String value) {
        if (value == null || value.isBlank()) return null;
        return value.trim();
    }

}
