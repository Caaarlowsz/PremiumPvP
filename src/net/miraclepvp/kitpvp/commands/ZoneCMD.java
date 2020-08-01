package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.commands.subcommands.zone.CreateZoneCMD;
import net.miraclepvp.kitpvp.commands.subcommands.zone.HelpZone;
import net.miraclepvp.kitpvp.commands.subcommands.zone.ListZone;
import net.miraclepvp.kitpvp.commands.subcommands.zone.RemoveZoneCMD;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class ZoneCMD implements CommandExecutor {

    private HelpZone helpZone = new HelpZone();
    private CreateZoneCMD createZoneCMD = new CreateZoneCMD();
    private RemoveZoneCMD removeZoneCMD = new RemoveZoneCMD();
    private ListZone listZone = new ListZone();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(color("&4Only players can execute this command."));
            return true;
        }
        if (!sender.hasPermission("miraclepvp.zone")) {
            sender.sendMessage(color("&4You don't have enough permissions to do this."));
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(color("&cPlease usage /zone help for more information"));
            return true;
        }
        switch (args[0]) {
            case "help":
                helpZone.onCommand(sender, cmd, label, args);
                break;
            case "create":
                createZoneCMD.onCommand(sender, cmd, label, args);
                break;
            case "remove":
                removeZoneCMD.onCommand(sender, cmd, label, args);
                break;
            case "list":
                listZone.onCommand(sender, cmd, label, args);
                break;
            default:
                sender.sendMessage(color("&cPlease usage /zone help for more information"));
                break;
        }
        return true;
    }
}
