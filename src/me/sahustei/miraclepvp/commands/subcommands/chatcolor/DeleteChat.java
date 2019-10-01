package me.sahustei.miraclepvp.commands.subcommands.chatcolor;

import me.sahustei.miraclepvp.bukkit.Text;
import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.chatcolor.Chatcolor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class DeleteChat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 2){
            sender.sendMessage(Text.color("&cPlease use /chatcolor delete <name>"));
            return true;
        }
        try {
            if (Data.getChatcolor(args[1]) == null) {
                sender.sendMessage(Text.color("&cThere is no chatcolor with this name."));
                return true;
            } else {
                Chatcolor chatcolor = Data.getChatcolor(args[1]);
                sender.sendMessage(Text.color("&aYou have succesfully deleted the chatcolor " + chatcolor.getName() + "!"));
                Data.chatcolors.remove(chatcolor);
                return true;
            }
        } catch(NoSuchElementException ex){
            sender.sendMessage(Text.color("&cThere is no chatcolor with this name."));
            return true;
        }
    }
}
