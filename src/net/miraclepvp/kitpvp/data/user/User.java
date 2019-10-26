package net.miraclepvp.kitpvp.data.user;

import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.chatcolor.Chatcolor;
import net.miraclepvp.kitpvp.data.kit.ArmorSlot;
import net.miraclepvp.kitpvp.data.kit.CustomKit;
import net.miraclepvp.kitpvp.data.kit.Kit;
import net.miraclepvp.kitpvp.data.kit.KitItem;
import net.miraclepvp.kitpvp.data.namecolor.Namecolor;
import net.miraclepvp.kitpvp.data.prefix.Prefix;
import net.miraclepvp.kitpvp.data.suffix.Suffix;
import net.miraclepvp.kitpvp.data.trail.Trail;
import net.miraclepvp.kitpvp.listeners.custom.PlayerStatusChangeEvent;
import net.miraclepvp.kitpvp.objects.CosmeticType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import sun.reflect.annotation.ExceptionProxy;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class User {

    public UUID uuid,
            prefix,
            activeSuffix,
            activeTrail,
            activeChatcolor,
            activeNamecolor,
            previousKit;
    public String lastVersion,
            firstJoin,
            lastJoin,
            lastCosmeticType;
    public Integer tokens,
            onlineTime,
            kills, assists,
            deaths,
            coins,
            level,
            crate,
            experience,
            killstreak,
            bestkillstreak;
    public List<UUID> trailsList,
            suffixesList,
            chatcolorsList,
            namecolorsList;
    public HashMap<UUID, CustomKit> kits;
    public Boolean cosmeticWasShop,
            autoDeploy,
            quickSelect,
            killBroadcast,
            streakBroadcast;

    public User(UUID uuid){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        this.uuid = uuid;
        this.lastVersion = Main.getInstance().version;
        this.tokens = Integer.valueOf(0);
        this.activeSuffix = null;
        this.prefix = null;
        this.activeTrail = null;
        this.activeChatcolor = null;
        this.activeNamecolor = null;
        this.previousKit = null;
        this.trailsList = new ArrayList<>();
        this.suffixesList = new ArrayList<>();
        this.chatcolorsList = new ArrayList<>();
        this.namecolorsList = new ArrayList<>();
        this.kits = new HashMap<>();
        this.firstJoin = formatter.format(date);
        this.lastJoin = formatter.format(date);
        this.onlineTime = Integer.valueOf(0);
        this.kills = Integer.valueOf(0);
        this.assists = Integer.valueOf(0);
        this.deaths = Integer.valueOf(0);
        this.coins = Integer.valueOf(0);
        this.crate = Integer.valueOf(0);
        this.level = 1;
        this.experience = Integer.valueOf(0);
        this.killstreak = Integer.valueOf(0);
        this.bestkillstreak = Integer.valueOf(0);
        this.cosmeticWasShop = false;
        this.quickSelect = false;
        this.autoDeploy = false;
        this.killBroadcast = false;
        this.streakBroadcast = false;
        this.lastCosmeticType = null;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getLastVersion() {
        return lastVersion;
    }

    public void setLastVersion(String lastVersion) {
        this.lastVersion = lastVersion;
    }

    public Integer getTokens() {
        return tokens;
    }

    public UUID getPreviousKit() {
        return previousKit;
    }

    public void setPreviousKit(UUID previousKit) {
        this.previousKit = previousKit;
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
    }

    public void setAutoDeploy(Boolean autoDeploy) {
        this.autoDeploy = autoDeploy;
    }

    public void setKillBroadcast(Boolean killBroadcast) {
        this.killBroadcast = killBroadcast;
    }

    public void setStreakBroadcast(Boolean streakBroadcast) {
        this.streakBroadcast = streakBroadcast;
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
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }

    public Integer getCoins() {
        return coins;
    }

    public void setBestkillstreak(Integer bestkillstreak) {
        this.bestkillstreak = bestkillstreak;
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }

    public Integer getBestkillstreak() {
        return bestkillstreak;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }

    public Integer getDeaths() {
        return deaths;
    }

    public UUID getPrefix() {
        return prefix;
    }

    public void setPrefix(Prefix prefix) {
        if(prefix == null){
            this.prefix = null;
        } else{
            this.prefix = prefix.getUuid();
        }
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
        checkLevelup(uuid, level, experience);
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }

    public Integer getExperience() {
        return experience;
    }

    public void setKills(Integer kills) {
        this.kills = kills;
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }

    public Integer getKills() {
        return kills;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }

    public Integer getAssists() {
        return assists;
    }

    public Integer getCrate() {
        return crate;
    }

    public void setCrate(Integer crate) {
        this.crate = crate;
    }

    public void setKillstreak(Integer killstreak) {
        if(this.bestkillstreak < killstreak)
            this.bestkillstreak = killstreak;
        this.killstreak = killstreak;
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        if(killstreak%3==0 && killstreak!=0 && streakBroadcast)
            Bukkit.broadcastMessage(color("&a" + getPlayer().getName() + " is on a killstreak of " + killstreak));
    }

    public Integer getKillstreak() {
        return killstreak;
    }

    public void setLevel(Integer level) {
        this.level = level;
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
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
        if(activeSuffix == null){
            this.activeSuffix = null;
        } else{
            this.activeSuffix = activeSuffix.getUuid();
            if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
                PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
                Bukkit.getServer().getPluginManager().callEvent(event);
            }
        }
    }

    public UUID getActiveTrail() {
        return activeTrail;
    }

    public void setActiveTrail(Trail activeTrail) {
        if(activeTrail == null){
            this.activeTrail = null;
        } else {
            this.activeTrail = activeTrail.getUuid();
            if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
                PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
                Bukkit.getServer().getPluginManager().callEvent(event);
            }
        }
    }

    public Boolean getCosmeticWasShop() {
        return cosmeticWasShop;
    }

    public void setCosmeticWasShop(Boolean cosmeticWasShop) {
        this.cosmeticWasShop = cosmeticWasShop;
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }

    public String getLastCosmeticType() {
        if(lastCosmeticType == null) return "Suffix";
        return lastCosmeticType;
    }

    public void setLastCosmeticType(CosmeticType lastCosmeticType) {
        if(lastCosmeticType == null){
            this.lastCosmeticType = null;
        } else {
            this.lastCosmeticType = lastCosmeticType.getName();
            if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
                PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
                Bukkit.getServer().getPluginManager().callEvent(event);
            }
        }
    }

    public UUID getActiveChatcolor() {
        return activeChatcolor;
    }

    public void setActiveChatcolor(Chatcolor activeChatcolor) {
        if(activeChatcolor == null){
            this.activeChatcolor = null;
        } else {
            this.activeChatcolor = activeChatcolor.getUuid();
            if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
                PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
                Bukkit.getServer().getPluginManager().callEvent(event);
            }
        }
    }

    public UUID getActiveNamecolor() {
        return activeNamecolor;
    }

    public void setActiveNamecolor(Namecolor activeNamecolor) {
        if(activeNamecolor == null){
            this.activeNamecolor = null;
        } else {
            this.activeNamecolor = activeNamecolor.getUuid();
            if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
                PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
                Bukkit.getServer().getPluginManager().callEvent(event);
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

    public List<UUID> getKitsList(){
        ArrayList<UUID> kitsList = new ArrayList<>();
        kits.forEach((uuid1, customKit) -> kitsList.add(uuid1));
        return kitsList;
    }

    public void addTrail(UUID uuid){
        List<UUID> trails = this.getTrailsList();
        trails.add(uuid);
        this.setTrailsList(trails);
    }

    public void removeTrail(UUID uuid){
        List<UUID> trails = this.getTrailsList();
        trails.remove(uuid);
        this.setTrailsList(trails);
    }

    public void setTrailsList(List<UUID> trailsList) {
        this.trailsList = trailsList;
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }

    public void addSuffix(UUID uuid){
        List<UUID> suffixes = this.getSuffixesList();
        suffixes.add(uuid);
        this.setSuffixesList(suffixes);
    }

    public void removeSuffixes(UUID uuid){
        List<UUID> trails = this.getTrailsList();
        trails.remove(uuid);
        this.setTrailsList(trails);
    }

    public void setSuffixesList(List<UUID> suffixesList) {
        this.suffixesList = suffixesList;
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }

    public void addChatColor(UUID uuid){
        List<UUID> colors = this.getChatcolorsList();
        colors.add(uuid);
        this.setChatcolorsList(colors);
    }

    public void removeChatColor(UUID uuid){
        List<UUID> colors = this.getChatcolorsList();
        colors.remove(uuid);
        this.setChatcolorsList(colors);
    }

    public void setChatcolorsList(List<UUID> chatcolorsList) {
        this.chatcolorsList = chatcolorsList;
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }

    public void addNameColor(UUID uuid){
        List<UUID> colors = this.getNamecolorsList();
        colors.add(uuid);
        this.setNamecolorsList(colors);
    }

    public void removeNameColor(UUID uuid){
        List<UUID> colors = this.getNamecolorsList();
        colors.remove(uuid);
        this.setNamecolorsList(colors);
    }

    public void setNamecolorsList(List<UUID> namecolorsList) {
        this.namecolorsList = namecolorsList;
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }

    public void addKit(UUID uuid){
        Kit kit = Data.getKit(uuid);
        kits.put(uuid, new CustomKit(uuid, kit.getItems(), kit.getArmor()));
    }

    public void removeKit(UUID uuid){
        kits.remove(uuid);
    }

    public CustomKit getKit(UUID uuid){
        return kits.get(uuid);
    }

    public void updateKit(UUID uuid, PlayerInventory inventory){
        HashMap<Integer, KitItem> items = new HashMap<>();
        ItemStack[] rawArmor = inventory.getArmorContents();
        for (int i = 0; i < inventory.getContents().length; i++){
            items.put(i, new KitItem(inventory.getContents()[i]));
        }
        HashMap<ArmorSlot, KitItem> armor = new HashMap<>();
        armor.put(ArmorSlot.HELMET, new KitItem(rawArmor[3]));
        armor.put(ArmorSlot.CHESTPLATE, new KitItem(rawArmor[2]));
        armor.put(ArmorSlot.LEGGING, new KitItem(rawArmor[1]));
        armor.put(ArmorSlot.BOOTS, new KitItem(rawArmor[0]));
        CustomKit customKit = new CustomKit(uuid, items, armor);
        if (!kits.containsKey(uuid)){
           kits.put(uuid, customKit);
        } else {
            kits.replace(uuid, customKit);
        }
    }

    public void setTokens(Integer tokens) {
        this.tokens = tokens;
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }

    public OfflinePlayer getPlayer(){
        return Bukkit.getOfflinePlayer(uuid);
    }

    public String getStat(StatType statType){
        if(statType.equals(StatType.KILLS))
            return String.valueOf(kills);
        if(statType.equals(StatType.DEATHS))
            return String.valueOf(deaths);
        if(statType.equals(StatType.STREAK))
            return String.valueOf(bestkillstreak);
        if(statType.equals(StatType.KD))
            return String.valueOf(getKdRatio());
        if(statType.equals(StatType.COINS))
            return String.valueOf(coins);
        if(statType.equals(StatType.LEVEL))
            return String.valueOf(level);
        if(statType.equals(StatType.EXPERIENCE))
            return String.valueOf(experience);
        if(statType.equals(StatType.TOKENS))
            return String.valueOf(tokens);
        return "null";
    }

    public static String transformTime(Integer onlineTime){
        int day = (int) TimeUnit.SECONDS.toDays(onlineTime);
        long hours = TimeUnit.SECONDS.toHours(onlineTime) - (day *24);
        long minute = TimeUnit.SECONDS.toMinutes(onlineTime) - (TimeUnit.SECONDS.toHours(onlineTime)* 60);
        long second = TimeUnit.SECONDS.toSeconds(onlineTime) - (TimeUnit.SECONDS.toMinutes(onlineTime) *60);
        return day + "d, " + hours + "h, " + minute + "m, " + second + "s.";
    }

    private static void checkLevelup(UUID uuid, Integer level, Integer experience){
        Integer calc = 1+level;
        Integer calc2 = 25*level;
        Integer finalcalc = calc2*calc;
        if(experience >= finalcalc){
            User user = Data.getUser(Bukkit.getOfflinePlayer(uuid));
            user.setLevel(user.getLevel()+1);
            user.setCrate(user.getCrate()+1);
            Bukkit.getOnlinePlayers().forEach(target -> {
                if(target.getUniqueId().toString().equals(uuid.toString()))
                    target.sendMessage(color("&aCongrats! You are now level " + user.getLevel() + " and received a crate!"));
            });
        }
    }
}
