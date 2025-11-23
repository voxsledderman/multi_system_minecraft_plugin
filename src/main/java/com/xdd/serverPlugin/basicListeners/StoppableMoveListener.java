package com.xdd.serverPlugin.basicListeners;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.cache.StoppableManager;
import com.xdd.serverPlugin.interfaces.Stoppable;
import com.xdd.serverPlugin.locations.ServerWorlds;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

public class StoppableMoveListener implements Listener {
    private final StoppableManager manager = ServerPlugin.getInstance().getStoppableManager();

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player player = e.getPlayer();

        Location from = e.getFrom();
        Location to = e.getTo();
        if(!from.getWorld().equals(to.getWorld()) || hasMovedSignificantly(from, to)) {
            UUID uuid = player.getUniqueId();
            Stoppable stoppable = manager.getStoppable(uuid);
            if (stoppable == null) return;

            stoppable.stop(Component.text("Ruszyłeś się, teleportacja przerwana!").color(TextColor.color(0xFF7070)));
            if(player.getWorld().equals(ServerWorlds.SPAWN_WORLD.getWorld())){
                player.setWorldBorder(null);
            }
            manager.unregisterStoppable(uuid);
        }
    }

    private boolean hasMovedSignificantly(Location from, Location to) {
        double t = 0.05;
        return Math.abs(from.getX() - to.getX()) > t
                || Math.abs(from.getY() - to.getY()) > t
                || Math.abs(from.getZ() - to.getZ()) > t;
    }
}
