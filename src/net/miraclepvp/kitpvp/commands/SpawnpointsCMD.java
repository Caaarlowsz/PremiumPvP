package net.miraclepvp.kitpvp.commands;

import com.google.common.collect.Lists;
import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.commands.subcommands.spawnpoint.AddSpawnpoints;
import net.miraclepvp.kitpvp.commands.subcommands.spawnpoint.HelpSpawnpoints;
import net.miraclepvp.kitpvp.commands.subcommands.spawnpoint.DeleteSpawnpoints;
import net.miraclepvp.kitpvp.commands.subcommands.spawnpoint.ListSpawnpoints;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SpawnpointsCMD implements CommandExecutor {

    private HelpSpawnpoints helpSpawnpoint = new HelpSpawnpoints();
    private AddSpawnpoints addSpawnpoint = new AddSpawnpoints();
    private DeleteSpawnpoints removeSpawnpoints = new DeleteSpawnpoints();
    private ListSpawnpoints listSpawnpoints = new ListSpawnpoints();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("miraclepvp.spawnpoint")){
            sender.sendMessage(Text.color("&4You don't have enough permissions to do this!"));
            return true;
        }
        if(args.length == 0){
            sender.sendMessage(Text.color("&cPlease use /spawnpoint help for more information."));
            return true;
        }
        switch(args[0].toLowerCase()){
            case "help":
                helpSpawnpoint.onCommand(sender, cmd, label, args);
                break;
            case "add":
                addSpawnpoint.onCommand(sender, cmd, label, args);
                break;
            case "delete":
                removeSpawnpoints.onCommand(sender, cmd, label, args);
                break;
            case "list":
                listSpawnpoints.onCommand(sender, cmd, label, args);
                break;
            default:
                sender.sendMessage(Text.color("&cPlease use /spawnpoint help for more information."));
                break;
        }
        return true;
    }
}
