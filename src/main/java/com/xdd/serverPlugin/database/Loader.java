package com.xdd.serverPlugin.database;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.cache.CacheManager;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.database.dao.PlayerDao;
import com.xdd.serverPlugin.database.data.PlayerData;
import com.xdd.serverPlugin.database.data.PlayerJsonData;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class Loader {
    public static void loadCampData(Player player, ServerPlugin plugin) throws SQLException {
        Camp camp = plugin.getCampDao().getCampByUuid(player.getUniqueId());
        if(camp != null) plugin.getCampManager().registerPlayerCamp(player, camp);
    }
    public static void loadPlayerData(Player player, ServerPlugin plugin) throws SQLException {
        CacheManager manager = plugin.getCacheManager();
        PlayerJsonData playerJsonData = plugin.getPlayerDao().getPlayerData(player);
        PlayerData playerData;
        if(playerJsonData == null) {
            playerData = PlayerData.getDefaultPlayerData(player);
            manager.addToDataMap(player, playerData);
            PlayerDao playerDao = new PlayerDao();
            playerDao.save(playerData);
            return;
        }
        playerData = playerJsonData.convertToPlayerData(player);
        manager.addToDataMap(player, playerData);
    }
}
