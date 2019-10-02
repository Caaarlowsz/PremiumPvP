package me.sahustei.miraclepvp;

import me.sahustei.miraclepvp.commands.*;
import me.sahustei.miraclepvp.data.Config;
import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.Top;
import me.sahustei.miraclepvp.data.user.StatType;
import me.sahustei.miraclepvp.data.user.User;
import me.sahustei.miraclepvp.inventories.listeners.CosmeticsListener;
import me.sahustei.miraclepvp.inventories.listeners.KitListener;
import me.sahustei.miraclepvp.listeners.SignListener;
import me.sahustei.miraclepvp.listeners.player.*;
import me.sahustei.miraclepvp.objects.CosmeticType;
import me.sahustei.miraclepvp.objects.Item;
import me.sahustei.miraclepvp.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static me.sahustei.miraclepvp.bukkit.Text.color;

public final class Main extends JavaPlugin {

    public String version = "0.2.1-SNAPSHOT";

    public ArrayList<Player> buildMode = new ArrayList<>();
    public HashMap<Player, Integer> combatTimer = new HashMap<>();
    public HashMap<UUID, Trails> playerTrails = new HashMap<>();

    private Integer cmdSize = 0;
    private Integer eventSize = 0;

    private Integer messageCount = 0;

    @Override
    public void onEnable() {
        Config.load();
        getLogger().info("Config has been loaded");

        StatType.load();
        getLogger().info("Stats Types have been loaded");

        Data.load();
        getLogger().info("Data has been loaded");

        registerListeners(
                new playerDamage(),
                new playerLeave(),
                new playerDeath(),
                new playerJoin(),
                new playerShoot(),
                new CosmeticsListener(),
                new playerChat(),
                new playerDrop(),
                new playerHunger(),
                new playerStatusChange(),
                new SignListener(),
                new playerBlockChange(),
                new KitListener(),
                new playerInventory()
        );
        getLogger().info("There are " + eventSize + " events loaded!");

        registerCommands(
                new String[]{"unbreakable", "rename", "cosmotokens", "trail", "suffix", "chatcolor", "namecolor", "stats", "kitpvp", "prefix", "kit"},
                new Unbreakable(),
                new Rename(),
                new CosmoTokens(),
                new TrailCMD(),
                new SuffixCMD(),
                new ChatColorCMD(),
                new NameColorCMD(),
                new StatsCMD(),
                new KitpvpCMD(),
                new PrefixCMD(),
                new KitCMD()
        );
        getLogger().info("There are " + cmdSize + " commands loaded!");

        CosmeticType.load();
        getLogger().info("Cosmetic Types have been loaded");

        Item.load();
        getLogger().info("Items have been loaded");

        Bukkit.getOnlinePlayers().stream().forEach(player -> {
            if (!Data.users.stream().anyMatch(i -> i.getUuid().equals(player.getUniqueId()))) {
                User user = new User(player.getUniqueId());
                Data.users.add(user);
            }
            //playerJoin.handleJoin(player);
        });

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.getOnlinePlayers().stream().forEach(player -> {
            User user = Data.getUser(player);
            user.setOnlineTime(user.getOnlineTime() + 1);

            if (combatTimer.get(player) == null) return;
            if (combatTimer.get(player) < -1) return;
            combatTimer.put(player, combatTimer.get(player) - 1);
            Integer timeLeft = combatTimer.get(player);
            Integer timeRan = 10 - timeLeft;
            String timer = "&cCombat Timer &8[";
            for (int i = 0; i < timeLeft; i++) {
                timer = timer + "&a|";
            }
            for (int i = 0; i < timeRan; i++) {
                timer = timer + "&7|";
            }
            timer = timer + "&8]";
            if (timeLeft == -1)
                timer = "&aYou are no longer in combat!";
            if (player.hasMetadata("vanished")) return;
            ActionbarUtil actionbarUtil = new ActionbarUtil(timer);
            actionbarUtil.sendToPlayer(player);
        }), 20L, 20L);
        getLogger().info("Combat Timer has been loaded");

        new BukkitRunnable() {
            @Override
            public void run() {
                if (messageCount >= Config.getMessages().size())
                    messageCount = 0;
                if (Config.getMessages().get(messageCount).contains("<br>")) {
                    String start = Config.getMessages().get(messageCount);
                    String[] split = start.split("<br>");
                    for (String s : split) {
                        ChatCenterUtil.sendCenteredBroadcast(color(s.replaceAll("<br>", "").replaceAll("%server_name%", Config.getServerName())));
                    }
                    messageCount++;
                    return;
                }
                ChatCenterUtil.sendCenteredBroadcast(color(Config.getMessages().get(messageCount)));
                messageCount++;
            }
        }.runTaskTimer(this, 0, Config.getBroadcastDelay() * 20);
        getLogger().info("Broadcast messages are loaded");

        new BukkitRunnable() {
            @Override
            public void run() {
                Top.reload();
                Data.signs.forEach(sign -> sign.update());
            }
        }.runTaskTimer(this, 0, Config.getSignReloadDelay() * 20);
        getLogger().info("Sign updater is loaded");

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Trails t : playerTrails.values())
                    t.tick();
            }
        }.runTaskTimer(this, 0, 1);

        new BukkitRunnable() {
            public void run() {
                Data.reload();
            }
        }.runTaskTimer(this, 0, 30 * 60 * 20);
        getLogger().info("Backup system is loaded");
    }

    private void registerListeners(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> {
            Bukkit.getPluginManager().registerEvents(listener, this);
            eventSize++;
        });
    }

    private void registerCommands(String[] commands, CommandExecutor... commandExecutors) {
        Arrays.stream(commandExecutors).forEach(command -> {
            getCommand(commands[cmdSize]).setExecutor(command);
            cmdSize++;
        });
    }

    @Override
    public void onDisable() {
        Data.save();
        Config.save();
    }

    public static Main getInstance() {
        return Main.getPlugin(Main.class);
    }
}
