package net.miraclepvp.kitpvp.utils;

import com.google.common.reflect.Reflection;
import net.miraclepvp.kitpvp.bukkit.NMSNickManager;
import net.miraclepvp.kitpvp.bukkit.nms.NMS;
import net.miraclepvp.kitpvp.bukkit.nms.NMSUtilities;
import net.miraclepvp.kitpvp.bukkit.reflection.Reflections;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class HologramUtil {

    private Location location;
    private List<String> lines; // 0 = Title, 1 = First place etc..
    public List<Object> entities = new ArrayList<>();

    private double offset = 0.25D; //Normal = 0.23D

    public HologramUtil(Location location, String[] text) {
        this.location = location;
        this.lines = new ArrayList<>();

        for(String s : text){
            lines.add(color(s));
        }

        update();

        for (Player all : Bukkit.getOnlinePlayers()) displayTo(all);
    }

    public void setLines(String[] text) {
        for (Player all : Bukkit.getOnlinePlayers()) removeFrom(all);

        lines.clear();
        for (String s : text) lines.add(color(s));
        update();

        for (Player all : Bukkit.getOnlinePlayers()) displayTo(all);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
        update();
    }

    public void displayTo(Player... players){
        for(Player p : players) {
            removeFrom(p);
            try {
                for (Object obj : entities) {
                    Object entityObject = NMS.EntityArmorStand.cast(obj);
                    Object id = entityObject.getClass().getMethod("getId").invoke(entityObject);

                    Object[] packet = new Object[]{
                            NMS.PacketPlayOutSpawnEntityLiving.getConstructor(NMS.EntityLiving).newInstance(entityObject), id
                    };

                    NMSUtilities.sendPacket(p, packet[0]);
                }
            }catch (Exception x){
                x.printStackTrace();
            }
        }
    }

    public void removeFrom(Player... players){
        for(Player p : players){

            try {
                for (Object obj : entities) {
                    Object entityObject = NMS.EntityArmorStand.cast(obj);
                    Object id = entityObject.getClass().getMethod("getId").invoke(entityObject);

                    NMSUtilities.sendPacket(p, getRemovePacket((int)id));
                }
            }catch (Exception x){
                x.printStackTrace();
            }
        }
    }

    public void update() {
        Bukkit.getOnlinePlayers().forEach(pl -> removeFrom(pl));

        try {
            Location spawnLoc = this.location.clone();
            entities.clear();
            for (int i = 0; i < lines.size(); i++) {
                try {
                    String line = lines.get(i);
                    Object craftWorld = NMS.CraftWorld.cast(location.getWorld());
                    Object entityObject = NMS.EntityArmorStand.getConstructor(NMS.World).newInstance(NMS.CraftWorld.getMethod("getHandle").invoke(craftWorld));

                    configureHologram(entityObject, line, spawnLoc);

                    entities.add(entityObject);

                    spawnLoc.subtract(0, (i==0 ? 0.30 : offset), 0);
                } catch (Exception x) {
                    x.printStackTrace();
                }
            }
        } catch (Exception x) {
            x.printStackTrace();
        }

        Bukkit.getOnlinePlayers().forEach(pl -> removeFrom(pl));
    }

    private void configureHologram(Object entityObject, String text, Location location) throws Exception {
        // Methods for modifying the properties
        Method setCustomName = entityObject.getClass().getMethod("setCustomName", String.class);
        Method setCustomNameVisible = entityObject.getClass().getMethod("setCustomNameVisible", boolean.class);

        Method setNoGravity;
        try{
            setNoGravity = entityObject.getClass().getMethod("setNoGravity", boolean.class);
        }catch (NoSuchMethodException e){
            setNoGravity = entityObject.getClass().getMethod("setGravity", boolean.class);
        }

        Method setLocation = entityObject.getClass().getMethod("setLocation", double.class, double.class, double.class, float.class, float.class);
        Method setInvisible = entityObject.getClass().getMethod("setInvisible", boolean.class);

        // Setting the properties
        setCustomName.invoke(entityObject, text);
        setCustomNameVisible.invoke(entityObject, true);
        setNoGravity.invoke(entityObject, true);
        setLocation.invoke(entityObject, location.getX(), location.getY(), location.getZ(), 0.0F, 0.0F);
        setInvisible.invoke(entityObject, true);
    }

    private Object[] getCreatePacket(Location location, String text) {
        try {
            Object entityObject = NMS.EntityArmorStand.getConstructor(NMS.World).newInstance(NMS.CraftWorld.getMethod("getHandle").invoke(NMS.CraftWorld.cast(location.getWorld())));
            Object id = entityObject.getClass().getMethod("getId").invoke(entityObject);

            configureHologram(entityObject, text, location);

            return new Object[]{NMS.PacketPlayOutSpawnEntityLiving.getConstructor(NMS.EntityLiving).newInstance(entityObject), id};
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private Object getRemovePacket(int id) {
        try {
            Class<?> packet = NMS.PacketPlayOutEntityDestroy;
            return packet.getConstructor(int[].class).newInstance(new int[]{id});
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
