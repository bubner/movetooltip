package me.bubner.movetooltip.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import me.bubner.movetooltip.MoveTooltip;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.network.chat.Component;

public class Settings {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("tooltip")
                .then(ClientCommandManager.argument("offset", IntegerArgumentType.integer())
                        .executes(context -> {
                            int offset = IntegerArgumentType.getInteger(context, "offset");
                            MoveTooltip.setOffset(offset);
                            context.getSource().sendFeedback(
                                    Component.literal("§7Tooltip Y offset set to " + offset + " pixels!"));
                            return 1;
                        }))
                .executes(context -> {
                    context.getSource().sendFeedback(
                            Component.literal("§7Usage: /tooltip <y-offset-px>"));
                    return 1;
                }));
    }
}