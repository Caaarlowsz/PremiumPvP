package net.miraclepvp.kitpvp.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class playerDamageRegister implements Listener {

    private HashMap<UUID, DamageProfile> profiles = new HashMap<>();

    @EventHandler
    public void on(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.setDeathMessage(null);
        event.getDrops().clear();
        event.setDroppedExp(0);
        if (!profiles.containsKey(player.getUniqueId())) profiles.put(player.getUniqueId(), new DamageProfile());
        DamageProfile profile = profiles.get(player.getUniqueId());
        profiles.remove(player.getUniqueId());
        Bukkit.getPluginManager().callEvent(new net.miraclepvp.kitpvp.listeners.custom.PlayerDeathEvent(player, profile));
        player.setHealth(player.getMaxHealth());
        playerJoin.handleSpawn(player);
    }

    @EventHandler
    public void on(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (!profiles.containsKey(p.getUniqueId())) profiles.put(p.getUniqueId(), new DamageProfile());
        profiles.remove(p.getUniqueId());
    }

    @EventHandler
    public void on(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();

        if (!profiles.containsKey(p.getUniqueId())) profiles.put(p.getUniqueId(), new DamageProfile());
        DamageProfile profile = profiles.get(p.getUniqueId());

        if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            e.setDamage(0);
            e.setCancelled(true);
            return;
        }

        if (e.getCause() == EntityDamageEvent.DamageCause.LAVA) {
            e.setDamage(0);
            e.setCancelled(true);
            return;
        }

        if (e.getCause().equals(EntityDamageEvent.DamageCause.FIRE) || e.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)) {
            if (p.getLocation().getBlock().getType().equals(Material.LAVA) || p.getLocation().getBlock().getType().equals(Material.STATIONARY_LAVA)) {
                e.setDamage(0);
                e.setCancelled(true);
            }
        }

        if (e.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
            e.setDamage(0);
            e.setCancelled(true);
            return;
        }

        if (e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
            e.setDamage(0);
            e.setCancelled(true);
            return;
        }

        profile.setDamageTaken(profile.getDamageTaken() + e.getDamage());
    }

    @EventHandler
    public void on(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        Player damager = null;

        if (!profiles.containsKey(p.getUniqueId())) profiles.put(p.getUniqueId(), new DamageProfile());
        DamageProfile profile = profiles.get(p.getUniqueId());

        if (!(e.getDamager() instanceof Player) && !(e.getDamager() instanceof Arrow) && !(e.getDamager() instanceof Snowball) && !(e.getDamager() instanceof Egg) && !(e.getDamager() instanceof FishHook)) {
            e.setDamage(0);
            e.setCancelled(true);
            return;
        }

        if (e.getDamager() instanceof Player) {
            damager = (Player) e.getDamager();
        }

        if (e.getDamager() instanceof Arrow) {
            Arrow da = (Arrow) e.getDamager();
            if (da.getShooter() instanceof Player) damager = (Player) da.getShooter();
        }

        if (e.getDamager() instanceof FishHook) {
            FishHook da = (FishHook) e.getDamager();
            if (da.getShooter() instanceof Player) damager = (Player) da.getShooter();
        }

        if (e.getDamager() instanceof Snowball) {
            Snowball da = (Snowball) e.getDamager();
            if (da.getShooter() instanceof Player) damager = (Player) da.getShooter();
        }

        if (e.getDamager() instanceof Egg) {
            Egg da = (Egg) e.getDamager();
            if (da.getShooter() instanceof Player) damager = (Player) da.getShooter();
        }


        if (damager != null) {
            if (damager.getUniqueId().equals(p.getUniqueId())) return;
            profile.setLastDamager(damager.getUniqueId());

            final double g = profile.given.getOrDefault(damager.getUniqueId(), 0.0);
            profile.given.remove(damager.getUniqueId());
            profile.given.put(damager.getUniqueId(), g + e.getDamage());
        }
    }

    public class DamageProfile {

        private double damageTaken;
        private UUID lastDamager;
        private HashMap<UUID, Double> given;

        public DamageProfile() {
            this.damageTaken = 0;
            this.lastDamager = null;
            this.given = new HashMap<>();
        }

        public double getDamageTaken() {
            return damageTaken;
        }

        public void setDamageTaken(double damageTaken) {
            this.damageTaken = damageTaken;
        }

        public UUID getLastDamager() {
            return lastDamager;
        }

        public void setLastDamager(UUID lastDamager) {
            this.lastDamager = lastDamager;
        }

        public HashMap<UUID, Double> getGiven() {
            return given;
        }
    }
}
