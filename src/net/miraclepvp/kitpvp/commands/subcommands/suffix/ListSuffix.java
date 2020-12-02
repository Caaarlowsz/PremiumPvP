package net.miraclepvp.kitpvp.commands.subcommands.suffix;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class ListSuffix implements CommandExecutor {

    Integer pos = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fSuffix"));
        player.sendMessage(color("&7A list of all suffixes."));
        player.sendMessage(color(""));
        Data.suffixes.forEach(suffix -> {
            pos++;
            player.sendMessage(Text.color("&5" + pos + ". &f" + suffix.getName() + "&8: &7" + suffix.getUuid()));
        });
        if(Data.suffixes.isEmpty())
            player.sendMessage(Text.color("&cNo suffixes added yet"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        pos=0;
        return true;
    }
}
