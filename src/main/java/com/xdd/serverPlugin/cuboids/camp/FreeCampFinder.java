package com.xdd.serverPlugin.cuboids.camp;

import com.xdd.serverPlugin.ServerPlugin;
import org.bukkit.Location;

import java.util.*;

public class FreeCampFinder {
    private static final LocationSaveManager locationSaveManager = ServerPlugin.getInstance().getLocationSaveManager();

    public static Map.Entry<Integer, Location> find() {
        Map<Integer, Location> all = locationSaveManager.getAllLocations();

        List<Integer> ids = new ArrayList<>(all.keySet());
        ids.sort(Collections.reverseOrder());

        for (int id : ids) {
            if (!locationSaveManager.isUsed(id)) {
                return new AbstractMap.SimpleEntry<>(id, all.get(id));
            }
        }
        return null;
    }

}
