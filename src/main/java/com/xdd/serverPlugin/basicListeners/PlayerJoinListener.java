package com.xdd.serverPlugin.basicListeners;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.cache.CacheManager;
import com.xdd.serverPlugin.locations.ServerLocations;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final CacheManager cacheManager = ServerPlugin.getInstance().getCacheManager();

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        cacheManager.loadDataAsync(player);
        player.teleport(ServerLocations.SPAWN.getLocation());

    }
}
