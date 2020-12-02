package net.miraclepvp.kitpvp;

import net.miraclepvp.kitpvp.bukkit.WorldManager;
import net.miraclepvp.kitpvp.data.Config;
import net.miraclepvp.kitpvp.data.Data;
import net.miraclepvp.kitpvp.data.SQL;
import net.miraclepvp.kitpvp.data.Top;
import net.miraclepvp.kitpvp.data.user.StatType;
import net.miraclepvp.kitpvp.data.user.User;
import net.miraclepvp.kitpvp.inventories.listeners.*;
import net.miraclepvp.kitpvp.listeners.SignListener;
import net.miraclepvp.kitpvp.listeners.entity.projectileHit;
import net.miraclepvp.kitpvp.listeners.player.movement.*;
import net.miraclepvp.kitpvp.listeners.player.pvp.playerDamage;
import net.miraclepvp.kitpvp.listeners.player.pvp.playerDamageRegister;
import net.miraclepvp.kitpvp.listeners.player.pvp.playerDeath;
import net.miraclepvp.kitpvp.listeners.player.pvp.playerShoot;
import net.miraclepvp.kitpvp.listeners.weatherChange;
import net.miraclepvp.kitpvp.objects.*;
import net.miraclepvp.kitpvp.commands.*;
import net.miraclepvp.kitpvp.listeners.player.*;
import net.miraclepvp.kitpvp.utils.ActionbarUtil;
import net.miraclepvp.kitpvp.utils.Trails;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.*;

import static net.miraclepvp.kitpvp.bukkit.Text.color;

public final class Main extends JavaPlugin {

    /* TODO
        1. Cooldown to respawn after you get killed
        2. Rewards for KillStreaks or rewards for leveling up
        3. Option to modify store items (of the npc)
        4. Duel scoreboard, such as showing the opponent's ping, life, soups or potions.
        5. Guild duels
        6. More special items (https://www.youtube.com/watch?v=NZWXbDjjHrs&feature=emb_err_woyt&ab_channel=Wazup92)
    */

    /* TODO working on
        1. Scoreboard 100% customizable
        2. Custom chat format
    */

    /* ADDED
        1. Toggle the possibility to hover on peoples names in chat
        2. Guild friendly fire toggle
        3. Update Notifier
        4. Title support
        5. Allow your players to drop soups
        6. Removed unused code
    */

    public String version = getDescription().getVersion();
    public Boolean isDevmode = false;

    private Connection connection;
    public String host, database, username, password;
    public int port;

    public HashMap<Player, Integer> combatTimer = new HashMap<>();
    public HashMap<UUID, Trails> playerTrails = new HashMap<>();

    private Integer cmdSize = 0;
    private Integer eventSize = 0;

    private Integer messageCount = 0;

