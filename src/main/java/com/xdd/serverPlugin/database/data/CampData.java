package com.xdd.serverPlugin.database.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@DatabaseTable(tableName = "camp")
public class CampData {

    @DatabaseField(columnName = "id", id = true)
    private int id;

    @DatabaseField(columnName = "spawn_location", unique = true)
    private String spawnLocation;

    @DatabaseField(columnName = "owner_uuid", unique = true)
    private String ownerUuid;

    @DatabaseField(columnName = "owner_nick", unique = true)
    private String ownerNick;

    @DatabaseField(columnName = "lvl")
    private int campLevel;

    @DatabaseField(columnName = "global_perms")
    private String globalPerms;

    @DatabaseField(columnName  = "personal_perms")
    private String personalPerms;

//    @DatabaseField(columnName = "npcs")
//    private String npcs;

    public CampData(){}

    public CampData(int id, String spawnLocation, String ownerUuid, String ownerNick, int campLevel, String globalPerms, String personalPerms) {
        this.id = id;
        this.spawnLocation = spawnLocation;
        this.ownerUuid = ownerUuid;
        this.ownerNick = ownerNick;
        this.campLevel = campLevel;
        this.globalPerms = globalPerms;
        this.personalPerms = personalPerms;
    }

}
