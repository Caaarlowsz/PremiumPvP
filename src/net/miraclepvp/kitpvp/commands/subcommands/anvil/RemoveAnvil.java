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

public class RemoveAnvil implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        if(args.length == 1){
            if(!player.getTargetBlock((Set)null, 5).getType().equals(Material.ANVIL)){
                player.sendMessage(color("&cYou have to be looking at a anvil to do this."));
                return true;
            }
            if(!Config.getAnvils().contains(FileManager.serialize(player.getTargetBlock((Set)null, 5).getLocation()))){
                player.sendMessage(color("&cThis anvil is not in the list."));
                return true;
            }
            Config.getAnvils().remove(FileManager.serialize(player.getTargetBlock((Set)null, 5).getLocation()));
            player.sendMessage(color("&aThe anvil is successfully removed from the list."));
            return true;
        }
        try {
            Integer value = Integer.parseInt(args[1])-1;
            Config.getAnvils().remove(Config.getAnvils().get(value));
            sender.sendMessage(color("&aYou deleted an anvil from the list."));
            return true;
        } catch (Exception ex) {
            sender.sendMessage(color("&cSomething went wrong, check your command."));
            return true;
        }
    }
}
