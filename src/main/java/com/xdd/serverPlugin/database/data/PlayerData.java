package com.xdd.serverPlugin.database.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@DatabaseTable(tableName = "player")
public class PlayerData {

    @DatabaseField(id = true, columnName = "uuid")
    private String playerUuid;

    @DatabaseField(columnName = "nickname", unique = true)
    private String playerNick;

    @DatabaseField(columnName = "economy_data")
    private String economyData;

    @DatabaseField(columnName = "member_of_camps_id")
    private String memberOfCampsWithID;

    public PlayerData(){}
}
