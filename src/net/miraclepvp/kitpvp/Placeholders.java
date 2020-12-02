package net.miraclepvp.kitpvp;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.guild.Guild;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.utils.PingUtil;
import org.bukkit.Bukkit;

public class Placeholders {

    public static String replaceLine(String line, User user) {
        Guild guild = null;
        try {
            if (user.getGuild() != null) guild = Data.getGuild(user.getGuild());
        }catch (Exception ex){
        }
        return line
                .replaceAll("%empty%", getEmptyLine())
                .replace("%server_players%", ""+ Bukkit.getServer().getOnlinePlayers().size())
                .replace("%server_tps%", ""+(double)Math.round(MinecraftServer.getServer().recentTps[0]*100.0)/100.0)
                .replaceAll("%player_name%", user.getPlayer().getName())
                .replaceAll("%player_level%", "" + user.getLevel())
                .replaceAll("%player_prefix%", "" + user.getPrefix()==null?"":Data.getPrefix(user.getPrefix())==null?"":Data.getPrefix(user.getPrefix()).getPrefix())
                .replaceAll("%player_suffix%", "" + user.getActiveSuffix()==null?"":Data.getSuffix(user.getActiveSuffix())==null?"":Data.getSuffix(user.getActiveSuffix()).getSuffix())
                .replaceAll("%player_experience%", "" + user.getExperience())
                .replaceAll("%player_experience_needed%", "" + user.getExperienceNeeded())
                .replaceAll("%player_progressbar%", user.getProgressBar())
                .replaceAll("%player_kills%", "" + user.getKills())
                .replaceAll("%player_assists%", "" + user.getAssists())
                .replaceAll("%player_deaths%", "" + user.getDeaths())
                .replaceAll("%player_kd%", "" + user.getKdRatio())
                .replaceAll("%player_killstreak%", "" + user.getKillstreak())
                .replaceAll("%player_highest_killstreak%", "" + user.getBestkillstreak())
                .replaceAll("%player_first_join%", user.getFirstJoin())
                .replaceAll("%player_last_join%", user.getLastJoin())
                .replaceAll("%player_coins%", "" + user.getCoins())
                .replaceAll("%player_ping%", "" + PingUtil.pingPlayer(user.getPlayer().getPlayer()))
                .replaceAll("%guild_name%", (guild==null ? "None" : guild.getName()))
                .replaceAll("%guild_tag%", (guild==null ? "None" : guild.getTag()))
                .replaceAll("%guild_level%", (guild==null ? "None" : ""+guild.getLevel()))
                .replaceAll("%guild_experience%", (guild==null ? "None" : ""+guild.getExperience()))
                .replaceAll("%guild_experience_needed%", (guild==null ? "None" : ""+guild.getExperienceNeeded()));
    }

    public static String getEmptyLine() {
        return "&" + Main.getRandom(1, 8) + "&" + Main.getRandom(1, 8) + "&" + Main.getRandom(1, 8);
    }
}
