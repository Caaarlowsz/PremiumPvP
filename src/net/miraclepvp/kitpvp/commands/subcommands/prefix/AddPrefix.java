package net.miraclepvp.kitpvp.commands.subcommands.prefix;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.prefix.Prefix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class AddPrefix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(color("&cPlease use /prefix add <name> <prefix>"));
            return true;
        }
        try {
            if (Data.getPrefix(args[1]) != null) {
                sender.sendMessage(color("&cThere is already a prefix with this name."));
                return true;
            }
        }catch(NoSuchElementException ex){
        }
        Prefix prefix = new Prefix(args[1], args[2]);
        Data.prefixes.add(prefix);
        sender.sendMessage(color("&aYou have succesfully added the prefix " + prefix.getName() + "!"));
        return true;
    }
}
