package net.miraclepvp.kitpvp.data;

import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.bukkit.FileManager;
import net.miraclepvp.kitpvp.objects.NickManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;

public class Config {

    private static String serverName = "KitPvP";
    private static String discordLink = "";
    private static Integer broadcastDelay = 300;
    private static Integer signReloadDelay = 900;
    private static ArrayList<String> messages = new ArrayList<>();
    private static ArrayList<String> curseWords = new ArrayList<>();
    private static Location lobbyLoc = new Location(Bukkit.getWorlds().get(0), 0,0,0,0,0);

    private static ArrayList<Location> spawnpoints = new ArrayList<>();

    public static void load(){
        messages.clear();
        curseWords.clear();
        spawnpoints.clear();

        FileManager.load(Main.getInstance(), "config.yml");
        FileConfiguration config = FileManager.get("config.yml");
        serverName = config.getString("broadcast.servername");
        discordLink = config.getString("discord.link");
        broadcastDelay = config.getInt("broadcast.delay");
        signReloadDelay = config.getInt("sign.reloaddelay");
        for (int i = 0; i < config.getStringList("broadcast.messages").size(); i++)
            messages.add(config.getStringList("broadcast.messages").get(i));
        for (int i = 0; i < config.getStringList("chat.curseWords").size(); i++)
            curseWords.add(config.getStringList("chat.curseWords").get(i));
        lobbyLoc = FileManager.deSerialize(config.getString("lobby.location"));

        FileManager.load(Main.getInstance(), "spawnpoints.yml");
        FileConfiguration spawnfile = FileManager.get("spawnpoints.yml");
        spawnpoints.clear();
        spawnfile.getStringList("locations").forEach(string -> spawnpoints.add(FileManager.deSerialize(string)));

        FileManager.load(Main.getInstance(), "nicknames.yml");
        FileConfiguration names = FileManager.get("nicknames.yml");
        NickManager.nickNames = names.getStringList("nicknames");
    }

    public static void save(){
        if(!FileManager.get("config.yml").getString("lobby.location").equalsIgnoreCase(FileManager.serialize(lobbyLoc))) {
            FileManager.set("config.yml", "lobby.location", FileManager.serialize(lobbyLoc));
            FileManager.save(Main.getInstance(), "config.yml");
        }

        ArrayList<String> stringlocs = new ArrayList<>();
        stringlocs.clear();
        spawnpoints.forEach(loc -> stringlocs.add(FileManager.serialize(loc)));
        FileManager.set("spawnpoints.yml", "locations", stringlocs);
        FileManager.save(Main.getInstance(), "spawnpoints.yml");

        FileManager.set("nicknames.yml", "nicknames", NickManager.nickNames);
    }

    public static void reload(){
        FileManager.reload(Main.getInstance(), "config.yml");
        load();
    }

    public static String getDiscordLink() {
        return discordLink;
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

    public static ArrayList<String> getCurseWords() {
        return curseWords;
    }

    public static Location getLobbyLoc() {
        return lobbyLoc;
    }

    public static void setLobbyLoc(Location lobbyLoc) {
        Config.lobbyLoc = lobbyLoc;
    }

    public static ArrayList<Location> getSpawnpoints() {
        return spawnpoints;
    }
}
