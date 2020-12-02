package net.miraclepvp.kitpvp.listeners.player;

import net.miraclepvp.kitpvp.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class playerConsume implements Listener {

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event){
        ItemStack item = event.getPlayer().getItemInHand();
        if(item.getType() == null) return;
        if(!item.getType().equals(Material.GOLDEN_APPLE)) return;
        if(item.getItemMeta() == null) return;
        if(item.getItemMeta().getDisplayName() == null) return;
        if(!ChatColor.stripColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase("Golden Apple+")) return;
        Player player = event.getPlayer();

        Collection<PotionEffect> effect = player.getActivePotionEffects();
        HashMap<PotionEffectType, Integer> duration = new HashMap<>();

        effect.stream().forEach(type -> duration.put(type.getType(), type.getDuration()));

        if(duration.containsKey(PotionEffectType.SPEED) && duration.get(PotionEffectType.SPEED)<(7*20))
            effect.remove(effect.stream().filter(potionEffect -> potionEffect.getType().equals(PotionEffectType.SPEED)).findFirst().get());
        if(duration.containsKey(PotionEffectType.ABSORPTION) && duration.get(PotionEffectType.ABSORPTION)<(120*20))
            effect.remove(effect.stream().filter(potionEffect -> potionEffect.getType().equals(PotionEffectType.ABSORPTION)).findFirst().get());
        if(duration.containsKey(PotionEffectType.REGENERATION) && duration.get(PotionEffectType.REGENERATION)<(7*20))
            effect.remove(effect.stream().filter(potionEffect -> potionEffect.getType().equals(PotionEffectType.REGENERATION)).findFirst().get());
        if(duration.containsKey(PotionEffectType.FIRE_RESISTANCE) && duration.get(PotionEffectType.FIRE_RESISTANCE)<(7*20))
            effect.remove(effect.stream().filter(potionEffect -> potionEffect.getType().equals(PotionEffectType.FIRE_RESISTANCE)).findFirst().get());

        effect.add(new PotionEffect(PotionEffectType.SPEED, 7*20, 1));
        effect.add(new PotionEffect(PotionEffectType.ABSORPTION, 120*20, 0));
        effect.add(new PotionEffect(PotionEffectType.REGENERATION, 7*20, 1));
        effect.add(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 7*20, 0));

        new BukkitRunnable(){
            @Override
            public void run() {
                player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
                effect.stream().forEach(potionEffect -> player.addPotionEffect(potionEffect));
            }
        }.runTaskLater(Main.getInstance(), 1);

    }
}
