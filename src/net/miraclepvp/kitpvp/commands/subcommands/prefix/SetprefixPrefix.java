package net.miraclepvp.kitpvp.commands.subcommands.prefix;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.bukkit.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class SetprefixPrefix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(Text.color("&cPlease use /prefix setprefix <name> <prefix>"));
            return true;
        }
        try {
            if (Data.getPrefix(args[1]) == null) {
                sender.sendMessage(Text.color("&cThere is no prefix with this name."));
                return true;
            }
        }catch(NoSuchElementException ex){
        }
        sender.sendMessage(Text.color("&aYou've set the prefix of " + Data.getPrefix(args[1]).getName() + " to " + args[2] + "."));
        Data.getPrefix(args[1]).setPrefix(args[2]);
        return true;
    }
}
