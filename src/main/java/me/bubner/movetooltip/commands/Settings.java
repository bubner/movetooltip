package me.bubner.movetooltip.commands;

import me.bubner.movetooltip.MoveTooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class Settings extends CommandBase {
    private static final String USE_COMMAND = "Usage: /tooltip <y-offset-px>";

    @Override
    public String getCommandName() {
        return "tooltip";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return USE_COMMAND;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1)
            throw new CommandException(USE_COMMAND);
        try {
            int offset = Integer.parseInt(args[0]);
            MoveTooltip.config
                    .get("settings", "offset", 0)
                    .set(offset);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
                    "§7Tooltip Y offset set to " + offset + " pixels!"));
        } catch (NumberFormatException e) {
            throw new CommandException(USE_COMMAND);
        } finally {
            MoveTooltip.config.save();
        }
    }
}
