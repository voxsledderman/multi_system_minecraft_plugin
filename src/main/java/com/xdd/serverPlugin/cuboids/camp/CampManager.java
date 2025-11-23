package com.xdd.serverPlugin.cuboids.camp;

import com.xdd.serverPlugin.ConstantValues;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.Utils.BorderUtils;
import com.xdd.serverPlugin.cuboids.SimpleCuboid;
import com.xdd.serverPlugin.permissions.CampPerms;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
public class CampManager {
    @Getter private final Map<UUID, Camp> playerCampList = new HashMap<>();
    @Getter  private final Map<UUID, List<Camp>> campsWherePlayerCanTeleport = new HashMap<>();
    @Getter  private final Map<UUID, Camp> lastlyCheckedCamp = new HashMap<>(); // TODO: usuń cache przy wyjsciu gracza z serwa

    private final List<Camp> campsWithPlayerWarp = new ArrayList<>(); //TODO: mechanika pwarpow do dodania

    public Camp createNewCamp(Player player){
        Map.Entry<Integer, Location> result = FreeCampFinder.find();
        if(result == null || result.getKey() == null || result.getValue() == null){
            throw new NullPointerException("[CampManager] Nie znaleziono wolnego obozu!");
        }
        var corners = getCorners(result.getValue());

        UUID uuid = player.getUniqueId();
        Camp camp = new Camp(corners.getUpperCorner(), corners.getBottomCorner(),
                result.getKey(), uuid, player.getName(), 1, CampPerms.getDefaultGlobalPerms());
        camp.spawnNPCs();
        ServerPlugin.getInstance().getLocationSaveManager().setUsed(result.getKey(), true);

        BorderUtils.putBarrierBlocks(camp);
        return camp;
    }



    private SimpleCuboid getCorners(Location centerLocation){

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
    public Camp getCampByID(int id){
       Camp camp = null;
        for(Camp camp1 : playerCampList.values()){
            if(camp1.getCampID() == id){
                camp = camp1;

            }
        }
        if(camp == null){
            // TODO: dodaj odczyt id z bazy
        }
        return camp;
    }

    public Camp getCampByNick(String ownerNickname){
        Camp camp = null;
        for(Camp camp1 : playerCampList.values()){
            if(camp1.getOwnerName().equals(ownerNickname)){
                camp = camp1;
            }
        }
        if(camp == null){
            // TODO: dodaj odczyt nicku z bazy
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
