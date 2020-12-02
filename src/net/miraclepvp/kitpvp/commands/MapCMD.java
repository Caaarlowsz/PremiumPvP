package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.commands.subcommands.map.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class MapCMD implements CommandExecutor {

    private HelpMap helpMap = new HelpMap();
    private CreateMap createMap = new CreateMap();
    private RemoveMap removeMap = new RemoveMap();
    private ListMap listMap = new ListMap();
    private IconMap iconMap = new IconMap();
    private NameMap nameMap = new NameMap();
    private DescriptionMap descriptionMap = new DescriptionMap();
    private TeleportMap teleportMap = new TeleportMap();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender.hasPermission("miraclepvp.map"))){
            sender.sendMessage(color("&cYou don't have enough permissions to do this."));
            return true;
        }
        if(args.length == 0){
            sender.sendMessage(color("&cPlease use /map help for more information."));
            return true;
        }
        switch (args[0].toLowerCase()){
            case "help":
                helpMap.onCommand(sender, cmd, label, args);
                break;
            case "create":
                createMap.onCommand(sender, cmd, label, args);
                break;
            case "remove":
                removeMap.onCommand(sender, cmd, label, args);
                break;
            case "teleport":
                teleportMap.onCommand(sender, cmd, label, args);
                break;
            case "list":
                listMap.onCommand(sender, cmd, label, args);
                break;
            case "icon":
                iconMap.onCommand(sender, cmd, label, args);
                break;
            case "name":
                nameMap.onCommand(sender, cmd, label, args);
                break;
            case "description":
                descriptionMap.onCommand(sender, cmd, label, args);
                break;
            default:
                sender.sendMessage(color("&cPlease use /map help for more information."));
                break;
        }
        return true;
    }
}
