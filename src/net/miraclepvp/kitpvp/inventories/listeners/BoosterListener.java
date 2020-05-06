package net.miraclepvp.kitpvp.inventories.listeners;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.Booster;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.inventories.BoosterGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class BoosterListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getName() == null) return;
        if (!(ChatColor.stripColor(event.getClickedInventory().getName()).startsWith("Boosters"))) return;
        event.setCancelled(true);
        if(event.getCurrentItem() == null) return;
        if(event.getCurrentItem().getType().equals(Material.PAPER)){
            //Getting booster details
            String[] words = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).split(" ");
            //Check if booster is personal
            Boolean personal = false;
            if(words[3].equalsIgnoreCase("personal"))
                personal = true;
            //Check what percentage the booster is
            Integer value = Integer.valueOf(words[0].replace("%", ""));
            //Check what type the booster is
            Booster.BoosterType type = Booster.BoosterType.COINS;
            if(words[1].equalsIgnoreCase("experience"))
                type = Booster.BoosterType.EXPERIENCE;

            //Check if the booster won't go higher then 100
            if(!personal) {
                if (type == Booster.BoosterType.EXPERIENCE)
                    if ((Integer.valueOf(Booster.experienceBoost) + value) > 100) {
                        event.getWhoClicked().sendMessage(color("&cCouldn't activate your booster, the maximum is 100%!"));
                        return;
                    }
                if (type == Booster.BoosterType.COINS)
                    if ((Integer.valueOf(Booster.coinBoost) + value) > 100) {
                        event.getWhoClicked().sendMessage(color("&cCouldn't activate your booster, the maximum is 100%!"));
                        return;
                    }
            } else {
                //Check if user has a personal booster
                if (Booster.activePersonalBoosters.containsKey(event.getWhoClicked().getUniqueId())) {
                    event.getWhoClicked().sendMessage(color("&cYou can not activate multiple personal boosters at once!"));
                    return;
                }
                if (value > 100) {
                    event.getWhoClicked().sendMessage(color("&cCouldn't activate your booster, the maximum is 100%!"));
                    return;
                }
            }

            //Creating the booster
            Booster.ActiveBooster activeBooster = new Booster.ActiveBooster(event.getWhoClicked().getUniqueId(), type, personal, value, 1800);

            //Sending a comformation mesesage
            if(personal)
                event.getWhoClicked().sendMessage(color("&aYou've activated a " + value + "% " + type.toString() + " booster."));
            else
                Bukkit.broadcastMessage(color("&5&l&k!! &a" + event.getWhoClicked().getName() + " activated a " + value + "% " + type.toString() + " booster!"));

            //Removing the booster from the user
            User user = Data.getUser(((Player) event.getWhoClicked()));
            String booster = Booster.serialize(type, value, personal);
            user.getBoosterList().remove(booster);

            //Updating the inventory
            event.getWhoClicked().openInventory(BoosterGUI.getInventory(((Player) event.getWhoClicked()), BoosterGUI.FilterType.ALL));
        }
        if(event.getCurrentItem().getType().equals(Material.HOPPER)){
            String[] words = event.getInventory().getName().split(" ");
            String lastWord = words[words.length-1];
            String typeName = lastWord.replace("(", "").replace(")", "");
            BoosterGUI.FilterType type = BoosterGUI.FilterType.valueOf(typeName);
            event.getWhoClicked().openInventory(BoosterGUI.getInventory(((Player) event.getWhoClicked()), BoosterGUI.FilterType.getNextFilter(type)));
        }
    }
}
