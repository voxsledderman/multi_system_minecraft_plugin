package com.xdd.serverPlugin.cache;

import com.xdd.serverPlugin.interfaces.Stoppable;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class StoppableManager {
    private final Map<UUID, Stoppable> activeStoppables = new HashMap<>();

    public void registerStoppable(UUID playerUuid, Stoppable stoppable) {
        if(activeStoppables.get(playerUuid) != null){
            activeStoppables.get(playerUuid).stop(Component.text("Anulowano poprzedni teleport...").color(TextColor.color(0xFF7070)));
        }
        activeStoppables.put(playerUuid, stoppable);
    }

    public void unregisterStoppable(UUID playerId) {
        activeStoppables.remove(playerId);
    }

    @Nullable
    public Stoppable getStoppable(UUID playerId) {
        return activeStoppables.get(playerId);
    }
}
