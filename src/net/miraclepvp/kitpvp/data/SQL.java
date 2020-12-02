package net.miraclepvp.kitpvp.data;

import net.miraclepvp.kitpvp.Main;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class SQL {

    public static class ReportSQL {

        public static void createReport(UUID player, UUID target, String reason){
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();

                PreparedStatement statement = Main.getInstance().getConnection().prepareStatement("INSERT INTO reports(uid, tid, message, date) VALUES (?,?,?,?);");
                statement.setString(1, player.toString());
                statement.setString(2, target.toString());
                statement.setString(3, reason);
                statement.setString(4, formatter.format(date));
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
                statement.executeUpdate("" +
                        "CREATE TABLE IF NOT EXISTS reports(" +
                        "id INT(11) NOT NULL AUTO_INCREMENT," +
                        "uid VARCHAR(36) NOT NULL," +
                        "tid VARCHAR(36) NOT NULL," +
                        "message LONGTEXT NOT NULL," +
                        "PRIMARY KEY (id)," +
                        "date VARCHAR(10) NOT NULL," +
                        "done TINYINT(1) DEFAULT '0'," +
                        "assigned VARCHAR(36));"
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
