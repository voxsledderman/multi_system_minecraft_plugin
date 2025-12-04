package com.xdd.serverPlugin.database.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.xdd.serverPlugin.database.data.subdatas.DailyRewardData;
import com.xdd.serverPlugin.database.data.subdatas.economy.EconomyData;
import com.xdd.serverPlugin.database.serializers.DailyRewardSerializer;
import com.xdd.serverPlugin.database.serializers.EconomySerializer;
import com.xdd.serverPlugin.database.serializers.IdCampSerializer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

@Getter @Setter
@DatabaseTable(tableName = "player")
public class PlayerJsonData {

    @DatabaseField(id = true, columnName = "uuid")
    private String playerUuid;

    @DatabaseField(columnName = "nickname", unique = true)
    private String playerNick;

    @DatabaseField(columnName = "economy_data")
    private String economyData;

    @DatabaseField(columnName = "daily_reward_data")
    private String dailyRewardData;

    @DatabaseField(columnName = "member_of_camps_id")
    private String memberOfCampsWithID;

    public PlayerJsonData(){}

    public PlayerJsonData(UUID playerUuid, String playerNick, EconomyData economyData, DailyRewardData dailyRewardData,
                          List<Integer> memberOfCampsWithID){
        this.playerUuid = playerUuid.toString();
        this.playerNick = playerNick;
        this.economyData = EconomySerializer.serialize(economyData);
        this.dailyRewardData = DailyRewardSerializer.serialize(dailyRewardData);
        this.memberOfCampsWithID = IdCampSerializer.serialize(memberOfCampsWithID);
    }

    public PlayerData convertToPlayerData(Player player){
        return new PlayerData(UUID.fromString(playerUuid), playerNick,
                EconomySerializer.deserialize(player, economyData), DailyRewardSerializer.deserialize(dailyRewardData),IdCampSerializer.deserialize(memberOfCampsWithID));
    }
}
