package net.miraclepvp.kitpvp.commands.subcommands.prefix;

import net.miraclepvp.kitpvp.data.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class RenamePrefix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(color("&cPlease use /prefix rename <name> <new_name>"));
            return true;
        }
        try {
            if (Data.getPrefix(args[1]) == null) {
                sender.sendMessage(color("&cThere is no prefix with this name."));
                return true;
            }
        }catch(NoSuchElementException ex){
        }
        sender.sendMessage(color("&aYou've set the name of " + Data.getPrefix(args[1]).getName() + " to " + args[2] + "."));
        Data.getPrefix(args[1]).setName(args[2]);
        return true;
    }
}
