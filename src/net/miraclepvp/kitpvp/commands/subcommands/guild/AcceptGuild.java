package net.miraclepvp.kitpvp.commands.subcommands.guild;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.guild.Guild;
import net.miraclepvp.kitpvp.objects.Board;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class AcceptGuild implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 2){
            sender.sendMessage(color("&cPlease use /guild accept <guild name>"));
            return true;
        }

        try {
            if (Data.getGuild(args[1]) == null) {
                sender.sendMessage(color("&cThere is no guild with this name."));
                return true;
            } else {
                Guild guild = Data.getGuild(args[1]);
                if(Data.getUser(((Player)sender)).getGuild() != null){
                    sender.sendMessage(color("&cYou are already in a guild."));
                    return true;
                }
                if(!guild.getInvites().contains(((Player)sender).getUniqueId())){
                    sender.sendMessage(color("&cYou are not invited for this guild."));
                    return true;
                }
                guild.getInvites().remove(((Player) sender).getUniqueId());
                guild.getMembers().add(((Player) sender).getUniqueId());
                Data.getUser(((Player) sender)).setGuild(guild.getUuid());
                guild.sendBroadcast(sender.getName() + " joined the guild.");
                Board.updatePlayerListName(((Player) sender));
                return true;
            }
        } catch(NoSuchElementException ex){
            sender.sendMessage(color("&cThere is no guild with this name."));
            return true;
        }
    }
}
