package net.miraclepvp.kitpvp.commands.subcommands.guild.helpPages;

import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class HelpGuildOne implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = (Player)sender;
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&fGuild &7(1/2)");
        ChatCenterUtil.sendCenteredMessage(player, "&7Get help with our guild system.");
        ChatCenterUtil.sendCenteredMessage(player, "");
        player.sendMessage(color("&f/guild accept <guild name>"));
        player.sendMessage(color("&f/guild create <guild name>"));
        player.sendMessage(color("&f/guild disband"));
        player.sendMessage(color("&f/guild members"));
        player.sendMessage(color("&f/guild online"));
        player.sendMessage(color("&f/guild discord <discord link>"));
        player.sendMessage(color("&f/guild info"));
        player.sendMessage(color("&f/guild mute <player/everyone> <time>"));
        player.sendMessage(color("&f/guild rename <new name>"));
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        return true;
    }
}