    @Override
    public void onEnable() {
        if (version.toLowerCase().endsWith("-dev"))
            isDevmode = true;

        WorldManager.loadEmptyWorld(World.Environment.NORMAL, "ffa");

        Config.load();
        getLogger().info("Config has been loaded");

        StatType.load();
        getLogger().info("Stats Types have been loaded");

        PermissionType.load();
        getLogger().info("Guild Permission Types have been loaded");

        Data.load();
        getLogger().info("Data has been loaded");

        if(Config.UseMySQL()) {
            try {
                mysqlSetup();
                SQL.ReportSQL.createTabel();
                getLogger().info("Connected to the MySQL Database.");
            } catch (Exception e) {
                getLogger().warning("MySQL failed to connect");
                if (!isDevmode)
                    getPluginLoader().disablePlugin(this);
            }
        }

        Bukkit.getServer().getWorlds().forEach(world -> {
            world.setStorm(false);
        });

        registerListeners(
                new playerDamage(),
                new playerLeave(),
                new playerDeath(),
                new playerJoin(),
                new playerShoot(),
                new CosmeticsListener(),
                new playerChat(),
                new playerDrop(),
                new playerHunger(),
                new playerStatusChange(),
                new SignListener(),
                new playerBlockChange(),
                new KitListener(),
                new playerInventory(),
                new playerDamageRegister(),
                new ProfileListener(),
                new PermissionsListener(),
                new CrateListener(),
                new playerTeleport(),
                new projectileHit(),
                new SupplydropListener(),
                new playerMove(),
                new BoosterListener(),
                new playerInteract(),
                new BountyListener(),
                new weatherChange(),
                new ReportListener(),
                new KitEditListener(),
                new AbilityListener(),
                new LeaderboardListener(),
                new inventoryClose(),
                new KitLayoutListener(),
                new BankerListener(),
                new ShopListener(),
                new playerConsume(),
                new AnvilListener(),
                new DuelListener(),
                new playerSpawn(),
                new playerDeploy()
        );
        getLogger().info("There are " + eventSize + " events loaded!");

        registerCommands(
                new String[]{
                        "unbreakable", "rename", "cosmotokens", "trail", "suffix", "chatcolor", "namecolor", "stats", "kitpvp", "prefix", "kit",
                        "coins", "guild", "spawnpoints", "chat", "staffchat", "sc", "supplydrop", "zone", "booster", "fly", "report", "crate",
                        "spawn", "freeze", "discord", "top", "duel", "map", "arena", "resetstats", "anvil", "hidechat"
                },
                new UnbreakableCMD(),
                new RenameCMD(),
                new CosmoTokens(),
                new TrailCMD(),
                new SuffixCMD(),
                new ChatColorCMD(),
                new NameColorCMD(),
                new StatsCMD(),
                new KitpvpCMD(),
                new PrefixCMD(),
                new KitCMD(),
                new CoinsCMD(),
                new GuildCMD(),
                new SpawnpointsCMD(),
                new ChatCMD(),
                new StaffchatCMD(),
                new StaffchatCMD(),
                new SupplydropCMD(),
                new ZoneCMD(),
                new BoosterCMD(),
                new FlyCMD(),
                new ReportCMD(),
                new CrateCMD(),
                new SpawnCMD(),
                new FreezeCMD(),
                new DiscordCMD(),
                new TopCMD(),
                new DuelCMD(),
                new MapCMD(),
                new ArenaCMD(),
                new ResetstatsCMD(),
                new AnvilCMD(),
                new HidechatCMD()
        );
        getLogger().info("There are " + cmdSize + " commands loaded!");

        CosmeticType.load();
        getLogger().info("Cosmetic Types have been loaded");

        playerChat.loadChatfilter();
        getLogger().info("Chatfilter has been loaded");

        Item.load();
        getLogger().info("Items have been loaded");

        playerMove.setupRegionList();
        getLogger().info("Zone Regions have been loaded");

        AnvilListener.prepareAnvil();
        getLogger().info("Anvil is prepared and ready to use");

        Bukkit.getOnlinePlayers().stream().forEach(player -> {
            if (!Data.users.stream().anyMatch(i -> i.getUuid().equals(player.getUniqueId()))) {
                User user = new User(player.getUniqueId());
                Data.users.add(user);
            }
            playerJoin.handleJoin(player);
        });

        new BukkitRunnable() {
            @Override
            public void run() {
                Thread thread = new Thread(() -> {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        User user = Data.getUser(player);
                        user.setOnlineTime(user.getOnlineTime() + 1);

                        if (player.hasMetadata("build")) new ActionbarUtil("&cYou are in buildmode!").sendToPlayer(player);
                        else if (combatTimer.get(player) != null) { //Anders als hij in combat is
                            Integer combattime = combatTimer.get(player);
                            if (combattime > 0) {
                                combattime--;
                                combatTimer.put(player, combattime);
                                Integer timeRan = 10 - combattime;
                                String timer = "&cCombat Timer &8[";
                                for (int i = 0; i < combattime; i++) timer+="&a|";
                                for (int i = 1; i < timeRan; i++) timer+="&7|";
                                timer+="&8]";
                                new ActionbarUtil(timer).sendToPlayer(player);
                            } else combatTimer.remove(player);
                        } else if (playerMove.inZone.get(player.getUniqueId()) != null) new ActionbarUtil("&5Location: " + playerMove.inZone.get(player.getUniqueId())).sendToPlayer(player);
                    }
                });
                thread.start();
            }
        }.runTaskTimer(this, 20L, 20L);
        getLogger().info("Combat Timer has been loaded");

