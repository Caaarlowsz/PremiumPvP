package net.miraclepvp.kitpvp.commands.subcommands.anvil;

import net.miraclepvp.kitpvp.bukkit.FileManager;
import net.miraclepvp.kitpvp.data.Config;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class AddAnvil implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        if(!player.getTargetBlock((Set)null, 5).getType().equals(Material.ANVIL)){
            player.sendMessage(color("&cYou have to be looking at a anvil to do this."));
            return true;
        }
        if(Config.getAnvils().contains(FileManager.serialize(player.getTargetBlock((Set)null, 5).getLocation()))){
            player.sendMessage(color("&cThis anvil is already in the list."));
            return true;
        }
        Config.getAnvils().add(FileManager.serialize(player.getTargetBlock((Set)null, 5).getLocation()));
        player.sendMessage(color("&aThe anvil is successfully added in the list."));
        return true;
    }
}
