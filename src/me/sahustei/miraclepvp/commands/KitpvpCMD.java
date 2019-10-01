package me.sahustei.miraclepvp.commands;

import jdk.nashorn.internal.objects.annotations.Setter;
import me.sahustei.miraclepvp.commands.subcommands.kitpvp.HelpKitpvp;
import me.sahustei.miraclepvp.commands.subcommands.kitpvp.InfoKitpvp;
import me.sahustei.miraclepvp.commands.subcommands.kitpvp.LobbyKitpvp;
import me.sahustei.miraclepvp.commands.subcommands.kitpvp.SetlobbyKitpvp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static me.sahustei.miraclepvp.bukkit.Text.color;

public class KitpvpCMD implements CommandExecutor {

    private HelpKitpvp helpKitpvp = new HelpKitpvp();
    private InfoKitpvp infoKitpvp = new InfoKitpvp();
    private SetlobbyKitpvp setlobbyKitpvp = new SetlobbyKitpvp();
    private LobbyKitpvp lobbyKitpvp = new LobbyKitpvp();

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
            default:
                sender.sendMessage(color("&cPlease use /kitpvp help for more information."));
                break;
        }
        return true;
    }
}
