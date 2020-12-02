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

public class OnlineGuild implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        User user = Data.getUser(player);
        if(user.getGuild() == null){
            sender.sendMessage(color("&4You are not in a guild."));
            return true;
        }
        Guild guild = Data.getGuild(user.getGuild());
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        player.sendMessage(color("&f" + guild.getName()));
        OfflinePlayer master = Bukkit.getOfflinePlayer(guild.getMaster());
        if(master.isOnline()) {
            player.sendMessage(color("&d&m--&d[ &5Master &d]&m--"));
            player.sendMessage(color("&7" + master.getName() + (master.isOnline() ? "&a" : "&c") + " ⬤"));
        }
        int officersOnline = (int) guild.getOfficers().stream().filter(uuid -> Bukkit.getOfflinePlayer(uuid).isOnline()).count();
        if(officersOnline > 0) {
            player.sendMessage(color("&d&m--&d[ &5Officer &d]&m--"));
            guild.getOfficers().forEach(uuid -> {
                OfflinePlayer target = Bukkit.getOfflinePlayer(uuid);
                if(target.isOnline())
                    player.sendMessage(color("&7" + target.getName() + "&a ⬤"));
            });
        }
        int membersOnline = (int) guild.getMembers().stream().filter(uuid -> Bukkit.getOfflinePlayer(uuid).isOnline()).count();
        if(membersOnline > 0) {
            player.sendMessage(color("&d&m--&d[ &5Member &d]&m--"));
            guild.getMembers().forEach(uuid -> {
                OfflinePlayer target = Bukkit.getOfflinePlayer(uuid);
                if(target.isOnline())
                    player.sendMessage(color("&7" + target.getName() + "&a ⬤"));
            });
        }
        player.sendMessage(" ");
        player.sendMessage(color("&7Total Members: " + guild.getPlayers().size()));
        int online = (int) guild.getPlayers().stream().filter(uuid -> Bukkit.getOfflinePlayer(uuid).isOnline()).count();
        int offline = guild.getPlayers().size() - online;
        player.sendMessage(color("&7Online members: " + online));
        player.sendMessage(color("&7Offline members: " + offline));
        player.sendMessage(color("&5&m-----------------------------------------------------"));
        return true;
    }
}
