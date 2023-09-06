package net.lostluma.moss_carpet.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.brigadier.CommandDispatcher;

import net.lostluma.moss_carpet.command.BlockPrinterCommand;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

@Mixin(Commands.class)
public class CommandsMixin {
	@Shadow
	@Final
	private CommandDispatcher<CommandSourceStack> dispatcher;

	@Inject(method = "<init>", at = @At("RETURN"))
	private void moss_carpet$onRegister(Commands.CommandSelection selection, CommandBuildContext buildContext, CallbackInfo callbackInfo) {
		BlockPrinterCommand.register(this.dispatcher, buildContext);
	}
}
