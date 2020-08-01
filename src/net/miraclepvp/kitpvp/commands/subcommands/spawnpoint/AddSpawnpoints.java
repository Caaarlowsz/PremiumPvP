package net.miraclepvp.kitpvp.commands.subcommands.spawnpoint;

import net.miraclepvp.kitpvp.data.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class AddSpawnpoints implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        Config.getSpawnpoints().add(player.getLocation());
        player.sendMessage(color("&aYour location has been added as a spawnpoint."));
        return true;
    }
}
