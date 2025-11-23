package com.xdd.serverPlugin.npc;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.cuboids.camp.CampManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class NpcInteractionListener implements Listener {
    private final CampManager campManager = ServerPlugin.getInstance().getCampManager();

    @EventHandler
    void interactEntity(PlayerInteractAtEntityEvent e){
        Player player = e.getPlayer();
        Entity entity = e.getRightClicked();
        var npc = isCampNPC(entity, player);
        if(npc != null) npc.getInteractAction(player).run();


    }

    NPC isCampNPC(Entity entity, Player player){
        var camp = campManager.getPlayerCamp(player);
        if(camp == null) return null;

        for(NPC npc : camp.getNpcs()){
            if(entity.getUniqueId().equals(npc.getEntity().getUniqueId())) return npc;
        }
        return null;
    }
}
