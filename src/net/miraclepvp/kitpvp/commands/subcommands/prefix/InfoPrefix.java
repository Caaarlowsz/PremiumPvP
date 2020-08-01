package net.miraclepvp.kitpvp.commands.subcommands.prefix;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.bukkit.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class InfoPrefix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(Text.color("&cPlease use /prefix info <name>"));
            return true;
        }
        Player player = (Player)sender;
        try {
            if (Data.getPrefix(args[1]) == null) {
                sender.sendMessage(Text.color("&cThere is no prefix with this name."));
                return true;
            }
        }catch(NoSuchElementException ex){
        }
        //Prefix prefix = Data.getPrefix(args[1]);
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color( "&f" + Data.getPrefix(args[1]).getName()));
        player.sendMessage(color( "&7" + Data.getPrefix(args[1]).getUuid()));
        sender.sendMessage(Text.color("&f"));
        sender.sendMessage(Text.color("&fName: &7" + Data.getPrefix(args[1]).getName()));
        sender.sendMessage(Text.color("&fPrefix: &7" + Data.getPrefix(args[1]).getPrefix()));
        sender.sendMessage(Text.color("&fWeight: &7" + Data.getPrefix(args[1]).getWeight()));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        return true;
    }
}
