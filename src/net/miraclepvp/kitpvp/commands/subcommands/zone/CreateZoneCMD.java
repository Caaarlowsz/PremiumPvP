package net.miraclepvp.kitpvp.commands.subcommands.zone;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.zone.Zone;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class CreateZoneCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("miraclepvp.zone.create")) {
            sender.sendMessage(color("&4You don't have enough permissions to do this."));
            return true;
        }
        if (args.length != 2) {
            sender.sendMessage(color("&cPlease use /zone create <name>"));
            return true;
        }

        try {
            if (Data.getZone(args[1]) != null) {
                sender.sendMessage(Text.color("&cThere is already a zone with this name."));
                return true;
            }
            Zone zone = new Zone(args[1]);
            Data.zones.add(zone);
            sender.sendMessage(color("&aYou've created the zone " + zone.getName() + "!"));
            return true;
        } catch (NoSuchElementException ex) {
            Zone zone = new Zone(args[1]);
            Data.zones.add(zone);
            sender.sendMessage(color("&aYou've created the zone " + zone.getName() + "!"));
            return true;
        }
    }
}
