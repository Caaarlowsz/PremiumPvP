package me.sahustei.miraclepvp.commands.subcommands.prefix;

import me.sahustei.miraclepvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpPrefix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&fPrefix");
        ChatCenterUtil.sendCenteredMessage(player, "&7Manage the prefixes in the server.");
        ChatCenterUtil.sendCenteredMessage(player, "");
        ChatCenterUtil.sendCenteredMessage(player, "&f/prefix add <name> <prefix>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/prefix delete <name>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/prefix set <player> <name>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/prefix rename <name> <new_name>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/prefix setprefix <name> <prefix>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/prefix setweight <name> <weight>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/prefix clear <player>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/prefix info <name>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/prefix list");
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        return true;
    }
}
