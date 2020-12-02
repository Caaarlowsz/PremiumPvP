package net.miraclepvp.kitpvp.commands.subcommands.kitpvp;

import net.miraclepvp.kitpvp.data.Config;
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
        Boolean isBackup = false;
        if(args.length>=2&&args[1].equalsIgnoreCase("backup"))isBackup=true;
        Config.save();
        Data.save(isBackup);
        sender.sendMessage(color("&aYou've successfully saved the data!"));
        return true;
    }
}
