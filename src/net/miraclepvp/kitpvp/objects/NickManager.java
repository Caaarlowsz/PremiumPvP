package net.miraclepvp.kitpvp.objects;

import net.miraclepvp.kitpvp.Main;
import net.miraclepvp.kitpvp.bukkit.NMSNickManager;
import net.miraclepvp.kitpvp.bukkit.UUIDFetcher;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.*;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public class NickManager {

    private Player p;
    private User user;

    public static HashMap<UUID, UUID> oldPrefixes = new HashMap<>();

    public static Field field;

    public static ArrayList<UUID> nickedPlayers = new ArrayList<>();

    public static HashMap<UUID, String> playerNicknames = new HashMap<>();

    public static List<String> nickNames = new ArrayList<>();

    public static List<String> blackList = new ArrayList<>();

    public static HashMap<UUID, String> oldDisplayNames = new HashMap<>();
    public static HashMap<UUID, String> oldPlayerListNames = new HashMap<>();

    //Get usual name
    public static String getRealName(UUID uuid){
        return UUIDFetcher.getName(uuid);
    }

    //Get NickManager
    public NickManager(Player p) {
        this.p = p;
        this.user = Data.getUser(p);
    }

    //Set the playerListName
    public void setPlayerListName(String name) {
        NMSNickManager.updatePlayerListName(p, name);
    }

    //Change the skin
    public void changeSkin(String skinName) {
        NMSNickManager.updateSkin(p, skinName);
    }

    //Update the player (show the changes)
    private void updatePlayer() {
        NMSNickManager.updatePlayer(p);
    }

    //Change the name
    public void setName(String nickName) {
        NMSNickManager.updateName(p, nickName);
    }

    //Nick a player
    public void nickPlayer(String nickName) {
        nickPlayer(nickName, nickName, null);
    }

    //Nick a player
    public void nickPlayer(String nickName, String skinName) {
        nickPlayer(nickName, skinName, null);
    }

    //Nick a player with different skin name
    public void nickPlayer(String nickName, String skinName, UUID prefix) {
        oldDisplayNames.put(p.getUniqueId(), p.getDisplayName());
        oldPlayerListNames.put(p.getUniqueId(), p.getPlayerListName());
        oldPrefixes.put(p.getUniqueId(), user.getPrefix());

        user.setPrefix(prefix == null ? Data.getPrefix("default") : Data.getPrefix(prefix));
        setName(nickName);
        changeSkin(skinName);

        updatePlayer();
        updatePrefix();

        nickedPlayers.add(p.getUniqueId());
        playerNicknames.put(p.getUniqueId(), nickName);
    }

    //Reset the player
    public void unnickPlayer() {
        String nickName = getRealName(p.getUniqueId());

        setName(nickName);
        changeSkin(nickName);

        updatePlayer();

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {

            @Override
            public void run() {
                p.setDisplayName(getOldDisplayName());
                setPlayerListName(getOldPlayerListName());
                user.setPrefix(getOldPrefix() == null ? null : Data.getPrefix(getOldPrefix()));

                oldDisplayNames.remove(p.getUniqueId());
                oldPlayerListNames.remove(p.getUniqueId());
                oldPrefixes.remove(p.getUniqueId());
            }
        }, 5);

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                updatePrefix();
            }
        }, 8);

        nickedPlayers.remove(p.getUniqueId());
        playerNicknames.remove(p.getUniqueId());
    }

    //Usual prefix
    public UUID getOldPrefix() {
        return oldPrefixes.get(p.getUniqueId());
    }

    //Check if a player is using a nickname
    public boolean isNicked() {
        return nickedPlayers.contains(p.getUniqueId());
    }

    //Get a random name
    public static String getRandomName() {
        return nickNames.get((new Random()).nextInt(nickNames.size()));
    }

    //Get the players nickname
    public String getNickName() {
        return (playerNicknames.containsKey(p.getUniqueId()) ? playerNicknames.get(p.getUniqueId()) : p.getName());
    }

    //Get the old displayname
    public String getOldDisplayName() {
        return oldDisplayNames.containsKey(p.getUniqueId()) ? oldDisplayNames.get(p.getUniqueId()) : p.getName();
    }

    //Get the old playerListName
    public String getOldPlayerListName() {
        return oldPlayerListNames.containsKey(p.getUniqueId()) ? oldPlayerListNames.get(p.getUniqueId()) : p.getName();
    }

    //Set the prefix
    public void updatePrefix() {
        String string = user.getPrefix() == null ? "&7" : Data.getPrefix(user.getPrefix()).getPrefix();

        setPlayerListName(color(string + "&7 " + p.getName()));

        Board.addPlayerInTab(p);

        p.chat("1045ynbgrvfnu4b6tr875vnh78gbv434giytb687tb78v436vb5t874ybv98vtv3b7vvnj5678hvno76itvb47vbv56o4iv564i3uv53iub4v6758i4v6b3587v3i4b65v7843v534v6tb487v5684bv563485vib8675v43v534");
    }

}
