package com.xdd.serverPlugin.npc;

import com.xdd.serverPlugin.ServerPlugin;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
public abstract class NPC {

    private final UUID uuid;
    private transient final ServerPlugin plugin = ServerPlugin.getInstance();
    private final Location npcLocation;
    private transient final Entity entity;
    private transient final Sound interactSound;

    protected NPC(@NotNull Entity entity, Sound interactSound) {
        this.entity = entity;
        this.interactSound = interactSound;
        this.uuid = entity.getUniqueId();
        this.npcLocation = entity.getLocation();
    }


    public abstract Runnable getInteractAction(Player player);
    public abstract String getKey();

    public void refreshEntity(){
        Bukkit.getEntity(uuid);
    }
}
