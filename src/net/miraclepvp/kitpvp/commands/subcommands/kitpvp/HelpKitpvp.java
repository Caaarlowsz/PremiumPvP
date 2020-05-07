package net.miraclepvp.kitpvp.commands.subcommands.kitpvp;

import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
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
        ChatCenterUtil.sendCenteredMessage(player, "&f/kitpvp map <player>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/kitpvp realname <player>");
        ChatCenterUtil.sendCenteredMessage(player, "&f/kitpvp setlobby");
        ChatCenterUtil.sendCenteredMessage(player, "&f/kitpvp buildmode");
        ChatCenterUtil.sendCenteredMessage(player, "&f/kitpvp gettracker");
        ChatCenterUtil.sendCenteredMessage(player, "&f/kitpvp getanvil");
        ChatCenterUtil.sendCenteredMessage(player, "&f/kitpvp reload");
        ChatCenterUtil.sendCenteredMessage(player, "&f/kitpvp save");
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        return true;
    }
}
