package net.miraclepvp.kitpvp.commands.subcommands.map;

import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpMap implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ChatCenterUtil.sendCenteredMessage(sender, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(sender, "&fMap");
        ChatCenterUtil.sendCenteredMessage(sender, "&7Manage the maps in the server.");
        ChatCenterUtil.sendCenteredMessage(sender, "");
        ChatCenterUtil.sendCenteredMessage(sender, "&f/map create <name>");
        ChatCenterUtil.sendCenteredMessage(sender, "&f/map remove <name>");
        ChatCenterUtil.sendCenteredMessage(sender, "&f/map teleport <name>");
        ChatCenterUtil.sendCenteredMessage(sender, "&f/map list");
        ChatCenterUtil.sendCenteredMessage(sender, "&f/map icon <name> <icon>");
        ChatCenterUtil.sendCenteredMessage(sender, "&f/map name <name> <new_name>");
        ChatCenterUtil.sendCenteredMessage(sender, "&f/map description <name> <description>");
        ChatCenterUtil.sendCenteredMessage(sender, "&5&m-----------------------------------------------------");
        return true;
    }
}
