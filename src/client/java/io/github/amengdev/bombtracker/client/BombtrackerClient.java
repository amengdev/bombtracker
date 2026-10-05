package io.github.amengdev.bombtracker.client;

import io.github.amengdev.bombtracker.BombParser;
import io.github.amengdev.bombtracker.Bombtracker;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;

public class BombtrackerClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		BombtrackerConfig config = BombtrackerConfig.load();
		DiscordWebhook webhook = config.webhookUrl() != null ? new DiscordWebhook(config.webhookUrl()) : null;
		BackendClient backend = config.backendUrl() != null ? new BackendClient(config.backendUrl()) : null;


		ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
			if (overlay) return;

			BombParser.Bomb bomb = BombParser.parse(message.getString());
			if (bomb == null) return;

			Bombtracker.LOGGER.info("BOMB FOUND1!!!11: {} on {} by {}",
					bomb.type(), bomb.server(), bomb.player());

			if (backend != null) {
				backend.report(bomb);
			}

			if (webhook != null) {
				webhook.send(String.format(" %s bomb thrown on %s by %s",
						bomb.type(),
						bomb.server(),
						bomb.player()));
			}
		});
	}
}