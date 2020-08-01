package net.miraclepvp.kitpvp.commands.subcommands.guild;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.guild.Guild;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class DisbandGuild implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        User user = Data.getUser(((Player)sender));
        if(user.getGuild() == null){
            sender.sendMessage(color("&4You are not in a guild."));
            return true;
        }
        Guild guild = Data.getGuild(user.getGuild());
        if(!guild.getMaster().equals(((Player) sender).getUniqueId())){
            sender.sendMessage(color("&4You don't have enough permissions to do this."));
            return true;
        }
        guild.getPlayers().forEach(player -> {
            OfflinePlayer offline = Bukkit.getOfflinePlayer(player);
            if(offline.isOnline())
                Bukkit.getPlayer(player).sendMessage(color("&cYou have been removed from the guild because the master disbanded the guild."));
            User target = Data.getUser(offline);
            target.setGuild(null);
        });
        Data.guilds.remove(guild);
        return true;
    }
}
