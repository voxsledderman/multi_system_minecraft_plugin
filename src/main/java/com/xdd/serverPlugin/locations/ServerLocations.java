package com.xdd.serverPlugin.locations;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public enum ServerLocations {
    SPAWN(ServerWorlds.SPAWN_WORLD,0.5, 65, 0.5, 90 ,0);


    private final ServerWorlds world;
    private final double x;
    private final double y;
    private final double z;
    private final float pitch;
    private final float yaw;



    ServerLocations(ServerWorlds world,double x, double y, double z, float pitch, float yaw){
        this. world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public Location getLocation(){
        return new Location(world.getWorld(), x, y, z, pitch, yaw);
    }
    public void teleport(Player player, boolean withSound){
        player.teleport(getLocation());
        if(withSound){
            player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 0.75f,1.2f);
        }
    }
}
