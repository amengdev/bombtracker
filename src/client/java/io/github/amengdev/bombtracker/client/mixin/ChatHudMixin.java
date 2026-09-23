package io.github.amengdev.bombtracker.client.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.components.ChatComponent;



import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatComponent.class)
public class ChatHudMixin {
	@Inject(at = @At("HEAD"), method = "addMessage(Lnet/minecraft/network/chat/Component;)V")
	private void onMessage(Component message, CallbackInfo ci) {
		System.out.println("[chat message] " + message.getString());
	}
}