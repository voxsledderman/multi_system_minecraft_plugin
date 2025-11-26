package com.xdd.serverPlugin.database.data.subdatas.economy;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class EconomyData {

    private final Player player;
    private final Money money;


    public EconomyData(Player player, double money) {
        this.player = player;
        this.money = new Money(money);
    }
}
