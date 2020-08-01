package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.commands.subcommands.duel.*;
import net.miraclepvp.kitpvp.data.duel.Duel;
import net.miraclepvp.kitpvp.listeners.player.playerJoin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class DuelCMD implements CommandExecutor {

    private HelpDuel helpDuel = new HelpDuel();
    private InviteDuel inviteDuel = new InviteDuel();
    private SpectateDuel spectateDuel = new SpectateDuel();
    private AcceptDuel acceptDuel = new AcceptDuel();
    private DeclineDuel declineDuel = new DeclineDuel();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(Duel.isIngame(((Player) sender)) || Duel.isSpectator(((Player) sender)) || ((Player) sender).hasMetadata("kit") || ((Player) sender).hasMetadata("event")) {
            sender.sendMessage(color("&cYou can only use this command at spawn."));
            return true;
        }
        if(args.length == 0){
            helpDuel.onCommand(sender, cmd, label, args);
            return true;
        }
        switch (args[0].toLowerCase()){
            case "invite":
                inviteDuel.onCommand(sender, cmd, label, args);
                break;
            case "spectate":
                spectateDuel.onCommand(sender, cmd, label, args);
                break;
            case "accept":
                acceptDuel.onCommand(sender, cmd, label, args);
                break;
            case "decline":
                declineDuel.onCommand(sender, cmd, label, args);
                break;
            default:
                helpDuel.onCommand(sender, cmd, label, args);
                break;
        }
        return true;
    }
}
