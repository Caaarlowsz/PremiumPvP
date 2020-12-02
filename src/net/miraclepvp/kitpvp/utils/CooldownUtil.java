package net.miraclepvp.kitpvp.utils;

import java.util.*;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.miraclepvp.kitpvp.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CooldownUtil {

    private static final JavaPlugin PLUGIN = JavaPlugin.getProvidingPlugin(Main.class);
    private static final Map<UUID, Map<String, Cooldown>> cooldown_holder = Maps.newHashMap();

    public static Cooldown prepare(Player player, String cooldownName, int seconds) {
        Map<String, Cooldown> player_cooldowns = (cooldown_holder.get(player.getUniqueId()) == null ? Maps.<String, Cooldown>newHashMap() : cooldown_holder.get(player.getUniqueId()));

        Cooldown cooldown = new Cooldown();
        cooldown.secondsLeft = seconds;
        cooldown.uuid = player.getUniqueId();
        cooldown.name = cooldownName;
        cooldown.started = false;
        cooldown.onlyOnline = false;

        player_cooldowns.put(cooldownName, cooldown);
        cooldown_holder.put(player.getUniqueId(), player_cooldowns);

        return cooldown;
    }

    public static void removeCooldown(Player player, String cooldownName) {
        Map<String, Cooldown> player_cooldowns = (cooldown_holder.get(player.getUniqueId()) == null ? Maps.<String, Cooldown>newHashMap() : cooldown_holder.get(player.getUniqueId()));

        player_cooldowns.remove(cooldownName);
        cooldown_holder.put(player.getUniqueId(), player_cooldowns);
    }

    public static Cooldown getCooldown(Player player, String cooldownName) {
        Map<String, Cooldown> player_cooldowns = (cooldown_holder.get(player.getUniqueId()) == null ? Maps.<String, Cooldown>newHashMap() : cooldown_holder.get(player.getUniqueId()));

        return player_cooldowns.get(cooldownName);
    }

    public static List<Cooldown> getCooldowns(Player player) {
        List<Cooldown> list = Lists.newArrayList();
        Map<String, Cooldown> player_cooldowns = (cooldown_holder.get(player.getUniqueId()) == null ? Maps.<String, Cooldown>newHashMap() : cooldown_holder.get(player.getUniqueId()));
        list.addAll(player_cooldowns.values());
        return Collections.unmodifiableList(list);
    }

    public static List<Cooldown> getCooldownsWithSecondsLeft(Player player) {
        List<Cooldown> list = Lists.newArrayList();
        for (Cooldown cd : getCooldowns(player)) {
            if (cd.hasTimeLeft()) {
                list.add(cd);
            }
        }

        return Collections.unmodifiableList(list);
    }

    public static class Cooldown {

        private int secondsLeft;
        private UUID uuid;
        private boolean started;
        private String name;
        private CooldownRunner runner;
        private boolean onlyOnline;

        private Cooldown() {
        }

        public String getName() {
            return this.name;
        }

        public Player getPlayer() {
            return Bukkit.getPlayer(uuid);
        }

        public int getSecondsLeft() {
            return this.secondsLeft;
        }

        public boolean hasTimeLeft() {
            return getSecondsLeft() != 0;
        }

        public boolean hasStarted() {
            return this.started;
        }

        public boolean onlyCountdownIfOnline() {
            return this.onlyOnline;
        }

        public void setSecondsLeft(int secondsLeft) {
            this.secondsLeft = secondsLeft;
        }

        public void onlyCountdownWhenOnline(boolean b) {
            this.onlyOnline = b;
        }

        public void delete() {
            CooldownUtil.removeCooldown(getPlayer(), getName());
        }

        public Cooldown start() {
            if (!hasStarted() && this.runner == null) {
                CooldownStartEvent event = new CooldownStartEvent(this);
                Bukkit.getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    this.runner = new CooldownRunner(this);
                    this.runner.runTaskTimerAsynchronously(PLUGIN, -1, 20);
                }
            }

            return this;
        }

        public void forceStop() {
            if (hasStarted() && this.runner != null) {
                this.runner.cancel();
                this.runner = null;
            }
        }
    }

    private static class CooldownRunner extends BukkitRunnable {

        private final Cooldown cooldown;

        CooldownRunner(Cooldown cooldown) {
            this.cooldown = cooldown;
        }

        public void run() {
            if (cooldown.secondsLeft == 0) {
                Bukkit.getPluginManager().callEvent(new CooldownFinishEvent(cooldown));
                cancel();
                return;
            }

            if (cooldown.onlyOnline) {
                if (cooldown.getPlayer() == null) {
                    return;
                }
            }

            cooldown.secondsLeft--;
        }
    }

    public static class CooldownStartEvent extends Event implements Cancellable {

        private static final HandlerList handlers = new HandlerList();
        private final Cooldown cooldown;
        private boolean cancelled = false;

        CooldownStartEvent(Cooldown cooldown) {
            this.cooldown = cooldown;
        }

        public Cooldown getCooldown() {
            return this.cooldown;
        }

        @Override
        public HandlerList getHandlers() {
            return handlers;
        }

        public static HandlerList getHandlerList() {
            return handlers;
        }

        public boolean isCancelled() {
            return this.cancelled;
        }

        public void setCancelled(boolean cancel) {
            this.cancelled = cancel;
        }
    }

    public static class CooldownFinishEvent extends Event {

        private static final HandlerList handlers = new HandlerList();
        private final Cooldown cooldown;

        CooldownFinishEvent(Cooldown cooldown) {
            this.cooldown = cooldown;
        }

        public Cooldown getCooldown() {
            return this.cooldown;
        }

        @Override
        public HandlerList getHandlers() {
            return handlers;
        }

        public static HandlerList getHandlerList() {
            return handlers;
        }
    }
}
