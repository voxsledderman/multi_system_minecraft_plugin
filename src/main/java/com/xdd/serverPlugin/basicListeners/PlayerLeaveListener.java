package com.xdd.serverPlugin.basicListeners;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.cache.CacheManager;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.cuboids.camp.CampManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class PlayerLeaveListener implements Listener {

    private final ServerPlugin plugin = ServerPlugin.getInstance();
    private final CampManager campManager = plugin.getCampManager();
    private final CacheManager cacheManager = plugin.getCacheManager();

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        Camp camp = campManager.getPlayerCamp(player);
        if(camp != null) {
            camp.setHasTriggeredSpawn(false);
        }

        saveToDatabaseAsync(player, camp);
    }

    private void saveToDatabaseAsync(Player player, Camp camp){
        Bukkit.getScheduler().runTaskAsynchronously(ServerPlugin.getInstance(), asyncTask -> {
            try {
                if(camp != null) {
                    plugin.getCampDao().save(camp);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            try {
                plugin.getPlayerDao().save(cacheManager.getPlayerData(player));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            Bukkit.getScheduler().runTask(plugin, () -> {
                if (player.isOnline()) {
                    plugin.getCacheManager().removeFromDataMap(player);
                    plugin.getCacheManager().clearPlayerCache(player);
                }
            });

        });
    }
}
