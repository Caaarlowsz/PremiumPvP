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
    private SetmapKitpvp setmapKitpvp = new SetmapKitpvp();
    private BuildmodeKitpvp buildmodeKitpvp = new BuildmodeKitpvp();

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
            case "setmap":
                setmapKitpvp.onCommand(sender, cmd, label, args);
                break;
            case "buildmode":
                buildmodeKitpvp.onCommand(sender, cmd, label, args);
                break;
            default:
                sender.sendMessage(color("&cPlease use /kitpvp help for more information."));
                break;
        }
        return true;
    }
}
