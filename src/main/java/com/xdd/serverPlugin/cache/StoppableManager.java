package com.xdd.serverPlugin.cache;

import com.xdd.serverPlugin.interfaces.Stoppable;
import lombok.Getter;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class StoppableManager {
    private final Map<UUID, Stoppable> activeStoppables = new HashMap<>();

    public void registerStoppable(UUID playerId, Stoppable stoppable) {
        activeStoppables.put(playerId, stoppable);
    }

    public void unregisterStoppable(UUID playerId) {
        activeStoppables.remove(playerId);
    }

    @Nullable
    public Stoppable getStoppable(UUID playerId) {
        return activeStoppables.get(playerId);
    }
}
