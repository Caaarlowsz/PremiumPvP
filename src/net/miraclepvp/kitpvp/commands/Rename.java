package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.bukkit.Text;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

public class Rename implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(!(sender.hasPermission("mriaclepvp.rename"))) return true;
        if(((Player) sender).getItemInHand().getType() == null || ((Player) sender).getItemInHand().getType() == Material.AIR) return true;
        if(args.length == 0){
            sender.sendMessage(Text.color("&cUse /rename <name>"));
            return true;
        }
        ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
        String zin = args[0];
        for(int i = 1; i < args.length; i++)
            zin = zin + " " + args[i];
        itemMeta.setDisplayName(Text.color(zin));
        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
        return true;
    }
}
