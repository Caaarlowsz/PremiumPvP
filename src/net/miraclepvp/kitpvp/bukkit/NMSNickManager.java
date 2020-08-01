package net.miraclepvp.kitpvp.bukkit;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import net.minecraft.server.v1_8_R3.PacketPlayOutEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.bukkit.reflection.Reflections;
import net.miraclepvp.kitpvp.objects.NickManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class NMSNickManager extends Reflections {

    public static void updatePlayerListName(Player p, String name) {
        try {
            Object entityPlayer = p.getClass().getMethod("getHandle").invoke(p);
            Class<?> craftChatMessage = getCraftBukkitClass("util.CraftChatMessage");
            String playerName = (String) entityPlayer.getClass().getMethod("getName").invoke(entityPlayer);

            if (name == null)
                name = playerName;

            Field f = getMinecraftClass("EntityPlayer").getDeclaredField("listName");
            f.setAccessible(true);
            f.set(entityPlayer, name.equals(playerName) ? null : ((Object[]) craftChatMessage.getMethod("fromString", String.class).invoke(craftChatMessage, name))[0]);
            f.setAccessible(false);

            Object entityPlayerArray = Array.newInstance(entityPlayer.getClass(), 1);
            Array.set(entityPlayerArray, 0, entityPlayer);

            Class<?> enumPlayerInfoAction = PacketPlayOutPlayerInfo.EnumPlayerInfoAction.class;
            Object packet = getMinecraftClass("PacketPlayOutPlayerInfo").getConstructor(enumPlayerInfoAction, entityPlayerArray.getClass()).newInstance(enumPlayerInfoAction.getDeclaredField("UPDATE_DISPLAY_NAME").get(enumPlayerInfoAction), entityPlayerArray);

            for (Player all : Bukkit.getOnlinePlayers()) {
                if (all.canSee(p))
                    sendPacket(packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateName(Player p, String nickName) {
        try {
            Object gameProfile = p.getClass().getMethod("getProfile").invoke(p);
            Field nameField = NickManager.field;
            nameField.setAccessible(true);
            nameField.set(gameProfile, nickName);
            nameField.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateSkin(Player p, String skinName) {
        try {
            Object gameProfile = p.getClass().getMethod("getProfile").invoke(p);
            GameProfile gp = (GameProfile) gameProfile;

            try {
                gp = GameProfileBuilder.fetch(UUIDFetcher.getUUID(skinName));
            } catch (Exception e) {
            }

            Collection<Property> props = gp.getProperties().get("textures");
            ((GameProfile) gameProfile).getProperties().removeAll("textures");
            ((GameProfile) gameProfile).getProperties().putAll("textures", props);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updatePlayer(Player p) {
        NickManager api = new NickManager(p);
        boolean onNick = !(api.isNicked());

        try {
            Object entityPlayer = p.getClass().getMethod("getHandle").invoke(p);
            Object worldClient = entityPlayer.getClass().getMethod("getWorld").invoke(entityPlayer);
            Object worldData = worldClient.getClass().getMethod("getWorldData").invoke(worldClient);
            Object interactManager = entityPlayer.getClass().getField("playerInteractManager").get(entityPlayer);
            Object entityPlayerArray = Array.newInstance(entityPlayer.getClass(), 1);
            Array.set(entityPlayerArray, 0, entityPlayer);

            Object packetEntityDestroy = getMinecraftClass("PacketPlayOutEntityDestroy").getConstructor(int[].class).newInstance(new int[]{p.getEntityId()});
            Object packetPlayOutPlayerInfoRemove;
            Object packetPlayOutPlayerInfoAdd;

            Class<?> enumPlayerInfoAction = PacketPlayOutPlayerInfo.EnumPlayerInfoAction.class;

            packetPlayOutPlayerInfoRemove = getMinecraftClass("PacketPlayOutPlayerInfo").getConstructor(enumPlayerInfoAction, entityPlayerArray.getClass()).newInstance(enumPlayerInfoAction.getDeclaredField("REMOVE_PLAYER").get(enumPlayerInfoAction), entityPlayerArray);
            packetPlayOutPlayerInfoAdd = getMinecraftClass("PacketPlayOutPlayerInfo").getConstructor(enumPlayerInfoAction, entityPlayerArray.getClass()).newInstance(enumPlayerInfoAction.getDeclaredField("ADD_PLAYER").get(enumPlayerInfoAction), entityPlayerArray);

            Object packetNamedEntitySpawn = getMinecraftClass("PacketPlayOutNamedEntitySpawn").getConstructor(getMinecraftClass("EntityHuman")).newInstance(entityPlayer);

            sendPacket(packetEntityDestroy);
            sendPacket(packetPlayOutPlayerInfoRemove);

            Class<?> enumGameMode = getMinecraftClass("WorldSettings").getDeclaredClasses()[0];

            Object packetRespawnPlayer = packetRespawnPlayer = getMinecraftClass("PacketPlayOutRespawn").getConstructor(int.class, getMinecraftClass("EnumDifficulty"), getMinecraftClass("WorldType"), enumGameMode).newInstance(p.getWorld().getEnvironment().getId(), worldClient.getClass().getMethod("getDifficulty").invoke(worldClient), worldData.getClass().getMethod("getType").invoke(worldData), interactManager.getClass().getMethod("getGameMode").invoke(interactManager));

            sendPacketNMS(p, packetRespawnPlayer);

            p.teleport(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch()));
            p.updateInventory();

            Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {

                @Override
                public void run() {
                    sendPacket(packetPlayOutPlayerInfoAdd);
                    sendPacketExceptSelf(p, packetNamedEntitySpawn);

                    try {
                        Object packetEntityLook = PacketPlayOutEntity.PacketPlayOutEntityLook.class;
                        Object packetHeadRotation = getMinecraftClass("PacketPlayOutEntityHeadRotation").newInstance();
                        setField(packetHeadRotation, "a", p.getEntityId());
                        setField(packetHeadRotation, "b", (byte) ((int) (p.getLocation().getYaw() * 256.0F / 360.0F)));

                        sendPacketExceptSelf(p, packetEntityLook);
                        sendPacketExceptSelf(p, packetHeadRotation);
                    } catch (IllegalArgumentException | IllegalAccessException | InstantiationException | SecurityException e) {
                        e.printStackTrace();
                    }
                }
            }, 5);

            updatePlayerCache(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updatePlayerCache(Player p) {
        try {
            Class<?> minecraftServer = getMinecraftClass("MinecraftServer");
            Object server = minecraftServer.getMethod("getServer").invoke(minecraftServer);
            Object playerList = server.getClass().getMethod("getPlayerList").invoke(server);
            Field f = getMinecraftClass("PlayerList").getDeclaredField("playersByName");

            f.setAccessible(true);

            Map<String, Object> map = (Map<String, Object>) f.get(playerList);
            ArrayList<String> toRemove = new ArrayList<>();

            for (String cachedName : map.keySet()) {
                if (cachedName != null) {
                    Object entityPlayer = map.get(cachedName);

                    if ((entityPlayer == null) || entityPlayer.getClass().getMethod("getUniqueID").invoke(entityPlayer).equals(p.getUniqueId()))
                        toRemove.add(cachedName);
                }
            }

            for (String string : toRemove)
                map.remove(string);

            map.put(p.getName(), p.getClass().getMethod("getHandle").invoke(p));

            f.set(playerList, map);
            f.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendPacket( Object packet) {
        for (Player all : Bukkit.getOnlinePlayers())
            sendPacketNMS(all, packet);
    }

    public static void sendPacketExceptSelf(Player p, Object packet) {
        for (Player all : Bukkit.getOnlinePlayers())
            if (!(all.getUniqueId().equals(p.getUniqueId())))
                sendPacketNMS(all, packet);
    }

    public static void sendPacketNMS(Player p, Object packet) {
        try {
            Object handle = p.getClass().getMethod("getHandle").invoke(p);
            Object playerConnection = handle.getClass().getDeclaredField("playerConnection").get(handle);

            try {
                playerConnection.getClass().getMethod("sendPacket", getMinecraftClass("Packet")).invoke(playerConnection, packet);
            }catch(IllegalArgumentException ex){
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}