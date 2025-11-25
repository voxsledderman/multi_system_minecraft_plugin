package com.xdd.serverPlugin.database.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xdd.serverPlugin.cuboids.SimpleCuboid;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.cuboids.camp.CampManager;
import com.xdd.serverPlugin.database.data.CampData;
import com.xdd.serverPlugin.database.serializers.jsonAdapters.LocationAdapter;
import com.xdd.serverPlugin.database.serializers.jsonAdapters.UuidNickAdapter;
import com.xdd.serverPlugin.records.UuidNick;
import org.bukkit.Location;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public class CampSerializer {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Location.class, new LocationAdapter())
//            .registerTypeAdapter(NPC.class, new NpcAdapter())
            .registerTypeAdapter(UuidNick.class, new UuidNickAdapter())
            .create();


    public static CampData serialize(Camp camp){
        return new CampData(camp.getCampID(), gson.toJson(camp.getSpawnLocation(), Location.class),
                camp.getOwnerUUID().toString(), camp.getOwnerName(), (camp.getCampLevel().ordinal() + 1),
                gson.toJson(camp.getPermissions(), new TypeToken<List<String>>(){}.getType()),
                gson.toJson(camp.getPermissionsPerPlayer(), new TypeToken<Map<UuidNick, List<String>>>(){}.getType()));
    }

    public static Camp deserialize(CampData campData){
        Location spawnLocation = gson.fromJson(campData.getSpawnLocation(), Location.class);
        SimpleCuboid simpleCuboid = CampManager.getCorners(spawnLocation);

        List<String> globalPerms = gson.fromJson(campData.getGlobalPerms(),
                new TypeToken<List<String>>(){}.getType());

        Map<UuidNick, List<String>> personalPerms = gson.fromJson(campData.getPersonalPerms(),
                new TypeToken<Map<UuidNick, List<String>>>(){}.getType());

        return new Camp(
                simpleCuboid.getUpperCorner(),
                simpleCuboid.getBottomCorner(),
                campData.getId(),
                UUID.fromString(campData.getOwnerUuid()),
                campData.getOwnerNick(),
                campData.getCampLevel(),
                globalPerms,
                personalPerms
        );
    }

}

