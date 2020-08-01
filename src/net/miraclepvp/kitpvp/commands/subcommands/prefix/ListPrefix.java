package net.miraclepvp.kitpvp.commands.subcommands.prefix;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class ListPrefix implements CommandExecutor {

    Integer pos = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fPrefix"));
        player.sendMessage(color("&7A list of all prefixes."));
        player.sendMessage(color(""));
        Data.prefixes.forEach(prefix -> {
            pos++;
            player.sendMessage(Text.color("&5" + pos + ". &f" + prefix.getName() + "&8: &7" + prefix.getUuid()));
        });
        if(Data.prefixes.isEmpty()) player.sendMessage(Text.color("&cNo prefixes added yet"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        pos=0;
        return true;
    }
}
