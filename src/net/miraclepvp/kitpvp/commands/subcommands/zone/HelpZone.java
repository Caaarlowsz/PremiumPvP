package net.miraclepvp.kitpvp.commands.subcommands.zone;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class HelpZone implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fZone"));
        player.sendMessage(color(""));
        player.sendMessage(color("&f/zone create <name>"));
        player.sendMessage(color("&f/zone remove <name>"));
        player.sendMessage(color("&f/zone list"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        return true;
    }
}
