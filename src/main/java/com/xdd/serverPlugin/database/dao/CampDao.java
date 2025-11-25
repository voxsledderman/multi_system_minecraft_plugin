package com.xdd.serverPlugin.database.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.database.data.CampData;
import com.xdd.serverPlugin.database.serializers.CampSerializer;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;

@Getter
public class CampDao {

    private final ServerPlugin plugin = ServerPlugin.getInstance();
    private final Dao<CampData, Integer> campDataDao;

    public CampDao() throws SQLException {
        ConnectionSource connectionSource = plugin.getDatabaseConnection().getConnectionSource();
        TableUtils.createTableIfNotExists(connectionSource, CampData.class);
        this.campDataDao = DaoManager.createDao(connectionSource, CampData.class);
    }

    public void save(Camp camp) throws SQLException {
        campDataDao.createOrUpdate(CampSerializer.serialize(camp));
    }
    public void deletePermanently(Camp camp) throws SQLException{
        campDataDao.delete(CampSerializer.serialize(camp));
    }
    public boolean playerHasCamp(Player player) throws SQLException {
        return getCampByUuid(player.getUniqueId()) != null;
    }

    public Camp getCampById(int id) throws SQLException {
        return CampSerializer.deserialize(campDataDao.queryForId(id));
    }
    public Camp getCampByNick(String ownerNick) throws SQLException {
        return CampSerializer.deserialize(campDataDao.queryBuilder()
                .where()
                .eq("owner_nick", ownerNick)
                .queryForFirst());
    }
    public Camp getCampByUuid(UUID uuid) throws SQLException {
        CampData campData = campDataDao.queryBuilder()
                .where()
                .eq("owner_uuid", uuid.toString())
                .queryForFirst();
        if(campData == null) return null;
        return CampSerializer.deserialize(campData);
    }
}
