package net.miraclepvp.kitpvp.commands.subcommands.kit;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class ListKit implements CommandExecutor {

    Integer pos = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fKit"));
        player.sendMessage(color("&7A list of all kits."));
        player.sendMessage(color(""));
        Data.kits.forEach(kit -> {
            pos++;
            player.sendMessage(Text.color("&5" + pos + ". &f" + kit.getName() + "&8: &7" + kit.getUuid()));
        });
        if(Data.kits.isEmpty())
            player.sendMessage(Text.color("&cNo kits added yet"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        pos=0;
        return true;
    }
}
