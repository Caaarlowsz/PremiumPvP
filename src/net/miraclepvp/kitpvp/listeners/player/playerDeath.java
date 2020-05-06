package net.miraclepvp.kitpvp.listeners.player;

import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.bukkit.SkullBuilder;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.duel.Duel;
import net.miraclepvp.kitpvp.data.user.Abilities;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.listeners.custom.PlayerDeathEvent;
import net.miraclepvp.kitpvp.utils.CooldownUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.UUID;

import static net.miraclepvp.kitpvp.bukkit.Text.color;
import static org.bukkit.Bukkit.getServer;

public class playerDeath implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        //Get the player who dies
        Player player = event.getPlayer();
        User user = event.getUser();

        if(Duel.isIngame(player)) return;

        //Get the damage profile
        playerDamageRegister.DamageProfile profile = event.getProfile();

        //Get the killstreak
        Integer oldKillstreak = user.getKillstreak();

        //Manage the combattimer
        if (Main.getInstance().combatTimer.containsKey(player)) Main.getInstance().combatTimer.remove(player);

        //Add a death
        user.setDeaths(user.getDeaths() + 1);

        //Reset killstreak
        user.setKillstreak(0);

        //Removing the enderpearl delay
        if (CooldownUtil.getCooldown(player, "enderpearl") != null)
            CooldownUtil.getCooldown(player, "enderpearl").delete();

        //Getting last damager
        if (profile.getLastDamager() != null) {
            User damager = Data.getUser(Bukkit.getOfflinePlayer(profile.getLastDamager()));
            damager.setKills(damager.getKills() + 1);
            damager.setKillstreak(damager.getKillstreak() + 1);

            //If last damager is online
            if (Bukkit.getOfflinePlayer(profile.getLastDamager()).isOnline()) {
                Player killer = Bukkit.getPlayer(profile.getLastDamager());
                //If user had a bounty
                if (user.getBounty() > 0)
                    killer.getInventory().addItem(
                            new ItemstackFactory(SkullBuilder.getPlayerSkull(player.getName()))
                                    .setDisplayName("&5" + player.getName() + "'s Head")
                                    .addLoreLine("&7Reward: " + user.getBounty() + " Coins")
                                    .addLoreLine("&7Bring this head to the Bounty Boss to claim your reward.")
                    );

                Arrays.stream(player.getInventory().getContents()).forEach(item -> {
                    if (item != null && item.getType() != null && !item.getType().equals(Material.AIR) && item.getItemMeta() != null &&
                            item.getItemMeta().getDisplayName() != null) {

                        if (item.getType().equals(Material.SKULL_ITEM) && ChatColor.stripColor(item.getItemMeta().getDisplayName()).endsWith("'s Head")) {
                            killer.getInventory().addItem(item);
                        }

                        if (item.getType().equals(Material.PAPER) && ChatColor.stripColor(item.getItemMeta().getDisplayName()).endsWith(" Banknode")) {
                            killer.getInventory().addItem(item);
                        }

                    }
                });


            }
        }

        //Removing the bounty
        if (user.getBounty() > 0) {
            Bukkit.broadcastMessage(color("&a" + Bukkit.getOfflinePlayer(profile.getLastDamager()).getName() + " claimed " + player.getName() + "'s head with a value of " + user.getBounty() + " coins."));
            user.setBounty(0);
        }

        try {
            //Everyone who damaged the dead player
            for (UUID uuid : profile.getGiven().keySet()) {
                OfflinePlayer damager = Bukkit.getOfflinePlayer(uuid);
                User duser = Data.getUser(damager);

                double totalDamage = profile.getDamageTaken();
                double damageDealt = profile.getGiven().get(uuid);

                int percentage = (int) ((100 / totalDamage) * damageDealt);
                int coins = (int) (((double) (int) 10 / (double) 100) * percentage);
                if (getServer().getOnlinePlayers().stream().filter(pl -> !pl.hasMetadata("vanished")).filter(pl -> !pl.hasMetadata("build")).count() < 10)
                    coins =  (int) (((double) (int) 15 / (double) 100) * percentage);
                int experience = (int) (((double) (int) 10 / (double) 100) * percentage);

                duser.setCoins(duser.getCoins() + coins, true);
                duser.setExperience(duser.getExperience() + experience, true);

                //Als het een assist is
                if (profile.getLastDamager() == null || !uuid.equals(profile.getLastDamager())) {
                    if (coins > 0 && experience > 0) {
                        duser.setAssists(duser.getAssists() + 1);
                        if (damager.isOnline())
                            Bukkit.getPlayer(damager.getUniqueId()).sendMessage(color("&aYou have a assist on " + player.getName() + " and received " + coins + " coins and " + experience + " experience."));
                    }
                }

                //Als het een kill is
                if (uuid.equals(profile.getLastDamager())) {
                    if (duser.isKillBroadcast())
                        Bukkit.broadcastMessage(color("&a" + damager.getName() + " brutally murdered " + player.getName() + (oldKillstreak > 1 ? " and ruined his killstreak of " + oldKillstreak : "") + "!"));
                    if (damager.isOnline())
                        Bukkit.getPlayer(damager.getUniqueId()).sendMessage(color("&aYou killed " + player.getName() + " and received " + coins + " coins and " + experience + " experience."));

                    if (!damager.isOnline() || (damager.isOnline() && Bukkit.getPlayer(damager.getUniqueId()).hasMetadata("kit"))) {
                        HashMap<Abilities.AbilityType, Integer> abilities = duser.getAbilities();

                        if (abilities.get(Abilities.AbilityType.MORE_COINS) != null &&
                                Abilities.chance(Abilities.AbilityType.MORE_COINS.getLvl(abilities.get(Abilities.AbilityType.MORE_COINS))))
                            duser.setCoins(duser.getCoins() + 5, false);

                        if (abilities.get(Abilities.AbilityType.MORE_EXP) != null &&
                                Abilities.chance(Abilities.AbilityType.MORE_EXP.getLvl(abilities.get(Abilities.AbilityType.MORE_EXP))))
                            duser.setExperience(duser.getExperience() + 5, false);

                        if (abilities.get(Abilities.AbilityType.GOLDEN_APPLE) != null &&
                                Abilities.chance(Abilities.AbilityType.GOLDEN_APPLE.getLvl(abilities.get(Abilities.AbilityType.GOLDEN_APPLE))))
                            if (damager.isOnline())
                                Bukkit.getPlayer(damager.getUniqueId()).getInventory().addItem(new ItemstackFactory(Material.GOLDEN_APPLE));

                        if (abilities.get(Abilities.AbilityType.ENDERPEARL) != null &&
                                Abilities.chance(Abilities.AbilityType.ENDERPEARL.getLvl(abilities.get(Abilities.AbilityType.ENDERPEARL))))
                            if (damager.isOnline())
                                Bukkit.getPlayer(damager.getUniqueId()).getInventory().addItem(new ItemstackFactory(Material.ENDER_PEARL));

                        if(damager.getPlayer() == null) return;

                        if(damager.getPlayer().hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))
                            damager.getPlayer().getActivePotionEffects().remove(damager.getPlayer().getActivePotionEffects().stream().filter(potionEffect -> potionEffect.getType().equals(PotionEffectType.INCREASE_DAMAGE)));
                        if (abilities.get(Abilities.AbilityType.STRENGTH) != null)
                            if (damager.isOnline())
                                Bukkit.getPlayer(damager.getUniqueId()).addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Abilities.AbilityType.STRENGTH.getLvl(abilities.get(Abilities.AbilityType.STRENGTH)) * 20, 0));

                        if(damager.getPlayer().hasPotionEffect(PotionEffectType.REGENERATION))
                            damager.getPlayer().getActivePotionEffects().remove(damager.getPlayer().getActivePotionEffects().stream().filter(potionEffect -> potionEffect.getType().equals(PotionEffectType.REGENERATION)));
                        if (abilities.get(Abilities.AbilityType.REGENERATION) != null)
                            if (damager.isOnline())
                                Bukkit.getPlayer(damager.getUniqueId()).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Abilities.AbilityType.REGENERATION.getLvl(abilities.get(Abilities.AbilityType.REGENERATION)) * 20, 0));
                    }
                }
            }
        } catch (ConcurrentModificationException ex) {
            ex.printStackTrace();
        }
        profile.getGiven().clear();
    }
}
