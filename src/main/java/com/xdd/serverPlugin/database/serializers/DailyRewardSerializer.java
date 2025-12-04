package com.xdd.serverPlugin.database.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xdd.serverPlugin.database.data.subdatas.DailyRewardData;
import com.xdd.serverPlugin.database.serializers.jsonAdapters.LocalDateAdapter;

import java.time.LocalDate;


public class DailyRewardSerializer {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();


    public static String serialize(DailyRewardData data){
        return gson.toJson(data);
    }

    public static DailyRewardData deserialize(String str){
        return gson.fromJson(str, DailyRewardData.class);
    }
}
