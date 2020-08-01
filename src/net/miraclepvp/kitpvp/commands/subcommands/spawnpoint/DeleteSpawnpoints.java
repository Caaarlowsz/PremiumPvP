package net.miraclepvp.kitpvp.commands.subcommands.spawnpoint;

import net.miraclepvp.kitpvp.data.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class DeleteSpawnpoints implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            Integer value = Integer.parseInt(args[1])-1;
            Config.getSpawnpoints().remove(Config.getSpawnpoints().get(value));
            sender.sendMessage(color("&aYou deleted a location from the list."));
            return true;
        } catch (Exception ex) {
            sender.sendMessage(color("&cSomething went wrong, check your command."));
            return true;
        }
    }
}
