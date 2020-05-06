package net.miraclepvp.kitpvp.commands;

import net.miraclepvp.kitpvp.objects.npc.NPCManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NPCCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("create")) {
                    String npcName = args[1];
                    NPCManager.createNPC(player, npcName);
                }
            }
        }
        return true;
    }
}
