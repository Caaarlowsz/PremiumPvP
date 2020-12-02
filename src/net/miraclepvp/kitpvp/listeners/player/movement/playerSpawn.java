package net.miraclepvp.kitpvp.listeners.player.movement;

import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.data.Config;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.duel.Duel;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.listeners.custom.PlayerSpawnEvent;
import net.miraclepvp.kitpvp.objects.Board;
import net.miraclepvp.kitpvp.objects.Item;
import net.miraclepvp.kitpvp.utils.CooldownUtil;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class playerSpawn implements Listener {

    @EventHandler
    public void onSpawn(PlayerSpawnEvent event){
        Player player = event.getPlayer();
        User user = event.getUser();

        //Lets remove the cooldowns
        CooldownUtil.Cooldown trackerCooldown = CooldownUtil.getCooldown(player, "tracker");
        if(trackerCooldown != null) trackerCooldown.delete();
        CooldownUtil.Cooldown enderpearlCooldown = CooldownUtil.getCooldown(player, "enderpearl");
        if(enderpearlCooldown != null) enderpearlCooldown.delete();
        CooldownUtil.Cooldown switcherballCooldown = CooldownUtil.getCooldown(player, "switcherball");
        if(switcherballCooldown != null) switcherballCooldown.delete();

        //Turn off buildmode
        if (player.hasMetadata("build"))
            player.removeMetadata("build", Main.getInstance());
        //Leave the duels match
        if (Duel.isIngame(player))
            Duel.getDuel(player).leave(player);
        //Stop spectating a duels match
        if (Duel.isSpectator(player))
            Duel.stopSpectating(player);
        //Is not in a zone anymore
        if (playerMove.inZone.containsKey(player.getUniqueId()))
            playerMove.inZone.remove(player.getUniqueId());

        //Set gamemode to adventure
        player.setGameMode(GameMode.ADVENTURE);

        if (player.hasMetadata("kit")) { //If the player had a kit
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
            user.setBanknoteValue(value); //Calculate banknotes
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (Config.getLobbyLoc().getY() > 0) //Teleport to the lobby
                    player.teleport(Config.getLobbyLoc().clone().add(0,0.5,0), PlayerTeleportEvent.TeleportCause.PLUGIN);

                player.setFireTicks(-100); //Remove fire
                player.setHealth(player.getMaxHealth()); //Heal player
                player.setFoodLevel(20); //Feed player
                player.setRemainingAir(player.getMaximumAir()); //Restore the player's air

                player.getInventory().setArmorContents(null); //Remove armor
                player.getInventory().clear(); //Clear inventory
                player.removeMetadata("kit", Main.getInstance()); //Doesn't have a kit anymore
                player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType())); //Remove all potion effects
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10000000, 3, false, false)); //Give the player infinite speed 2

                Item.types.forEach(item -> player.getInventory().setItem(item.getPosition(), item.getItem())); //Set lobby items

                //Disabling rank features when no permissions
                if (!player.hasPermission("miraclepvp.donator.fly")) user.setFlyEnabled(false);
                if (!player.hasPermission("miraclepvp.donator.quickselect")) user.setQuickSelect(false);
                if (!player.hasPermission("miraclepvp.donator.autodeploy")) user.setAutoDeploy(false);
                if (!player.hasPermission("miraclepvp.donator.killbroadcast")) user.setKillBroadcast(false);
                if (!player.hasPermission("miraclepvp.donator.streakbroadcast")) user.setStreakBroadcast(false);

                if (user.isFlyEnabled())
                    player.setAllowFlight(true); //Set lobby flight
            }
        }.runTaskLater(Main.getInstance(), 1);
    }
}
