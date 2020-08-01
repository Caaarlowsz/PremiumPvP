package net.miraclepvp.kitpvp.commands.subcommands.supplydrop;

import net.miraclepvp.kitpvp.bukkit.FileManager;
import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.zone.Zone;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class AddlocSupplydrop implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("miraclepvp.supplydrop.addlocation")) {
            sender.sendMessage(color("&4You don't have enough permissions to do this."));
            return true;
        }
        if(args.length != 2){
            sender.sendMessage(color("&cPlease use /supplydrop addlocation <zone>"));
            return true;
        }

        try {
            if (Data.getZone(args[1]) == null) {
                sender.sendMessage(Text.color("&cThere is no zone with this name."));
                return true;
            }

            Zone zone = Data.getZone(args[1]);
            zone.getSupplydropLocations().add(FileManager.serialize(((Player) sender).getLocation()));
            sender.sendMessage(color("&aYour location has been added as a supplydrop location in the zone " + zone.getName() + "."));
            return true;
        }catch (NoSuchElementException ex){
            sender.sendMessage(Text.color("&cThere is no zone with this name."));
            return true;
        }
    }
}
