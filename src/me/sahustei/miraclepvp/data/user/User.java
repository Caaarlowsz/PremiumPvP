package me.sahustei.miraclepvp.data.user;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.sahustei.miraclepvp.Main;
import me.sahustei.miraclepvp.data.chatcolor.Chatcolor;
import me.sahustei.miraclepvp.data.namecolor.Namecolor;
import me.sahustei.miraclepvp.data.prefix.Prefix;
import me.sahustei.miraclepvp.data.suffix.Suffix;
import me.sahustei.miraclepvp.data.trail.Trail;
import me.sahustei.miraclepvp.listeners.custom.PlayerStatusChangeEvent;
import me.sahustei.miraclepvp.objects.CosmeticType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class User {

    public UUID uuid,
            prefix,
            activeSuffix,
            activeTrail,
            activeChatcolor,
            activeNamecolor,
            previousKit;
    public String lastVersion, firstJoin, lastJoin, lastCosmeticType;
    public Integer tokens, onlineTime, kills, deaths, coins, level, experience, killstreak, bestkillstreak;
    public List<UUID> trailsList,
            suffixesList,
            chatcolorsList,
            namecolorsList,
            kitsList;
    public Boolean cosmeticWasShop, autoDeploy, quickSelect;

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
        this.kitsList = new ArrayList<>();
        this.firstJoin = formatter.format(date);
        this.lastJoin = formatter.format(date);
        this.onlineTime = Integer.valueOf(0);
        this.kills = Integer.valueOf(0);
        this.deaths = Integer.valueOf(0);
        this.coins = Integer.valueOf(0);
        this.level = Integer.valueOf(0);
        this.experience = Integer.valueOf(0);
        this.killstreak = Integer.valueOf(0);
        this.bestkillstreak = Integer.valueOf(0);
        this.cosmeticWasShop = false;
        this.quickSelect = true;
        this.autoDeploy = false;
        this.lastCosmeticType = null;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getLastVersion() {
        return lastVersion;
    }

    public void setLastVersion(String lastVersion) {
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
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

    public void setQuickSelect(Boolean quickSelect) {
        this.quickSelect = quickSelect;
    }

    public void setAutoDeploy(Boolean autoDeploy) {
        this.autoDeploy = autoDeploy;
    }

    public Integer getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Integer onlineTime) {
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        this.onlineTime = onlineTime;
    }

    public String getFirstJoin() {
        return firstJoin;
    }

    public String getLastJoin() {
        return lastJoin;
    }

    public void setLastJoin(String lastJoin) {
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        this.lastJoin = lastJoin;
    }

    public void setCoins(Integer coins) {
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        this.coins = coins;
    }

    public Integer getCoins() {
        return coins;
    }

    public void setBestkillstreak(Integer bestkillstreak) {
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        this.bestkillstreak = bestkillstreak;
    }

    public Integer getBestkillstreak() {
        return bestkillstreak;
    }

    public void setDeaths(Integer deaths) {
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        this.deaths = deaths;
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
    }

    public void setExperience(Integer experience) {
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        this.experience = experience;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setKills(Integer kills) {
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        this.kills = kills;
    }

    public Integer getKills() {
        return kills;
    }

    public void setKillstreak(Integer killstreak) {
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        this.killstreak = killstreak;
    }

    public Integer getKillstreak() {
        return killstreak;
    }

    public void setLevel(Integer level) {
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        this.level = level;
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
            if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
                PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
                Bukkit.getServer().getPluginManager().callEvent(event);
            }
            this.activeSuffix = activeSuffix.getUuid();
        }
    }

    public UUID getActiveTrail() {
        return activeTrail;
    }

    public void setActiveTrail(Trail activeTrail) {
        if(activeTrail == null){
            this.activeTrail = null;
        } else {
            if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
                PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
                Bukkit.getServer().getPluginManager().callEvent(event);
            }
            this.activeTrail = activeTrail.getUuid();
        }
    }

    public Boolean getCosmeticWasShop() {
        return cosmeticWasShop;
    }

    public void setCosmeticWasShop(Boolean cosmeticWasShop) {
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        this.cosmeticWasShop = cosmeticWasShop;
    }

    public String getLastCosmeticType() {
        if(lastCosmeticType == null) return "Suffix";
        return lastCosmeticType;
    }

    public void setLastCosmeticType(CosmeticType lastCosmeticType) {
        if(lastCosmeticType == null){
            this.lastCosmeticType = null;
        } else {
            if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
                PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
                Bukkit.getServer().getPluginManager().callEvent(event);
            }
            this.lastCosmeticType = lastCosmeticType.getName();
        }
    }

    public UUID getActiveChatcolor() {
        return activeChatcolor;
    }

    public void setActiveChatcolor(Chatcolor activeChatcolor) {
        if(activeChatcolor == null){
            this.activeChatcolor = null;
        } else {
            if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
                PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
                Bukkit.getServer().getPluginManager().callEvent(event);
            }
            this.activeChatcolor = activeChatcolor.getUuid();
        }
    }

    public UUID getActiveNamecolor() {
        return activeNamecolor;
    }

    public void setActiveNamecolor(Namecolor activeNamecolor) {
        if(activeNamecolor == null){
            this.activeNamecolor = null;
        } else {
            if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
                PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
                Bukkit.getServer().getPluginManager().callEvent(event);
            }
            this.activeNamecolor = activeNamecolor.getUuid();
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
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        this.trailsList = trailsList;
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
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        this.suffixesList = suffixesList;
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
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        this.chatcolorsList = chatcolorsList;
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
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        this.namecolorsList = namecolorsList;
    }

    public void addKit(UUID uuid){
        List<UUID> kit = this.getKitsList();
        kit.add(uuid);
        this.setKitsList(kit);
    }

    public void removeKit(UUID uuid){
        List<UUID> kit = this.getKitsList();
        kit.remove(uuid);
        this.setKitsList(kit);
    }

    public void setKitsList(List<UUID> kitsList) {
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        this.kitsList = kitsList;
    }

    public void setTokens(Integer tokens) {
        if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))){
            PlayerStatusChangeEvent event = new PlayerStatusChangeEvent(Bukkit.getPlayer(uuid));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        this.tokens = tokens;
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
}
