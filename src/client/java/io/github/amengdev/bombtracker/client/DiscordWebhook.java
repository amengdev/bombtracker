package io.github.amengdev.bombtracker.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.github.amengdev.bombtracker.Bombtracker;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DiscordWebhook {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private final String url;

    public DiscordWebhook(String url) {
        this.url = url;
    }
    public void send(String content) {
        JsonObject body = new JsonObject();
        body.addProperty("content", content);

        // block mentions
        JsonObject mentions = new JsonObject();
        mentions.add("parse", new JsonArray());
        body.add("allowed_mentions", mentions);

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

        CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(res -> {
                        if (res.statusCode() >= 300) {
                            Bombtracker.LOGGER.warn("Webhook failed ({}): {}", res.statusCode(), res.body());
                        }
                })
                .exceptionally(e ->{
                    Bombtracker.LOGGER.warn("webhook error", e);
                    return null;
                });

    }


}
