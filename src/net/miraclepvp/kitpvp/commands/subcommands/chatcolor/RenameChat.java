package net.miraclepvp.kitpvp.commands.subcommands.chatcolor;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.chatcolor.Chatcolor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class RenameChat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 3){
            sender.sendMessage(Text.color("&cPlease use /chatcolor rename <name> <new_name>"));
            return true;
        }
        try {
            if (Data.getChatcolor(args[1]) == null) {
                sender.sendMessage(Text.color("&cThere is no chatcolor with this name."));
                return true;
            } else {
                Chatcolor chatcolor = Data.getChatcolor(args[1]);
                sender.sendMessage(Text.color("&aYou have succesfully renamed the chatcolor " + chatcolor.getName() + "!"));
                chatcolor.setName(args[2]);
                return true;
            }
        } catch(NoSuchElementException ex){
            sender.sendMessage(Text.color("&cThere is no chatcolor with this name."));
            return true;
        }
    }
}
