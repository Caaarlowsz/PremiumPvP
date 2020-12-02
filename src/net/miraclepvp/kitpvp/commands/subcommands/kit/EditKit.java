package net.miraclepvp.kitpvp.commands.subcommands.kit;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.kit.Kit;
import net.miraclepvp.kitpvp.inventories.KitEditGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.NoSuchElementException;

public class EditKit implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(Text.color("&cPlease use /kit edit <name>"));
            return true;
        }
        try {
            if (Data.getKit(args[1]) == null) {
                sender.sendMessage(Text.color("&cThere is no kit with this name."));
                return true;
            } else {
                Kit kit = Data.getKit(args[1]);
                ((Player)sender).openInventory(KitEditGUI.getMainInventory(kit.getName()));
                return true;
            }
        } catch(NoSuchElementException ex){
            sender.sendMessage(Text.color("&cThere is no kit with this name."));
            return true;
        }
    }
}
