package net.miraclepvp.kitpvp.commands.subcommands.kitpvp;

import net.miraclepvp.kitpvp.data.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class ReloadKitpvp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender.hasPermission("miraclepvp.kitpvp.reload"))) {
            sender.sendMessage(color("&4You don't have enough permissions to do this!"));
            return true;
        }
        Config.reload();
        sender.sendMessage(color("&aYou've reloaded the config."));
        return true;
    }
}
