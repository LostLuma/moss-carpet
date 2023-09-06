package net.lostluma.moss_carpet.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

import static net.minecraft.commands.Commands.literal;

public class BlockPrinterCommand {
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
		dispatcher.register(
			literal("block-printer")
				.requires(source -> source.hasPermission(2))
				.requires(CommandSourceStack::isPlayer)
				.executes(commandContext -> printBlocks(commandContext.getSource()))
		);
	}

	public static int printBlocks(CommandSourceStack source) {
		Level world = source.getLevel();
		ServerPlayer player = source.getPlayer();

		BlockPos startPos = player.getOnPos().above();
		Direction lookingDirection = player.getMotionDirection();

		Direction forwardDirection = lookingDirection.getAxis() == Axis.Y ? Direction.NORTH : lookingDirection;
		Direction rightDirection = lookingDirection.getClockWise();

		int blocksIterated = 0;
		int rowLength = 16;

        for (var block : BuiltInRegistries.BLOCK) {
			BlockPos placePos = startPos
				.relative(rightDirection, blocksIterated % rowLength + 1)
				.relative(forwardDirection, Math.floorDiv(blocksIterated, rowLength));

			world.setBlock(placePos, block.defaultBlockState(), 2);

			blocksIterated++;
		}

		int blocksFilled = blocksIterated;
		source.sendSuccess(() -> Component.translatable("commands.fill.success", blocksFilled), true);

		return Command.SINGLE_SUCCESS;
	}
}
