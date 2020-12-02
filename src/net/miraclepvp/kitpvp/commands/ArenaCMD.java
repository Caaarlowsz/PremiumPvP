package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.commands.subcommands.arena.*;
import net.miraclepvp.kitpvp.commands.subcommands.map.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class ArenaCMD implements CommandExecutor {

    private HelpArena helpArena = new HelpArena();
    private CreateArena createArena = new CreateArena();
    private RemoveArena removeArena = new RemoveArena();
    private ListArena listArena = new ListArena();
    private Pos1Arena pos1Arena = new Pos1Arena();
    private Pos2Arena pos2Arena = new Pos2Arena();
    private ToggleArena toggleArena = new ToggleArena();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender.hasPermission("miraclepvp.arena"))){
            sender.sendMessage(color("&cYou don't have enough permissions to do this."));
            return true;
        }
        if(args.length == 0){
            sender.sendMessage(color("&cPlease use /arena help for more information."));
            return true;
        }
        switch (args[0].toLowerCase()){
            case "help":
                helpArena.onCommand(sender, cmd, label, args);
                break;
            case "create":
                createArena.onCommand(sender, cmd, label, args);
                break;
            case "remove":
                removeArena.onCommand(sender, cmd, label, args);
                break;
            case "list":
                listArena.onCommand(sender, cmd, label, args);
                break;
            case "pos1":
                pos1Arena.onCommand(sender, cmd, label, args);
                break;
            case "pos2":
                pos2Arena.onCommand(sender, cmd, label, args);
                break;
            case "toggle":
                toggleArena.onCommand(sender, cmd, label, args);
                break;
            default:
                sender.sendMessage(color("&cPlease use /arena help for more information."));
                break;
        }
        return true;
    }
}
