package net.miraclepvp.kitpvp.listeners.player;

import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.listeners.custom.PlayerDeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class playerDeath implements Listener {


    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        //Get the player who dies
        Player player = event.getPlayer();
        User user = event.getUser();

        //Get the damage profile
        playerDamageRegister.DamageProfile profile = event.getProfile();

        Integer oldKillstreak = user.getKillstreak();

        //Manage the combattimer
        if(Main.getInstance().combatTimer.get(player) != null) {
            if (!(Main.getInstance().combatTimer.containsKey(player))) return;
            Main.getInstance().combatTimer.remove(player);
        }

        user.setDeaths(user.getDeaths()+1);
        user.setKillstreak(0);

        //Player got killed by a player
        if(profile.getLastDamager() != null){
            User damager = Data.getUser(Bukkit.getOfflinePlayer(profile.getLastDamager()));
            damager.setKills(damager.getKills()+1);
            damager.setKillstreak(damager.getKillstreak()+1);
        }

        //Everyone who damaged the dead player
        for(UUID uuid : profile.getGiven().keySet()){
            Player damager = Bukkit.getPlayer(uuid);
            User duser = Data.getUser(damager);

            double totalDamage = profile.getDamageTaken();
            double damageDealt = profile.getGiven().get(uuid);

            int percentage = (int) ((100/totalDamage)*damageDealt);
            int coins = (int) (((double)(int) 10/(double)100) * percentage);
            int experience = (int) (((double)(int) 10/(double)100) * percentage);
            int bounty = (int) (((double)(int) 0)/(double)100) * percentage; //TODO Get users bounty

            duser.setCoins(duser.getCoins()+coins);
            duser.setExperience(duser.getExperience()+experience);

            //Als het een assist is
            if(profile.getLastDamager() == null || !uuid.equals(profile.getLastDamager())) {
                duser.setAssists(duser.getAssists() + 1);
                damager.sendMessage(color("&aYou have a assist on " + player.getName() + " and received " + coins + " coins and " + experience + " experience."));
                return;
            }

            if(bounty > 0){
                damager.sendMessage(color("&a" + player.getName() + " had a bounty of " + bounty + " coins, congrats!"));
                //TODO remove bounty (user.setBounty(0);)
                duser.setCoins(duser.getCoins()+bounty);
            }

            if(duser.isKillBroadcast())
                Bukkit.broadcastMessage(color("&a" + damager.getName() + " brutally murdered " + player.getName() + (oldKillstreak > 1 ? " and ruined his killstreak of " + oldKillstreak : "") + "!"));

            damager.sendMessage(color("&aYou killed " + player.getName() + " and received " + coins + " coins and " + experience + " experience."));
        }
    }
}
