package net.miraclepvp.kitpvp.commands.subcommands.namecolor;

import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpName implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&fNameColor");
        ChatCenterUtil.sendCenteredMessage(player, "&7Manage the namecolors in the server.");
        ChatCenterUtil.sendCenteredMessage(player, "");
        ChatCenterUtil.sendCenteredMessage(player, "&f/namecolor add <name> <namecolor> <icon> <price>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/namecolor delete <name>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/namecolor give <player> <name>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/namecolor rename <name> <new_name>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/namecolor price <name> <new_price>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/namecolor sellprice <name> <new_sellprice>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/namecolor icon <name> <icon>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/namecolor list");
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        return true;
    }
}
