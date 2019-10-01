package me.sahustei.miraclepvp.listeners;

import me.sahustei.miraclepvp.data.Data;
import me.sahustei.miraclepvp.data.Top;
import me.sahustei.miraclepvp.data.sign.Sign;
import me.sahustei.miraclepvp.data.user.StatType;
import me.sahustei.miraclepvp.data.user.User;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static me.sahustei.miraclepvp.bukkit.Text.color;

public class SignListener implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event){
        if(event.getLine(0).equalsIgnoreCase("[MiraclePvP]")){
            try{
                StatType statType = StatType.valueOf(event.getLine(1).toUpperCase());
                try {
                    Integer position = Integer.parseInt(event.getLine(2));
                    if(position > 0 && position <= 100){
                        org.bukkit.material.Sign s = (org.bukkit.material.Sign) event.getBlock().getState().getData();
                        BlockFace directionFacing = s.getFacing();
                        event.setLine(0, color("&0&l»&5 Stats &0&l«"));
                        Sign sign = new Sign(statType, position, event.getBlock().getLocation(), directionFacing);
                        sign.setUser(Data.getUser(event.getPlayer()));
                        Data.signs.add(sign);

                        sign.update();
                        event.getPlayer().sendMessage(color("&aYou added a leaderboard sign."));
                        return;
                    }
                    event.getPlayer().sendMessage(color("&cThe given position could not load. Choose a number between 1 and 100"));
                } catch(NoSuchElementException ex){
                    event.getPlayer().sendMessage(color("&cThe given position is not a number."));
                    ex.printStackTrace();
                }
            } catch (NoSuchElementException ex){
                event.getPlayer().sendMessage(color("&cThe given stattype does not exist. Try TOKENS, KILLS, DEATHS, COINS, KD, STREAK, LEVEL, EXPERIENCE."));
            }
            event.setLine(0, color("&5Stats"));
            event.setLine(1, color("&4ERROR"));
            event.setLine(2, "");
            event.setLine(3, "");
        }
    }

    @EventHandler
    public void signRemoved(BlockBreakEvent event) {
        Location blockLocation = event.getBlock().getLocation();
        World w = blockLocation.getWorld();
        Block b = w.getBlockAt(blockLocation);
        if(b.getType() == Material.WALL_SIGN || b.getType() == Material.SIGN_POST){
            if(Data.getSign(b.getLocation()) != null){
                if(!(event.getPlayer().hasPermission("miraclepvp.sign.break"))){
                    event.getPlayer().sendMessage(color("&cYou are not allowed to break leaderboard signs."));
                    event.setCancelled(true);
                    return;
                }
                event.getPlayer().sendMessage(color("&aYou removed a leaderboard sign."));
                Data.signs.remove(Data.getSign(b.getLocation()));
            }
        }
    }
}
