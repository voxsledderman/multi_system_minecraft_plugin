package com.xdd.serverPlugin.cuboids;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.Utils.TextUtils;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.cuboids.camp.CampManager;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class CampListener implements Listener {
    private final ServerPlugin plugin = ServerPlugin.getInstance();
    private final CampManager campManager = plugin.getCampManager();

    @EventHandler
    public void onEnderPearl(PlayerTeleportEvent e){
        Player player = e.getPlayer();
        PlayerTeleportEvent.TeleportCause cause = e.getCause();

        if(!cause.equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL) &&
        !cause.equals(PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT)) return;

        Camp camp = campManager.getPlayerCamp(player);
        if(camp == null) return;
        if(camp.getCurrentBorder().isInside(e.getTo())) return;
        e.setCancelled(true);
        player.sendMessage(TextUtils.formatMessage("Nie możesz teleportować się poza granicę obozu!", TextColor.color(0xFF7070)));
    }
}
