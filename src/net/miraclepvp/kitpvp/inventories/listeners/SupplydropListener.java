package net.miraclepvp.kitpvp.inventories.listeners;

import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.Booster;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.objects.Supplydrop;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class SupplydropListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getName() == null) return;
        if (!(event.getClickedInventory().getName().contains("Supply Drop: "))) return;
        event.setCancelled(true);

        User user = Data.getUser(((Player) event.getWhoClicked()));
        if (event.getCurrentItem() != null)
            switch (event.getCurrentItem().getType()) {
                case EXP_BOTTLE:
                    Integer exp = Integer.valueOf(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).replace(" Experience", ""));
                    user.setExperience(user.getExperience() + exp, false);
                    event.getWhoClicked().sendMessage(color("&aYou've received " + exp + " experience!"));
                    break;
                case GOLD_NUGGET:
                    Integer coins = Integer.valueOf(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).replace(" Coins", ""));
                    user.setCoins(user.getCoins() + coins, false);
                    event.getWhoClicked().sendMessage(color("&aYou've received " + coins + " coins!"));
                    break;
                case GOLDEN_APPLE:
                    event.getWhoClicked().getInventory().addItem(new ItemstackFactory(Material.GOLDEN_APPLE, event.getCurrentItem().getAmount()));
                    break;
                case ARROW:
                    event.getWhoClicked().getInventory().addItem(new ItemstackFactory(Material.ARROW, event.getCurrentItem().getAmount()));
                    break;
                case PAPER:
                    String name = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
                    Boolean personal = name.contains("Personal");
                    Booster.BoosterType type = Booster.BoosterType.COINS;
                    String[] words = name.split(" ");
                    Integer value = Integer.valueOf(words[0].replace("%", ""));
                    if(name.contains("EXP"))
                        type = Booster.BoosterType.EXPERIENCE;
                    Booster booster = new Booster(type, value, personal);
                    user.getBoosterList().add(Booster.serialize(booster));
                    event.getWhoClicked().sendMessage(color("&aYou've received a " + value + "% " + type.name() + " " + (personal ? "personal" : "network") + " booster!"));
                    break;
                case ENDER_PEARL:
                    event.getWhoClicked().getInventory().addItem(new ItemstackFactory(Material.ENDER_PEARL, event.getCurrentItem().getAmount()));
                    break;
            }

        event.getInventory().setItem(event.getSlot(), null);

        Integer itemsInside = 0;
        for (int i = 0; i < 26; i++) {
            if (event.getInventory().getItem(i) != null) {
                itemsInside++;
            }
        }

        if (itemsInside <= 0) {
            World world = event.getWhoClicked().getWorld();
            String name = event.getInventory().getName();
            if (name.contains("Normal"))
                name = name.replace("Supply Drop: Normal - ", "");
            else if (name.contains("Vote"))
                name = name.replace("Supply Drop: Vote - ", "");

            String[] coords = name.split(" ");

            Integer
                    x = Integer.valueOf(coords[0].replace("X:", "")),
                    y = Integer.valueOf(coords[1].replace("Y:", "")),
                    z = Integer.valueOf(coords[2].replace("Z:", ""));
            Location location = new Location(world, x, y, z);
            Supplydrop.Crate crate = Supplydrop.getCrate(location);
            crate.delete();
            Bukkit.broadcastMessage(color("&cThe supplydrop at " + crate.getZone().getName() + " has been looted!"));
        }
    }
}
