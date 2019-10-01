package me.sahustei.miraclepvp.data.sign;

import me.sahustei.miraclepvp.bukkit.FileManager;
import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.Top;
import me.sahustei.miraclepvp.data.user.StatType;
import me.sahustei.miraclepvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;

import java.util.UUID;

import static me.sahustei.miraclepvp.bukkit.Text.color;

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
            sign.setLine(1, color("&5loading..."));
            sign.setLine(2, "");
            sign.setLine(3, "");
            sign.update();
            return;
        }
        User user = Top.getTop(getStats(), getPosition());
        sign.setLine(1, color("&5" + user.getStat(getStats()) + " " + getStats().getName()));
        sign.setLine(2, color("&5" + user.getPlayer().getName()));
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
            skull.setOwner(user.getPlayer().getName());
            skull.update();
        }
        if (h2.getType() == Material.SKULL) {
            Skull skull = (Skull) h2.getState();
            skull.setSkullType(SkullType.PLAYER);
            skull.setOwner(user.getPlayer().getName());
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
