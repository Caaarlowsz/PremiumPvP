package net.miraclepvp.kitpvp.data.user;

import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.chatcolor.Chatcolor;
import net.miraclepvp.kitpvp.data.kit.ArmorSlot;
import net.miraclepvp.kitpvp.data.kit.Kit;
import net.miraclepvp.kitpvp.data.kit.KitEffects;
import net.miraclepvp.kitpvp.data.namecolor.Namecolor;
import net.miraclepvp.kitpvp.data.prefix.Prefix;
import net.miraclepvp.kitpvp.data.suffix.Suffix;
import net.miraclepvp.kitpvp.data.trail.Trail;
import net.miraclepvp.kitpvp.inventories.listeners.BountyListener;
import net.miraclepvp.kitpvp.inventories.listeners.ShopListener;
import net.miraclepvp.kitpvp.listeners.custom.PlayerStatusChangeEvent;
import net.miraclepvp.kitpvp.objects.Board;
import net.miraclepvp.kitpvp.objects.Chatmode;
import net.miraclepvp.kitpvp.objects.CosmeticType;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static net.miraclepvp.kitpvp.bukkit.Text.IntegerToCompactInteger;
import static net.miraclepvp.kitpvp.bukkit.Text.color;
import static org.bukkit.Bukkit.getServer;

public class User {

    public UUID uuid,
            prefix,
            activeSuffix,
            activeTrail,
            activeChatcolor,
            activeNamecolor,
            previousKit,
            guild;
    public String lastVersion,
            firstJoin,
            lastJoin,
            lastCosmeticType;
    public Integer tokens,
            onlineTime,
            kills,
            assists,
            deaths,
            coins,
            level,
            gearcommon,
            gearmiracle,
            cosmeticcommon,
            cosmeticmiracle,
            experience,
            killstreak,
            bestkillstreak,
            bounty,
            banknoteValue;
    public List<UUID> trailsList,
            suffixesList,
            chatcolorsList,
            namecolorsList,
            mutedUsers,
            kits;
    public List<String> boosterList;
    public HashMap<Abilities.AbilityType, Integer> abilities;
    public HashMap<UUID, ArrayList<Integer>> kitLayouts;
    public Boolean cosmeticWasShop,
            autoDeploy,
            quickSelect,
            killBroadcast,
            streakBroadcast,
            everyoneMuted,
            flyEnabled;
    public Chatmode chatmode;

    public User(UUID uuid) {
        createStats(uuid, false);
    }

