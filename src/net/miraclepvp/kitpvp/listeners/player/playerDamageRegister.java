package net.miraclepvp.kitpvp.listeners.player;

import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.duel.Duel;
import net.miraclepvp.kitpvp.data.user.Abilities;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.objects.ServerEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class playerDamageRegister implements Listener {

    private HashMap<UUID, DamageProfile> profiles = new HashMap<>();

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        event.setDeathMessage(null);

        if(event.getEntity().hasMetadata("event")){
            event.getEntity().spigot().respawn();
            ServerEvent.leave(event.getEntity(), true);
            return;
        }

        if(Duel.isIngame(event.getEntity())){
            Duel duel = Duel.getDuel(event.getEntity());
            if(duel.host.equals(event.getEntity().getUniqueId()))
                duel.end(Bukkit.getPlayer(duel.joined));
            else
                duel.end(Bukkit.getPlayer(duel.host));

            event.getEntity().spigot().respawn();
            playerJoin.handleSpawn(event.getEntity());
        }
    }

    @EventHandler
    public void on(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if(player.hasMetadata("event")) return;
        event.getDrops().clear();
        event.setDroppedExp(0);
        if(Duel.isIngame(player) || player.hasMetadata("event")) return;
        if (!profiles.containsKey(player.getUniqueId())) profiles.put(player.getUniqueId(), new DamageProfile());
        DamageProfile profile = profiles.get(player.getUniqueId());
        profiles.remove(player.getUniqueId());
        try {
            Bukkit.getPluginManager().callEvent(new net.miraclepvp.kitpvp.listeners.custom.PlayerDeathEvent(player, profile));
        }catch(Exception ex){
            Bukkit.getServer().getLogger().warning("WARNING! Something went wrong booting the deathEvent at MiraclePvP:listeners.player.playerDamageRegister:44");
        }
        player.setHealth(player.getMaxHealth());
        playerJoin.handleSpawn(player);
    }

    @EventHandler
    public void on(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (profiles.containsKey(p.getUniqueId())) profiles.remove(p.getUniqueId());
    }

    @EventHandler
    public void on(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();

        if(Duel.isIngame(p)) {
            if(Duel.getDuel(p).status.equals(Duel.Status.PREPARING))
                e.setCancelled(true);
            return;
        }

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

        if (e.getCause() == EntityDamageEvent.DamageCause.VOID) return;

        if(p.hasMetadata("event")) return;

        profile.setDamageTaken(profile.getDamageTaken() + e.getDamage());
    }

    @EventHandler
    public void on(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        Player damager = null;

        if(p.hasMetadata("event")) return;

        if(Duel.isIngame(p) || Duel.isIngame(damager)) return;

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
            new BukkitRunnable(){
                @Override
                public void run() {
                    ((Player)da.getShooter()).sendMessage(color("&7" + p.getName() + " has &c" + Math.round(p.getHealth() * 100.00) / 100.00 + "hp&7."));
                }
            }.runTaskLater(Main.getInstance(), 5L);
            User duser = Data.getUser((Player)da.getShooter());
            HashMap<Abilities.AbilityType, Integer> abilities = duser.getAbilities();
            if(abilities.get(Abilities.AbilityType.ARROW_BACK) != null &&
                    Abilities.chance(Abilities.AbilityType.ARROW_BACK.getLvl(abilities.get(Abilities.AbilityType.ARROW_BACK))))
                ((Player)da.getShooter()).getInventory().addItem(new ItemstackFactory(Material.ARROW));
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

        if (damager != null && !e.isCancelled()) {
            if (damager.getUniqueId().equals(p.getUniqueId())) return;
            profile.setLastDamager(damager.getUniqueId());

            final double g = profile.given.getOrDefault(damager.getUniqueId(), 0.0);
            profile.given.remove(damager.getUniqueId());
            profile.given.put(damager.getUniqueId(), g + e.getDamage());

            if(!Duel.isIngame(p))
                Main.getInstance().combatTimer.put(p, 10);
            if(!Duel.isIngame(damager))
                Main.getInstance().combatTimer.put(damager, 10);
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
