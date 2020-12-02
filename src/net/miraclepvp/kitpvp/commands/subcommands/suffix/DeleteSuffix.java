package net.miraclepvp.kitpvp.commands.subcommands.suffix;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.suffix.Suffix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class DeleteSuffix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 2){
            sender.sendMessage(Text.color("&cPlease use /suffix delete <name>"));
            return true;
        }
        try {
            if (Data.getSuffix(args[1]) == null) {
                sender.sendMessage(Text.color("&cThere is no suffix with this name."));
                return true;
            } else {
                Suffix suffix = Data.getSuffix(args[1]);
                sender.sendMessage(Text.color("&aYou have succesfully deleted the suffix " + suffix.getName() + "!"));
                Data.suffixes.remove(suffix);
                return true;
            }
        } catch(NoSuchElementException ex){
            sender.sendMessage(Text.color("&cThere is no suffix with this name."));
            return true;
        }
    }
}
