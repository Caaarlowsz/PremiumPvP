package net.miraclepvp.kitpvp.commands.subcommands.guild;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class MuteGuild implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        User user = Data.getUser(((Player)sender));
        if(user.getGuild() == null){
            sender.sendMessage(color("&4You are not in a guild."));
            return true;
        }

        if(user.getEveryoneMuted() == null)
            user.everyoneMuted = false;

        Player player = ((Player) sender);
        player.sendMessage(color("&aYou've " + (user.getEveryoneMuted() ? "unmuted" : "muted") + " the guild."));
        user.setEveryoneMuted(!user.getEveryoneMuted());
        return true;
    }
}
