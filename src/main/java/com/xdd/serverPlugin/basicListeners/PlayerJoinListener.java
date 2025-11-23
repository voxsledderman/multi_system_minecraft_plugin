package com.xdd.serverPlugin.basicListeners;

import com.xdd.serverPlugin.locations.ServerLocations;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        player.teleport(ServerLocations.SPAWN.getLocation());
    }
}
