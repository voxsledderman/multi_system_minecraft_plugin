package com.xdd.serverPlugin.locations;

import org.bukkit.Bukkit;
import org.bukkit.World;

public enum ServerWorlds {
    SPAWN_WORLD("world"),
    CAMP_WORLD("camp_world");

    private final String worldName;

    ServerWorlds(String worldName){
        this.worldName = worldName;
    }

    public World getWorld(){
        World world = Bukkit.getWorld(worldName);
        if(world == null){
            throw new NullPointerException("świat [" + worldName +  "] nie istnieje!");
        } else return world;
    }
}
