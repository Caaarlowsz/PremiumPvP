package net.miraclepvp.kitpvp.commands.subcommands.namecolor;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.namecolor.Namecolor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class SellpriceName implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 3){
            sender.sendMessage(Text.color("&cPlease use /namecolor sellprice <name> <new_sellprice>"));
            return true;
        }
        try {
            try {
                Integer newprice = Integer.parseInt(args[2]);
                if (Data.getNamecolor(args[1]) == null) {
                    sender.sendMessage(Text.color("&cThere is no namecolor with this name."));
                    return true;
                } else {
                    Namecolor namecolor = Data.getNamecolor(args[1]);
                    sender.sendMessage(Text.color("&aYou have succesfully changed the sellprice of the namecolor " + namecolor.getName() + "!"));
                    namecolor.setSell(newprice);
                    return true;
                }
            }catch (NumberFormatException ex){
                sender.sendMessage(Text.color("&cThe given sellprice is not a valid price."));
                return true;
            }
        } catch(NoSuchElementException ex){
            sender.sendMessage(Text.color("&cThere is no namecolor with this name or the given sellprice is not valid."));
            return true;
        }
    }
}
