package net.miraclepvp.kitpvp.commands.subcommands.prefix;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.bukkit.Text;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

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
        String value = args[2];
        try {
            ChatColor.getByChar(value);
            sender.sendMessage(color("&aYou've set the weight of " + Data.getPrefix(args[1]).getName() + " to " + value + "."));
            Data.getPrefix(args[1]).setWeight(value);
            return true;
        }catch (Exception ex){
            sender.sendMessage(color("&cThe weight has to be a chatcolor."));
            return true;
        }
    }
}
