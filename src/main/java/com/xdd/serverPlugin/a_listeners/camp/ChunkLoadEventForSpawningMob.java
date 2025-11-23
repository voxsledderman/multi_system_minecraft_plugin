package com.xdd.serverPlugin.a_listeners.camp;

import com.xdd.serverPlugin.ConstantValues;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.cuboids.camp.CampManager;
import com.xdd.serverPlugin.cuboids.camp.monsters.MythicMonsterSpawnerOutsideBorder;
import com.xdd.serverPlugin.locations.ServerWorlds;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

public class ChunkLoadEventForSpawningMob implements Listener {
    private final ServerPlugin plugin = ServerPlugin.getInstance();
    private final CampManager campManager = plugin.getCampManager();
    private final Random random = new Random();

    @EventHandler
    void onLoad(ChunkLoadEvent e){

        Chunk chunk = e.getChunk();
        World world = chunk.getWorld();
        if(!world.equals(ServerWorlds.CAMP_WORLD.getWorld())) return;

        int blockX = chunk.getX() << 4;
        int blockZ = chunk.getZ() << 4;
        Location loc = new Location(world, blockX + 8, ConstantValues.CENTER_CAMP_Y, blockZ + 8);

        var camps = new ArrayList<>(campManager.getPlayerCampList().values());

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {

            Camp foundCamp = null;

            for(Camp iterationCamp : camps){
                if(iterationCamp.contains(loc)){
                    foundCamp = iterationCamp;
                    break;
                }
            }

            if(foundCamp == null) return;

            Camp finalFoundCamp = foundCamp;

            Bukkit.getScheduler().runTask(plugin, () -> {

                if(finalFoundCamp.isHasTriggeredSpawn()) return;

                finalFoundCamp.setHasTriggeredSpawn(true);

                new BukkitRunnable() {
                    int runs = 0;

                    @Override
                    public void run() {
                        if(runs > 6) {
                            this.cancel();
                            return;
                        }

                        int drop = random.nextInt(11);
                        boolean isCommon = drop < 8;
                        int index = isCommon ? 0 : random.nextInt(ConstantValues.zombieKeys.length);
                        MythicMob mob = MythicBukkit.inst().getMobManager()
                                .getMythicMob(ConstantValues.zombieKeys[index])
                                .orElse(null);

                        if(mob == null){
                            this.cancel();
                            return;
                        }

                        MythicMonsterSpawnerOutsideBorder spawner =
                                new MythicMonsterSpawnerOutsideBorder(finalFoundCamp, mob, ConstantValues.CENTER_CAMP_Y);

                        spawner.spawnMobs(1 + random.nextInt(3));
                        runs++;
                    }
                }.runTaskTimer(plugin, 30, 20);
            });

        });
    }

}
