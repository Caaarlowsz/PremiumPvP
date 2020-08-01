package net.miraclepvp.kitpvp.commands.subcommands.suffix;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.suffix.Suffix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class BuyableSuffix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 2){
            sender.sendMessage(Text.color("&cPlease use /suffix buyable <name>"));
            return true;
        }
        try {
            if (Data.getSuffix(args[1]) == null) {
                sender.sendMessage(Text.color("&cThere is no suffix with this name."));
                return true;
            } else {
                Suffix suffix = Data.getSuffix(args[1]);
                suffix.setBuyable(!suffix.getBuyable());
                sender.sendMessage(Text.color("&aYou have successfully set the suffix " + suffix.getName() + " to " + (suffix.getBuyable() ? "": "not ") + "buyable!"));
                return true;
            }
        } catch(NoSuchElementException ex){
            sender.sendMessage(Text.color("&cThere is no suffix with this name."));
            return true;
        }
    }
}
