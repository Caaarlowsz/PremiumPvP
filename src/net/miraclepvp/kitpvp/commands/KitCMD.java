package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.commands.subcommands.kit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class KitCMD implements CommandExecutor {

    private HelpKit helpKit = new HelpKit();
    private CreateKit createKit = new CreateKit();
    private DeleteKit deleteKit = new DeleteKit();
    private RenameKit renameKit = new RenameKit();
    private ListKit listKit = new ListKit();
    private GiveKit giveKit = new GiveKit();
    private ToggleKit toggleKit = new ToggleKit();
    private AddEffectKit addEffectKit = new AddEffectKit();
    private RemoveEffectKit removeEffectKit = new RemoveEffectKit();
    private PriceKit priceKit = new PriceKit();
    private SellpriceKit sellpriceKit = new SellpriceKit();
    private EditKit editKit = new EditKit();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(color("&cOnly players are allowed to execute this command."));
            return true;
        }
        if(!(sender.hasPermission("miraclepvp.kit"))){
            sender.sendMessage(color("&4You don't have enough permissions to do this."));
            return true;
        }
        if(args.length == 0){
            sender.sendMessage(color("&cPlease use /kit help for more information."));
            return true;
        }
        switch (args[0].toLowerCase()){
            case "help":
                helpKit.onCommand(sender, cmd, label, args);
                break;
            case "create":
                createKit.onCommand(sender, cmd, label, args);
                break;
            case "delete":
                deleteKit.onCommand(sender, cmd, label, args);
                break;
            case "rename":
                renameKit.onCommand(sender, cmd, label, args);
                break;
            case "list":
                listKit.onCommand(sender, cmd, label, args);
                break;
            case "give":
                giveKit.onCommand(sender, cmd, label, args);
                break;
            case "toggle":
                toggleKit.onCommand(sender, cmd, label, args);
                break;
            case "addeffect":
                addEffectKit.onCommand(sender, cmd, label, args);
                break;
            case "removeeffect":
                removeEffectKit.onCommand(sender, cmd, label, args);
                break;
            case "price":
                priceKit.onCommand(sender, cmd, label, args);
                break;
            case "sellprice":
                sellpriceKit.onCommand(sender, cmd, label, args);
                break;
            case "edit":
                editKit.onCommand(sender, cmd, label, args);
                break;
            default:
                sender.sendMessage(color("&cPlease use /kit help for more information."));
                break;
        }
        return true;
    }
}
