package io.github.amengdev.bombtracker.client;

import io.github.amengdev.bombtracker.Bombtracker;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class BombtrackerConfig {
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("bombtracker.properties");

    public static String loadWebHookUrl(){
        Properties properties = new Properties();
        try {
            if (!Files.exists(PATH)) {
                properties.setProperty("webhook_url", "");
                try (var out = Files.newOutputStream(PATH)){
                    properties.store(out, "Bomb Tracker config");
                }
                Bombtracker.LOGGER.warn("Created {}. Add your webhookl URL there", PATH);
                return null;
            }
            try (var in = Files.newInputStream(PATH)){
                properties.load(in);
            }
        }  catch (IOException e) { Bombtracker.LOGGER.error("Couldn't read config", e); return null;}
        String url = properties.getProperty("webhook_url", "").trim();
        return url.isEmpty() ? null : url;
    }

}
