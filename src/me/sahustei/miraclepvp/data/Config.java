package me.sahustei.miraclepvp.data;

import me.sahustei.miraclepvp.Main;
import me.sahustei.miraclepvp.bukkit.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;

public class Config {

    private static String serverName = "KitPvP";
    private static Integer broadcastDelay = 300;
    private static Integer signReloadDelay = 900;
    private static ArrayList<String> messages = new ArrayList<>();
    private static Location lobbyLoc = new Location(Bukkit.getWorlds().get(0), 0,0,0,0,0);

    public static void load(){
        FileManager.load(Main.getInstance(), "config.yml");
        FileConfiguration config = FileManager.get("config.yml");
        serverName = config.getString("broadcast.servername");
        broadcastDelay = config.getInt("broadcast.delay");
        broadcastDelay = config.getInt("sign.reloaddelay");
        for (int i = 0; i < config.getStringList("broadcast.messages").size(); i++)
            messages.add(config.getStringList("broadcast.messages").get(i));
        lobbyLoc = FileManager.deSerialize(config.getString("lobby.location"));
    }

    public static void save(){
        if(!FileManager.get("config.yml").getString("lobby.location").equalsIgnoreCase(FileManager.serialize(lobbyLoc))) {
            FileManager.set("config.yml", "lobby.location", FileManager.serialize(lobbyLoc));
            FileManager.save(Main.getInstance(), "config.yml");
        }
    }

    public static String getServerName() {
        return serverName;
    }

    public static Integer getBroadcastDelay() {
        return broadcastDelay;
    }

    public static Integer getSignReloadDelay() {
        return signReloadDelay;
    }

    public static ArrayList<String> getMessages() {
        return messages;
    }

    public static Location getLobbyLoc() {
        return lobbyLoc;
    }

    public static void setLobbyLoc(Location lobbyLoc) {
        Config.lobbyLoc = lobbyLoc;
    }
}
