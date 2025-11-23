package com.xdd.serverPlugin.npc;

import com.xdd.serverPlugin.ServerPlugin;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
public abstract class NPC {
    private final UUID uuid;
    private final ServerPlugin plugin = ServerPlugin.getInstance();
    private final Location npcLocation;
    private final Entity entity;
    private final Sound interactSound;

    protected NPC(Entity entity, Sound interactSound) {
        this.uuid = entity.getUniqueId();
        this.npcLocation = entity.getLocation();
        this.entity = entity;
        this.interactSound = interactSound;
    }

    public abstract Runnable getInteractAction(Player player);
}
