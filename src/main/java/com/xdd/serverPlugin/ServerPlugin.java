package com.xdd.serverPlugin;

import com.xdd.serverPlugin.a_listeners.camp.ChunkLoadEventForSpawningMob;
import com.xdd.serverPlugin.a_listeners.camp.GlobalCampListeners;
import com.xdd.serverPlugin.a_listeners.camp.MobsDoNotCrossBorderListener;
import com.xdd.serverPlugin.a_listeners.camp.SpecificCampListeners;
import com.xdd.serverPlugin.basicListeners.PlayerChatListener;
import com.xdd.serverPlugin.basicListeners.PlayerJoinListener;
import com.xdd.serverPlugin.basicListeners.PlayerLeaveListener;
import com.xdd.serverPlugin.basicListeners.StoppableMoveListener;
import com.xdd.serverPlugin.cache.*;
import com.xdd.serverPlugin.commands.admin.GenerateCampsCommand;
import com.xdd.serverPlugin.commands.admin.TestCommand;
import com.xdd.serverPlugin.commands.player.*;
import com.xdd.serverPlugin.cuboids.CampListener;
import com.xdd.serverPlugin.cuboids.camp.CampManager;
import com.xdd.serverPlugin.cuboids.camp.LocationSaveManager;
import com.xdd.serverPlugin.database.DatabaseConnection;
import com.xdd.serverPlugin.database.DatabaseManager;
import com.xdd.serverPlugin.database.dao.CampDao;
import com.xdd.serverPlugin.database.dao.PlayerDao;
import com.xdd.serverPlugin.npc.NpcInteractionListener;
import com.xdd.serverPlugin.shopSystem.config.ShopManager;
import com.xdd.serverPlugin.tittle.TitleManager;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.message.LiteMessages;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;


@Getter
public final class ServerPlugin extends JavaPlugin {
    private final static long DATA_SAFE_PERIOD_MINUTES = 3;

    private LiteCommands<CommandSender> liteCommands;
    private TitleManager titleManager;
    private StoppableManager stoppableManager;
    private LocationSaveManager locationSaveManager;
    private AdminActionsManager adminActionsManager;
    private CampManager campManager;
    private CacheManager cacheManager;
    private PlayerInputsManager playerInputsManager;
    private DatabaseConnection databaseConnection;
    private CampDao campDao;
    private PlayerDao playerDao;
    private ShopManager shopManager;

    @Override
    public void onEnable() {
        DatabaseManager.setupConfig();
        saveResource("oboz.schem",false);

        databaseConnection = new DatabaseConnection();
        makeDatabaseConnection(databaseConnection);

        titleManager = new TitleManager();
        stoppableManager = new StoppableManager();
        locationSaveManager = new LocationSaveManager();
        adminActionsManager = new AdminActionsManager();
        campManager = new CampManager();
        cacheManager = new CacheManager(this);
        playerInputsManager = new PlayerInputsManager();
        shopManager = new ShopManager();
        shopManager.load();

        try {
            campDao = new CampDao();
            playerDao = new PlayerDao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.liteCommands = LiteBukkitFactory.builder("fallback-prefix", this)
                .commands(
                        new SpawnCommand(),
                        new GenerateCampsCommand(),
                        new TeleportCommand(),
                        new CampCommand(),
                        new TestCommand(),
                        new MoneyCommand(),
                        new ShopCommand()
                )
                .message(LiteMessages.MISSING_PERMISSIONS, permission -> "§cNie masz permisji na wykonanie tej komendy!")
                .message(LiteMessages.INVALID_USAGE, invalidUsage ->  "§cNiepoprawne użycie komendy!")
                .build();

        runDataSaveRunnable();

        Bukkit.getPluginManager().registerEvents(new StoppableMoveListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLeaveListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new CampListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new GlobalCampListeners(), this);
        Bukkit.getPluginManager().registerEvents(new SpecificCampListeners(), this);
        Bukkit.getPluginManager().registerEvents(new MobsDoNotCrossBorderListener(), this);
        Bukkit.getPluginManager().registerEvents(new NpcInteractionListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChunkLoadEventForSpawningMob(), this);
    }

    @Override
    public void onDisable() {
       closeDatabaseConnection(databaseConnection);
    }

    private void makeDatabaseConnection(@NotNull DatabaseConnection databaseConnection) {
        try {
            databaseConnection.connect();
        } catch (SQLException e) {
            this.getLogger().warning("[ServerPlugin] Nie udało się połączyć z bazą danych, zamykam serwer");
            Bukkit.getServer().shutdown();
            throw new RuntimeException(e);
        }
    }
    private void closeDatabaseConnection(DatabaseConnection databaseConnection){
        try {
            databaseConnection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void runDataSaveRunnable(){
        long savePeriod = 20 * 60 * DATA_SAFE_PERIOD_MINUTES;
        PeriodicDataSaverRunnable runnable = new PeriodicDataSaverRunnable();
        runnable.runTaskTimerAsynchronously(this, savePeriod, savePeriod);
    }
    public static ServerPlugin getInstance(){
        return JavaPlugin.getPlugin(ServerPlugin.class);
    }
}
