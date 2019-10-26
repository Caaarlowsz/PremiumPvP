package net.miraclepvp.kitpvp.objects;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.prefix.Prefix;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

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

    public static void updateTabPrefix(Player player) {
        Scoreboard board = player.getScoreboard();
        Team none = board.getTeam("ENone") == null ? board.registerNewTeam("ENone") : board.getTeam("ENone");
        none.setPrefix(color("&7"));
        none.setNameTagVisibility(NameTagVisibility.ALWAYS);
        for (Prefix prefix : Data.prefixes) {
            String teamname = prefix.getWeight() + prefix.getName();
            if (board.getTeam(teamname) == null) {
                Team team = board.registerNewTeam(teamname);
                team.setPrefix(color(prefix.getPrefix() + "&7 "));
                team.setNameTagVisibility(NameTagVisibility.ALWAYS);
            }
        }
        for (Player all : Bukkit.getOnlinePlayers()) {
            User user = Data.getUser(all);
            try {
                Prefix prefix = Data.getPrefix(user.getPrefix());
                String teamname = prefix.getWeight() + prefix.getName();
                if (board.getTeam(teamname) != null)
                    board.getTeam(teamname).addEntry(all.getName());
            } catch (Exception ex) {
                none.addEntry(all.getName());
            }
        }

    }

    public static void loadHP(Player player) {
        Scoreboard score = player.getScoreboard();
        Objective objective = score.getObjective("health") == null ? score.registerNewObjective("health", "health") : score.getObjective("health");
        objective.setDisplayName(color("&c❤"));
        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
    }

    public static void showScoreboard(Player p) {
        if (p.getScoreboard().equals(p.getServer().getScoreboardManager().getMainScoreboard())) {
            p.setScoreboard(p.getServer().getScoreboardManager().getNewScoreboard());
        }
        Scoreboard score;
        score = p.getScoreboard();
        User user = Data.getUser(p);

        updateTabPrefix(p);

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

        loadHP(p);
    }
}
