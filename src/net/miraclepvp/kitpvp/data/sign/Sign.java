package net.miraclepvp.kitpvp.data.sign;

import net.miraclepvp.kitpvp.bukkit.FileManager;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.Top;
import net.miraclepvp.kitpvp.data.user.StatType;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.bukkit.Text;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;

import java.util.UUID;

public class Sign {

    private UUID uuid, user;
    private String stats, blockFace, location;
    private Integer position;

    public Sign(StatType stats, Integer position, Location location, BlockFace blockFace) {
        this.uuid = UUID.randomUUID();
        this.user = null;
        this.stats = stats.toString();
        this.position = position;
        this.location = FileManager.serialize(location);
        this.blockFace = blockFace.name();
    }

    public void update() {
        Block b = getLocation().getBlock();
        org.bukkit.block.Sign sign = (org.bukkit.block.Sign) getLocation().getBlock().getState();
        try {
            Top.getTop(getStats(), getPosition());
        }catch(IndexOutOfBoundsException ex){
            sign.setLine(1, Text.color("&5loading..."));
            sign.setLine(2, "");
            sign.setLine(3, "");
            sign.update();
            return;
        }
        User user = Top.getTop(getStats(), getPosition());
        Boolean compact = false;
        if(getStats().equals(StatType.KILLS) || getStats().equals(StatType.DEATHS))
            compact = true;
        sign.setLine(1, Text.color("&5" + user.getStat(getStats(), compact) + " " + getStats().getName()));
        sign.setLine(2, Text.color("&5" +  Bukkit.getOfflinePlayer(user.getUuid()).getName()));
        sign.setLine(3, "");
        sign.update();
        setUser(user);

        Block h1 = b.getRelative(BlockFace.UP, 1);
        Block h2 = b.getRelative(BlockFace.UP, 1);
        if (getBlockFace().equalsIgnoreCase("east"))
            h2 = b.getRelative(-1, 1, 0);
        if (getBlockFace().equalsIgnoreCase("west"))
            h2 = b.getRelative(1, 1, 0);
        if (getBlockFace().equalsIgnoreCase("south"))
            h2 = b.getRelative(0, 1, -1);
        if (getBlockFace().equalsIgnoreCase("north"))
            h2 = b.getRelative(0, 1, 1);
        if (h1.getType() == Material.SKULL) {
            Skull skull = (Skull) h1.getState();
            skull.setSkullType(SkullType.PLAYER);
            skull.setOwner(Bukkit.getOfflinePlayer(user.getUuid()).getName());
            skull.update();
        }
        if (h2.getType() == Material.SKULL) {
            Skull skull = (Skull) h2.getState();
            skull.setSkullType(SkullType.PLAYER);
            skull.setOwner(Bukkit.getOfflinePlayer(user.getUuid()).getName());
            skull.update();
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public StatType getStats() {
        return StatType.valueOf(stats);
    }

    public Integer getPosition() {
        return position;
    }

    public void setUser(User user) {
        this.user = user.getUuid();
    }

    public User getUser() {
        return Data.getUser(Bukkit.getOfflinePlayer(user));
    }

    public Location getLocation() {
        return FileManager.deSerialize(location);
    }

    public String getBlockFace() {
        return blockFace;
    }
}
