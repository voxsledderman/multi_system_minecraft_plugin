package com.xdd.serverPlugin.Utils;

import com.xdd.serverPlugin.ConstantValues;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.cuboids.SimpleCuboid;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class BorderUtils {

    public static void clearBarrierBlocks(Camp camp) {

        SimpleCuboid cuboid = camp.getSimpleCuboidFromCurrentLevel();
        cuboid.changeY(ConstantValues.CENTER_CAMP_Y, ConstantValues.CENTER_CAMP_Y + 1);
        final List<Location> blocks = new ArrayList<>(cuboid.getBorderBlockLocations());

        new BukkitRunnable() {
            int removed = 0;

            @Override
            public void run() {
                removed = 0;

                for (int i = 0; i < blocks.size() && removed < 500; i++) {
                    Location loc = blocks.get(i);
                    if (loc.getBlock().getType() != Material.AIR) {
                        loc.getBlock().setType(Material.AIR);
                        removed++;
                    }
                }
                blocks.removeIf(loc -> loc.getBlock().getType() == Material.AIR);

                if (blocks.isEmpty()) {
                    this.cancel();
                }
            }
        }.runTaskTimer(ServerPlugin.getInstance(), 0L, 30L);
    }


    public static void putBarrierBlocks(Camp camp) {
        SimpleCuboid cuboid = camp.getSimpleCuboidFromCurrentLevel();
        cuboid.changeY(ConstantValues.CENTER_CAMP_Y, ConstantValues.CENTER_CAMP_Y + 1);

        final List<Location> blocks = new ArrayList<>(cuboid.getBorderBlockLocations());

        new BukkitRunnable() {
            int placed = 0;

            @Override
            public void run() {
                placed = 0;

                for (int i = 0; i < blocks.size() && placed < 500; i++) {
                    Location loc = blocks.get(i);
                    if (loc.getBlock().getType() != Material.BARRIER) {
                        loc.getBlock().setType(Material.BARRIER);
                        placed++;
                    }
                }
                blocks.removeIf(loc -> loc.getBlock().getType() == Material.BARRIER);
                if (blocks.isEmpty()) {
                    this.cancel();
                }
            }
        }.runTaskTimer(ServerPlugin.getInstance(), 0L, 30L);
    }

}
