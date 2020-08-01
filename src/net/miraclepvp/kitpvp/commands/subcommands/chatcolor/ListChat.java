package net.miraclepvp.kitpvp.commands.subcommands.chatcolor;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class ListChat implements CommandExecutor {

    Integer pos = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&fChatColor"));
        player.sendMessage(color("&7A list of all chatcolors."));
        player.sendMessage(color( ""));
        Data.chatcolors.forEach(chatcolor -> {
            pos++;
            sender.sendMessage(Text.color("&5" + pos + ". &f" + chatcolor.getName() + "&8: &7" + chatcolor.getUuid()));
        });
        if(Data.chatcolors.isEmpty())
            sender.sendMessage(Text.color("&cNo chatcolors added yet"));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        pos=0;
        return true;
    }
}
