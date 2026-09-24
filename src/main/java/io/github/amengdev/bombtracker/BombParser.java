package io.github.amengdev.bombtracker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BombParser {
    private static final Pattern BOMB = Pattern.compile("^[^\\p{L}\\p{N}]*(\\w+) has thrown an? (.+?) Bomb on (\\w+)$");

    public record Bomb(String player, String type, String server) {}

    public static Bomb parse(String raw) {
        String text = raw.replaceAll("\\s*\\n[^\\p{L}\\p{N}]*", " ");
        Matcher m = BOMB.matcher(text);
        if (!m.matches()) { return null;}
        return new Bomb (m.group(1), m.group(2), m.group(3));
    }
}
