package net.miraclepvp.kitpvp.commands.subcommands.serverevent;

import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpServerevent implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&fServer Event");
        ChatCenterUtil.sendCenteredMessage(player, "");
        if(player.hasPermission("miraclepvp.serverevent")) {
            ChatCenterUtil.sendCenteredMessage(player, "&f/serverevent start");
            ChatCenterUtil.sendCenteredMessage(player, "&f/serverevent stop");
            ChatCenterUtil.sendCenteredMessage(player, "&f/serverevent open");
            ChatCenterUtil.sendCenteredMessage(player, "&f/serverevent close");
            ChatCenterUtil.sendCenteredMessage(player, "&f/serverevent broadcast");
            ChatCenterUtil.sendCenteredMessage(player, "&f/serverevent setlocation");
            ChatCenterUtil.sendCenteredMessage(player, "&f/serverevent setkit <kitname>");
        }
        ChatCenterUtil.sendCenteredMessage(player, "&f/serverevent join");
        ChatCenterUtil.sendCenteredMessage(player, "&f/serverevent leave");
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        return true;
    }
}
