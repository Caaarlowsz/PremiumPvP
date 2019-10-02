package me.sahustei.miraclepvp.objects;

import me.sahustei.miraclepvp.Main;
import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.user.User;
import me.sahustei.miraclepvp.utils.PingUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

import static me.sahustei.miraclepvp.bukkit.Text.color;

public class Board {

    public static String getEntryFromScore(Objective o, int score) {
        if (o == null) {
            return null;
        }
        if (!hasScoreTaken(o, score)) {
            return null;
        }
        for (String s : o.getScoreboard().getEntries()) {
            if (o.getScore(s).getScore() == score) {
                return o.getScore(s).getEntry();
            }
        }
        return null;
    }

    public static boolean hasScoreTaken(Objective o, int score) {
        for (String s : o.getScoreboard().getEntries()) {
            if (o.getScore(s).getScore() == score) {
                return true;
            }
        }
        return false;
    }

    public static void replaceScore(Objective o, int score, String name) {
        if (hasScoreTaken(o, score)) {
            if (getEntryFromScore(o, score).equalsIgnoreCase(name)) {
                return;
            }
            if (!getEntryFromScore(o, score).equalsIgnoreCase(name)) {
                o.getScoreboard().resetScores(getEntryFromScore(o, score));
            }
        }
        o.getScore(name).setScore(score);
    }

    //TODO Sorteren, andere team naampies?
    public static void setTabPrefix(Player player) {
        Scoreboard board = player.getScoreboard();
        for (Player all : Bukkit.getOnlinePlayers()) {
            Integer weight = Data.getUser(all).getPrefix() == null ? 0 : Data.getPrefix(Data.getUser(all).getPrefix()).getWeight();
            Team team = board.getTeam(all.getName()) == null ? board.registerNewTeam(all.getName()) : board.getTeam(all.getName());

            String prefix = Data.getUser(all).getPrefix() == null ? "" : Data.getPrefix(Data.getUser(all).getPrefix()).getPrefix() + " ";
            String nameColor = Data.getUser(all).getActiveNamecolor() == null ? "&7" : Data.getNamecolor(Data.getUser(all).getActiveNamecolor()).getColor().toString();
            String suffix = Data.getUser(all).getActiveSuffix() == null ? "" : " " + Data.getSuffix(Data.getUser(all).getActiveSuffix());

            team.setPrefix(color(prefix + nameColor));
            team.setSuffix(color(suffix));
            team.setNameTagVisibility(NameTagVisibility.ALWAYS);
            team.addEntry(all.getName());
        }
    }

    public static void updateHP(Player player){
        //TODO Show HP
    }

    public static void showScoreboard(Player p) {
        if (p.getScoreboard().equals(p.getServer().getScoreboardManager().getMainScoreboard())) {
            p.setScoreboard(p.getServer().getScoreboardManager().getNewScoreboard());
        }
        setTabPrefix(p);
        Scoreboard score;
        score = p.getScoreboard();
        User user = Data.getUser(p);

        Objective objective = score.getObjective(p.getName()) == null ? score.registerNewObjective(p.getName(), "dummy") : score.getObjective(p.getName());

        objective.setDisplayName(color("&d&l» &5&lMiraclePvP &d&l«"));
        replaceScore(objective, 14, color("&5Player »"));
        replaceScore(objective, 13, color("&f Name: &7" + p.getName()));
        replaceScore(objective, 12, color("&f Level: &7" + user.getLevel() + " &8[&7" + user.getExperience() + " exp&8]"));
        replaceScore(objective, 11, color("&f Coins: &7" + user.getCoins()));
        replaceScore(objective, 10, color("&3"));
        replaceScore(objective, 9, color("&5Stats »"));
        replaceScore(objective, 8, color("&f Kills: &7" + user.getKills()));
        replaceScore(objective, 7, color("&f Deaths: &7" + user.getDeaths()));
        replaceScore(objective, 6, color("&f K/D Ratio: &7" + user.getKdRatio()));
        replaceScore(objective, 5, color("&2"));
        replaceScore(objective, 4, color("&5Streaks »"));
        replaceScore(objective, 3, color("&f Killstreak: &7" + user.getKillstreak()));
        replaceScore(objective, 2, color("&f Highest Killstreak: &7" + user.getBestkillstreak()));
        replaceScore(objective, 1, color("&1"));
        replaceScore(objective, 0, color("&7play.miraclepvp.net"));

        if (objective.getDisplaySlot() != DisplaySlot.SIDEBAR) {
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
    }
}
