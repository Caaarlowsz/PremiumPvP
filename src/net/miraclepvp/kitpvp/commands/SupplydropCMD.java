package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.commands.subcommands.supplydrop.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class SupplydropCMD implements CommandExecutor {

    private HelpSupplydrop helpSupplydrop = new HelpSupplydrop();
    private AddlocSupplydrop addlocSupplydrop = new AddlocSupplydrop();
    private RemovelocSupplydrop removelocSupplydrop = new RemovelocSupplydrop();
    private LocationlistSupplydrop listSupplydrop = new LocationlistSupplydrop();
    private SpawnSupplydrop spawnSupplydrop = new SpawnSupplydrop();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(color("&4Only players can execute this command."));
            return true;
        }
        if (!sender.hasPermission("miraclepvp.supplydrop")) {
            sender.sendMessage(color("&4You don't have enough permissions to do this."));
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(color("&cPlease usage /supplydrop help for more information"));
            return true;
        }
        switch (args[0]) {
            case "help":
                helpSupplydrop.onCommand(sender, cmd, label, args);
                break;
            case "addlocation":
                addlocSupplydrop.onCommand(sender, cmd, label, args);
                break;
            case "removelocation":
                removelocSupplydrop.onCommand(sender, cmd, label, args);
                break;
            case "locationlist":
                listSupplydrop.onCommand(sender, cmd, label, args);
                break;
            case "spawn":
                spawnSupplydrop.onCommand(sender, cmd, label, args);
                break;
            default:
                sender.sendMessage(color("&cPlease usage /supplydrop help for more information"));
                break;
        }
        return true;
    }
}
