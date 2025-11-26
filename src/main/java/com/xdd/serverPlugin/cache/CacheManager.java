package com.xdd.serverPlugin.cache;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.cuboids.camp.CampManager;
import com.xdd.serverPlugin.database.Loader;
import com.xdd.serverPlugin.database.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CacheManager {
    private final Map<UUID, PlayerData> playerDataMap = new HashMap<>();
    private final ServerPlugin plugin;
    private final CampManager campManager;

    public CacheManager(ServerPlugin plugin) {
        this.plugin = plugin;
        this.campManager = plugin.getCampManager();
    }

    public void clearPlayerCache(Player player){
        Camp camp = campManager.getPlayerCamp(player);
        if (camp != null) {
            if(camp.isAnyPlayerWithPermissionOnCamp()) return;
            camp.unloadCuboidChunks();
            campManager.unregisterPlayerCamp(player);
            campManager.getLastlyCheckedCamp().remove(player.getUniqueId());
        }
    }

    public void loadDataAsync(Player player){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, asyncTask -> {
            try {
                Loader.loadCampData(player, plugin);
                Loader.loadPlayerData(player, plugin);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void addToDataMap(@NotNull Player player, PlayerData data){
        playerDataMap.put(player.getUniqueId(), data);
    }
    public void removeFromDataMap(@NotNull Player player){
        playerDataMap.remove(player.getUniqueId());
    }
    public PlayerData getPlayerData(@NotNull Player player){
        return playerDataMap.get(player.getUniqueId());
    }
}
