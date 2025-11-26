package com.xdd.serverPlugin.database.serializers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IdCampSerializer {
    private static final Gson gson = new Gson();
    private static final Type LIST_OF_INTEGER_TYPE = new TypeToken<List<Integer>>(){}.getType();

    public static List<Integer> deserialize(String jsonString){
        if (jsonString == null || jsonString.isEmpty()){
            return new ArrayList<>();
        }
        try {
            List<Integer> campIDs = gson.fromJson(jsonString, LIST_OF_INTEGER_TYPE);
            return campIDs != null ? campIDs : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Błąd deserializacji listy ID obozów: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    public static String serialize(List<Integer> campIDs){
        return gson.toJson(campIDs, LIST_OF_INTEGER_TYPE);
    }

}
