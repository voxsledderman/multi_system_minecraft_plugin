package com.xdd.serverPlugin.cuboids.camp;

import com.xdd.serverPlugin.ConstantValues;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.Utils.BorderUtils;
import com.xdd.serverPlugin.cuboids.SimpleCuboid;
import com.xdd.serverPlugin.database.dao.CampDao;
import com.xdd.serverPlugin.permissions.CampPerms;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.*;

@Getter
public class CampManager {
    private final ServerPlugin plugin = ServerPlugin.getInstance();
    @Getter private final Map<UUID, Camp> playerCampList = new HashMap<>();
    @Getter  private final Map<UUID, List<Camp>> campsWherePlayerCanTeleport = new HashMap<>();
    @Getter  private final Map<UUID, Camp> lastlyCheckedCamp = new HashMap<>();

    private final List<Camp> campsWithPlayerWarp = new ArrayList<>(); //TODO: mechanika pwarpow do dodania

    public Camp createNewCamp(Player player) throws SQLException {
        Map.Entry<Integer, Location> result = FreeCampFinder.find();
        if(result == null || result.getKey() == null || result.getValue() == null){
            throw new NullPointerException("[CampManager] Nie znaleziono wolnego obozu!");
        }
        var corners = getCorners(result.getValue());

        UUID uuid = player.getUniqueId();
        Camp camp = new Camp(corners.getUpperCorner(), corners.getBottomCorner(),
                result.getKey(), uuid, player.getName(), 1, CampPerms.getDefaultGlobalPerms(), new HashMap<>());
        camp.spawnNPCs();
        ServerPlugin.getInstance().getLocationSaveManager().setUsed(result.getKey(), true);
        BorderUtils.putBarrierBlocks(camp);
        CampDao campDao = plugin.getCampDao();
        if(campDao.playerHasCamp(player)){
            return campDao.getCampByUuid(player.getUniqueId());
        }
        campDao.save(camp);
        return camp;
    }



    public static SimpleCuboid getCorners(Location centerLocation){

       int halfSizeXZ = ConstantValues.CAMP_SIZE / 2;

        Location bottomCorner = centerLocation.clone().subtract(halfSizeXZ,ConstantValues.CAMP_DOWN_HEIGHT, halfSizeXZ);
        Location upperCorner = centerLocation.clone().add(halfSizeXZ, ConstantValues.CAMP_HEIGHT, halfSizeXZ);

        return new SimpleCuboid(upperCorner, bottomCorner);
    }

    public List<Camp> getPlayerCampsAvailableForTp(Player player){
        var camps = campsWherePlayerCanTeleport.get(player.getUniqueId());
        if(camps == null){
            // TODO: dodaj znalezienie wszystkich obozów do których nalezy gracz z bazy
            camps = new ArrayList<>();
        }
        return camps;
    }
    public Camp getCampByID(int id) throws SQLException {
       Camp camp = null;
        for(Camp camp1 : playerCampList.values()){
            if(camp1.getCampID() == id){
                camp = camp1;

            }
        }
        if(camp == null){
            camp = plugin.getCampDao().getCampById(id);
        }
        return camp;
    }

    public Camp getCampByNick(String ownerNickname) throws SQLException {
        Camp camp = null;
        for(Camp camp1 : playerCampList.values()){
            if(camp1.getOwnerName().equals(ownerNickname)){
                camp = camp1;
            }
        }
        if(camp == null){
            camp = plugin.getCampDao().getCampByNick(ownerNickname);
        }
        return camp;
    }

    public Camp getPlayerCamp(Player player){
        return playerCampList.get(player.getUniqueId());
    }
    public void registerPlayerCamp(Player player, Camp camp){
        playerCampList.put(player.getUniqueId(), camp);
    }
    public void unregisterPlayerCamp(Player player){
        playerCampList.remove(player.getUniqueId());
    }
    public void unregisterPlayerCamp(UUID uuid){
        playerCampList.remove(uuid);
    }
}
