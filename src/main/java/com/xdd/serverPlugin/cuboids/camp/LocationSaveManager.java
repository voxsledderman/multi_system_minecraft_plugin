package com.xdd.serverPlugin.cuboids.camp;

import com.xdd.serverPlugin.ServerPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LocationSaveManager {

    private final ServerPlugin plugin;
    private File file;
    private FileConfiguration config;

    public LocationSaveManager() {
        this.plugin = ServerPlugin.getInstance();
        setupFile();
    }

    private void setupFile() {
        file = new File(plugin.getDataFolder(), "used-locations.yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public int addLocation(Location loc, boolean used) {
        int nextId = getNextId();
        config.set("used-locations." + nextId + ".world", loc.getWorld().getName());
        config.set("used-locations." + nextId + ".x", loc.getBlockX());
        config.set("used-locations." + nextId + ".y", loc.getBlockY());
        config.set("used-locations." + nextId + ".z", loc.getBlockZ());
        config.set("used-locations." + nextId + ".used", used);
        save();
        return nextId;
    }

    public Location getLocation(int id) {
        if (!config.contains("used-locations." + id)) return null;
        String worldName = config.getString("used-locations." + id + ".world");
        if(worldName == null) return null;
        World world = Bukkit.getWorld(worldName);
        if (world == null) return null;
        int x = config.getInt("used-locations." + id + ".x");
        int y = config.getInt("used-locations." + id + ".y");
        int z = config.getInt("used-locations." + id + ".z");
        return new Location(world, x, y, z);
    }

    public boolean isUsed(int id) {
        return config.getBoolean("used-locations." + id + ".used", false);
    }

    public void setUsed(int id, boolean used) {
        config.set("used-locations." + id + ".used", used);
        save();
    }

    public Map<Integer, Location> getAllLocations() {
        Map<Integer, Location> map = new HashMap<>();
        if (!config.contains("used-locations")) return map;

        for (String key : config.getConfigurationSection("used-locations").getKeys(false)) {
            try {
                int id = Integer.parseInt(key);
                Location loc = getLocation(id);
                if (loc != null) map.put(id, loc);
            } catch (NumberFormatException ignored) {}
        }
        return map;
    }

    public int getIdByLocation(Location loc) {
        if (loc == null || loc.getWorld() == null) return -1;

        for (String key : config.getConfigurationSection("used-locations").getKeys(false)) {
            int id = Integer.parseInt(key);
            String worldName = config.getString("used-locations." + id + ".world");
            int x = config.getInt("used-locations." + id + ".x");
            int y = config.getInt("used-locations." + id + ".y");
            int z = config.getInt("used-locations." + id + ".z");

            if (worldName == null) continue;

            if (worldName.equals(loc.getWorld().getName())
                    && x == loc.getBlockX()
                    && y == loc.getBlockY()
                    && z == loc.getBlockZ()) {
                return id;
            }
        }
        return -1;
    }

    private int getNextId() {
        if (!config.contains("used-locations")) return 1;
        int max = 0;
        for (String key : config.getConfigurationSection("used-locations").getKeys(false)) {
            try {
                int id = Integer.parseInt(key);
                if (id > max) max = id;
            } catch (NumberFormatException ignored) {}
        }
        return max + 1;
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }
}