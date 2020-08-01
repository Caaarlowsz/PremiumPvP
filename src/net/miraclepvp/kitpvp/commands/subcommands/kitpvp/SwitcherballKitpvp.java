package net.miraclepvp.kitpvp.commands.subcommands.kitpvp;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class SwitcherballKitpvp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(!sender.hasPermission("miraclepvp.kitpvp.getswitcherball")){
            sender.sendMessage(color("&cYou are not permitted to do this."));
            return true;
        }
        ((Player) sender).getInventory().addItem(new ItemstackFactory(Material.SNOW_BALL).setDisplayName("&5Switcherball").addLoreLine("&7Throw this item at other").addLoreLine("&7players to switch positions."));
        return true;
    }
}
