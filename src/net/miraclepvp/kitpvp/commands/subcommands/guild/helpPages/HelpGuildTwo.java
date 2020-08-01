package net.miraclepvp.kitpvp.commands.subcommands.guild.helpPages;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class HelpGuildTwo implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fGuild &7(2/2)"));
        player.sendMessage(color("&7Get help with our guild system."));
        player.sendMessage(color(""));
        player.sendMessage(color("&f/guild slow"));
        player.sendMessage(color("&f/guild invite <player>"));
        player.sendMessage(color("&f/guild motd <motd>"));
        player.sendMessage(color("&f/guild tranfer <player>"));
        player.sendMessage(color("&f/guild promote <player>"));
        player.sendMessage(color("&f/guild demote <player>"));
        player.sendMessage(color("&f/guild kick <player>"));
        player.sendMessage(color("&f/guild chat <message>"));
        player.sendMessage(color("&f/guild leave"));
        player.sendMessage(color("&f/guild permissions"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        return true;
    }
}