    public void createStats(UUID uuid, Boolean reset){
        if(reset && Bukkit.getOfflinePlayer(uuid).isOnline())
            Bukkit.getOfflinePlayer(uuid).getPlayer().kickPlayer(color("&cYour stats have been resetted by staff."));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        this.uuid = uuid;
        this.lastVersion = Main.getInstance().version;
        this.tokens = Integer.valueOf(0);
        this.activeSuffix = null;
        this.prefix = Data.getPrefix("default").getUuid();
        this.activeTrail = null;
        this.activeChatcolor = null;
        this.activeNamecolor = null;
        this.previousKit = null;
        this.trailsList = new ArrayList<>();
        this.suffixesList = new ArrayList<>();
        this.chatcolorsList = new ArrayList<>();
        this.namecolorsList = new ArrayList<>();
        this.kits = new ArrayList<>();
        this.firstJoin = formatter.format(date);
        this.lastJoin = formatter.format(date);
        this.onlineTime = Integer.valueOf(0);
        this.kills = Integer.valueOf(0);
        this.assists = Integer.valueOf(0);
        this.deaths = Integer.valueOf(0);
        this.coins = Integer.valueOf(0);
        this.gearcommon = Integer.valueOf(0);
        this.gearmiracle = Integer.valueOf(0);
        this.cosmeticcommon = Integer.valueOf(0);
        this.cosmeticmiracle = Integer.valueOf(0);
        this.level = 1;
        this.experience = Integer.valueOf(0);
        this.killstreak = Integer.valueOf(0);
        this.bestkillstreak = Integer.valueOf(0);
        this.bounty = Integer.valueOf(0);
        this.cosmeticWasShop = false;
        this.quickSelect = false;
        this.autoDeploy = false;
        this.killBroadcast = false;
        this.streakBroadcast = false;
        this.lastCosmeticType = null;
        this.guild = null;
        this.everyoneMuted = false;
        this.mutedUsers = new ArrayList<>();
        this.boosterList = new ArrayList<>();
        this.chatmode = Chatmode.ALL;
        this.flyEnabled = false;
        this.abilities = new HashMap<>();
        this.kitLayouts = new HashMap<>();
        this.banknoteValue = 0;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getLastVersion() {
        return lastVersion;
    }

    public void setLastVersion(String lastVersion) {
        this.lastVersion = lastVersion;
        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public Integer getTokens() {
        return tokens;
    }

    public UUID getPreviousKit() {
        return previousKit;
    }

    public UUID getGuild() {
        return guild;
    }

    public void setGuild(UUID guild) {
        if (guild == null)
            setChatmode(Chatmode.ALL);
        this.guild = guild;
        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public Integer getGearcommon() {
        return gearcommon;
    }

    public Integer getGearmiracle() {
        return gearmiracle;
    }

    public Integer getCosmeticcommon() {
        return cosmeticcommon;
    }

    public Integer getCosmeticmiracle() {
        return cosmeticmiracle;
    }

    public void setBanknoteValue(Integer banknoteValue) {
        if(this.banknoteValue == null)
            this.banknoteValue = 0;
        this.banknoteValue = banknoteValue;
    }

    public Integer getBanknoteValue() {
        if(this.banknoteValue == null)
            this.banknoteValue = 0;
        return banknoteValue;
    }

    public HashMap<UUID, ArrayList<Integer>> getKitLayouts() {
        if (kitLayouts == null)
            kitLayouts = new HashMap<>();
        return kitLayouts;
    }

    public void setToKitLayouts(UUID kitUUID, Inventory inventory) {
        HashMap<Integer, ItemStack> fakeInventory = new HashMap<>();
        //              <--- ADDING THE HOTBAR ITEMS --->
        for (int i = 0; i < 9; i++) {
            ItemStack item = inventory.getItem(36 + i);
            try {
                //if the item is not nothing
                String lore = item.getItemMeta().getLore().get(item.getItemMeta().getLore().size() - 1);
                item.setAmount(Integer.parseInt(ChatColor.stripColor(lore.replace("Amount: ", ""))));

                //Removing the lore
                if (item.getItemMeta().getLore().size() > 1) {
                    item.getItemMeta().getLore().remove(item.getItemMeta().getLore().size() - 1);
                } else {
                    item.getItemMeta().setLore(null);
                }
            } catch (Exception ex) {
                item = new ItemstackFactory(Material.AIR);
            }
            fakeInventory.put(i, item);
        }

        //              <--- ADDING THE OTHER ITEMS --->
        for (int i = 0; i < 27; i++) {
            ItemStack item = inventory.getItem(i);
            try {
                //if the item is not nothing
                String lore = item.getItemMeta().getLore().get(item.getItemMeta().getLore().size() - 1);
                item.setAmount(Integer.parseInt(ChatColor.stripColor(lore.replace("Amount: ", ""))));
                //Removing the lore
                if (item.getItemMeta().getLore().size() > 1) {
                    item.getItemMeta().getLore().remove(item.getItemMeta().getLore().size() - 1);
                } else {
                    item.getItemMeta().setLore(null);
                }
            } catch (Exception ex) {
                item = new ItemstackFactory(Material.AIR);
            }
            fakeInventory.put(i+9, item);
        }

        //Saving the order
        //Making a order list
        ArrayList<Integer> order = new ArrayList<>();
        for (int i = 0; i < 36; i++)
            order.add(i);

        Data.getKit(kitUUID).getItems().forEach((integer, kitItem) -> {
            //All items in the kit
            for (int i = 0; i < fakeInventory.size(); i++) {
                //Scan thru the fake items
                ItemStack fakeItem = fakeInventory.get(i);
                ItemStack realItem = kitItem.getItemStack();
                if (fakeItem.getType().equals(realItem.getType()) && fakeItem.getData().equals(realItem.getData())) {
                    //The items are the same
                    order.set(integer, i);
                }
            }
        });

        getKitLayouts().put(kitUUID, order);

        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public void removeFromKitLayouts(UUID kitUUID) {
        if (!getKitLayouts().containsKey(kitUUID)) return;
        getKitLayouts().remove(kitUUID);
    }

    public boolean giveKit(UUID kitUUID, Boolean customAllowed) {
        if (!Bukkit.getOfflinePlayer(uuid).isOnline()) return false;
        if (Data.getKit(kitUUID).getName() == null) return false;

        Kit kit = Data.getKit(kitUUID);
        Player player = Bukkit.getPlayer(uuid);

        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        ItemStack helmet = kit.getArmor().get(ArmorSlot.HELMET).getItemStack();
        ItemStack chestplate = kit.getArmor().get(ArmorSlot.CHESTPLATE).getItemStack();
        ItemStack leggings = kit.getArmor().get(ArmorSlot.LEGGING).getItemStack();
        ItemStack boots = kit.getArmor().get(ArmorSlot.BOOTS).getItemStack();

        if (customAllowed) {
            ArrayList<Integer> order = null;
            if(getKitLayouts().containsKey(kitUUID))
                order = getKitLayouts().get(kitUUID);

            for (int i = 0; i < kit.getItems().size(); i++)
                player.getInventory().setItem((getKitLayouts().containsKey(kitUUID) ? order.get(i) : i), kit.getItems().get(i).getItemStack());

            ShopListener.sell(player, banknoteValue);
            banknoteValue = 0;
        } else {
            for (int i = 0; i < kit.getItems().size(); i++)
                player.getInventory().setItem(i, kit.getItems().get(i).getItemStack());
        }

        if (helmet != null) player.getInventory().setHelmet(helmet);
        if (chestplate != null) player.getInventory().setChestplate(chestplate);
        if (leggings != null) player.getInventory().setLeggings(leggings);
        if (boots != null) player.getInventory().setBoots(boots);

        if (kit.getEffects().size() > 0)
            kit.getEffects().forEach(effect -> player.addPotionEffect(KitEffects.deSerialize(effect)));

        player.setGameMode(GameMode.ADVENTURE);

        if (player.hasMetadata("build")) {
            player.sendMessage(color("&cYour buildmode has been deactived since you've received a kit."));
            player.removeMetadata("build", Main.getInstance());
        }

        HashMap<Abilities.AbilityType, Integer> abilities = Data.getUser(player).getAbilities();
        if (abilities.get(Abilities.AbilityType.ABSORTION) != null)
            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, Abilities.AbilityType.ABSORTION.getLvl(abilities.get(Abilities.AbilityType.ABSORTION)) * 20, 0));
        if (abilities.get(Abilities.AbilityType.REGEN_SPAWN) != null)
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Abilities.AbilityType.REGEN_SPAWN.getLvl(abilities.get(Abilities.AbilityType.REGEN_SPAWN)) * 20, 0));

        return true;
    }

