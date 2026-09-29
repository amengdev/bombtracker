package io.github.amengdev.bombtracker.client;

import com.google.gson.JsonObject;
import io.github.amengdev.bombtracker.BombParser;
import io.github.amengdev.bombtracker.Bombtracker;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class BackendClient {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private final URI endpoint;

    public BackendClient(String baseURL) {
        this.endpoint = URI.create(baseURL + "/bombs");
    }

    public void report(BombParser.Bomb bomb){
        JsonObject body = new JsonObject();
        body.addProperty("player", bomb.player());
        body.addProperty("type", bomb.type());
        body.addProperty("server", bomb.server());

        HttpRequest request = HttpRequest.newBuilder(endpoint)
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(5))
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

        CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(res -> {
                    if (res.statusCode() != 202) {
                        Bombtracker.LOGGER.warn("Backend rejected bomb ({}): {}", res.statusCode(), res.body());
                    }
                })
                .exceptionally(e -> {
                    Bombtracker.LOGGER.warn("Couldn't reach backend: {}", e.getMessage());
                    return null;
                });
    }
}
