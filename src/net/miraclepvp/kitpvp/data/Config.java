package net.miraclepvp.kitpvp.data;

import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.bukkit.FileManager;
import net.miraclepvp.kitpvp.objects.Board;
import net.miraclepvp.kitpvp.objects.Tablist;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;

public class Config {

    private static String
            serverName = "KitPvP",
            discordLink = "";
    private static Integer
            broadcastDelay = 300,
            signReloadDelay = 900;
    private static Boolean
            useMySQL = false,
            chatNameHover = true,
            toggleFriendlyFire = false;
    private static ArrayList<String>
            curseWords = new ArrayList<>(),
            broadcastMessages = new ArrayList<>(),
            anvils = new ArrayList<>();
    private static Location lobbyLoc = new Location(Bukkit.getWorlds().get(0), 0,0,0,0,0);

    private static ArrayList<Location> spawnpoints = new ArrayList<>();

    public static void load(){
        curseWords.clear();
        spawnpoints.clear();
        broadcastMessages.clear();
        anvils.clear();

        FileManager.load(Main.getInstance(), "config.yml");
        FileConfiguration config = FileManager.get("config.yml");
        serverName = config.getString("broadcast.servername");
        discordLink = config.getString("discord.link");
        broadcastDelay = config.getInt("broadcast.delay");
        signReloadDelay = config.getInt("sign.reloaddelay");

        useMySQL = config.getBoolean("use-mysql");
        toggleFriendlyFire = config.getBoolean("pvp.toggle-guild-friendlyfire");

        curseWords.addAll(config.getStringList("chat.curseWords"));
        chatNameHover = config.getBoolean("chat.name-hover");

        broadcastMessages.addAll(config.getStringList("broadcast.messages"));
        anvils.addAll(config.getStringList("anvils"));

        lobbyLoc = FileManager.deSerialize(config.getString("lobby.location"));

        FileManager.load(Main.getInstance(), "spawnpoints.yml");
        FileConfiguration spawnfile = FileManager.get("spawnpoints.yml");
        spawnpoints.clear();
        spawnfile.getStringList("locations").forEach(string -> spawnpoints.add(FileManager.deSerialize(string)));

        Board.load();
        Tablist.load();
    }

    public static void save(){
        FileManager.set("config.yml", "lobby.location", FileManager.serialize(lobbyLoc));
        FileManager.set("config.yml", "anvils", anvils);
        FileManager.save(Main.getInstance(), "config.yml");

        ArrayList<String> stringlocs = new ArrayList<>();
        stringlocs.clear();
        spawnpoints.forEach(loc -> stringlocs.add(FileManager.serialize(loc)));
        FileManager.set("spawnpoints.yml", "locations", stringlocs);
        FileManager.save(Main.getInstance(), "spawnpoints.yml");
    }

    public static void reload(){
        FileManager.reload(Main.getInstance(), "config.yml");
        load();
    }

    public static ArrayList<String> getBroadcastMessages() {
        return broadcastMessages;
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

    public static Boolean getToggleFriendlyFire() {
        return toggleFriendlyFire;
    }

    public static Integer getSignReloadDelay() {
        return signReloadDelay;
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

    public static ArrayList<String> getAnvils() {
        return anvils;
    }

    public static Boolean getChatNameHover() {
        return chatNameHover;
    }

    public static Boolean UseMySQL() {
        return useMySQL;
    }
}
