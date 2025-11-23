package com.xdd.serverPlugin.records;

import org.bukkit.entity.Player;

import java.util.UUID;

public record UuidNick(UUID uuid, String nick) {

    public static UuidNick of(Player player) {
        return new UuidNick(player.getUniqueId(), player.getName());
    }

}
