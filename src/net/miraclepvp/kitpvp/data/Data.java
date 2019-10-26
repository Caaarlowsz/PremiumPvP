package net.miraclepvp.kitpvp.data;

import com.google.gson.Gson;
import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.data.chatcolor.Chatcolor;
import net.miraclepvp.kitpvp.data.kit.Kit;
import net.miraclepvp.kitpvp.data.namecolor.Namecolor;
import net.miraclepvp.kitpvp.data.prefix.Prefix;
import net.miraclepvp.kitpvp.data.sign.Sign;
import net.miraclepvp.kitpvp.data.suffix.Suffix;
import net.miraclepvp.kitpvp.data.trail.Trail;
import net.miraclepvp.kitpvp.data.user.User;
import org.apache.commons.io.FileUtils;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Data {

    public static List<User> users = new ArrayList<>();
    public static List<Trail> trails = new ArrayList<>();
    public static List<Suffix> suffixes = new ArrayList<>();
    public static List<Prefix> prefixes = new ArrayList<>();
    public static List<Chatcolor> chatcolors = new ArrayList<>();
    public static List<Namecolor> namecolors = new ArrayList<>();
    public static List<Kit> kits = new ArrayList<>();
    public static List<Sign> signs = new ArrayList<>();

    public static User getUser(OfflinePlayer player){
        return users.stream().filter(u -> u.getUuid().equals(player.getUniqueId())).findFirst().get();
    }

    public static Trail getTrail(String name){
        return trails.stream().filter(t -> t.getName().equalsIgnoreCase(name)).findFirst().get();
    }

    public static Trail getTrail(UUID uuid){
        return trails.stream().filter(t -> t.getUuid().equals(uuid)).findFirst().get();
    }

    public static Suffix getSuffix(String name){
        return suffixes.stream().filter(s -> s.getName().equalsIgnoreCase(name)).findFirst().get();
    }

    public static Suffix getSuffix(UUID uuid){
        return suffixes.stream().filter(s -> s.getUuid().equals(uuid)).findFirst().get();
    }

    public static Prefix getPrefix(String name){
        return prefixes.stream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst().get();
    }

    public static Prefix getPrefix(UUID uuid){
        return prefixes.stream().filter(p -> p.getUuid().equals(uuid)).findFirst().get();
    }

    public static Chatcolor getChatcolor(String name){
        return chatcolors.stream().filter(c -> c.getName().equalsIgnoreCase(name)).findFirst().get();
    }

    public static Chatcolor getChatcolor(UUID uuid){
        return chatcolors.stream().filter(c -> c.getUuid().equals(uuid)).findFirst().get();
    }

    public static Namecolor getNamecolor(String name){
        return namecolors.stream().filter(n -> n.getName().equalsIgnoreCase(name)).findFirst().get();
    }

    public static Namecolor getNamecolor(UUID uuid){
        return namecolors.stream().filter(n -> n.getUuid().equals(uuid)).findFirst().get();
    }

    public static Sign getSign(UUID uuid){
        return signs.stream().filter(si -> si.getUuid().equals(uuid)).findFirst().get();
    }

    public static Sign getSign(Location loc){
        return signs.stream().filter(si -> si.getLocation().equals(loc)).findFirst().get();
    }

    public static Kit getKit(String name){
        return kits.stream().filter(k -> k.getName().equalsIgnoreCase(name)).findFirst().get();
    }

    public static Kit getKit(UUID uuid){
        return kits.stream().filter(k -> k.getUuid().equals(uuid)).findFirst().get();
    }



    public static void load(){
        Gson gson = new Gson();
        File dataFolder = new File(Main.getInstance().getDataFolder() + File.separator + "data");
        if(!dataFolder.exists()){
            try{
                dataFolder.mkdir();
            }catch (Exception ex){
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
        users.clear();
        if(playerDataFolder.listFiles().length > 0){
            Arrays.stream(playerDataFolder.listFiles()).filter(file -> !file.isDirectory()).filter(file -> file.getName().contains(".json")).forEach(file -> {
                try {
                    users.add(gson.fromJson(FileUtils.readFileToString(file), User.class));
                } catch (IOException e){
                    e.printStackTrace();
                }
            });
        }
        trails.clear();
        if(trailDataFolder.listFiles().length > 0){
            Arrays.stream(trailDataFolder.listFiles()).filter(file -> !file.isDirectory()).filter(file -> file.getName().contains(".json")).forEach(file -> {
                try {
                    trails.add(gson.fromJson(FileUtils.readFileToString(file), Trail.class));
                } catch (IOException e){
                    e.printStackTrace();
                }
            });
        }
        suffixes.clear();
        if(suffixDataFolder.listFiles().length > 0){
            Arrays.stream(suffixDataFolder.listFiles()).filter(file -> !file.isDirectory()).filter(file -> file.getName().contains(".json")).forEach(file -> {
                try {
                    suffixes.add(gson.fromJson(FileUtils.readFileToString(file), Suffix.class));
                } catch (IOException e){
                    e.printStackTrace();
                }
            });
        }
        prefixes.clear();
        if(prefixDataFolder.listFiles().length > 0){
            Arrays.stream(prefixDataFolder.listFiles()).filter(file -> !file.isDirectory()).filter(file -> file.getName().contains(".json")).forEach(file -> {
                try {
                    prefixes.add(gson.fromJson(FileUtils.readFileToString(file), Prefix.class));
                } catch (IOException e){
                    e.printStackTrace();
                }
            });
        }
        chatcolors.clear();
        if(chatcolorsDataFolder.listFiles().length > 0){
            Arrays.stream(chatcolorsDataFolder.listFiles()).filter(file -> !file.isDirectory()).filter(file -> file.getName().contains(".json")).forEach(file -> {
                try {
                    chatcolors.add(gson.fromJson(FileUtils.readFileToString(file), Chatcolor.class));
                } catch (IOException e){
                    e.printStackTrace();
                }
            });
        }
        namecolors.clear();
        if(namecolorsDataFolder.listFiles().length > 0){
            Arrays.stream(namecolorsDataFolder.listFiles()).filter(file -> !file.isDirectory()).filter(file -> file.getName().contains(".json")).forEach(file -> {
                try {
                    namecolors.add(gson.fromJson(FileUtils.readFileToString(file), Namecolor.class));
                } catch (IOException e){
                    e.printStackTrace();
                }
            });
        }
        signs.clear();
        if(signsDataFolder.listFiles().length > 0){
            Arrays.stream(signsDataFolder.listFiles()).filter(file -> !file.isDirectory()).filter(file -> file.getName().contains(".json")).forEach(file -> {
                try {
                    signs.add(gson.fromJson(FileUtils.readFileToString(file), Sign.class));
                } catch (IOException e){
                    e.printStackTrace();
                }
            });
        }
        kits.clear();
        if(kitsDataFolder.listFiles().length > 0){
            Arrays.stream(kitsDataFolder.listFiles()).filter(file -> !file.isDirectory()).filter(file -> file.getName().contains(".json")).forEach(file -> {
                try {
                    kits.add(gson.fromJson(FileUtils.readFileToString(file), Kit.class));
                } catch (IOException e){
                    e.printStackTrace();
                }
            });
        }
    }

    public static void save(){
        Gson gson = new Gson();
        File dataFolder = new File(Main.getInstance().getDataFolder() + File.separator + "data");
        File playerDataFolder = getDirectory(dataFolder, "players");
        File trailDataFolder = getDirectory(dataFolder, "trails");
        File suffixDataFolder = getDirectory(dataFolder, "suffixes");
        File prefixDataFolder = getDirectory(dataFolder, "prefixes");
        File chatcolorsDataFolder = getDirectory(dataFolder, "chatcolors");
        File namecolorsDataFolder = getDirectory(dataFolder, "namecolors");
        File signsDataFolder = getDirectory(dataFolder, "signs");
        File kitsDataFolder = getDirectory(dataFolder, "kits");
        clearFolder(playerDataFolder);
        users.stream().forEach(user -> {
            try {
                File file = new File(playerDataFolder + File.separator + user.getUuid() + ".json");
                file.createNewFile();
                FileUtils.write(file, gson.toJson(user));
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        clearFolder(trailDataFolder);
        trails.stream().forEach(trail -> {
            try {
                File file = new File(trailDataFolder + File.separator + trail.getUuid() + ".json");
                file.createNewFile();
                FileUtils.write(file, gson.toJson(trail));
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        clearFolder(suffixDataFolder);
        suffixes.stream().forEach(suffix -> {
            try {
                File file = new File(suffixDataFolder + File.separator + suffix.getUuid() + ".json");
                file.createNewFile();
                FileUtils.write(file, gson.toJson(suffix));
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        clearFolder(prefixDataFolder);
        prefixes.stream().forEach(prefix -> {
            try {
                File file = new File(prefixDataFolder + File.separator + prefix.getUuid() + ".json");
                file.createNewFile();
                FileUtils.write(file, gson.toJson(prefix));
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        clearFolder(chatcolorsDataFolder);
        chatcolors.stream().forEach(color -> {
            try {
                File file = new File(chatcolorsDataFolder + File.separator + color.getUuid() + ".json");
                file.createNewFile();
                FileUtils.write(file, gson.toJson(color));
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        clearFolder(namecolorsDataFolder);
        namecolors.stream().forEach(color -> {
            try {
                File file = new File(namecolorsDataFolder + File.separator + color.getUuid() + ".json");
                file.createNewFile();
                FileUtils.write(file, gson.toJson(color));
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        clearFolder(signsDataFolder);
        signs.stream().forEach(sign -> {
            try {
                File file = new File(signsDataFolder + File.separator + sign.getUuid() + ".json");
                file.createNewFile();
                FileUtils.write(file, gson.toJson(sign));
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        clearFolder(kitsDataFolder);
        kits.stream().forEach(kit -> {
            try {
                File file = new File(kitsDataFolder + File.separator + kit.getUuid() + ".json");
                file.createNewFile();
                FileUtils.write(file, gson.toJson(kit));
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    private static void clearFolder(File file){
        for (File folderFile : file.listFiles()){
            folderFile.delete();
        }
    }

    public static void reload(){
        save();
        load();
    }

    private static File getDirectory(File mainFolder, String directoryName){
        File file = new File(mainFolder + File.separator + directoryName);
        if (!file.exists()){
            try {
                file.mkdir();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return new File(mainFolder + File.separator + directoryName);
    }
}
