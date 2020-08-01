package net.miraclepvp.kitpvp.commands.subcommands.zone;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class ListZone implements CommandExecutor {

    Integer pos = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fZone"));
        player.sendMessage(color("&7A list of all zones."));
        player.sendMessage(color(""));
        Data.zones.forEach(zone -> {
            pos++;
            player.sendMessage(Text.color("&5" + pos + ". &f" + zone.getName() + "&8: &7" + zone.getUuid()));
        });
        if(Data.zones.isEmpty())
            player.sendMessage(Text.color("&cNo zones added yet"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        pos=0;
        return true;
    }
}
