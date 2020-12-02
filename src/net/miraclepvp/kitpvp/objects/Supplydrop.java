package net.miraclepvp.kitpvp.objects;

import net.minecraft.server.v1_8_R3.TileEntityChest;
import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.bukkit.FileManager;
import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.zone.Zone;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.ContainerBlock;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftChest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.Console;
import java.lang.reflect.Field;
import java.util.*;

import static net.miraclepvp.kitpvp.Main.getRandom;
import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class Supplydrop {

    public static List<Crate> activeCrates = new ArrayList<>();

    public static Crate getCrate(UUID uuid) {
        return activeCrates.stream().filter(c -> c.uuid.equals(uuid)).findFirst().get();
    }

    public static Crate getCrate(Location location) {
        return activeCrates.stream().filter(c -> c.location.getBlock().getLocation().equals(location)).findFirst().get();
    }

    public static void despawnCrates() {
        try {
            activeCrates.forEach(crate -> {
                crate.delete();
                Bukkit.broadcastMessage(color("&cThe supplydrop at " + crate.getZone().getName() + " has been despawned!"));
            });
        } catch (ConcurrentModificationException ex) {

        }
    }

    public enum DropType {

        NORMAL("Normal"),
        VOTE("Vote");

        private String name;

        DropType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static class Crate {

        private UUID uuid;
        private boolean spawned;
        private DropType dropType;
        private Zone zone;
        private Location location;
        private static List<ItemStack> items = new ArrayList<>();

        public static void prepareItems() {
            Random random = new Random();
            for (int i = 50; i < 100; i++)
                items.add(createCoin(i));
            for (int i = 100; i < 150; i++)
                items.add(createExperience(i));
            for (int i = 0; i < 100; i++)
                items.add(new ItemstackFactory(Material.GOLDEN_APPLE, random.nextInt(2) + 1));
            for (int i = 0; i < 75; i++)
                items.add(new ItemstackFactory(Material.ARROW, getRandom(6, 16)));
            for (int i = 0; i < 30; i++)
                items.add(new ItemstackFactory(Material.ENDER_PEARL));
            for (int i = 0; i < 10; i++)
                items.add(createPersonalCoinBooster(10));
            for (int i = 0; i < 5; i++)
                items.add(createPersonalCoinBooster(15));
            for (int i = 0; i < 10; i++)
                items.add(createPersonalEXPBooster(10));
            for (int i = 0; i < 5; i++)
                items.add(createPersonalEXPBooster(15));
        }

        public Crate(DropType dropType) {
            this.uuid = UUID.randomUUID();
            this.dropType = dropType;
        }

        public void delete() {
            try {
                BlockState state = location.getBlock().getState();
                Inventory inv = ((ContainerBlock) state).getInventory();
                inv.clear();
                location.getBlock().setType(Material.AIR);
                activeCrates.remove(this);
            } catch (ClassCastException ex) {

            }
        }

        public Zone getZone() {
            return zone;
        }

        public Crate spawn() {
            if (!spawned) {
                activeCrates.add(this);

                Random random = new Random();
                zone = Data.zones.get(random.nextInt(Data.zones.size()));
                location = FileManager.deSerialize(zone.getSupplydropLocations().get(random.nextInt(zone.getSupplydropLocations().size())));
                location.getBlock().setType(new ItemstackFactory(Material.CHEST).setDisplayName("Supply Drop").getType());

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        BlockState state = location.getBlock().getState();
                        Inventory inv = ((ContainerBlock) state).getInventory();
                        inv.clear();

                        //Creating a list of avaible slots
                        List<Integer> avaibleSlots = new ArrayList<>();
                        for (int i = 1; i < inv.getSize(); i++)
                            avaibleSlots.add(i);

                        //Adding items
                        Integer itemamount = getRandom(2, 5);
                        Collections.shuffle(items);

                        //Setting the items
                        for (int i = 0; i < itemamount; i++) {
                            Collections.shuffle(avaibleSlots);
                            Integer slot = avaibleSlots.get(0);
                            inv.setItem(slot - 1, items.get(i));
                            avaibleSlots.remove(0);
                        }

                        try {
                            CraftChest chest = (CraftChest) location.getBlock().getState();
                            Field inventoryField = chest.getClass().getDeclaredField("chest");
                            inventoryField.setAccessible(true);
                            TileEntityChest teChest = ((TileEntityChest) inventoryField.get(chest));
                            teChest.a("Supply Drop: " + dropType.getName() + " - X:" + location.getBlock().getX() + " Y:" + location.getBlock().getY() + " Z:" + location.getBlock().getZ());
                            Bukkit.broadcastMessage(color("&aA " + dropType.getName() + " supply drop spawned at " + zone.getName() + "!"));
                            location.getWorld().strikeLightningEffect(location);
                            spawned = true;
                        } catch (Exception e) {
                            e.printStackTrace();
                            delete();
                            Bukkit.broadcastMessage(color("&cThe supplydrop at " + zone.getName() + " has been despawned!"));
                        }
                    }
                };
                thread.start();

                //Despawn crate after 5 minutes
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        try {
                            if (activeCrates.contains(getCrate(uuid))) {
                                delete();
                                Bukkit.broadcastMessage(color("&cThe supplydrop at " + zone.getName() + " has been despawned!"));
                            } else {
                                Bukkit.getLogger().warning("Could not delete the supplydrop. The drop is not in the list of active drops.");
                            }
                        } catch (NoSuchElementException ex) {
                            //DROP IS ALREADY DELETED
                        }
                    }
                }.runTaskLater(Main.getInstance(), 6000L);
            }
            return this;
        }
    }

    public static ItemStack createCoin(Integer amount) {
        return new ItemstackFactory(Material.GOLD_NUGGET).setDisplayName("&6" + amount + " Coins").addLoreLine("&7Click on this item to redeem your reward.");
    }

    public static ItemStack createExperience(Integer amount) {
        return new ItemstackFactory(Material.EXP_BOTTLE).setDisplayName("&5" + amount + " Experience").addLoreLine("&7Click on this item to redeem your reward.");
    }

    public static ItemStack createPersonalEXPBooster(Integer amount) {
        return new ItemstackFactory(Material.PAPER).setDisplayName("&3" + amount + "% EXP - Personal Booster").addLoreLine("&7Click on this item to redeem your reward.");
    }

    public static ItemStack createPersonalCoinBooster(Integer amount) {
        return new ItemstackFactory(Material.PAPER).setDisplayName("&3" + amount + "% Coin - Personal Booster").addLoreLine("&7Click on this item to redeem your reward.");
    }
}
