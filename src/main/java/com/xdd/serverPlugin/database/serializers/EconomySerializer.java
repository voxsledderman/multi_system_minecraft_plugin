package com.xdd.serverPlugin.database.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xdd.serverPlugin.database.data.subdatas.economy.EconomyData;
import com.xdd.serverPlugin.database.data.subdatas.economy.Money;
import com.xdd.serverPlugin.database.serializers.jsonAdapters.MoneyAdapter;
import org.bukkit.entity.Player;

public class EconomySerializer {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Money.class, new MoneyAdapter())
            .create();

    public static EconomyData deserialize(Player player, String str){
        return new EconomyData(player, gson.fromJson(str, Double.class));
    }

    public static String serialize(EconomyData data){
        Money money = data.getMoney();
        return gson.toJson(money);
    }
}
