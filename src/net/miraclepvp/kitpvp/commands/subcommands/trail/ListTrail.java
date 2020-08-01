package net.miraclepvp.kitpvp.commands.subcommands.trail;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class ListTrail implements CommandExecutor {

    Integer pos = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fTrail"));
        player.sendMessage(color("&7A list of all trails."));
        player.sendMessage(color(""));
        Data.trails.forEach(trail -> {
            pos++;
            player.sendMessage(Text.color("&5" + pos + ". &f" + trail.getName() + "&8: &7" + trail.getUuid()));
        });
        if(Data.trails.isEmpty())
            player.sendMessage(Text.color("&cNo trails added yet"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        pos=0;
        return true;
    }
}
