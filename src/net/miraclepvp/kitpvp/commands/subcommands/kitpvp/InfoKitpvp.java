package net.miraclepvp.kitpvp.commands.subcommands.kitpvp;

import net.miraclepvp.kitpvp.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class InfoKitpvp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fKitPvP"));
        player.sendMessage(color("&7Version " + Main.getInstance().version));
        player.sendMessage(color(""));
        player.sendMessage(color("&fAuthor: &7" + Main.getInstance().getDescription().getAuthors().get(0)));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        return true;
    }
}
