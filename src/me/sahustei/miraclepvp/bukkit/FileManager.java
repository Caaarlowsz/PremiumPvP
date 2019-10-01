package me.sahustei.miraclepvp.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileManager {

    public static Map<String, FileConfiguration> configs = new HashMap();

    public FileManager() {
    }

    public static boolean isFileLoaded(String fileName) {
        return configs.containsKey(fileName);
    }

    public static void loadSchematic(Plugin plugin, String fileName) {
        File file = new File(plugin.getDataFolder() + "/schematics/", fileName);
        if (!file.exists()) {
            try {
                plugin.saveResource(fileName, false);
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }
    }

    public static void load(Plugin plugin, String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            try {
                plugin.saveResource(fileName, false);
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }

        if (!isFileLoaded(fileName)) {
            configs.put(fileName, YamlConfiguration.loadConfiguration(file));
        }

    }

    public static FileConfiguration get(String fileName) {
        return isFileLoaded(fileName) ? (FileConfiguration) configs.get(fileName) : null;
    }

    public static boolean update(String fileName, String path, Object value) {
        if (isFileLoaded(fileName) && !((FileConfiguration) configs.get(fileName)).contains(path)) {
            ((FileConfiguration) configs.get(fileName)).set(path, value);
            return true;
        } else {
            return false;
        }
    }

    public static void set(String fileName, String path, Object value) {
        if (isFileLoaded(fileName)) {
            ((FileConfiguration) configs.get(fileName)).set(path, value);
        }

    }

    public void addComment(String fileName, String path, String... comments) {
        if (isFileLoaded(fileName)) {
            String[] var4 = comments;
            int var5 = comments.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                String comment = var4[var6];
                if (!((FileConfiguration) configs.get(fileName)).contains(path)) {
                    ((FileConfiguration) configs.get(fileName)).set("_COMMENT_" + comments.length, " " + comment);
                }
            }
        }

    }

    public static void remove(String fileName, String path) {
        if (isFileLoaded(fileName)) {
            ((FileConfiguration) configs.get(fileName)).set(path, (Object) null);
        }

    }

    public static boolean contains(String fileName, String path) {
        return isFileLoaded(fileName) ? ((FileConfiguration) configs.get(fileName)).contains(path) : false;
    }

    public static void reload(Plugin plugin, String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if (isFileLoaded(fileName)) {
            try {
                ((FileConfiguration) configs.get(fileName)).load(file);
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }

    }

    public static void save(Plugin plugin, String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if (isFileLoaded(fileName)) {
            try {
                ((FileConfiguration) configs.get(fileName)).save(file);
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }
    }

    public static String serialize(Location location) {
        String text = "";
        text += location.getWorld().getName() + ";";
        text += location.getX() + ";";
        text += location.getY() + ";";
        text += location.getZ() + ";";
        text += location.getYaw() + ";";
        text += location.getPitch() + ";";
        return text;
    }

    public static Location deSerialize(String locationRaw) {
        locationRaw = locationRaw.replaceAll("_", ".");
        String[] splittedLocationRaw = locationRaw.split(";");
        return new Location(
                Bukkit.getWorld(splittedLocationRaw[0]),
                Double.valueOf(splittedLocationRaw[1]),
                Double.valueOf(splittedLocationRaw[2]),
                Double.valueOf(splittedLocationRaw[3]),
                Float.valueOf(splittedLocationRaw[4]),
                Float.valueOf(splittedLocationRaw[5])
        );
    }
}