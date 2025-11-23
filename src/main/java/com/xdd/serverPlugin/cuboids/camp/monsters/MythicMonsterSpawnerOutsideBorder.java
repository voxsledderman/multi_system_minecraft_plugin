package com.xdd.serverPlugin.cuboids.camp.monsters;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.api.mobs.entities.SpawnReason;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.core.mobs.ActiveMob;
import io.lumine.mythic.core.mobs.DespawnMode;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Entity;

import java.util.Random;

@Getter
public class MythicMonsterSpawnerOutsideBorder {

    private final Camp camp;
    private final MythicMob mythicMob;
    private final int yCord;
    private final Random random = new Random();

    public MythicMonsterSpawnerOutsideBorder(Camp camp, MythicMob mythicMob, int yCord) {
        this.camp = camp;
        this.mythicMob = mythicMob;
        this.yCord = yCord;
    }


    public void spawnMobs(int amount){
        if(mythicMob != null && camp != null){
            doForMythicMobAndCamp(amount);
        }
    }
    private void doForMythicMobAndCamp(int amount) {
        WorldBorder worldBorder = camp.getCurrentBorder();
        if (worldBorder == null) {
            ServerPlugin.getInstance().getLogger().info(
                    "Nie zespawnowano mobów bo camp gracza [%s] nie ma ustawionego worldbordera"
                            .formatted(camp.getOwnerName()));
            return;
        }

        Random random = new Random();

        Location center = worldBorder.getCenter();
        double radius = worldBorder.getSize() / 2.0;

        int spawned = 0;
        while (spawned < amount) {

            double minOffset = 5;
            double maxOffset = 10;
            double xCord, zCord;
            boolean spawnOnXEdge = random.nextBoolean();

            if (spawnOnXEdge) {
                double offsetX = minOffset + random.nextDouble() * (maxOffset - minOffset);
                xCord = center.getX() + (random.nextBoolean() ? radius + offsetX : -radius - offsetX);
                zCord = center.getZ() + random.nextDouble() * (radius * 2) - radius;
            } else {
                double offsetZ = minOffset + random.nextDouble() * (maxOffset - minOffset);
                zCord = center.getZ() + (random.nextBoolean() ? radius + offsetZ : -radius - offsetZ);
                xCord = center.getX() + random.nextDouble() * (radius * 2) - radius;
            }


            Location spawnLoc = new Location(camp.getSpawnLocation().getWorld(), xCord, yCord, zCord);

            ActiveMob mob = mythicMob.spawn(BukkitAdapter.adapt(spawnLoc), 1, SpawnReason.NATURAL);

            Entity entity = BukkitAdapter.adapt(mob.getEntity());
            entity.setSilent(true);
            entity.setPersistent(false);

            mob.setDespawnMode(DespawnMode.CHUNK);
            spawned++;
        }
    }

}
