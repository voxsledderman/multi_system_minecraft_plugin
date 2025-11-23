package com.xdd.serverPlugin.cache;


import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class PlayerInputsManager {
    private final Map<UUID, BukkitTask> chatInputMap = new HashMap<>();

    public void removeFromChatMap(Player player){
        chatInputMap.remove(player.getUniqueId());
    }

    public void addToChatMap(Player player, BukkitTask task){
        chatInputMap.put(player.getUniqueId(), task);
    }
    public BukkitTask getChatTask(Player player){
        return chatInputMap.get(player.getUniqueId());
    }
    public boolean chatContains(Player player){
        return chatInputMap.containsKey(player.getUniqueId());
    }
}
