package net.miraclepvp.kitpvp.inventories.listeners;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.inventories.BountyGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class BountyListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getName() == null) return;
        if (!(ChatColor.stripColor(event.getClickedInventory().getName()).startsWith("Bounty"))) return;
        event.setCancelled(true);
        if (event.getCurrentItem() == null) return;
        if(event.getCurrentItem().getItemMeta() == null) return;
        if(event.getCurrentItem().getItemMeta().getDisplayName().isEmpty()) return;
        if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(" ")) return;
        if(event.getCurrentItem().getType().equals(Material.PAPER)) return;

        String[] words = ChatColor.stripColor(event.getClickedInventory().getName()).replace("- ", "").split(" ");
        if(words.length==2){
            if(event.getSlot() == 49)
                event.getWhoClicked().openInventory(BountyGUI.getPlayersInventory(((Player) event.getWhoClicked()), 1));
            return;
        }
        else{
            if(words[2].equalsIgnoreCase("players")){
                String name = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
                if(!checkAvaible(name)){
                    event.getWhoClicked().sendMessage(color("&cThis player is not available anymore."));
                    event.getWhoClicked().closeInventory();
                    return;
                }
                Player player = Bukkit.getPlayer(name);
                event.getWhoClicked().openInventory(BountyGUI.getSetBountyInventory(player, 100));
                return;
            } else {
                if(!checkAvaible(words[2])){
                    event.getWhoClicked().sendMessage(color("&cThis player is not available anymore."));
                    event.getWhoClicked().closeInventory();
                    return;
                }
                Player player = Bukkit.getPlayer(words[2]);
                User clicker = Data.getUser(((Player) event.getWhoClicked()));
                Integer value = Integer.valueOf(ChatColor.stripColor(event.getInventory().getItem(22).getItemMeta().getDisplayName()).replace(" Coins", ""));
                switch (event.getSlot()){
                    case 12:
                        setPrice(((Player) event.getWhoClicked()), player, Integer.valueOf(value+50));
                        break;
                    case 13:
                        setPrice(((Player) event.getWhoClicked()), player, Integer.valueOf(value+100));
                        break;
                    case 14:
                        setPrice(((Player) event.getWhoClicked()), player, Integer.valueOf(value+500));
                        break;
                    case 30:
                        setPrice(((Player) event.getWhoClicked()), player, Integer.valueOf(value-50));
                        break;
                    case 31:
                        setPrice(((Player) event.getWhoClicked()), player, Integer.valueOf(value-100));
                        break;
                    case 32:
                        setPrice(((Player) event.getWhoClicked()), player, Integer.valueOf(value-500));
                        break;
                    case 22:
                        Integer cost = Integer.valueOf(((value/100)*10)+value);
                        if (clicker.getCoins() < cost) {
                            event.getWhoClicked().sendMessage(color("&cYou dont have enough coins to afford this bounty."));
                            event.getWhoClicked().closeInventory();
                            break;
                        }
                        clicker.setCoins(clicker.getCoins()-cost, false);
                        event.getWhoClicked().closeInventory();
                        setBounty(player, value);
                        break;
                }
                return;
            }
        }
    }

    private void setPrice(Player player, Player target, Integer price){
        Integer min = 100,
                max = 2000;
        if(price<=max && price>=min)
            player.openInventory(BountyGUI.getSetBountyInventory(target, price));
        else {
            if(price>max) {
                player.sendMessage(color("&cThis action would go over the maximum of " + max + ". I've set the value to 2000"));
                player.openInventory(BountyGUI.getSetBountyInventory(target, 2000));
            }
            if(price<min) {
                player.sendMessage(color("&cThis action would go under the minimum of " + min + ". I've set the value to 100"));
                player.openInventory(BountyGUI.getSetBountyInventory(target, 100));
            }
        }
    }

    private Boolean checkAvaible(String player){
        if(!Bukkit.getOfflinePlayer(player).isOnline()) return false;
        if(Data.getUser(Bukkit.getOfflinePlayer(player)).getBounty()>0) return false;
        return true;
    }

    public static void setBounty(OfflinePlayer player, Integer value){
        int result;
        int temp = value % 50;
        if (temp < 25) result = value - temp;
        else result = value + 50 - temp;
        
        Data.getUser(player).setBounty(result);
        Bukkit.broadcastMessage(color("&5!! &6" + player.getName() + " has a bounty of " + result + " on his head. Bring this head to the Bounty Boss for your reward!"));
    }
}
