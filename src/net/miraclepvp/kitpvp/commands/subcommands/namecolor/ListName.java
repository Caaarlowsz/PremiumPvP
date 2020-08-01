package net.miraclepvp.kitpvp.commands.subcommands.namecolor;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class ListName implements CommandExecutor {

    Integer pos = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fNameColor"));
        player.sendMessage(color("&7A list of all namecolors."));
        player.sendMessage(color(""));
        Data.namecolors.forEach(namecolor -> {
            pos++;
            player.sendMessage(Text.color("&5" + pos + ". &f" + namecolor.getName() + "&8: &7" + namecolor.getUuid()));
        });
        if(Data.namecolors.isEmpty())
            player.sendMessage(Text.color("&cNo namecolors added yet"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        pos=0;
        return true;
    }
}
