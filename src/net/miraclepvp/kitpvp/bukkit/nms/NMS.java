package net.miraclepvp.kitpvp.bukkit.nms;

import org.bukkit.Bukkit;

public class NMS {

    public static String getVersion(){
        return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    public static Class<?> ChatSerializer;
    public static Class<?> ChatComponent;

    public static Class<?> CraftItemStack;
    public static Class<?> CraftWorld;

    public static Class<?> World;

    public static Class<?> Entity;
    public static Class<?> EntityArmorStand;
    public static Class<?> EntityLiving;

    public static Class<?> Packet;
    public static Class<?> PacketPlayOutSpawnEntityLiving;
    public static Class<?> PacketPlayOutEntityDestroy;
    public static Class<?> PacketPlayOutTitle;
    public static Class<?> PacketPlayOutChat;

    public static void init() throws ClassNotFoundException {

        if(getVersion().equalsIgnoreCase("v1_8_R1")){
            ChatSerializer = Class.forName("net.minecraft.server." + getVersion() + ".ChatSerializer");
        }else{
            ChatSerializer = Class.forName("net.minecraft.server." + getVersion() + ".IChatBaseComponent$ChatSerializer");
        }

        ChatComponent = Class.forName("net.minecraft.server." + getVersion() + ".IChatBaseComponent");

        CraftItemStack = Class.forName("org.bukkit.craftbukkit." + getVersion() + ".inventory.CraftItemStack");
        CraftWorld = Class.forName("org.bukkit.craftbukkit." + getVersion() + ".CraftWorld");

        Entity = Class.forName("net.minecraft.server." + getVersion() + ".Entity");
        Entity = Class.forName("net.minecraft.server." + getVersion() + ".Entity");
        World = Class.forName("net.minecraft.server." + getVersion() + ".World");

        EntityArmorStand = Class.forName("net.minecraft.server." + getVersion() + ".EntityArmorStand");
        EntityLiving = Class.forName("net.minecraft.server." + getVersion() + ".EntityLiving");

        Packet = Class.forName("net.minecraft.server." + getVersion() + ".Packet");
        PacketPlayOutSpawnEntityLiving = Class.forName("net.minecraft.server." + getVersion() + ".PacketPlayOutSpawnEntityLiving");
        PacketPlayOutEntityDestroy = Class.forName("net.minecraft.server." + getVersion() + ".PacketPlayOutEntityDestroy");
        PacketPlayOutTitle = Class.forName("net.minecraft.server." + getVersion() + ".PacketPlayOutTitle");
        PacketPlayOutChat = Class.forName("net.minecraft.server." + getVersion() + ".PacketPlayOutChat");
    }
}
