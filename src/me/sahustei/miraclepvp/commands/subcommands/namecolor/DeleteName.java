package me.sahustei.miraclepvp.commands.subcommands.namecolor;

import me.sahustei.miraclepvp.bukkit.Text;
import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.chatcolor.Chatcolor;
import me.sahustei.miraclepvp.data.namecolor.Namecolor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.naming.Name;
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
