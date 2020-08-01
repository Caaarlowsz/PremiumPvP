package net.miraclepvp.kitpvp.commands.subcommands.namecolor;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.namecolor.Namecolor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class RenameName implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 3){
            sender.sendMessage(Text.color("&cPlease use /namecolor rename <name> <new_name>"));
            return true;
        }
        try {
            if (Data.getNamecolor(args[1]) == null) {
                sender.sendMessage(Text.color("&cThere is no namecolor with this name."));
                return true;
            } else {
                Namecolor namecolor = Data.getNamecolor(args[1]);
                sender.sendMessage(Text.color("&aYou have succesfully renamed the namecolor " + namecolor.getName() + "!"));
                namecolor.setName(args[2]);
                return true;
            }
        } catch(NoSuchElementException ex){
            sender.sendMessage(Text.color("&cThere is no namecolor with this name."));
            return true;
        }
    }
}
