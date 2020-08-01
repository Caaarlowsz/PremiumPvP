package net.miraclepvp.kitpvp.commands.subcommands.map;

import com.sun.xml.internal.bind.v2.util.StackRecorder;
import net.miraclepvp.kitpvp.data.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class ListMap implements CommandExecutor {

    Integer pos = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fMap"));
        player.sendMessage(color("&7A list of all maps."));
        player.sendMessage(color(""));
        Data.maps.forEach(map -> {
            pos++;
            sender.sendMessage(color("&5" + pos + ". &f" + map.name + "&8: &7" + map.uuid));
        });
        if(Data.maps.isEmpty())
            sender.sendMessage(color("&cNo maps added yet"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        pos=0;
        return true;
    }
}
