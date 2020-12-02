package net.miraclepvp.kitpvp.data.guild;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.Booster;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.objects.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class Guild {

    private UUID
            uuid,
            creator,
            master;
    private String
            name,
            motd,
            discord,
            created,
            tag;
    private Integer
            experience,
            level;
    private Boolean
            slow = false,
            friendlyfire = true;
    private ArrayList<UUID>
            members,
            officers,
            invites;
    private ArrayList<PermissionType>
            memberPerms,
            officerPerms;
    private ArrayList<Character> chatcolors;
    private Character activeColor;

    public Guild(String name, UUID creator) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        this.uuid = UUID.randomUUID();
        this.chatcolors = new ArrayList<>();
        chatcolors.add(ChatColor.DARK_GRAY.getChar());
        this.activeColor = ChatColor.DARK_GRAY.getChar();
        this.creator = creator;
        this.master = creator;
        this.name = name;
        this.tag = name.substring(0, 4).toUpperCase();
        this.motd = "Set the motd using /guild motd <motd>";
        this.discord = "Set the discord using /guild discord <discord>";
        this.created = formatter.format(date);
        this.experience = Integer.valueOf(0);
        this.level = Integer.valueOf(1);
        this.slow = false;
        this.friendlyfire = true;
        this.members = new ArrayList<>();
        this.officers = new ArrayList<>();
        this.memberPerms = new ArrayList<>();
        this.memberPerms.add(PermissionType.CHAT);
        this.officerPerms = new ArrayList<>();
        this.officerPerms.add(PermissionType.INVITE);
        this.officerPerms.add(PermissionType.CHAT);
        this.officerPerms.add(PermissionType.SLOW);
        this.officerPerms.add(PermissionType.KICK);
        this.invites = new ArrayList<>();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getCreated() {
        return created;
    }

    public void setCreator(UUID creator) {
        this.creator = creator;
    }

    public UUID getCreator() {
        return creator;
    }

    public void setMaster(UUID master) {
        this.master = master;
    }

    public UUID getMaster() {
        return master;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTag(String tag) {
        this.tag = tag;
        for(UUID pID : getPlayers()){
            if(Bukkit.getOfflinePlayer(pID).isOnline())
                Board.updatePlayerListName(Bukkit.getPlayer(pID));
        }
    }

    public String getTag() {
        if(tag == null) tag= name.substring(0, 4).toUpperCase();
        return tag;
    }

    public void setMotd(String motd) {
        this.motd = motd;
    }

    public String getMotd() {
        return motd;
    }

    public void setDiscord(String discord) {
        this.discord = discord;
    }

    public String getDiscord() {
        return discord;
    }

    public void setExperience(Integer experience) {
        Integer old = this.experience;
        Float added = Float.valueOf(experience - old);
        Integer globalExtra = 0;
        AtomicReference<Integer> personalExtra = new AtomicReference<>(0);
        Integer total = Math.round(old + added);
        total = (globalExtra + personalExtra.get()) + total;
        this.experience = total;
        checkLevelup(uuid, experience);
    }

    private static void checkLevelup(UUID uuid, Integer experience) {
        Guild guild = Data.getGuild(uuid);
        if (experience >= guild.getExperienceNeeded()) {
            guild.setLevel(guild.getLevel()+1);
            Integer level = guild.getLevel();
            String unlock = "";
            if(level==3){
                unlock="a gray tag color";
                guild.getTagcolors().add(ChatColor.GRAY.getChar());
            }
            else if(level==6)unlock="7 slots";
            else if(level==9){
                unlock="a white tag color";
                guild.getTagcolors().add(ChatColor.WHITE.getChar());
            }
            else if(level==12)unlock="9 slots";
            else if(level==15){
                unlock="a lime tag color";
                guild.getTagcolors().add(ChatColor.GREEN.getChar());
            }
            else if(level==18)unlock="11 slots";
            else if(level==21){
                unlock="a italic tag color";
                guild.getTagcolors().add(ChatColor.ITALIC.getChar());
            }
            else if(level==24)unlock="13 slots";
            else if(level==27){
                unlock="a bold tag color";
                guild.getTagcolors().add(ChatColor.BOLD.getChar());
            }
            else if(level==30)unlock="15 slots";
            else if(level==33){
                unlock="a dark green tag color";
                guild.getTagcolors().add(ChatColor.GREEN.getChar());
            }
            else if(level==36)unlock="17 slots";
            else if(level==39){
                unlock="a yellow tag color";
                guild.getTagcolors().add(ChatColor.YELLOW.getChar());
            }
            else if(level==42)unlock="19 slots";
            else if(level==45){
                unlock="a gold tag color";
                guild.getTagcolors().add(ChatColor.GOLD.getChar());
            }
            else if(level==48)unlock="21 slots";
            else if(level==51){
                unlock="a light red tag color";
                guild.getTagcolors().add(ChatColor.RED.getChar());
            }
            else if(level==54)unlock="23 slots";
            else if(level==57){
                unlock="a dark red tag color";
                guild.getTagcolors().add(ChatColor.DARK_RED.getChar());
            }
            else if(level==60)unlock="25 slots";
            guild.sendBroadcast("The Guild has reached level " + guild.getLevel() + (unlock.length()==0?"":" and unlocked "+unlock) + ".");
        }
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

    public int getExperienceNeeded(){
        return getExperienceNeeded(level);
    }

    public int getExperienceNeeded(Integer lvl){
        Integer calc = 75 * lvl;
        Integer calc2 = 15 + lvl;
        return calc * calc2;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getLevel() {
        return level;
    }

    public Boolean getSlow() {
        return slow;
    }

    public void setSlow(Boolean slow) {
        this.slow = slow;
    }

    public Boolean getFriendlyfire() {
        if(friendlyfire == null)
            friendlyfire = true;
        return friendlyfire;
    }

    public void setFriendlyfire(Boolean friendlyfire) {
        this.friendlyfire = friendlyfire;
    }

    public ArrayList<UUID> getMembers() {
        return members;
    }

    public ArrayList<UUID> getOfficers() {
        return officers;
    }

    public ArrayList<UUID> getInvites() {
        return invites;
    }

    public ArrayList<PermissionType> getMemberPerms() {
        return memberPerms;
    }

    public ArrayList<PermissionType> getOfficerPerms() {
        return officerPerms;
    }

    public ArrayList<Character> getTagcolors() {
        if(chatcolors == null){
            chatcolors = new ArrayList<>();
            chatcolors.add(ChatColor.DARK_GRAY.getChar());
        }
        return chatcolors;
    }

    public Character getActiveColor() {
        if(activeColor==null) activeColor = ChatColor.DARK_GRAY.getChar();
        return activeColor;
    }

    public void setActiveColor(Character activeColor) {
        this.activeColor = activeColor;
        for(UUID pID : getPlayers()){
            if(Bukkit.getOfflinePlayer(pID).isOnline())
                Board.updatePlayerListName(Bukkit.getPlayer(pID));
        }
    }

    public int getMaxPlayers(){
        if(level<6) return 5;
        else if(level>=60) return 25;
        else if(level>=54) return 23;
        else if(level>=48) return 21;
        else if(level>=42) return 19;
        else if(level>=36) return 17;
        else if(level>=30) return 15;
        else if(level>=24) return 13;
        else if(level>=18) return 11;
        else if(level>=12) return 9;
        else return 7;
    }

    public ArrayList<UUID> getPlayers() {
        ArrayList<UUID> players = new ArrayList<>();
        members.forEach(member -> players.add(member));
        officers.forEach(officer -> players.add(officer));
        players.add(master);
        return players;
    }

    public void sendBroadcast(String message) {
        getPlayers().forEach(uuid -> {
            if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
                Player target = Bukkit.getPlayer(uuid);
                target.sendMessage(color("&5&m-----------------------------------------------------"));
                target.sendMessage(color("&7" + message));
                target.sendMessage(color("&5&m-----------------------------------------------------"));
            }
        });
    }

    public void sendMessage(OfflinePlayer sender, String message) {
        getPlayers().forEach(uuid -> {
            if (!Data.getUser(Bukkit.getOfflinePlayer(uuid)).getEveryoneMuted())
                if(!Data.getUser(Bukkit.getOfflinePlayer(uuid)).getHiddenChats().contains(Chatmode.GUILD)) {
                    if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
                        Player target = Bukkit.getPlayer(uuid);
                        target.sendMessage(color("&f[&5Guild&f] &f" + sender.getName() + (Bukkit.getOfflinePlayer(master).getName().equals(sender.getName()) ? " &7[GM]" : "") + "&8: &7" + message));
                    }
                }
        });
    }
}
