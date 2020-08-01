package net.miraclepvp.kitpvp.commands.subcommands.namecolor;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.namecolor.Namecolor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class DeleteName implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 2){
            sender.sendMessage(Text.color("&cPlease use /namecolor delete <name>"));
            return true;
        }
        try {
            if (Data.getNamecolor(args[1]) == null) {
                sender.sendMessage(Text.color("&cThere is no namecolor with this name."));
                return true;
            } else {
                Namecolor namecolor = Data.getNamecolor(args[1]);
                sender.sendMessage(Text.color("&aYou have succesfully deleted the namecolor " + namecolor.getName() + "!"));
                Data.namecolors.remove(namecolor);
                return true;
            }
        } catch(NoSuchElementException ex){
            sender.sendMessage(Text.color("&cThere is no namecolor with this name."));
            return true;
        }
    }
}