        new BukkitRunnable() {
            @Override
            public void run() {
                if (messageCount >= Config.getBroadcastMessages().size()) messageCount = 0;
                Bukkit.broadcastMessage(color("&1 "));
                Bukkit.broadcastMessage(color(Config.getBroadcastMessages().get(messageCount))
                        .replaceAll("%server_name%", Config.getServerName())
                        .replaceAll("%server_version%", Main.getInstance().version)
                        .replaceAll("%arrow%", "»"));
                Bukkit.broadcastMessage(color("&2 "));
                messageCount++;
            }
        }.runTaskTimer(this, 0, Config.getBroadcastDelay() * 20);
        getLogger().info("Broadcast messages are loaded");

        new BukkitRunnable() {
            @Override
            public void run() {
                Top.reload();
                Data.signs.forEach(sign -> sign.update());
            }
        }.runTaskTimer(this, 0, Config.getSignReloadDelay() * 20);
        getLogger().info("Sign updater is loaded");

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Trails t : playerTrails.values()) t.tick();
            }
        }.runTaskTimer(this, 0, 5);

        Supplydrop.Crate.prepareItems();
        new BukkitRunnable() {
            @Override
            public void run() {
                if(Bukkit.getOnlinePlayers().size()>=5)
                    new Supplydrop.Crate(Supplydrop.DropType.NORMAL).spawn();
            }
        }.runTaskTimer(this, 36000L, 72000L);
        getLogger().info("Supplydrop system is loaded");

        if(Config.UseMySQL()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        Statement statement = getConnection().createStatement();
                        ResultSet res = statement.executeQuery("SELECT * FROM faketable;");
                        res.next();
                    } catch (SQLException e) {
                    } catch (Exception e) {
                        if (!isDevmode)
                            e.printStackTrace();
                    }
                }
            }.runTaskTimerAsynchronously(this, 0, 12000);

            try {
                Statement statement = getConnection().createStatement();
                statement.execute("CREATE TABLE IF NOT EXISTS `reports` (`id` INT NOT NULL AUTO_INCREMENT, `uid` VARCHAR(36) NOT NULL, `tid` VARCHAR(36) NOT NULL, `message` VARCHAR(255) NOT NULL, PRIMARY KEY (`id`));");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (Exception ex) {
                if (!isDevmode)
                    ex.printStackTrace();
            }
        }
    }

    private void registerListeners(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> {
            Bukkit.getPluginManager().registerEvents(listener, this);
            eventSize++;
        });
    }

    private void registerCommands(String[] commands, CommandExecutor... commandExecutors) {
        Arrays.stream(commandExecutors).forEach(command -> {
            getCommand(commands[cmdSize]).setExecutor(command);
            cmdSize++;
        });
    }

    @Override
    public void onDisable() {
        Data.save(false);
        Config.save();

        try {
            Supplydrop drop = new Supplydrop();
            drop.despawnCrates();
        } catch (NoClassDefFoundError ex) {
        }
    }

    public void mysqlSetup() {
        host = this.getConfig().getString("mysql.host");
        port = this.getConfig().getInt("mysql.port");
        database = this.getConfig().getString("mysql.database");
        username = this.getConfig().getString("mysql.username");
        password = getConfig().getString("mysql.password");

        mysqlOpenConnection();
    }

    public void mysqlOpenConnection() {
        try {
            synchronized (this) {
                if (getConnection() != null && !getConnection().isClosed()) return;
                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public static int getRandom(int lower, int upper) {
        return new Random().nextInt((upper - lower) + 1) + lower;
    }

    public static Main getInstance() {
        return Main.getPlugin(Main.class);
    }
}
