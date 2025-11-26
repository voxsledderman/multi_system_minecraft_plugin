package com.xdd.serverPlugin.database.data;

import com.xdd.serverPlugin.database.data.subdatas.economy.EconomyData;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class PlayerData {
    private final UUID uuid;
    private final String nickname;
    private final EconomyData economyData;
    private final List<Integer> campsID;

    public PlayerData(UUID uuid, String nickname, EconomyData economyData, List<Integer> campsID) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.economyData = economyData;
        this.campsID = campsID;
    }

    public static PlayerData getDefaultPlayerData(Player player){
        return new PlayerData(player.getUniqueId(), player.getName(), new EconomyData(player, 500), new ArrayList<>());
    }
}
