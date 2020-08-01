package net.miraclepvp.kitpvp.data.report;

import net.miraclepvp.kitpvp.data.SQL;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.UUID;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class Reports {

    public static class Report {

        private Integer id;
        private UUID
                user,
                target;
        private List<String>
                reasons;

        public Report(UUID player, UUID target, List<String> reasons){
            String reason = reasons.toString().substring(1, reasons.toString().length()-1);
            SQL.ReportSQL.createReport(player, target, reason);
            Bukkit.getOnlinePlayers().stream().filter(all -> all.hasPermission("miraclepvp.report.receive")).forEach(
                    all -> all.sendMessage(color("&5&k!! &f" + Bukkit.getOfflinePlayer(player).getName() + " reported " + Bukkit.getOfflinePlayer(target).getName() + " for " + reason + "."))
            );
            this.id = 0; //TODO get id from database
            this.user = player;
            this.target = target;
            this.reasons = reasons;
        }

        public UUID getUser() {
            return user;
        }

        public UUID getTarget() {
            return target;
        }

        public List<String> getReasons() {
            return reasons;
        }
    }

    public enum ReportCategory {

        CHAT,
        CHEATING,
        ABUSING,
        OTHERS;

        ReportCategory(){
        }

        public enum Chat {

            ADVERTISING,
            SPAMMING,
            CURSING;

            Chat() {
            }

        }

        public enum Cheating {

            SPEED,
            KILLAURA,
            FLY,
            JUMP,
            VELOCITY,
            ESP,
            REACH,
            AUTOCLICKER;

            Cheating() {
            }

        }

        public enum Abusing {

            ALT_ABUSE,
            BUGGING;

            Abusing() {
            }

        }

        public enum Others {

            BAD_NAME,
            BAD_SKIN,
            TEAMING;

            Others() {
            }

        }

    }
}
