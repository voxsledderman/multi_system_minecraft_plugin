package com.xdd.serverPlugin.cache;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.cuboids.camp.CampManager;
import org.bukkit.entity.Player;

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
        }
    }
}
