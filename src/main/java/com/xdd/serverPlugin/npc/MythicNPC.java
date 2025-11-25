package com.xdd.serverPlugin.npc;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;

public abstract class MythicNPC extends NPC {


    protected MythicNPC(Entity entity, Sound interactSound) {
        super(entity, interactSound);
    }
}
