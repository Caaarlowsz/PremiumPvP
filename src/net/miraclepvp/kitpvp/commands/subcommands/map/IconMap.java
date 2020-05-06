package net.miraclepvp.kitpvp.commands.subcommands.map;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.duel.Map;
import net.miraclepvp.kitpvp.inventories.KitEditGUI;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class IconMap implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(color("&cPlease use /map icon <name> <icon>"));
            return true;
        }
        Map map;
        try {
            map = Data.getMap(args[1]);
        } catch (NoSuchElementException e) {
            sender.sendMessage(Text.color("&cThere is no map with this name."));
            return true;
        }
        Material material = Material.getMaterial(args[2]);
        if(material == null || material.equals(Material.AIR)){
            sender.sendMessage(color("&cThe given item is not valid"));
            return true;
        }
        map.icon = material;
        sender.sendMessage(color("&aSuccessfully changed the icon of " + map.name + "!"));
        return true;
    }
}
