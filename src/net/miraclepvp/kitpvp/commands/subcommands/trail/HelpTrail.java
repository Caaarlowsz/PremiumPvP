package net.miraclepvp.kitpvp.commands.subcommands.trail;

import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpTrail implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&fTrail");
        ChatCenterUtil.sendCenteredMessage(player, "&7Manage the trails in the server.");
        ChatCenterUtil.sendCenteredMessage(player, "");
        ChatCenterUtil.sendCenteredMessage(player, "&f/trail add <name> <particle> <icon> <price>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/trail delete <name>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/trail give <player> <name>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/trail rename <name> <new_name>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/trail price <name> <new_price>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/trail sellprice <name> <new_sellprice>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/trail icon <name> <icon>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/trail list");
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        return true;
    }
}
