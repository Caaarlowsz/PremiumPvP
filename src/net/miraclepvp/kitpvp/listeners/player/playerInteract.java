package net.miraclepvp.kitpvp.listeners.player;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.duel.Duel;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.inventories.*;
import net.miraclepvp.kitpvp.inventories.listeners.BankerListener;
import net.miraclepvp.kitpvp.inventories.listeners.ShopListener;
import net.miraclepvp.kitpvp.objects.hasKit;
import net.miraclepvp.kitpvp.utils.CooldownUtil;
import net.miraclepvp.kitpvp.utils.TeleportUtil;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class playerInteract implements Listener {

    @EventHandler
    public void onTNTignite(EntityChangeBlockEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Arrow)) return;
        if (!event.getBlock().getType().equals(Material.TNT)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void NPCInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() != EntityType.PLAYER) return;
        Entity entity = event.getRightClicked();
        Player player = event.getPlayer();
        if (entity.getCustomName() == null) return;
        User user = Data.getUser(player);
        if (user == null) return;

        //NPC Name
        String name = ChatColor.stripColor(entity.getCustomName());

        //When Bounty Boss (Avoid Cooldown)
        if (name.equals("Bounty Boss")) {
            ItemStack item = player.getItemInHand();
            if (item == null ||
                    item.getType() == null ||
                    !item.getType().equals(Material.SKULL_ITEM) ||
                    item.getItemMeta() == null ||
                    item.getItemMeta().getDisplayName() == null ||
                    !ChatColor.stripColor(item.getItemMeta().getDisplayName()).endsWith("'s Head")) {
                player.sendMessage(color("&6I don't need this, just give me the heads i'm looking for!"));
                player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
                player.damage(player.getMaxHealth()/2);
                return;
            }
            String playerName = ChatColor.stripColor(item.getItemMeta().getDisplayName()).replace("'s Head", "");
            Integer value = Integer.valueOf(ChatColor.stripColor(item.getItemMeta().getLore().get(0)).replace("Reward: ", "").replace(" Coins", ""));
            player.setItemInHand(null);
            ShopListener.sell(player, value);
            player.sendMessage(color("&6Thanks for confirming " + playerName + "'s death for me, here is your payment of " + value + " coins in banknotes."));
            return;
        }

        //Other NPC's
        CooldownUtil.Cooldown cooldown = CooldownUtil.getCooldown(player, "npc-" + name);
        if (cooldown == null || !cooldown.hasTimeLeft()) {
            CooldownUtil.Cooldown newCooldown = CooldownUtil.prepare(player, "npc-" + name, 7);
            newCooldown.start();
            switch (name) {
                case "Bounty Hunter":
                    player.openInventory(BountyGUI.getMainInventory(1));
                    break;
                case "Play":
                    if (user.getPreviousKit() == null || (Data.getKit(user.getPreviousKit()).getPrice() > 0 && !(user.getKitsList().contains(user.getPreviousKit())))) {
                        player.openInventory(KitGUI.getInventory(player, false, 1));
                        player.sendMessage(color("&6Please select a kit you want to use and try again."));
                        break;
                    }
                    if(Duel.invites.containsKey(player.getUniqueId())){
                        player.sendMessage(color("&cYou can't deploy while your invite is still open."));
                        return;
                    }
                    player.sendMessage(color("&6Be careful, it's dangerous out there!"));
                    player.playSound(player.getLocation(), Sound.VILLAGER_HAGGLE, 1, 1);
                    TeleportUtil.getTeleport(player);
                    player.playSound(player.getLocation(), Sound.PORTAL_TRAVEL, 1, 0);
                    player.playEffect(player.getLocation(), Effect.PORTAL, 5);
                    if(user.giveKit(user.getPreviousKit(), true)){
                        player.setMetadata("kit", new hasKit());
                        player.setAllowFlight(false);
                        player.setFlying(false);
                    }else {
                        player.sendMessage(color("&cCouldn't set your kit, something went wrong."));
                        playerJoin.handleSpawn(player);
                    }
                    break;
                case "Booster":
                    player.openInventory(BoosterGUI.getInventory(player, BoosterGUI.FilterType.ALL));
                    break;
                case "Banker":
                    player.openInventory(BankerGUI.getInventory());
                    break;
                case "Robert":
                    player.openInventory(ShopGUI.getArmory());
                    break;
                case "Hale":
                    player.openInventory(ShopGUI.getBrewery());
                    break;
                case "James":
                    player.openInventory(ShopGUI.getBlacksmith());
                    break;
            }
        }
    }

    @EventHandler
    public void onCrateInteract(PlayerInteractEvent event) {
        //Touch a block
        if (event.getPlayer().hasMetadata("build")) return;
        if (event.getClickedBlock() == null) return;
        if (event.getClickedBlock().getType() == null) return;
        if (!event.getClickedBlock().getType().equals(Material.CHEST)) return;

        org.bukkit.block.Chest chest = (Chest) event.getClickedBlock().getState();
        if (chest.getBlockInventory().getTitle().contains("Supply Drop")) {
            event.setCancelled(false);
            return;
        }
        else {
            event.setCancelled(true);
            if (!chest.getBlockInventory().getTitle().contains("Crate"))
                return;
        }

        //Clicked block is a crate
        String name = ChatColor.stripColor(chest.getBlockInventory().getTitle());

        Boolean
                common = true,
                gear = true;
        if (name.contains("Miracle"))
            common = false;
        if (name.contains("Cosmetic"))
            gear = false;

        User user = Data.getUser(event.getPlayer());

        if (gear) {
            if (common) {
                if (user.getGearcommon() <= 0) {
                    event.getPlayer().sendMessage(color("&cYou have no Common Gear keys."));
                    return;
                }
            } else {
                if (user.getGearmiracle() <= 0) {
                    event.getPlayer().sendMessage(color("&cYou have no Miracle Gear keys."));
                    return;
                }
            }
        } else {
            if (common) {
                if (user.getCosmeticcommon() <= 0) {
                    event.getPlayer().sendMessage(color("&cYou have no Common Cosmetic keys."));
                    return;
                }
            } else {
                if (user.getCosmeticmiracle() <= 0) {
                    event.getPlayer().sendMessage(color("&cYou have no Miracle Cosmetic keys."));
                    return;
                }
            }
        }

        CooldownUtil.Cooldown cooldown = CooldownUtil.getCooldown(event.getPlayer(), "crate");
        if (cooldown == null || !cooldown.hasTimeLeft()) {
            CooldownUtil.Cooldown newCooldown = CooldownUtil.prepare(event.getPlayer(), "crate", 8);
            newCooldown.start();

            if (gear) {
                if (common)
                    user.setGearcommon(user.getGearcommon() - 1);
                else
                    user.setGearmiracle(user.getGearmiracle() - 1);
            } else {
                if (common)
                    user.setCosmeticcommon(user.getCosmeticcommon() - 1);
                else
                    user.setCosmeticmiracle(user.getCosmeticmiracle() - 1);
            }

            event.getPlayer().openInventory(CrateGUI.getInventory(event.getPlayer(), common, gear));
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        //Touch a block
        if (event.getPlayer().hasMetadata("build")) return;
        if (event.getAction().equals(Action.PHYSICAL))
            switch (event.getClickedBlock().getType()) {
                case SOIL:
                    event.setCancelled(true);
                    break;
                case TRIPWIRE:
                    event.setCancelled(true);
                    break;
                case GOLD_PLATE:
                    event.setCancelled(true);
                    break;
                case IRON_PLATE:
                    event.setCancelled(true);
                    break;
                case STONE_PLATE:
                    event.setCancelled(true);
                    break;
                case WOOD_PLATE:
                    event.setCancelled(true);
                    break;
            }

        //RightClick on a block
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            switch (event.getClickedBlock().getType()) {
                case WALL_SIGN:
                    try {
                        if (Data.getSign(event.getClickedBlock().getLocation()) != null)
                            event.getPlayer().openInventory(LeaderboardGUI.getTop(Data.getSign(event.getClickedBlock().getLocation()).getStats()));
                    }catch (NoSuchElementException ex){
                    }
                    break;
                case WOODEN_DOOR:
                    event.setCancelled(true);
                    break;
                case WORKBENCH:
                    event.setCancelled(true);
                    break;
                case BREWING_STAND:
                    event.setCancelled(true);
                    break;
                case DARK_OAK_DOOR:
                    event.setCancelled(true);
                    break;
                case ACACIA_DOOR:
                    event.setCancelled(true);
                    break;
                case BIRCH_DOOR:
                    event.setCancelled(true);
                    break;
                case JUNGLE_DOOR:
                    event.setCancelled(true);
                    break;
                case SPRUCE_DOOR:
                    event.setCancelled(true);
                    break;
                case WOOD_DOOR:
                    event.setCancelled(true);
                    break;
                case TRAP_DOOR:
                    event.setCancelled(true);
                    break;
                case ENDER_CHEST:
                    event.setCancelled(true);
                    break;
                case TRAPPED_CHEST:
                    event.setCancelled(true);
                    break;
                case HOPPER:
                    event.setCancelled(true);
                    break;
                case ANVIL:
                    event.setCancelled(true);
                    if(event.getPlayer().hasMetadata("kit") && event.getClickedBlock().hasMetadata("miracleAnvil"))
                        event.getPlayer().openInventory(AnvilGUI.getInventory());
                    break;
                case ENCHANTMENT_TABLE:
                    event.setCancelled(true);
                    break;
                case FURNACE:
                    event.setCancelled(true);
                    break;
                case BURNING_FURNACE:
                    event.setCancelled(true);
                    break;
                case BEACON:
                    event.setCancelled(true);
                    break;
                case DROPPER:
                    event.setCancelled(true);
                    break;
                case FENCE_GATE:
                    event.setCancelled(true);
                    break;
                case ACACIA_FENCE_GATE:
                    event.setCancelled(true);
                    break;
                case BIRCH_FENCE_GATE:
                    event.setCancelled(true);
                    break;
                case DARK_OAK_FENCE_GATE:
                    event.setCancelled(true);
                    break;
                case JUNGLE_FENCE_GATE:
                    event.setCancelled(true);
                    break;
                case SPRUCE_FENCE_GATE:
                    event.setCancelled(true);
                    break;
                case DISPENSER:
                    event.setCancelled(true);
                    break;
                case STONE_BUTTON:
                    event.setCancelled(true);
                    break;
                case WOOD_BUTTON:
                    event.setCancelled(true);
                    break;
                case LEVER:
                    event.setCancelled(true);
                    break;
                case TNT:
                    event.setCancelled(true);
                    break;
            }
    }
}
