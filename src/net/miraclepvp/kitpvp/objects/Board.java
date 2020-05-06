package net.miraclepvp.kitpvp.objects;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.prefix.Prefix;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import static net.miraclepvp.kitpvp.bukkit.Text.IntegerToCompactInteger;
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

    public static void addPlayerInTab(Player player) {
        //Add player in other player's tablist
        for (Player all : Bukkit.getOnlinePlayers()) {

            //Get scoreboard
            Scoreboard board = all.getScoreboard();

            //Get targeted user
            User user = Data.getUser(player);

            //Setting targeted player to prefix if prefix is found
            try {
                String teamname = Data.getPrefix(user.getPrefix()).getWeight() + Data.getPrefix(user.getPrefix()).getName();
                Prefix prefix = Data.getPrefix(user.getPrefix());
                if (board.getTeam(teamname) == null) {
                    Team team = board.registerNewTeam(teamname);
                    team.setPrefix(color(prefix.getPrefix() + (prefix.getName().equalsIgnoreCase("default") ? "" : "&7 ")));
                    team.setNameTagVisibility(NameTagVisibility.ALWAYS);
                }
                board.getTeam(teamname).addEntry(player.getName());
            } catch(Exception ex){
                board.getTeam("ENone").addEntry(player.getName());
            }
        }
    }

    public static void showEveryoneInTab(Player player) {
        //Showing everyone in this players tablist

        //Get scoreboard
        Scoreboard board = player.getScoreboard();

        if(board.getTeam("ENone") == null){
            Team none = board.registerNewTeam("ENone");
            none.setPrefix(color("&7"));
            none.setNameTagVisibility(NameTagVisibility.ALWAYS);
        }
        Team none = board.getTeam("ENone");

        for(Player all : Bukkit.getOnlinePlayers()) {
            //Get targeted user
            User user = Data.getUser(all);

            //Setting targeted player to none if no prefix is found
            if (user.getPrefix() == null || Data.getPrefix(user.getPrefix()) == null) {
                none.addEntry(all.getName());
                return;
            }

            //Setting targeted player to prefix if prefix is found
            else {
                String teamname = Data.getPrefix(user.getPrefix()).getWeight() + Data.getPrefix(user.getPrefix()).getName();
                Prefix prefix = Data.getPrefix(user.getPrefix());
                if (board.getTeam(teamname) == null) {
                    Team team = board.registerNewTeam(teamname);
                    team.setPrefix(color(prefix.getPrefix() + (prefix.getName().equalsIgnoreCase("default") ? "" : "&7 ")));
                    team.setNameTagVisibility(NameTagVisibility.ALWAYS);
                }
                board.getTeam(teamname).addEntry(all.getName());
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

        Objective objective = score.getObjective(p.getName()) == null ? score.registerNewObjective(p.getName(), "dummy") : score.getObjective(p.getName());

        objective.setDisplayName(color("&d&l» &5&lMiraclePvP &d&l«"));
        replaceScore(objective, 15, color("&5Level &d»"));
        replaceScore(objective, 14, color("&f Level: &7" + user.getLevel()));
        replaceScore(objective, 13, color("&f Progress: &7" + IntegerToCompactInteger(user.getExperience(), 1, false) + "&8/&7" + IntegerToCompactInteger(user.getExperienceNeeded(),1, false)));
        replaceScore(objective, 12, color("&f  " + user.getProgressBar()));
        replaceScore(objective, 11, color("&3"));
        replaceScore(objective, 10, color("&5Stats &d»"));
        replaceScore(objective, 9, color("&f Kills: &7" + IntegerToCompactInteger(user.getKills(),1, false)));
        replaceScore(objective, 8, color("&f Assists: &7" + IntegerToCompactInteger(user.getAssists(),1, false)));
        replaceScore(objective, 7, color("&f Deaths: &7" + IntegerToCompactInteger(user.getDeaths(),1, false)));
        replaceScore(objective, 6, color("&f K/D Ratio: &7" + user.getKdRatio()));
        replaceScore(objective, 5, color("&2"));
        replaceScore(objective, 4, color("&5Streaks &d»"));
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
