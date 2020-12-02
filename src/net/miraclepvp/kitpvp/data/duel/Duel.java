package net.miraclepvp.kitpvp.data.duel;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.kit.Kit;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.listeners.custom.PlayerSpawnEvent;
import net.miraclepvp.kitpvp.objects.hasKit;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class Duel {

    public static List<Duel> duels = new ArrayList<>();

    public static HashMap<UUID, UUID>
            spectators = new HashMap<>(),
            invites = new HashMap<>(),
            games = new HashMap<>();

    public static Boolean playerInvited(Player inviter, Player invited) {
        if (invites.containsKey(inviter.getUniqueId()))
            if (invites.get(inviter.getUniqueId()).equals(invited.getUniqueId()))
                return true;
        return false;
    }

    public static Boolean isIngame(Player player) {
        try {
            return games.containsKey(player.getUniqueId()) || games.containsValue(player.getUniqueId());
        } catch (Exception ex) {
            return false;
        }
    }

    public static void joinGame(Player player, Duel duel) {
        if (invites.containsKey(duel.host))
            invites.remove(duel.host);

        duel.joined = player.getUniqueId();

        games.put(duel.host, player.getUniqueId());

        User host = Data.getUser(Bukkit.getOfflinePlayer(duel.host));
        User invited = Data.getUser(player);

        if (duel.bid > 0) {
            host.setCoins(host.getCoins() - duel.bid, false);
            invited.setCoins(invited.getCoins() - duel.bid, false);
        }

        duel.start();
    }

    public static void spectatePlayer(Player player, Player target) {
        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(target.getLocation());
        player.sendMessage(color("&aYou are now spectating " + target.getName() + "."));

        if (spectators.containsKey(player.getUniqueId()))
            spectators.remove(player.getUniqueId());
        spectators.put(player.getUniqueId(), target.getUniqueId());
    }

    public static void stopSpectating(Player player) {
        spectators.remove(player.getUniqueId());
    }

    public static boolean isSpectator(OfflinePlayer offlinePlayer) {
        return spectators.containsKey(offlinePlayer.getUniqueId());
    }

    public static UUID getSpectating(OfflinePlayer offlinePlayer) {
        return spectators.get(offlinePlayer.getUniqueId());
    }

    public static void declineGame(Player player, Duel duel) {
        if (invites.containsKey(duel.host)) {
            if (invites.get(duel.host).equals(player.getUniqueId())) {
                if (Bukkit.getOfflinePlayer(duel.host).isOnline())
                    Bukkit.getPlayer(duel.host).sendMessage(color("&c" + player.getName() + " declined your duel invite."));
                Data.getMap(duel.map).getArena(duel.arena).enabled = true;
                player.sendMessage(color("&aYou've successfully declined " + Bukkit.getOfflinePlayer(duel.host).getName() + "'s Duel invite."));
                invites.remove(duel.host);
                return;
            }
        }
        player.sendMessage(color("&cThis player did not invite you or the invite is already expired."));
    }

    public static Duel getDuel(OfflinePlayer player) {
        try {
            return duels.stream().filter(duel -> duel.host.equals(player.getUniqueId())).findFirst().get();
        } catch (Exception ex) {
            try {
                return duels.stream().filter(duel -> duel.joined.equals(player.getUniqueId())).findFirst().get(); //Joined
            } catch (Exception e) {
                return null;
            }
        }
    }

    /*      DUEL OBJECT     */

    public UUID
            host,
            joined,
            arena,
            map;
    public Integer
            bid;
    public Boolean
            allowSpectator;
    public Kit
            kit;

    public Status status;

    public Duel(UUID host) {
        Bukkit.broadcastMessage("new duel created for " + host);

        this.host = host;
        this.joined = null;
        this.bid = 0;
        this.allowSpectator = true;
        this.kit = null;
        this.map = null;
        this.arena = null;
        this.status = Status.PENDING;
    }

    public void invitePlayer(Player invited) {
        Player inviter = Bukkit.getPlayer(host);
        if (invites.containsKey(inviter.getUniqueId())) {
            inviter.sendMessage(color("&cYou can not send multiple invites at the same time."));
            return;
        }
        invites.put(inviter.getUniqueId(), invited.getUniqueId());
        inviter.sendMessage(color("&aYou've successfully invited " + invited.getName() + " for a duel. This player has 10 seconds to accept."));
        invited.sendMessage(" ");
        invited.sendMessage(color("&6You have been invited for a duel by " + inviter.getName() + (bid > 0 ? " for " + bid + " coins." : ".")));
        invited.sendMessage(color("&7Kit: " + kit.getName()));
        invited.sendMessage(color("&7Map: " + Data.getMap(map).name));
        invited.sendMessage(color("&7Spectators: " + (allowSpectator ? "allowed" : "disallowed")));
        TextComponent acceptComponent = new TextComponent(ChatColor.GREEN + "ACCEPT"); //ACCEPT
        acceptComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duel accept " + inviter.getName()));
        TextComponent middleLine = new TextComponent(ChatColor.DARK_GRAY + " - "); //MIDDLELINE
        TextComponent declineComponent = new TextComponent(ChatColor.RED + "DECLINE"); //DECLINE
        declineComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duel decline " + inviter.getName()));
        acceptComponent.addExtra(middleLine);
        acceptComponent.addExtra(declineComponent);
        invited.spigot().sendMessage(acceptComponent);
        invited.sendMessage(" ");

        new BukkitRunnable() {
            @Override
            public void run() {
                if (invites.containsKey(inviter.getUniqueId()) && invites.get(inviter.getUniqueId()).equals(invited.getUniqueId())) {
                    inviter.sendMessage(color("&cYour invite for " + invited.getName() + " has expired."));
                    invited.sendMessage(color("&cThe invite from " + inviter.getName() + " has expired."));
                    invites.remove(inviter.getUniqueId());
                    Data.getMap(map).getArena(arena).enabled = true;
                }
            }
        }.runTaskLater(Main.getInstance(), 200L);
    }

    public void sendMessage(String text) {
        try {
            Player pOne = Bukkit.getPlayer(host);
            Player pTwo = Bukkit.getPlayer(joined);

            pOne.sendMessage(color(text));
            pTwo.sendMessage(color(text));
        } catch (NullPointerException ex) {
        }
    }

    public void start() {
        status = Status.PREPARING;
        if (!Bukkit.getOfflinePlayer(host).isOnline() || !Bukkit.getOfflinePlayer(joined).isOnline())
            end(null);
        Player pOne = Bukkit.getPlayer(host);
        Player pTwo = Bukkit.getPlayer(joined);

        Arena ar = Data.getMap(map).getArena(arena);

        pOne.teleport(ar.getLocationA());
        pTwo.teleport(ar.getLocationB());
        preparePlayer(pOne);
        preparePlayer(pTwo);

        final Integer[] count = {5};
        new BukkitRunnable() {
            @Override
            public void run() {
                if (status.equals(Status.DONE)) {
                    cancel();
                    return;
                }

                if (count[0] == 0) {
                    runGame();
                    cancel();
                } else {
                    sendMessage("&6The game starts in " + count[0] + " second" + (count[0] > 1 ? "s" : "") + "..");
                    count[0]--;
                }

            }
        }.runTaskTimer(Main.getInstance(), 0L, 20L);
    }

    public void runGame() {
        if (status.equals(Status.DONE)) return;

        status = Status.INGAME;

        sendMessage("&6Let the game begin, good luck!");

        new BukkitRunnable() {
            public void run() {
                if (status.equals(Status.DONE)) {
                    cancel();
                    return;
                }
                sendMessage("&cThe game will end in 5 minutes!");
            }
        }.runTaskLater(Main.getInstance(), 20 * 60 * 10);

        new BukkitRunnable() {
            public void run() {
                if (status.equals(Status.DONE)) {
                    cancel();
                    return;
                }
                sendMessage("&cThe game will end in 1 minute!");
            }
        }.runTaskLater(Main.getInstance(), 20 * 60 * 14);

        final Integer[] counter = {10};
        new BukkitRunnable() {
            public void run() {
                if (status.equals(Status.DONE)) {
                    cancel();
                    return;
                }
                if (counter[0] == 0) {
                    end(null);
                    cancel();
                    return;
                }
                sendMessage("&cThe game will end in " + counter[0] + " second" + (counter[0] > 1 ? "s" : ""));
                counter[0]--;
            }
        }.runTaskTimer(Main.getInstance(), 17800L, 20L);
    }

    public void leave(Player player) {
        UUID winner = null;
        if (this.host.equals(player.getUniqueId()))
            winner = this.joined;
        else
            winner = this.host;
        end(Bukkit.getOfflinePlayer(winner));
    }

    public void end(OfflinePlayer winner) {
        status = Status.DONE;
        
        sendMessage("&cThe game ended, " + (winner == null ? "nobody" : winner.getName()) + " has won the game!");

        spectators.forEach((uuid, uuid2) -> {
            if(Bukkit.getOfflinePlayer(uuid).isOnline() && (uuid2.equals(host) || uuid2.equals(joined)))
                Bukkit.getPlayer(uuid).sendMessage(color("&cThe game ended, " + (winner == null ? "nobody" : winner.getName()) + " has won the game!"));
        });

        OfflinePlayer pOne = Bukkit.getOfflinePlayer(this.host);
        OfflinePlayer pTwo = Bukkit.getOfflinePlayer(this.joined);

        User uOne = Data.getUser(pOne);
        User uTwo = Data.getUser(pTwo);
        if (winner == null) {
            uOne.setCoins(uOne.getCoins() + bid, false);
            uTwo.setCoins(uTwo.getCoins() + bid, false);
        } else {
            User wUser = Data.getUser(winner);
            wUser.setCoins(wUser.getCoins() + (bid * 2), false);
        }

        spectators.forEach((uuid, uuid2) -> {
            if (uuid2.equals(host) || uuid2.equals(joined))
                if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Bukkit.getPluginManager().callEvent(new PlayerSpawnEvent(Bukkit.getPlayer(uuid)));
                        }
                    }.runTaskLater(Main.getInstance(), 5L);
                }
        });

        Data.getMap(map).getArena(arena).enabled = true;
        duels.remove(this);
        games.remove(host);

        if (winner == null) {
            if (pOne.isOnline())
                Bukkit.getPluginManager().callEvent(new PlayerSpawnEvent(pOne.getPlayer()));
            if (pTwo.isOnline())
                Bukkit.getPluginManager().callEvent(new PlayerSpawnEvent(pTwo.getPlayer()));
        } else if(winner.isOnline())
            Bukkit.getPluginManager().callEvent(new PlayerSpawnEvent(Bukkit.getPlayer(winner.getUniqueId())));
    }

    private void preparePlayer(Player player) {
        User user = Data.getUser(player);
        user.giveKit(kit.getUuid(), true, false);
        player.setMetadata("kit", new hasKit());
        player.setAllowFlight(false);
        player.setFlying(false);
    }

    public enum Status {
        PENDING,
        PREPARING,
        INGAME,
        DONE;

        Status() {
        }
    }
}
