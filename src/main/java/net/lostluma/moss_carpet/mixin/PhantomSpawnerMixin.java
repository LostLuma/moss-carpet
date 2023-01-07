package net.lostluma.moss_carpet.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.lostluma.moss_carpet.MossCarpetSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.PhantomSpawner;

@Mixin(PhantomSpawner.class)
public class PhantomSpawnerMixin {
	private boolean isInsomniaDisabled(Integer position, Integer spawn) {
		return Math.abs(position - spawn) < MossCarpetSettings.insomniaDisabledDistance;
	}

	@Redirect(
		method = "tick(Lnet/minecraft/server/level/ServerLevel;ZZ)I",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isSpectator()Z")
	)
	private boolean moss_carpet$shouldDisablePhantoms(Player player) {
		BlockPos position = player.blockPosition();
		BlockPos spawn = player.getLevel().getSharedSpawnPos();

		return player.isSpectator() || (isInsomniaDisabled(position.getX(), spawn.getX()) && isInsomniaDisabled(position.getZ(), spawn.getZ()));
	}
}
