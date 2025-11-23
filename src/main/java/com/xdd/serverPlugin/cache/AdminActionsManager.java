package com.xdd.serverPlugin.cache;

import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.cuboids.camp.generator.CampGenerator;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class AdminActionsManager {
    private final Map<UUID, CampGenerator> runningCampGeneration = new HashMap<>();
    private final Map<UUID, Integer> pendingConfirmations = new HashMap<>();
    private final Map<Integer, Camp> pendingCampDeleteConfirmations = new HashMap<>();



    public CampGenerator getRunningCampGeneration(Player player){
        return runningCampGeneration.get(player.getUniqueId());
    }
    public void registerCampGeneration(Player player, CampGenerator campGenerator){
        runningCampGeneration.put(player.getUniqueId(), campGenerator);
    }
    public void unregisterCampGeneration(Player player){
        runningCampGeneration.remove(player.getUniqueId());
    }
}
