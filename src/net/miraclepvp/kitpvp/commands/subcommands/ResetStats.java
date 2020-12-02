package net.miraclepvp.kitpvp.commands.subcommands;

import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.objects.Crate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class ResetStats implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof ConsoleCommandSender)){
            sender.sendMessage(color("&cThis command can only be executed thru the console."));
            return true;
        }
        for(User user : Data.users){
            user.setPreviousKit(null);
            user.setActiveSuffix(null);
            user.setActiveTrail(null);
            user.setActiveChatcolor(null);
            user.setActiveNamecolor(null);
            user.setKills(0);
            user.setAssists(0);
            user.setDeaths(0);
            user.setCoins(0, false);
            user.setLevel(1);
            user.getCrates().put(Crate.GEAR, 0);
            user.getCrates().put(Crate.GEARMIRACLE, 0);
            user.setExperience(0, false);
            user.setKillstreak(0);
            user.setBestkillstreak(0);
            user.setBounty(0);
            user.setBanknoteValue(0);
            user.getActiveAbilities().clear();
        }
        return true;
    }
}
