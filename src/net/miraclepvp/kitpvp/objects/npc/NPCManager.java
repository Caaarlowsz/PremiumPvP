package net.miraclepvp.kitpvp.objects.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;
import net.minecraft.server.v1_8_R3.*;
import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.bukkit.ItemstackFactory;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static net.miraclepvp.kitpvp.bukkit.NMSNickManager.sendPacketNMS;
import static net.miraclepvp.kitpvp.bukkit.reflection.Reflections.getMinecraftClass;
import static net.miraclepvp.kitpvp.bukkit.reflection.Reflections.setField;

public class NPCManager {

    public static List<EntityPlayer> npcs = new ArrayList<>();

    public static void createNPC(Player player, String npcName) {
        Location location = player.getLocation();
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) player.getWorld()).getHandle();

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "§5" + npcName);

        EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld));
        Player npcPlayer = npc.getBukkitEntity().getPlayer();
        npcPlayer.setPlayerListName("");

        npc.setLocation(location.getX(), location.getY(), location.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());

        npcs.add(npc);

        for(Player all : Bukkit.getOnlinePlayers())
            showNPC(all, npc);
    }

    public static void sendPacket(Object packet) {
        for (Player all : Bukkit.getOnlinePlayers())
            sendPacketNMS(all, packet);
    }


    public static boolean setSkin(GameProfile profile, UUID uuid) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(String.format("https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false", UUIDTypeAdapter.fromUUID(uuid))).openConnection();
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                String reply = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                String skin = reply.split("\"value\":\"")[1].split("\"")[0];
                String signature = reply.split("\"signature\":\"")[1].split("\"")[0];
                profile.getProperties().put("textures", new Property("textures", skin, signature));
                return true;
            } else {
                System.out.println("Connection could not be opened (Response code " + connection.getResponseCode() + ", " + connection.getResponseMessage() + ")");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void showNPC(Player player, UUID id){
        EntityPlayer entityPlayer = npcs.stream().filter(entity -> entity.getProfile().getId().equals(id)).findFirst().get();
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityPlayer));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(entityPlayer));
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer));

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                try {
                    Object packetEntityLook = PacketPlayOutEntity.PacketPlayOutEntityLook.class;
                    Object packetHeadRotation = getMinecraftClass("PacketPlayOutEntityHeadRotation").newInstance();
                    setField(packetHeadRotation, "a", entityPlayer.getId());
                    setField(packetHeadRotation, "b", (byte) ((int) (entityPlayer.getBukkitEntity().getLocation().getYaw() * 256.0F / 360.0F)));

                    sendPacket(packetEntityLook);
                    sendPacket(packetHeadRotation);
                } catch (IllegalArgumentException | IllegalAccessException | InstantiationException | SecurityException e) {
                    e.printStackTrace();
                }
            }
        }, 5);
    }

    public static void showNPC(Player player, EntityPlayer entityPlayer){
        showNPC(player, entityPlayer.getProfile().getId());
    }
}
