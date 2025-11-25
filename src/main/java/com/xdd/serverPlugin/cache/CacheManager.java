package com.xdd.serverPlugin.cache;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.cuboids.camp.CampManager;
import com.xdd.serverPlugin.database.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;

public class CacheManager {

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
    public void loadPlayerDataAsync(Player player){
        final UUID uuid = player.getUniqueId();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, asyncTask -> {
            try {
                Camp camp = plugin.getCampDao().getCampByUuid(uuid);
                PlayerData playerData = plugin.getPlayerDao().getPlayerData(player); //TODO: dodaj playerDate, dodaj handlera dla wyjątku
                if(camp != null) campManager.registerPlayerCamp(player, camp);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
