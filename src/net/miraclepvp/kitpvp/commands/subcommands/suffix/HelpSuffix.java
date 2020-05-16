package net.miraclepvp.kitpvp.commands.subcommands.suffix;

import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpSuffix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&fSuffix");
        ChatCenterUtil.sendCenteredMessage(player, "&7Manage the suffixes in the server.");
        ChatCenterUtil.sendCenteredMessage(player, "");
        ChatCenterUtil.sendCenteredMessage(player, "&f/suffix add <name> <suffix> <icon> <price>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/suffix delete <name>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/suffix give <player> <name>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/suffix remove <player> <name>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/suffix buyable <name>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/suffix setsuffix <name> <suffix>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/suffix rename <name> <new_name>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/suffix price <name> <new_price>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/suffix sellprice <name> <new_sellprice>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/suffix icon <name> <icon>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/suffix list");
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        return true;
    }
}
