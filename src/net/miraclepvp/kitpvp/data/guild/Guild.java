package net.miraclepvp.kitpvp.data.guild;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.objects.Chatmode;
import net.miraclepvp.kitpvp.objects.PermissionType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

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
            created;
    private Integer
            experience,
            level;
    private Boolean
            slow = false;
    private ArrayList<UUID>
            members,
            officers,
            invites;
    private ArrayList<PermissionType>
            memberPerms,
            officerPerms;

    public Guild(String name, UUID creator) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        this.uuid = UUID.randomUUID();
        this.creator = creator;
        this.master = creator;
        this.name = name;
        this.motd = "Set the motd using /guild motd <motd>";
        this.discord = "Set the discord using /guild discord <discord>";
        this.created = formatter.format(date);
        this.experience = Integer.valueOf(0);
        this.level = Integer.valueOf(1);
        this.slow = false;
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
        this.experience = experience;
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
