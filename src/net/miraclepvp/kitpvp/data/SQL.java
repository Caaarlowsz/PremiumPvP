package net.miraclepvp.kitpvp.data;

import net.miraclepvp.kitpvp.Main;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class SQL {

    public static class ReportSQL {

        public static void createReport(UUID player, UUID target, String reason){
            try {
                PreparedStatement statement = Main.getInstance().getConnection().prepareStatement("INSERT INTO reports(uid, tid, message) VALUES (?,?,?);");
                statement.setString(1, player.toString());
                statement.setString(2, target.toString());
                statement.setString(3, reason);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void loadTabel() {

        }

        public static void createTabel() {
            try {
                PreparedStatement statement = Main.getInstance().getConnection().prepareStatement("SELECT *");
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS reports(id INT(11) NOT NULL AUTO_INCREMENT,uid VARCHAR(255) NOT NULL,tid VARCHAR(255) NOT NULL,message VARCHAR(255) NOT NULL,PRIMARY KEY (id));");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
