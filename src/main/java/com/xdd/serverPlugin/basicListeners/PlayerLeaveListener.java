package com.xdd.serverPlugin.basicListeners;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.cuboids.camp.CampManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class PlayerLeaveListener implements Listener {

    private final ServerPlugin plugin = ServerPlugin.getInstance();
    private final CampManager campManager = plugin.getCampManager();

    @EventHandler
    public void onQuit(PlayerQuitEvent e) throws SQLException {
        Player player = e.getPlayer();

        Camp camp = campManager.getPlayerCamp(player);
        camp.setHasTriggeredSpawn(false);

        plugin.getCacheManager().clearPlayerCache(player);
        plugin.getCampDao().save(camp);
//        plugin.getPlayerDao().save(player);
    }
}
