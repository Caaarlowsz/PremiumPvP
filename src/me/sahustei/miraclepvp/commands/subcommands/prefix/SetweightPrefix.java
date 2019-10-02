package me.sahustei.miraclepvp.commands.subcommands.prefix;

import me.sahustei.miraclepvp.data.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

import static me.sahustei.miraclepvp.bukkit.Text.color;

public class SetweightPrefix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(color("&cPlease use /prefix setweight <name> <weight>"));
            return true;
        }
        try {
            if (Data.getPrefix(args[1]) == null) {
                sender.sendMessage(color("&cThere is no prefix with this name."));
                return true;
            }
        }catch(NoSuchElementException ex){
        }
        try{
            Integer value = Integer.parseInt(args[2]);
            if(value < 0 || value.toString().length() > 1){
                sender.sendMessage(color("&cThe given weight is not valid, make sure the amount is between 0 and 9."));
                return true;
            }
            sender.sendMessage(color("&aYou've set the weight of " + Data.getPrefix(args[1]).getName() + " to " + value + "."));
            Data.getPrefix(args[1]).setWeight(value);
            return true;
        }catch(IllegalArgumentException ex){
            sender.sendMessage(color("&cThe given weight is not a number."));
            return true;
        }
    }
}
