package net.lostluma.moss_carpet.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.lostluma.moss_carpet.MossCarpetSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.PhantomSpawner;

@Mixin(PhantomSpawner.class)
public class PhantomSpawnerMixin {
	private boolean isInsomniaDisabled(Integer position, Integer spawn) {
		return Math.abs(position - spawn) < MossCarpetSettings.insomniaDisabledDistance;
	}

	@Redirect(
		method = "tick(Lnet/minecraft/server/level/ServerLevel;ZZ)I",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;isSpectator()Z")
	)
	private boolean moss_carpet$shouldDisablePhantoms(ServerPlayer player) {
		BlockPos position = player.blockPosition();
		BlockPos spawn = player.level().getSharedSpawnPos();

		return player.isSpectator() || (isInsomniaDisabled(position.getX(), spawn.getX()) && isInsomniaDisabled(position.getZ(), spawn.getZ()));
	}
}
