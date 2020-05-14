package net.miraclepvp.kitpvp.listeners.player;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.miraclepvp.kitpvp.commands.FreezeCMD;
import net.miraclepvp.kitpvp.commands.SpawnCMD;
import net.miraclepvp.kitpvp.data.Config;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.duel.Duel;
import net.miraclepvp.kitpvp.data.zone.Zone;
import net.miraclepvp.kitpvp.inventories.FreezeGUI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import sun.applet.AppletEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.sk89q.worldguard.bukkit.BukkitUtil.toVector;
import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class playerMove implements Listener {

    private static List<ProtectedRegion> protectedRegionList = new ArrayList<>();
    public static HashMap<UUID, String> inZone = new HashMap<>();

    public static void setupRegionList(){
        WorldGuardPlugin guard = WorldGuardPlugin.inst();
        RegionManager manager = guard.getRegionManager(Bukkit.getWorlds().get(0));
        Data.zones.forEach(zone -> protectedRegionList.add(manager.getRegion(zone.getName())));
    }

    public String getInRegion(Player player){
        WorldGuardPlugin guard = WorldGuardPlugin.inst();
        Vector v = toVector(player.getLocation());
        RegionManager manager = guard.getRegionManager(player.getWorld());
        ApplicableRegionSet set = manager.getApplicableRegions(v);
        for (ProtectedRegion each : set)
            if (protectedRegionList.contains(each))
                return each.getId();
        return null;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        if(event.getFrom().getX() == event.getTo().getX() && event.getFrom().getZ() == event.getTo().getZ()) return;

        if(Duel.getDuel(event.getPlayer())!=null && (Duel.isIngame(event.getPlayer()) && Duel.getDuel(event.getPlayer()).status.equals(Duel.Status.PREPARING)))
            event.setCancelled(true); //Avoid people in duels in prepare from moving

        if(event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockZ() == event.getTo().getBlockZ()) return;

        if(Duel.isSpectator(event.getPlayer())){
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(Duel.getSpectating(event.getPlayer()));
            if(!offlinePlayer.isOnline()) return;
            Player target = offlinePlayer.getPlayer();

            Integer x = event.getFrom().getBlockX();
            Integer z = event.getFrom().getBlockZ();
            Integer difX = target.getLocation().getBlockX() - x;
            Integer difZ = target.getLocation().getBlockZ() - z;

            if (difX > 20 || difX < -20 || difZ > 20 || difZ < -20)
                event.getPlayer().teleport(target.getLocation());
        }

        if(FreezeCMD.frozenList.contains(event.getPlayer().getUniqueId())) {
            Integer x = event.getFrom().getBlockX();
            Integer z = event.getFrom().getBlockZ();
            Integer difX = Config.getLobbyLoc().getBlockX() - x;
            Integer difZ = Config.getLobbyLoc().getBlockZ() - z;
            if (difX > 1 || difX < -1 || difZ > 1 || difZ < -1) {
                event.getPlayer().teleport(Config.getLobbyLoc());
                event.getPlayer().openInventory(FreezeGUI.getInventory());
            }
        }
        if(event.getPlayer().hasMetadata("build") || event.getPlayer().hasMetadata("vanished")) return;
        if(SpawnCMD.teleporting.contains(event.getPlayer().getUniqueId())){
            if(event.getFrom().getBlockX() == event.getTo().getBlockX() &&
                    event.getFrom().getBlockY() == event.getTo().getBlockY() &&
                    event.getFrom().getBlockZ() == event.getTo().getBlockZ()) return;
            SpawnCMD.teleporting.remove(event.getPlayer().getUniqueId());
            event.getPlayer().sendMessage(color("&cTeleportation cancelled because you moved."));
        }
        if(!event.getPlayer().hasMetadata("kit") && !Duel.isSpectator(event.getPlayer()) && !event.getPlayer().hasMetadata("event")){
            Integer x = event.getFrom().getBlockX();
            Integer z = event.getFrom().getBlockZ();
            Integer difX = Config.getLobbyLoc().getBlockX()-x;
            Integer difZ = Config.getLobbyLoc().getBlockZ()-z;
            if(difX > 200 || difX < -200 || difZ > 200 || difZ < -200) {
                event.getPlayer().teleport(Config.getLobbyLoc());
                event.getPlayer().sendMessage(color("&cYou can't leave the spawn!"));
            }
            return;
        }
        try {
            if(getInRegion(event.getPlayer()) == null){
                inZone.remove(event.getPlayer());
                return;
            }
            String region = getInRegion(event.getPlayer());
            Player player = event.getPlayer();
            String zone = Data.getZone(region).getName();
            if (inZone.get(player.getUniqueId()) != null && inZone.get(player.getUniqueId()).equalsIgnoreCase(zone))
                return;
            inZone.put(player.getUniqueId(), zone);
        }catch (Exception ex){
            Bukkit.getLogger().warning("MoveEvent failed at " + getInRegion(event.getPlayer()) + " [X:" + event.getPlayer().getLocation().getBlockX() + " Y:" + event.getPlayer().getLocation().getBlockY() + " Z:" + event.getPlayer().getLocation().getBlockZ() + "] for player " + event.getPlayer().getName() + " [Errorcode: #5727]");
        }
    }
}
