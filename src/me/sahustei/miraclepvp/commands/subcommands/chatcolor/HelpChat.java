package me.sahustei.miraclepvp.commands.subcommands.chatcolor;

import me.sahustei.miraclepvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpChat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&fChatColor");
        ChatCenterUtil.sendCenteredMessage(player, "&7Manage the chatcolors in the server.");
        ChatCenterUtil.sendCenteredMessage(player, "");
        ChatCenterUtil.sendCenteredMessage(player, "&f/chatcolor add <name> <chatcolor> <icon> <price>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/chatcolor delete <name>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/chatcolor give <player> <name>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/chatcolor rename <name> <new_name>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/chatcolor price <name> <new_price>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/chatcolor sellprice <name> <new_sellprice>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/chatcolor icon <name> <icon>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/chatcolor list");
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        return true;
    }
}
