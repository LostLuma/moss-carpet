package net.lostluma.moss_carpet.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.lostluma.moss_carpet.MossCarpet;
import net.minecraft.core.registries.BuiltInRegistries;

@Mixin(BuiltInRegistries.class)
public class BuiltInRegistriesMixin {
	@Inject(method = "createContents()V", at = @At("RETURN"))
	private static void moss_carpet$afterContentRegistration(CallbackInfo callbackInfo) {
		MossCarpet.init();
	}
}
