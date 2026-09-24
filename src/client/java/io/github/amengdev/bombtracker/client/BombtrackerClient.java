package io.github.amengdev.bombtracker.client;

import io.github.amengdev.bombtracker.BombParser;
import io.github.amengdev.bombtracker.Bombtracker;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;

public class BombtrackerClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
			if (overlay) return;

			BombParser.Bomb bomb = BombParser.parse(message.getString());
			if (bomb != null) {
				Bombtracker.LOGGER.info("BOMB FOUND1!!!11: {} on {} by {}",
						bomb.type(), bomb.server(), bomb.player());
			}
		});
	}
}