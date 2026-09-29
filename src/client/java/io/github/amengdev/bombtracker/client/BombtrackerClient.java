package io.github.amengdev.bombtracker.client;

import io.github.amengdev.bombtracker.BombParser;
import io.github.amengdev.bombtracker.Bombtracker;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;

public class BombtrackerClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		String url = BombtrackerConfig.loadWebHookUrl();
		DiscordWebhook webhook = (url != null) ? new DiscordWebhook(url) : null;

		// TODO: move to config
		BackendClient backend = new BackendClient("http://localhost:8080");
		ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
			if (overlay) return;

			BombParser.Bomb bomb = BombParser.parse(message.getString());
			if (bomb == null) return;

			Bombtracker.LOGGER.info("BOMB FOUND1!!!11: {} on {} by {}",
					bomb.type(), bomb.server(), bomb.player());
			backend.report(bomb);
			if (webhook != null) {
				webhook.send(String.format(" %s bomb thrown on %s by %s",
						bomb.type(),
						bomb.server(),
						bomb.player()));
			}
		});
	}
}