package com.xdd.serverPlugin.database.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.database.data.PlayerData;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.sql.SQLException;
@Getter
public class PlayerDao {

    private final ServerPlugin plugin = ServerPlugin.getInstance();
    private final Dao<PlayerData, String> playerDataDao;

    public PlayerDao() throws SQLException {
        ConnectionSource connectionSource = plugin.getDatabaseConnection().getConnectionSource();
        TableUtils.createTableIfNotExists(connectionSource, PlayerData.class);
        this.playerDataDao = DaoManager.createDao(connectionSource, PlayerData.class);
    }

    public void save(Player player) throws SQLException {
        playerDataDao.createOrUpdate(new PlayerData());
    }
    public boolean playerExists(Player player) throws SQLException {
        return playerDataDao.idExists(player.getUniqueId().toString());
    }

    public PlayerData getPlayerData(Player player) throws SQLException {
        return playerDataDao.queryForId(player.getUniqueId().toString());
    }
}

