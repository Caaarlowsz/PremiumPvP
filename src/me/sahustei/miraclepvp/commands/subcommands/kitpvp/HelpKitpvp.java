package me.sahustei.miraclepvp.commands.subcommands.kitpvp;

import me.sahustei.miraclepvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpKitpvp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = (Player)sender;
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&fKitPvP");
        ChatCenterUtil.sendCenteredMessage(player, "&7Aliases: kp.");
        ChatCenterUtil.sendCenteredMessage(player, "");
        ChatCenterUtil.sendCenteredMessage(player, "&f/kitpvp info");
        ChatCenterUtil.sendCenteredMessage(player, "&f/kitpvp lobby <player>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/kitpvp setlobby");
        ChatCenterUtil.sendCenteredMessage(player, "&f/kitpvp setmap");
        ChatCenterUtil.sendCenteredMessage(player, "&f/kitpvp buildmode");
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        return true;
    }
}
