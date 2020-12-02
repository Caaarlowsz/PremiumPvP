package net.miraclepvp.kitpvp.commands.subcommands.supplydrop;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.miraclepvp.kitpvp.bukkit.FileManager;
import net.miraclepvp.kitpvp.bukkit.Text;
import net.miraclepvp.kitpvp.data.Config;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.zone.Zone;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.NoSuchElementException;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class LocationlistSupplydrop implements CommandExecutor {

    Integer pos = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;

        if(args.length != 2){
            sender.sendMessage(color("&cPlease use /supplydrop locationlist <zone>"));
            return true;
        }
        try {
            if (Data.getZone(args[1]) == null) {
                sender.sendMessage(Text.color("&cThere is no zone with this name."));
                return true;
            }

            player.sendMessage(color("&5&m-----------------------------------------------------"));
            player.sendMessage(color("&fSupplydrop"));
            player.sendMessage(color("&7A list of all supplydrops location in the given zone."));
            player.sendMessage(color("&7Click on a location to teleport."));
            player.sendMessage(color(""));
            Zone zone = Data.getZone(args[1]);
            zone.getSupplydropLocations().stream().forEach(spwn -> {
                pos++;
                Location spawnpoint = FileManager.deSerialize(spwn);
                IChatBaseComponent comp = IChatBaseComponent.ChatSerializer
                        .a(color("{text: \"&5" + pos + ". &f" + spawnpoint.getWorld().getName() + ", " + (Math.round(spawnpoint.getX() * 100.00) / 100.00) + ", " + (Math.round(spawnpoint.getY() * 100.00) / 100.00) + ", " + (Math.round(spawnpoint.getZ() * 100.00) / 100.00) + "\", clickEvent: {\"action\": \"run_command\" , value: \"/minecraft:tp " + player.getName() + " " + spawnpoint.getX() + " " + spawnpoint.getY() + " " + spawnpoint.getZ() + "\"}}"));
                PacketPlayOutChat ppoc = new PacketPlayOutChat(comp);
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(ppoc);
            });

            if (Config.getSpawnpoints().isEmpty())
                player.sendMessage(color("&cNo supplydrop locations added yet"));
            player.sendMessage(color("&5&m-----------------------------------------------------"));
            pos = 0;
            return true;
        }catch (NoSuchElementException ex){
            sender.sendMessage(Text.color("&cThere is no zone with this name."));
            return true;
        }
    }
}
