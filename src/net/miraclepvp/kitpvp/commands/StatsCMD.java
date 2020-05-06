package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import net.miraclepvp.kitpvp.bukkit.Text;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.NoSuchElementException;

public class StatsCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 1){
            if(!(sender instanceof Player)) return true;
            if(!(sender.hasPermission("miraclepvp.stats"))){
                sender.sendMessage(Text.color("You don't have enough permissions to execute this command."));
                return true;
            }
            Player player = ((Player) sender);
            sendStats(player, player);
            return true;
        }
        if(!(sender.hasPermission("miraclepvp.stats.others"))){
            sender.sendMessage(Text.color("You don't have enough permissions to execute this command."));
            return true;
        }
        try {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            sendStats(sender, target);
        } catch(NoSuchElementException ex){
            sender.sendMessage(Text.color("&cThis player doesn't exist."));
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
        player.sendMessage(Text.color("&fUUID: &7" + target.getUniqueId().toString()));
        player.sendMessage(Text.color("&fFirst Joined: &7" + user.getFirstJoin()));
        player.sendMessage(Text.color("&fLast Joined: &7" + user.getLastJoin() + " &8(&7" + user.getLastVersion() + "&8)"));
        player.sendMessage(Text.color("&fOnline Time: &7" + User.transformTime(user.getOnlineTime())));
        ChatCenterUtil.sendCenteredMessage(player, "");
        player.sendMessage(Text.color("&fOwned Kits: &7" + user.getKitsList().size()));
        player.sendMessage(Text.color("&fOwned Cosmetics: &7" + (user.getTrailsList().size() + user.getChatcolorsList().size() + user.getNamecolorsList().size() + user.getSuffixesList().size())));
        ChatCenterUtil.sendCenteredMessage(player, "");
        player.sendMessage(Text.color("&fCosmotokens: &7" + user.getTokens()));
        player.sendMessage(Text.color("&fKills: &7" + user.getKills()));
        player.sendMessage(Text.color("&fAssists: &7" + user.getAssists()));
        player.sendMessage(Text.color("&fDeaths: &7" + user.getDeaths()));
        player.sendMessage(Text.color("&fK/D Ratio: &7" + user.getKdRatio()));
        player.sendMessage(Text.color("&fCoins: &7" + user.getCoins()));
        player.sendMessage(Text.color("&fLevel: &7" + user.getLevel() + " &8[&7"+ user.getExperience() + " exp&8]"));
        player.sendMessage(Text.color("&fKillstreak: &7" + user.getKillstreak()));
        player.sendMessage(Text.color("&fHighest Killstreak: &7" + user.bestkillstreak));
        ChatCenterUtil.sendCenteredMessage(player, "");
        player.sendMessage(Text.color("&fGuild: &7" + (user.getGuild() == null ? "none" : Data.getGuild(user.getGuild()).getName())));
        player.sendMessage(Text.color("&fOnline: " + (target.isOnline() ? "&aTrue" : "&cFalse")));
        ChatCenterUtil.sendCenteredMessage(player, "&5&m-----------------------------------------------------");
    }
}
