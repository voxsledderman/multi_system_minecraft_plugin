package com.xdd.serverPlugin.database;

import com.xdd.serverPlugin.ServerPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class DatabaseManager {

    private static FileConfiguration config;

    public static void setupConfig() {
        File dataFolder = ServerPlugin.getInstance().getDataFolder();

        if (!dataFolder.exists() && !dataFolder.mkdirs()) {
            ServerPlugin.getInstance().getLogger().warning("Nie udało się utworzyć folderu pluginu!");
        }
        ServerPlugin.getInstance().saveResource("database.yml", false);

        File configFile = new File(dataFolder, "database.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public static String getPort(){
        return config.getString("database.port");
    }
    public static String getHost(){
        return config.getString("database.host");
    }
    public static String getDatabase(){
        return config.getString("database.database");
    }
    public static String getUsername(){
        return config.getString("database.username");
    }
    public static String getPassword(){
        return config.getString("database.password");
    }
}
