package net.miraclepvp.kitpvp.listeners.player;

import net.minecraft.server.v1_8_R3.*;
import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.data.Config;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.duel.Duel;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.objects.Board;
import net.miraclepvp.kitpvp.objects.Item;
import net.miraclepvp.kitpvp.objects.Tablist;
import net.miraclepvp.kitpvp.objects.npc.NPCManager;
import net.miraclepvp.kitpvp.utils.ChatCenterUtil;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.*;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class playerJoin implements Listener {

    @EventHandler
    public void onPreJoin(PlayerPreLoginEvent event) {
        if (!Data.users.stream().anyMatch(u -> u.getUuid().equals(event.getUniqueId()))) {
            User user = new User(event.getUniqueId());
            Data.users.add(user);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        handleJoin(event.getPlayer());
    }

    public static void handleJoin(Player player) {
        User user = Data.getUser(player);

        player.spigot().respawn();

        ChatCenterUtil.sendCenteredMessage(player, "&d&m-----------------------------------------------------");
        ChatCenterUtil.sendCenteredMessage(player, "&7Welcome to MiraclePvP");
        ChatCenterUtil.sendCenteredMessage(player, "&7");
        ChatCenterUtil.sendCenteredMessage(player, "&7Website: www.miraclepvp.net");
        ChatCenterUtil.sendCenteredMessage(player, "&7Store: miraclepvp.tebex.io");
        ChatCenterUtil.sendCenteredMessage(player, "&7Discord: discord.gg/TPXdxJK");
        ChatCenterUtil.sendCenteredMessage(player, "&7");
        ChatCenterUtil.sendCenteredMessage(player, "&7If you have any questions, visit the information page:");
        ChatCenterUtil.sendCenteredMessage(player, "&7www.miraclepvp.net/information.php");
        ChatCenterUtil.sendCenteredMessage(player, "&d&m-----------------------------------------------------");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        if (!user.getLastVersion().equalsIgnoreCase(Main.getInstance().version)) {
            player.sendMessage(color("&a&lUPDATE! &fOur plugin has been updated since your latest join, make sure to read our changelog in the discord! https://discord.gg/TPXdxJK"));
            player.sendMessage(color("&7Your last played version: " + user.getLastVersion() + ". The newest version (current) is: " + Main.getInstance().version));
            user.setLastVersion(Main.getInstance().version);
        }
        user.setLastJoin(formatter.format(date));
        Tablist.sendTab(player);
        for (EntityPlayer npc : NPCManager.npcs)
            NPCManager.showNPC(player, npc);

        handleSpawn(player);

//        ArrayList<String> holo = new ArrayList<>();
//        holo.add("&5&lWelcome to MiraclePvP");
//        holo.add("");
//        holo.add("&fHow to get started?");
//        holo.add("&7Click on a compass and select the default kit.");
//        holo.add("&7Now, go talk to the \"Play\" npc. He will teleport you to the map.");
//        holo.add("");
//        holo.add("&fHow to get Cosmetics?");
//        holo.add("&7Since we are focussed on avoiding Pay2Win at all costs,");
//        holo.add("&7we made a custom cosmetic system. You can buy cosmetics with &5Cosmotokens&7.");
//        holo.add("&7we made a custom cosmetic system.");
//        Location loc = new Location(Config.getLobbyLoc().getWorld(), 998.5, 105, 38.5);
//        HologramUtil playholo = new HologramUtil(loc, holo.toArray(new String[0]));
//        playholo.displayTo(player);

        new BukkitRunnable() {
            @Override
            public void run() {
                Board.showEveryoneInTab(player);
                Board.addPlayerInTab(player);
            }
        }.runTaskLater(Main.getInstance(), 5L);
    }

    public static void handleSpawn(Player player) {
        User user = Data.getUser(player);
        Board.showScoreboard(player);

        if (player.hasMetadata("vanished")) return;
        if (player.hasMetadata("build"))
            player.removeMetadata("build", Main.getInstance());
        if(Duel.isIngame(player))
            Duel.getDuel(player).leave(player);

        if(Duel.isSpectator(player))
            Duel.stopSpectating(player);

        if (playerMove.inZone.containsKey(player.getUniqueId()))
            playerMove.inZone.remove(player.getUniqueId());

        player.setGameMode(GameMode.ADVENTURE);

        if (player.hasMetadata("kit")) {
            Integer value = 0;
            for (int slot = 0; slot < player.getInventory().getSize(); slot++) {
                if (player.getInventory().getItem(slot) != null) {
                    ItemStack item = player.getInventory().getItem(slot);
                    if (item.getType().equals(Material.PAPER) &&
                            item.getItemMeta() != null &&
                            item.getItemMeta().getDisplayName() != null &&
                            item.getItemMeta().getLore() != null &&
                            item.getItemMeta().getDisplayName().startsWith(color("&5$")) &&
                            item.getItemMeta().getDisplayName().endsWith(" Banknote")) {
                        Integer worth = Integer.parseInt(ChatColor.stripColor(item.getItemMeta().getDisplayName()).substring(1, ChatColor.stripColor(item.getItemMeta().getDisplayName()).length()).replaceAll(" Banknote", ""));
                        for (int i = 0; i < item.getAmount(); i++)
                            value += worth;
                    }
                }
            }
            user.setBanknoteValue(value);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (Config.getLobbyLoc().getY() > 0) {
                    player.teleport(new Location(
                            Config.getLobbyLoc().getWorld(),
                            Config.getLobbyLoc().getX(),
                            Config.getLobbyLoc().getY() + 0.5,
                            Config.getLobbyLoc().getZ(),
                            Config.getLobbyLoc().getYaw(),
                            Config.getLobbyLoc().getPitch()
                    ), PlayerTeleportEvent.TeleportCause.PLUGIN);
                }
                player.setFireTicks(-100);
                player.setHealth(player.getMaxHealth());

                player.removeMetadata("kit", Main.getInstance());
                player.getInventory().setArmorContents(null);
                player.getInventory().clear();
                player.setFoodLevel(20);
                player.setRemainingAir(player.getMaximumAir());
                player.setHealth(player.getMaxHealth());
                player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10000000, 3, false, false));

                Item.types.forEach(item -> player.getInventory().setItem(item.getPosition(), item.getItem()));

                //Disabling rank features when no rank
                if (!player.hasPermission("miraclepvp.donator.fly")) user.setFlyEnabled(false);
                if (!player.hasPermission("miraclepvp.donator.quickselect")) user.setQuickSelect(false);
                if (!player.hasPermission("miraclepvp.donator.autodeploy")) user.setAutoDeploy(false);
                if (!player.hasPermission("miraclepvp.donator.killbroadcast")) user.setKillBroadcast(false);
                if (!player.hasPermission("miraclepvp.donator.streakbroadcast")) user.setStreakBroadcast(false);

                if (user.isFlyEnabled())
                    player.setAllowFlight(true);
            }
        }.runTaskLater(Main.getInstance(), 1);

    }
}
