package net.miraclepvp.kitpvp.commands.subcommands.supplydrop;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.zone.Zone;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class RemovelocSupplydrop implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("miraclepvp.supplydrop.removelocation")) {
            sender.sendMessage(color("&4You don't have enough permissions to do this."));
            return true;
        }
        if(args.length != 3){
            sender.sendMessage(color("&cPlease use /supplydrop removelocation <zone> <id>"));
            return true;
        }
        try {
            if (Data.getZone(args[1]) == null) {
                sender.sendMessage(Text.color("&cThere is no zone with this name."));
                return true;
            }
            try {
                Integer value = Integer.parseInt(args[2]) - 1;
                Zone zone = Data.getZone(args[1]);
                zone.getSupplydropLocations().remove(zone.getSupplydropLocations().get(value));
                sender.sendMessage(color("&aYou deleted a location from the list."));
                return true;
            } catch (Exception ex) {
                sender.sendMessage(color("&cSomething went wrong, check your command."));
                return true;
            }
        }catch (NoSuchElementException exc){
            sender.sendMessage(Text.color("&cThere is no zone with this name."));
            return true;
        }
    }
}
