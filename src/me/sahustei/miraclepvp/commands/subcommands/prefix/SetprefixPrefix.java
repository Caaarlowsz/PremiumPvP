package me.sahustei.miraclepvp.commands.subcommands.prefix;

import me.sahustei.miraclepvp.data.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

import static me.sahustei.miraclepvp.bukkit.Text.color;

public class SetprefixPrefix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(color("&cPlease use /prefix setprefix <name> <prefix>"));
            return true;
        }
        try {
            if (Data.getPrefix(args[1]) == null) {
                sender.sendMessage(color("&cThere is no prefix with this name."));
                return true;
            }
        }catch(NoSuchElementException ex){
        }
        sender.sendMessage(color("&aYou've set the prefix of " + Data.getPrefix(args[1]).getName() + " to " + args[2] + "."));
        Data.getPrefix(args[1]).setPrefix(args[2]);
        return true;
    }
}
