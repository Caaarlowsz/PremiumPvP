package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.bukkit.Text;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

public class Unbreakable implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(!(sender.hasPermission("miraclepvp.unbreakable"))) return true;
        if(((Player) sender).getItemInHand().getType() == null || ((Player) sender).getItemInHand().getType() == Material.AIR) return true;
        ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        ((Player) sender).getItemInHand().setItemMeta(itemMeta);
        sender.sendMessage(Text.color("&aThis item is now unbreakable."));
        return true;
    }
}
