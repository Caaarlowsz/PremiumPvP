package net.miraclepvp.kitpvp.commands.subcommands.prefix;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.prefix.Prefix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class DeletePrefix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(color("&cPlease use /prefix delete <name>"));
            return true;
        }
        try {
            if (Data.getPrefix(args[1]) == null) {
                sender.sendMessage(color("&cThere is no prefix with this name."));
                return true;
            }
        }catch(NoSuchElementException ex){
        }
        Prefix prefix = Data.getPrefix(args[1]);
        sender.sendMessage(Text.color("&aYou have succesfully deleted the prefix " + prefix.getName() + "!"));
        Data.prefixes.remove(prefix);
        return true;
    }
}
