package net.miraclepvp.kitpvp.data;

import com.google.gson.Gson;
import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.bukkit.WorldManager;
import net.miraclepvp.kitpvp.data.chatcolor.Chatcolor;
import net.miraclepvp.kitpvp.data.duel.Map;
import net.miraclepvp.kitpvp.data.guild.Guild;
import net.miraclepvp.kitpvp.data.kit.Kit;
import net.miraclepvp.kitpvp.data.namecolor.Namecolor;
import net.miraclepvp.kitpvp.data.prefix.Prefix;
import net.miraclepvp.kitpvp.data.user.Booster;
import net.miraclepvp.kitpvp.data.zone.Zone;
import net.miraclepvp.kitpvp.data.sign.Sign;
import net.miraclepvp.kitpvp.data.suffix.Suffix;
import net.miraclepvp.kitpvp.data.trail.Trail;
import net.miraclepvp.kitpvp.data.user.User;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Data {

    public static List<User> users = new ArrayList<>();
    public static List<Trail> trails = new ArrayList<>();
    public static List<Suffix> suffixes = new ArrayList<>();
    public static List<Prefix> prefixes = new ArrayList<>();
    public static List<Chatcolor> chatcolors = new ArrayList<>();
    public static List<Namecolor> namecolors = new ArrayList<>();
    public static List<Kit> kits = new ArrayList<>();
    public static List<Sign> signs = new ArrayList<>();
    public static List<Guild> guilds = new ArrayList<>();
    public static List<Zone> zones = new ArrayList<>();
    public static List<Map> maps = new ArrayList<>();

    public static User getUser(OfflinePlayer player) {
        return users.stream().filter(u -> u.getUuid().equals(player.getUniqueId())).findFirst().get();
    }

    public static Trail getTrail(String name) {
        return trails.stream().filter(t -> t.getName().equalsIgnoreCase(name)).findFirst().get();
    }

    public static Trail getTrail(UUID uuid) {
        return trails.stream().filter(t -> t.getUuid().equals(uuid)).findFirst().get();
    }

    public static Suffix getSuffix(String name) {
        return suffixes.stream().filter(s -> s.getName().equalsIgnoreCase(name)).findFirst().get();
    }

    public static Suffix getSuffix(UUID uuid) {
        return suffixes.stream().filter(s -> s.getUuid().equals(uuid)).findFirst().get();
    }

    public static Prefix getPrefix(String name) {
        return prefixes.stream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst().get();
    }

    public static Prefix getPrefix(UUID uuid) {
        return prefixes.stream().filter(p -> p.getUuid().equals(uuid)).findFirst().get();
    }

    public static Chatcolor getChatcolor(String name) {
        return chatcolors.stream().filter(c -> c.getName().equalsIgnoreCase(name)).findFirst().get();
    }

    public static Chatcolor getChatcolor(UUID uuid) {
        return chatcolors.stream().filter(c -> c.getUuid().equals(uuid)).findFirst().get();
    }

    public static Namecolor getNamecolor(String name) {
        return namecolors.stream().filter(n -> n.getName().equalsIgnoreCase(name)).findFirst().get();
    }

    public static Namecolor getNamecolor(UUID uuid) {
        return namecolors.stream().filter(n -> n.getUuid().equals(uuid)).findFirst().get();
    }

    public static Sign getSign(UUID uuid) {
        return signs.stream().filter(si -> si.getUuid().equals(uuid)).findFirst().get();
    }

    public static Sign getSign(Location loc) {
        return signs.stream().filter(si -> si.getLocation().equals(loc)).findFirst().get();
    }

    public static Kit getKit(String name) {
        return kits.stream().filter(k -> k.getName().equalsIgnoreCase(name)).findFirst().get();
    }

    public static Kit getKit(UUID uuid) {
        return kits.stream().filter(k -> k.getUuid().equals(uuid)).findFirst().get();
    }

    public static Guild getGuild(String name) {
        return guilds.stream().filter(g -> g.getName().equalsIgnoreCase(name)).findFirst().get();
    }

    public static Guild getGuild(UUID uuid) {
        return guilds.stream().filter(g -> g.getUuid().equals(uuid)).findFirst().get();
    }

    public static Zone getZone(String name) {
        return zones.stream().filter(z -> z.getName().equalsIgnoreCase(name)).findFirst().get();
    }

    public static Zone getZone(UUID uuid) {
        return zones.stream().filter(z -> z.getUuid().equals(uuid)).findFirst().get();
    }

    public static Map getMap(String name) {
        return maps.stream().filter(m -> m.name.equalsIgnoreCase(name)).findFirst().get();
    }

    public static Map getMap(UUID uuid) {
        return maps.stream().filter(m -> m.uuid.equals(uuid)).findFirst().get();
    }

    public static void load() {
        Gson gson = new Gson();
        File dataFolder = new File(Main.getInstance().getDataFolder() + File.separator + "data");
        if (!dataFolder.exists()) {
            try {
                dataFolder.mkdir();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        File playerDataFolder = getDirectory(dataFolder, "players");
        File trailDataFolder = getDirectory(dataFolder, "trails");
        File suffixDataFolder = getDirectory(dataFolder, "suffixes");
        File prefixDataFolder = getDirectory(dataFolder, "prefixes");
        File chatcolorsDataFolder = getDirectory(dataFolder, "chatcolors");
        File namecolorsDataFolder = getDirectory(dataFolder, "namecolors");
        File signsDataFolder = getDirectory(dataFolder, "signs");
        File kitsDataFolder = getDirectory(dataFolder, "kits");
        File guildsDataFolder = getDirectory(dataFolder, "guilds");
        File zonesDataFolder = getDirectory(dataFolder, "zones");
        File boostersDataFolder = getDirectory(dataFolder, "activeBoosters");
        File mapsDataFolder = getDirectory(dataFolder, "maps");
        users.clear();
        if (playerDataFolder.listFiles().length > 0) {
            Arrays.stream(playerDataFolder.listFiles()).filter(file -> !file.isDirectory()).filter(file -> file.getName().contains(".json")).forEach(file -> {
                try {
                    users.add(gson.fromJson(FileUtils.readFileToString(file), User.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        trails.clear();
        if (trailDataFolder.listFiles().length > 0) {
            Arrays.stream(trailDataFolder.listFiles()).filter(file -> !file.isDirectory()).filter(file -> file.getName().contains(".json")).forEach(file -> {
                try {
                    trails.add(gson.fromJson(FileUtils.readFileToString(file), Trail.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        suffixes.clear();
        if (suffixDataFolder.listFiles().length > 0) {
            Arrays.stream(suffixDataFolder.listFiles()).filter(file -> !file.isDirectory()).filter(file -> file.getName().contains(".json")).forEach(file -> {
                try {
                    suffixes.add(gson.fromJson(FileUtils.readFileToString(file), Suffix.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        prefixes.clear();
        if (prefixDataFolder.listFiles().length > 0) {
            Arrays.stream(prefixDataFolder.listFiles()).filter(file -> !file.isDirectory()).filter(file -> file.getName().contains(".json")).forEach(file -> {
                try {
                    prefixes.add(gson.fromJson(FileUtils.readFileToString(file), Prefix.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        try{
            getPrefix("Default");
        }catch (Exception ex){
            Data.prefixes.add(new Prefix("Default", "&7"));
        }
        chatcolors.clear();
        if (chatcolorsDataFolder.listFiles().length > 0) {
            Arrays.stream(chatcolorsDataFolder.listFiles()).filter(file -> !file.isDirectory()).filter(file -> file.getName().contains(".json")).forEach(file -> {
                try {
                    chatcolors.add(gson.fromJson(FileUtils.readFileToString(file), Chatcolor.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        namecolors.clear();
        if (namecolorsDataFolder.listFiles().length > 0) {
            Arrays.stream(namecolorsDataFolder.listFiles()).filter(file -> !file.isDirectory()).filter(file -> file.getName().contains(".json")).forEach(file -> {
                try {
                    namecolors.add(gson.fromJson(FileUtils.readFileToString(file), Namecolor.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        signs.clear();
        if (signsDataFolder.listFiles().length > 0) {
            Arrays.stream(signsDataFolder.listFiles()).filter(file -> !file.isDirectory()).filter(file -> file.getName().contains(".json")).forEach(file -> {
                try {
                    signs.add(gson.fromJson(FileUtils.readFileToString(file), Sign.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        kits.clear();
        if (kitsDataFolder.listFiles().length > 0) {
            Arrays.stream(kitsDataFolder.listFiles()).filter(file -> !file.isDirectory()).filter(file -> file.getName().contains(".json")).forEach(file -> {
                try {
                    kits.add(gson.fromJson(FileUtils.readFileToString(file), Kit.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        guilds.clear();
        if (guildsDataFolder.listFiles().length > 0) {
            Arrays.stream(guildsDataFolder.listFiles()).filter(file -> !file.isDirectory()).filter(file -> file.getName().contains(".json")).forEach(file -> {
                try {
                    guilds.add(gson.fromJson(FileUtils.readFileToString(file), Guild.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        zones.clear();
        if (zonesDataFolder.listFiles().length > 0) {
            Arrays.stream(zonesDataFolder.listFiles()).filter(file -> !file.isDirectory()).filter(file -> file.getName().contains(".json")).forEach(file -> {
                try {
                    zones.add(gson.fromJson(FileUtils.readFileToString(file), Zone.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        maps.clear();
        if (mapsDataFolder.listFiles().length > 0) {
            Arrays.stream(mapsDataFolder.listFiles()).filter(file -> !file.isDirectory()).filter(file -> file.getName().contains(".json")).forEach(file -> {
                try {
                    Map map = gson.fromJson(FileUtils.readFileToString(file), Map.class);
                    maps.add(map);
                    map.arenaList.forEach(arena -> arena.enabled = true);
                    WorldManager.loadEmptyWorld(World.Environment.NORMAL, map.name.toLowerCase());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        Booster.activeBoosters.clear();
        Booster.activePersonalBoosters.clear();
        if (boostersDataFolder.listFiles().length > 0) {
            Arrays.stream(boostersDataFolder.listFiles()).filter(file -> !file.isDirectory()).filter(file -> file.getName().contains(".json")).forEach(file -> {
                try {
                    Booster.ActiveBooster booster = gson.fromJson(FileUtils.readFileToString(file), Booster.ActiveBooster.class);
                    if (booster.personal)
                        Booster.activePersonalBoosters.put(booster.owner, booster);
                    else {
                        Booster.activeBoosters.add(booster);
                        if (booster.boosterType.equals(Booster.BoosterType.COINS))
                            Booster.coinBoost = +booster.percentage;
                        else if (booster.boosterType.equals(Booster.BoosterType.EXPERIENCE))
                            Booster.experienceBoost = +booster.percentage;
                    }
                    booster.forceRunnable();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void save(Boolean isBackup) {
        Gson gson = new Gson();
        File dataFolder = new File(Main.getInstance().getDataFolder() + File.separator + "data");

        if (isBackup) {
            //The save is a backup
            File main = new File("Backups-MiraclePvP");
            if (!main.exists()) {
                try {
                    main.mkdir();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            Date date = new Date();

            dataFolder = getDirectory(main, formatter.format(date));

            if (!dataFolder.exists()) {
                try {
                    dataFolder.mkdir();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        File playerDataFolder = getDirectory(dataFolder, "players");
        File trailDataFolder = getDirectory(dataFolder, "trails");
        File suffixDataFolder = getDirectory(dataFolder, "suffixes");
        File prefixDataFolder = getDirectory(dataFolder, "prefixes");
        File chatcolorsDataFolder = getDirectory(dataFolder, "chatcolors");
        File namecolorsDataFolder = getDirectory(dataFolder, "namecolors");
        File signsDataFolder = getDirectory(dataFolder, "signs");
        File kitsDataFolder = getDirectory(dataFolder, "kits");
        File guildsDataFolder = getDirectory(dataFolder, "guilds");
        File zonesDataFolder = getDirectory(dataFolder, "zones");
        File boostersDataFolder = getDirectory(dataFolder, "activeBoosters");
        File mapsDataFolder = getDirectory(dataFolder, "maps");

        if (!isBackup)
            clearFolder(playerDataFolder);
        for (User user : users) {
            try {
                try {
                    File file = new File(playerDataFolder + File.separator + user.getUuid() + ".json");
                    file.createNewFile();
                    FileUtils.write(file, gson.toJson(user));
                } catch (IllegalArgumentException ex) {
                    Bukkit.getLogger().warning("Something went wrong saving data. Backup? " + isBackup.toString() + ". User: " + user.getUuid());
                    return;
                } catch (IOException ex) {
                    Bukkit.getLogger().warning("Something went wrong saving data. Backup? " + isBackup.toString() + ". User: " + user.getUuid());
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        if (!isBackup)
            clearFolder(trailDataFolder);
        for (Trail trail : trails) {
            try {
                File file = new File(trailDataFolder + File.separator + trail.getUuid() + ".json");
                file.createNewFile();
                FileUtils.write(file, gson.toJson(trail));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!isBackup)
            clearFolder(suffixDataFolder);
        for (Suffix suffix : suffixes) {
            try {
                File file = new File(suffixDataFolder + File.separator + suffix.getUuid() + ".json");
                file.createNewFile();
                FileUtils.write(file, gson.toJson(suffix));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!isBackup)
            clearFolder(prefixDataFolder);
        for (Prefix prefix : prefixes) {
            try {
                File file = new File(prefixDataFolder + File.separator + prefix.getUuid() + ".json");
                file.createNewFile();
                FileUtils.write(file, gson.toJson(prefix));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!isBackup)
            clearFolder(chatcolorsDataFolder);
        for (Chatcolor color : chatcolors) {
            try {
                File file = new File(chatcolorsDataFolder + File.separator + color.getUuid() + ".json");
                file.createNewFile();
                FileUtils.write(file, gson.toJson(color));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!isBackup)
            clearFolder(namecolorsDataFolder);
        for (Namecolor color : namecolors) {
            try {
                File file = new File(namecolorsDataFolder + File.separator + color.getUuid() + ".json");
                file.createNewFile();
                FileUtils.write(file, gson.toJson(color));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!isBackup)
            clearFolder(signsDataFolder);
        for (Sign sign : signs) {
            try {
                File file = new File(signsDataFolder + File.separator + sign.getUuid() + ".json");
                file.createNewFile();
                FileUtils.write(file, gson.toJson(sign));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!isBackup)
            clearFolder(kitsDataFolder);
        for (Kit kit : kits) {
            try {
                File file = new File(kitsDataFolder + File.separator + kit.getUuid() + ".json");
                file.createNewFile();
                FileUtils.write(file, gson.toJson(kit));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!isBackup)
            clearFolder(guildsDataFolder);
        for (Guild guild : guilds) {
            try {
                File file = new File(guildsDataFolder + File.separator + guild.getUuid() + ".json");
                file.createNewFile();
                FileUtils.write(file, gson.toJson(guild));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!isBackup)
            clearFolder(zonesDataFolder);
        for (Zone zone : zones) {
            try {
                File file = new File(zonesDataFolder + File.separator + zone.getUuid() + ".json");
                file.createNewFile();
                FileUtils.write(file, gson.toJson(zone));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!isBackup)
            clearFolder(mapsDataFolder);
        for (Map map : maps) {
            try {
                File file = new File(mapsDataFolder + File.separator + map.uuid + ".json");
                file.createNewFile();
                FileUtils.write(file, gson.toJson(map));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!isBackup)
            clearFolder(boostersDataFolder);
        for (Booster.ActiveBooster booster : Booster.activeBoosters) {
            try {
                LocalDateTime start = convertToLocalDateTimeViaInstant(booster.momentOfStart);
                LocalDateTime now = LocalDateTime.now();
                long diff = ChronoUnit.SECONDS.between(start, now);
                Integer timeLeft = booster.timeInSeconds;
                booster.timeInSeconds = Math.round(timeLeft - diff);

                File file = new File(boostersDataFolder + File.separator + booster.uuid + ".json");
                file.createNewFile();
                FileUtils.write(file, gson.toJson(booster));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Booster.ActiveBooster booster : Booster.activePersonalBoosters.values()) {
            try {
                LocalDateTime start = convertToLocalDateTimeViaInstant(booster.momentOfStart);
                LocalDateTime now = LocalDateTime.now();
                long diff = ChronoUnit.SECONDS.between(start, now);
                Integer timeLeft = booster.timeInSeconds;
                booster.timeInSeconds = Math.round(timeLeft - diff);

                File file = new File(boostersDataFolder + File.separator + booster.uuid + ".json");
                file.createNewFile();
                FileUtils.write(file, gson.toJson(booster));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void clearFolder(File file) {
        for (File folderFile : file.listFiles()) {
            folderFile.delete();
        }
    }

    public static void reload() {
        save(false);
        load();
    }

    public static void backup() {
        Bukkit.getServer().getLogger().warning("[MiraclePvP] Generating backup...");
        save(true);
        load();
        Bukkit.getServer().getLogger().warning("[MiraclePvP] Backup successfully generated!");
    }

    private static File getDirectory(File mainFolder, String directoryName) {
        File file = new File(mainFolder + File.separator + directoryName);
        if (!file.exists()) {
            try {
                file.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new File(mainFolder + File.separator + directoryName);
    }

    public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
