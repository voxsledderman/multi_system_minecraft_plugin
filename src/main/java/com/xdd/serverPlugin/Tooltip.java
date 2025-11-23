package com.xdd.serverPlugin;

public enum Tooltip {

    WOODEN("minecraft:material"),
    COMMON("minecraft:common"),
    UNCOMMON("minecraft:uncommon"),
    RARE("minecraft:rare"),
    EPIC("minecraft:epic"),
    LEGENDARY("minecraft:legendary");

    private final String key;


    Tooltip(String key) {
        this.key = key;
    }
    public String getKey(){
        return this.key;
    }
}
