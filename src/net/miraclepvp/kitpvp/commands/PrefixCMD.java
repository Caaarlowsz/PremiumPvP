package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.commands.subcommands.prefix.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PrefixCMD implements CommandExecutor {

    private HelpPrefix helpPrefix = new HelpPrefix();
    private AddPrefix addPrefix = new AddPrefix();
    private DeletePrefix deletePrefix = new DeletePrefix();
    private SetPrefix setPrefix = new SetPrefix();
    private RenamePrefix renamePrefix = new RenamePrefix();
    private SetprefixPrefix setprefixPrefix = new SetprefixPrefix();
    private SetweightPrefix setweightPrefix = new SetweightPrefix();
    private ClearPrefix clearPrefix = new ClearPrefix();
    private ListPrefix listPrefix = new ListPrefix();
    private InfoPrefix infoPrefix = new InfoPrefix();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("miraclepvp.prefix")){
            sender.sendMessage(Text.color("&4You don't have enough permissions to do this!"));
            return true;
        }
        if(args.length == 0){
            sender.sendMessage(Text.color("&cPlease use /prefix help for more information."));
            return true;
        }
        switch(args[0].toLowerCase()){
            case "help":
                helpPrefix.onCommand(sender, cmd, label, args);
                break;
            case "add":
                addPrefix.onCommand(sender, cmd, label, args);
                break;
            case "delete":
                deletePrefix.onCommand(sender, cmd, label, args);
                break;
            case "set":
                setPrefix.onCommand(sender, cmd, label, args);
                break;
            case "rename":
                renamePrefix.onCommand(sender, cmd, label, args);
                break;
            case "setprefix":
                setprefixPrefix.onCommand(sender, cmd, label, args);
                break;
            case "setweight":
                setweightPrefix.onCommand(sender, cmd, label, args);
                break;
            case "clear":
                clearPrefix.onCommand(sender, cmd, label, args);
                break;
            case "list":
                listPrefix.onCommand(sender, cmd, label, args);
                break;
            case "info":
                infoPrefix.onCommand(sender, cmd, label, args);
                break;
            default:
                sender.sendMessage(Text.color("&cPlease use /prefix help for more information."));
                break;
        }
        return true;
    }
}
