package net.miraclepvp.kitpvp.commands.subcommands.kitpvp;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class AnvilKitpvp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(!sender.hasPermission("miraclepvp.kitpvp.getanvil")){
            sender.sendMessage(color("&cYou are not permitted to do this."));
            return true;
        }
        ((Player) sender).getInventory().addItem(new ItemstackFactory(Material.ANVIL).setDisplayName(color("&5Anvil")).addLoreLine("&7Place this anvil to add a custom anvil."));
        return true;
    }
}
