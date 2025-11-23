package com.xdd.serverPlugin.cuboids.camp.generator;

import com.xdd.serverPlugin.ConstantValues;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.locations.ServerWorlds;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FreePlaceFinder {

    private static final World WORLD = ServerWorlds.CAMP_WORLD.getWorld();

    /**
     * Zwraca centrum obozu w siatce na podstawie indeksu.
     * index = 0 → centrum siatki
     */
    public static Location getGridCenter(int index) {
        int step = ConstantValues.CAMP_SIZE + ConstantValues.GAP_BETWEEN_CAMPS;

        if (index == 0) {
            return new Location(WORLD, 0.5, ConstantValues.CENTER_CAMP_Y, 0.5);
        }

        int gridSize = (int) Math.ceil(Math.sqrt(index + 1));
        int row = index / gridSize;
        int col = index % gridSize;

        double offsetX = (col - gridSize / 2.0) * step;
        double offsetZ = (row - gridSize / 2.0) * step;

        return new Location(WORLD, offsetX + 0.5, ConstantValues.CENTER_CAMP_Y, offsetZ + 0.5);
    }

    /**
     * Wygeneruj listę centrów obozów w siatce z centralnym punktem.
     */
    public static List<Location> generateGridCenters(int count) {
        List<Location> centers = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            centers.add(getGridCenter(i));
        }
        return centers;
    }

    /**
     * Znajduje najbliższe wolne miejsce na nowy obóz.
     *
     * @return lokalizacja najbliższego wolnego centrum
     */
    public static Location findNextFreeCenter() {
        Set<String> occupied = new HashSet<>();
        for (Location loc : ServerPlugin.getInstance().getLocationSaveManager().getAllLocations().values()) {
            occupied.add(key(loc));
        }

        int index = 0;
        while (true) {
            Location candidate = getGridCenter(index);
            if (!occupied.contains(key(candidate))) {
                return candidate;
            }
            index++;
        }
    }

    /**
     * Zwraca lokalizację na podstawie ID obozu (numer 1..x)
     */
    public static Location getLocationById(int campId) {
        int index = campId - 1; // id = 1 → index 0
        return getGridCenter(index);
    }


    private static String key(Location loc) {
        return loc.getBlockX() + ":" + loc.getBlockZ();
    }
}
