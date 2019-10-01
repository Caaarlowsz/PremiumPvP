package me.sahustei.miraclepvp.commands;

import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.user.User;
import me.sahustei.miraclepvp.utils.ChatCenterUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.NoSuchElementException;

import static me.sahustei.miraclepvp.bukkit.Text.color;

public class StatsCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 1){
            if(!(sender instanceof Player)) return true;
            if(!(sender.hasPermission("miraclepvp.stats"))){
                sender.sendMessage(color("You don't have enough permissions to execute this command."));
                return true;
            }
            Player player = ((Player) sender);
            sendStats(player, player);
            return true;
        }
        if(!(sender.hasPermission("miraclepvp.stats.others"))){
            sender.sendMessage(color("You don't have enough permissions to execute this command."));
            return true;
        }
        try {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            sendStats(sender, target);
        } catch(NoSuchElementException ex){
            sender.sendMessage(color("&cThis player doesn't exist."));
            return true;
        }
        return true;
    }

    private void sendStats(CommandSender player, OfflinePlayer target){
        User user = Data.getUser(target);
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&f" + target.getName());
        ChatCenterUtil.sendCenteredMessage(player, "&7" + target.getName() + "'s player stats");
        ChatCenterUtil.sendCenteredMessage(player, "");
        player.sendMessage(color("&fUUID: &7" + target.getUniqueId().toString()));
        player.sendMessage(color("&fFirst Joined: &7" + user.getFirstJoin()));
        player.sendMessage(color("&fLast Joined: &7" + user.getLastJoin() + " &8(&7" + user.getLastVersion() + "&8)"));
        player.sendMessage(color("&fOnline Time: &7" + User.transformTime(user.getOnlineTime())));
        ChatCenterUtil.sendCenteredMessage(player, "");
        player.sendMessage(color("&fOwned Kits: &7" + user.getKitsList().size()));
        player.sendMessage(color("&fOwned Cosmetics: &7" + (user.getTrailsList().size() + user.getChatcolorsList().size() + user.getKitsList().size() + user.getNamecolorsList().size() + user.getSuffixesList().size())));
        ChatCenterUtil.sendCenteredMessage(player, "");
        player.sendMessage(color("&fCosmotokens: &7" + user.getTokens()));
        player.sendMessage(color("&fKills: &7" + user.getKills()));
        player.sendMessage(color("&fDeaths: &7" + user.getDeaths()));
        player.sendMessage(color("&fK/D Ratio: &7" + user.getKdRatio()));
        player.sendMessage(color("&fCoins: &7" + user.getCoins()));
        player.sendMessage(color("&fLevel: &7" + user.getLevel() + " &8[&7"+ user.getExperience() + " exp&8]"));
        player.sendMessage(color("&fKillstreak: &7" + user.getKillstreak()));
        player.sendMessage(color("&fHighest Killstreak: &7" + user.bestkillstreak));
        ChatCenterUtil.sendCenteredMessage(player, "");
        
        player.sendMessage(color("&fOnline: " + (target.isOnline() ? "&aTrue" : "&cFalse")));
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
    }
}
