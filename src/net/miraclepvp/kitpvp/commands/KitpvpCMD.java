package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.commands.subcommands.kitpvp.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class KitpvpCMD implements CommandExecutor {

    private HelpKitpvp helpKitpvp = new HelpKitpvp();
    private InfoKitpvp infoKitpvp = new InfoKitpvp();
    private SetlobbyKitpvp setlobbyKitpvp = new SetlobbyKitpvp();
    private LobbyKitpvp lobbyKitpvp = new LobbyKitpvp();
    private BuildmodeKitpvp buildmodeKitpvp = new BuildmodeKitpvp();
    private ReloadKitpvp reloadKitpvp = new ReloadKitpvp();
    private TrackerKitpvp trackerKitpvp = new TrackerKitpvp();
    private SaveKitpvp saveKitpvp = new SaveKitpvp();
    private AnvilKitpvp anvilKitpvp = new AnvilKitpvp();
    private MapKitpvp mapKitpvp = new MapKitpvp();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0){
            sender.sendMessage(color("&cPlease use /kitpvp help for more information."));
            return true;
        }
        switch(args[0].toLowerCase()){
            case "help":
                helpKitpvp.onCommand(sender, cmd, label, args);
                break;
            case "info":
                infoKitpvp.onCommand(sender, cmd, label, args);
                break;
            case "setlobby":
                setlobbyKitpvp.onCommand(sender, cmd, label, args);
                break;
            case "lobby":
                lobbyKitpvp.onCommand(sender, cmd, label, args);
                break;
            case "buildmode":
                buildmodeKitpvp.onCommand(sender, cmd, label, args);
                break;
            case "reload":
                reloadKitpvp.onCommand(sender, cmd, label, args);
                break;
            case "gettracker":
                trackerKitpvp.onCommand(sender, cmd, label, args);
                break;
            case "save":
                saveKitpvp.onCommand(sender, cmd, label, args);
                break;
            case "getanvil":
                anvilKitpvp.onCommand(sender, cmd, label, args);
                break;
            case "map":
                mapKitpvp.onCommand(sender, cmd, label, args);
                break;
            default:
                sender.sendMessage(color("&cPlease use /kitpvp help for more information."));
                break;
        }
        return true;
    }
}