    public void setGearcommon(Integer gearcommon) {
        this.gearcommon = gearcommon;

        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public void setGearmiracle(Integer gearmiracle) {
        this.gearmiracle = gearmiracle;

        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public void setCosmeticcommon(Integer cosmeticcommon) {
        this.cosmeticcommon = cosmeticcommon;

        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public void setCosmeticmiracle(Integer cosmeticmiracle) {
        this.cosmeticmiracle = cosmeticmiracle;

        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public HashMap<Abilities.AbilityType, Integer> getAbilities() {
        return abilities;
    }

    public void setPreviousKit(UUID previousKit) {
        this.previousKit = previousKit;

        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public Boolean isAutoDeploy() {
        return autoDeploy;
    }

    public Boolean isQuickSelect() {
        return quickSelect;
    }

    public Boolean isKillBroadcast() {
        return killBroadcast;
    }

    public Boolean isStreakBroadcast() {
        return streakBroadcast;
    }

    public void setQuickSelect(Boolean quickSelect) {
        this.quickSelect = quickSelect;

        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public void setAutoDeploy(Boolean autoDeploy) {
        this.autoDeploy = autoDeploy;

        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public void setKillBroadcast(Boolean killBroadcast) {
        this.killBroadcast = killBroadcast;

        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public void setStreakBroadcast(Boolean streakBroadcast) {
        this.streakBroadcast = streakBroadcast;

        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public Integer getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Integer onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getFirstJoin() {
        return firstJoin;
    }

    public String getLastJoin() {
        return lastJoin;
    }

    public void setLastJoin(String lastJoin) {
        this.lastJoin = lastJoin;

        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public void setCoins(Integer coins, Boolean booster) {
        Integer old = this.coins;
        Float added = Float.valueOf(coins - old);
        Integer globalExtra = 0;
        AtomicReference<Integer> personalExtra = new AtomicReference<>(0);
        Integer total = Math.round(old + added);
        if (booster) {
            //Network boost
            if (Booster.coinBoost > 0) {
                Float withBoost = (added / 100) * (Float.valueOf(Booster.coinBoost) + 100);
                globalExtra = Math.round(withBoost - added);
                if (Bukkit.getOfflinePlayer(uuid).isOnline() && globalExtra > 0)
                    Bukkit.getPlayer(uuid).sendMessage(color("&6+" + globalExtra + " Coins (" + Booster.coinBoost + "% Network Booster)"));
            }
            //Personal boost
            if (Booster.activePersonalBoosters.containsKey(uuid)) {
                Booster.activePersonalBoosters.forEach(((playerid, boost) -> {
                    //Booster is from this player
                    if (playerid.equals(uuid)) {
                        if (boost.boosterType.equals(Booster.BoosterType.EXPERIENCE)) return;
                        Float withBoost = (added / 100) * (Float.valueOf(boost.percentage) + 100);
                        personalExtra.set(Math.round(withBoost - added));
                        if (Bukkit.getOfflinePlayer(uuid).isOnline() && personalExtra.get() > 0)
                            Bukkit.getPlayer(uuid).sendMessage(color("&6+" + personalExtra.get() + " Coins (" + boost.percentage + "% Personal Booster)"));
                    }
                }));
            }
        }
        total = (globalExtra + personalExtra.get()) + total;
        this.coins = total;
        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public Integer getCoins() {
        return coins;
    }

    public void setBestkillstreak(Integer bestkillstreak) {
        this.bestkillstreak = bestkillstreak;
        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public Integer getBestkillstreak() {
        return bestkillstreak;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public Integer getDeaths() {
        return deaths;
    }

    public UUID getPrefix() {
        return prefix;
    }

    public void setPrefix(Prefix prefix) {
        if (prefix == null) {
            this.prefix = null;
        } else {
            this.prefix = prefix.getUuid();
        }
        if (Bukkit.getOfflinePlayer(uuid).isOnline())
            Board.addPlayerInTab(Bukkit.getPlayer(uuid));
        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public void setExperience(Integer experience, Boolean booster) {
        Integer old = this.experience;
        Float added = Float.valueOf(experience - old);
        Integer globalExtra = 0;
        AtomicReference<Integer> personalExtra = new AtomicReference<>(0);
        Integer total = Math.round(old + added);
        if (booster) {
            //Network boost
            if (Booster.experienceBoost > 0) {
                Float withBoost = (added / 100) * (Float.valueOf(Booster.experienceBoost) + 100);
                globalExtra = Math.round(withBoost - added);
                if (Bukkit.getOfflinePlayer(uuid).isOnline() && globalExtra > 0)
                    Bukkit.getPlayer(uuid).sendMessage(color("&6+" + globalExtra + " Experience (" + Booster.experienceBoost + "% Network Booster)"));
            }
            //Personal boost
            if (Booster.activePersonalBoosters.containsKey(uuid)) {
                Booster.activePersonalBoosters.forEach(((playerid, boost) -> {
                    //Booster is from this player
                    if (playerid.equals(uuid)) {
                        if (boost.boosterType.equals(Booster.BoosterType.COINS)) return;
                        Float withBoost = (added / 100) * (Float.valueOf(boost.percentage) + 100);
                        personalExtra.set(Math.round(withBoost - added));
                        if (Bukkit.getOfflinePlayer(uuid).isOnline() && personalExtra.get() > 0)
                            Bukkit.getPlayer(uuid).sendMessage(color("&6+" + personalExtra.get() + " Experience (" + boost.percentage + "% Personal Booster)"));
                    }
                }));
            }
        }
        total = (globalExtra + personalExtra.get()) + total;
        this.experience = total;
        checkLevelup(uuid, level, experience);
        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public Integer getExperience() {
        return experience;
    }

    public void setKills(Integer kills) {
        this.kills = kills;
        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public Integer getKills() {
        return kills;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public Integer getAssists() {
        return assists;
    }

    public void setKillstreak(Integer killstreak) {
        if (this.bestkillstreak < killstreak)
            this.bestkillstreak = killstreak;
        this.killstreak = killstreak;
        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
        if (killstreak % 3 == 0 && killstreak != 0 && streakBroadcast)
            Bukkit.broadcastMessage(color("&a" + getPlayer().getName() + " is on a killstreak of " + killstreak));

        //Give bounty
        if(killstreak <= 10 || bounty > 0) return;
        Random random = new Random();
        if(random.nextInt(15) == 1){
            Integer value = killstreak*(random.nextInt(5)+10);
            if(value>2000) value=2000;
            BountyListener.setBounty(getPlayer(), value);
        }
    }

    public Integer getKillstreak() {
        return killstreak;
    }

    public void setLevel(Integer level) {
        this.level = level;
        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public Integer getLevel() {
        return level;
    }

    public double getKdRatio() {
        if (getDeaths() == 0) {
            return getKills();
        }
        double kd = (double) this.kills / (double) this.deaths;
        return Math.round(kd * 100.00) / 100.00;
    }

    public UUID getActiveSuffix() {
        return activeSuffix;
    }

    public void setActiveSuffix(Suffix activeSuffix) {
        if (activeSuffix == null) {
            this.activeSuffix = null;
        } else {
            this.activeSuffix = activeSuffix.getUuid();
            if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
                PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
                getServer().getPluginManager().callEvent(event);
            }
        }
    }

    public UUID getActiveTrail() {
        return activeTrail;
    }

    public void setActiveTrail(Trail activeTrail) {
        if (activeTrail == null) {
            this.activeTrail = null;
        } else {
            this.activeTrail = activeTrail.getUuid();
            if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
                PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
                getServer().getPluginManager().callEvent(event);
            }
        }
    }

    public Boolean getCosmeticWasShop() {
        return cosmeticWasShop;
    }

    public void setCosmeticWasShop(Boolean cosmeticWasShop) {
        this.cosmeticWasShop = cosmeticWasShop;
        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public String getLastCosmeticType() {
        if (lastCosmeticType == null) return "Suffix";
        return lastCosmeticType;
    }

    public void setLastCosmeticType(CosmeticType lastCosmeticType) {
        if (lastCosmeticType == null) {
            this.lastCosmeticType = null;
        } else {
            this.lastCosmeticType = lastCosmeticType.getName();
            if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
                PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
                getServer().getPluginManager().callEvent(event);
            }
        }
    }

    public UUID getActiveChatcolor() {
        return activeChatcolor;
    }

    public void setActiveChatcolor(Chatcolor activeChatcolor) {
        if (activeChatcolor == null) {
            this.activeChatcolor = null;
        } else {
            this.activeChatcolor = activeChatcolor.getUuid();
            if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
                PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
                getServer().getPluginManager().callEvent(event);
            }
        }
    }

    public UUID getActiveNamecolor() {
        return activeNamecolor;
    }

    public void setActiveNamecolor(Namecolor activeNamecolor) {
        if (activeNamecolor == null) {
            this.activeNamecolor = null;
        } else {
            this.activeNamecolor = activeNamecolor.getUuid();
            if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
                PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
                getServer().getPluginManager().callEvent(event);
            }
        }
    }

    public List<UUID> getTrailsList() {
        return trailsList;
    }

    public List<UUID> getSuffixesList() {
        return suffixesList;
    }

    public List<UUID> getChatcolorsList() {
        return chatcolorsList;
    }

    public List<UUID> getNamecolorsList() {
        return namecolorsList;
    }

    public List<UUID> getKitsList() {
        return kits;
    }

    public void addTrail(UUID uuid) {
        List<UUID> trails = this.getTrailsList();
        trails.add(uuid);
        this.setTrailsList(trails);
    }

    public void removeTrail(UUID uuid) {
        List<UUID> trails = this.getTrailsList();
        trails.remove(uuid);
        this.setTrailsList(trails);
    }

    public void setTrailsList(List<UUID> trailsList) {
        this.trailsList = trailsList;
        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public void addSuffix(UUID uuid) {
        List<UUID> suffixes = this.getSuffixesList();
        suffixes.add(uuid);
        this.setSuffixesList(suffixes);
    }

    public void removeSuffixes(UUID uuid) {
        List<UUID> suffixes = this.getSuffixesList();
        suffixes.remove(uuid);
        this.setSuffixesList(suffixes);
    }

    public void setSuffixesList(List<UUID> suffixesList) {
        this.suffixesList = suffixesList;
        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public void addChatColor(UUID uuid) {
        List<UUID> colors = this.getChatcolorsList();
        colors.add(uuid);
        this.setChatcolorsList(colors);
    }

    public void removeChatColor(UUID uuid) {
        List<UUID> colors = this.getChatcolorsList();
        colors.remove(uuid);
        this.setChatcolorsList(colors);
    }

    public void setChatcolorsList(List<UUID> chatcolorsList) {
        this.chatcolorsList = chatcolorsList;
        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public void addNameColor(UUID uuid) {
        List<UUID> colors = this.getNamecolorsList();
        colors.add(uuid);
        this.setNamecolorsList(colors);
    }

    public void removeNameColor(UUID uuid) {
        List<UUID> colors = this.getNamecolorsList();
        colors.remove(uuid);
        this.setNamecolorsList(colors);
    }

    public void setNamecolorsList(List<UUID> namecolorsList) {
        this.namecolorsList = namecolorsList;
        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public void setTokens(Integer tokens) {
        this.tokens = tokens;
        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            getServer().getPluginManager().callEvent(event);
        }
    }

    public OfflinePlayer getPlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

    public String getStat(StatType statType, Boolean compact) {
        if (statType.equals(StatType.KILLS))
            return compact ? IntegerToCompactInteger(kills, 2, false) : String.valueOf(kills);
        if (statType.equals(StatType.DEATHS))
            return compact ? IntegerToCompactInteger(deaths, 2, false) : String.valueOf(deaths);
        if (statType.equals(StatType.STREAK))
            return compact ? IntegerToCompactInteger(bestkillstreak, 2, false) : String.valueOf(bestkillstreak);
        if (statType.equals(StatType.LEVEL))
            return compact ? IntegerToCompactInteger(level, 2, false) : String.valueOf(level);
        return "null";
    }

    public static String transformTime(Integer onlineTime) {
        int day = (int) TimeUnit.SECONDS.toDays(onlineTime);
        long hours = TimeUnit.SECONDS.toHours(onlineTime) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(onlineTime) - (TimeUnit.SECONDS.toHours(onlineTime) * 60);
        long second = TimeUnit.SECONDS.toSeconds(onlineTime) - (TimeUnit.SECONDS.toMinutes(onlineTime) * 60);
        return day + "d, " + hours + "h, " + minute + "m, " + second + "s.";
    }

    public int getExperienceNeeded(){
        return getExperienceNeeded(level);
    }

    public int getExperienceNeeded(Integer lvl){
        Integer calc = 1 + lvl;
        Integer calc2 = 25 * lvl;
        return calc2 * calc;
    }

    public String getProgressBar(){
        String bar = "&8[&5";

        Integer totalNeeded = getExperienceNeeded()-getExperienceNeeded(level-1);
        Integer totalHave = experience-getExperienceNeeded(level-1);

        Integer colored = Math.round((float)10/(float)totalNeeded*(float)totalHave);
        for(int i=0;i<colored;i++)
            bar+="■";
        bar+="&7";
        for(int i=0;i<(10-colored);i++)
            bar+="■";
        bar+="&8]";
        return bar;
    }

    private static void checkLevelup(UUID uuid, Integer level, Integer experience) {
        User user = Data.getUser(Bukkit.getOfflinePlayer(uuid));
        if (experience >= user.getExperienceNeeded()) {
            user.setLevel(user.getLevel() + 1);
            if (Bukkit.getOfflinePlayer(uuid).isOnline())
                Bukkit.getPlayer(uuid).sendMessage(color("&aCongrats! You are now level " + user.getLevel() + "!"));
            if (user.getLevel() % 5 == 0) {
                if (user.getLevel() % 10 == 0) {
                    user.setGearmiracle(user.getGearmiracle() + 1);
                    if (Bukkit.getOfflinePlayer(uuid).isOnline())
                        Bukkit.getPlayer(uuid).sendMessage(color("&aYou've receiven a Miracle Gear crate."));
                } else {
                    user.setGearcommon(user.getGearcommon() + 1);
                    if (Bukkit.getOfflinePlayer(uuid).isOnline())
                        Bukkit.getPlayer(uuid).sendMessage(color("&aYou've receiven a Common Gear crate."));
                }
            }
        }
    }

    public List<UUID> getMutedUsers() {
        return mutedUsers;
    }

    public Boolean getEveryoneMuted() {
        return everyoneMuted;
    }

    public void setEveryoneMuted(Boolean everyoneMuted) {
        this.everyoneMuted = everyoneMuted;
    }

    public Chatmode getChatmode() {
        return chatmode;
    }

    public List<String> getBoosterList() {
        return boosterList;
    }

    public void setChatmode(Chatmode chatmode) {
        this.chatmode = chatmode;
    }

    public void setBounty(Integer bounty) {
        int result = bounty;
        int temp = bounty % 50;
        if (temp < 25)
            result = bounty - temp;
        else
            result = bounty + 50 - temp;
        this.bounty = result;
    }

    public Integer getBounty() {
        return bounty;
    }

    public Boolean isFlyEnabled() {
        return flyEnabled;
    }

    public void setFlyEnabled(Boolean flyEnabled) {
        if (Bukkit.getOfflinePlayer(uuid).isOnline())
            Bukkit.getPlayer(uuid).setAllowFlight(flyEnabled);
        this.flyEnabled = flyEnabled;
    }
}
