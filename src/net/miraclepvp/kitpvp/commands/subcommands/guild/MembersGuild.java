package net.miraclepvp.kitpvp.commands.subcommands.guild;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.guild.Guild;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class MembersGuild implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        User user = Data.getUser(player);
        if(user.getGuild() == null){
            sender.sendMessage(color("&4You are not in a guild."));
            return true;
        }
        Guild guild = Data.getGuild(user.getGuild());
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&f" + guild.getName());
        ChatCenterUtil.sendCenteredMessage(player, "&d&m--&d[ &5Master &d]&m--");
        OfflinePlayer master = Bukkit.getOfflinePlayer(guild.getMaster());
        player.sendMessage(color("&7" + master.getName() + (master.isOnline() ? "&a" : "&c") + " ⬤"));
        ChatCenterUtil.sendCenteredMessage(player, "&d&m--&d[ &5Officer &d]&m--");
        guild.getOfficers().forEach(uuid -> {
            OfflinePlayer target = Bukkit.getOfflinePlayer(uuid);
            player.sendMessage(color("&7" + target.getName() + (target.isOnline() ? "&a" : "&c") + " ⬤"));
        });
        ChatCenterUtil.sendCenteredMessage(player, "&d&m--&d[ &5Member &d]&m--");
        guild.getMembers().forEach(uuid -> {
            OfflinePlayer target = Bukkit.getOfflinePlayer(uuid);
            player.sendMessage(color("&7" + target.getName() + (target.isOnline() ? "&a" : "&c") + " ⬤"));
        });
        player.sendMessage(" ");
        player.sendMessage(color("&7Total Members: " + guild.getPlayers().size()));
        int online = (int) guild.getPlayers().stream().filter(uuid -> Bukkit.getOfflinePlayer(uuid).isOnline()).count();
        player.sendMessage(color("&7Online members: " + online));
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        return true;
    }
}
