package net.miraclepvp.kitpvp.commands.subcommands.kitpvp;

import net.miraclepvp.kitpvp.data.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class SaveKitpvp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender.hasPermission("miraclepvp.kitpvp.save"))){
            sender.sendMessage(color("&cYou don't have the required permissions to do this."));
            return true;
        }
        Data.save(false);
        sender.sendMessage(color("&aYou've succesfully saved the data!"));
        return true;
    }
}
