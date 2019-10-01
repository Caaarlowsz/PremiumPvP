package me.sahustei.miraclepvp.commands.subcommands.trail;

import me.sahustei.miraclepvp.bukkit.Text;
import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.trail.Trail;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class SellpriceTrail implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 3){
            sender.sendMessage(Text.color("&cPlease use /trail sellprice <name> <new_sellprice>"));
            return true;
        }
        try {
            try {
                Integer newprice = Integer.parseInt(args[2]);
                if (Data.getTrail(args[1]) == null) {
                    sender.sendMessage(Text.color("&cThere is no trail with this name."));
                    return true;
                } else {
                    Trail trail = Data.getTrail(args[1]);
                    sender.sendMessage(Text.color("&aYou have succesfully changed the sellprice of the trail " + trail.getName() + "!"));
                    trail.setSell(newprice);
                    return true;
                }
            }catch (NumberFormatException ex){
                sender.sendMessage(Text.color("&cThe given sellprice is not a valid price."));
                return true;
            }
        } catch(NoSuchElementException ex){
            sender.sendMessage(Text.color("&cThere is no trail with this name or the given sellprice is not valid."));
            return true;
        }
    }
}
