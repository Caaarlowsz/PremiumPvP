package net.miraclepvp.kitpvp.commands.subcommands.kitpvp;

import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InfoKitpvp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&fKitPvP");
        ChatCenterUtil.sendCenteredMessage(player, "&7Version " + Main.getInstance().version);
        ChatCenterUtil.sendCenteredMessage(player, "");
        ChatCenterUtil.sendCenteredMessage(player, "&fAuthor: &7AlmostSomeone");
        ChatCenterUtil.sendCenteredMessage(player, "&fWebsite: &7www.sahustei.nl");
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        return true;
    }
}
