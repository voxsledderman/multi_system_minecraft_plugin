package com.xdd.serverPlugin.database.serializers.jsonAdapters;

import com.google.gson.*;
import com.xdd.serverPlugin.records.UuidNick;

import java.lang.reflect.Type;
import java.util.UUID;

public class UuidNickAdapter implements JsonSerializer<UuidNick>, JsonDeserializer<UuidNick> {
    @Override
    public JsonElement serialize(UuidNick src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }

    @Override
    public UuidNick deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        String[] parts = json.getAsString().split(":");
        return new UuidNick(UUID.fromString(parts[0]), parts[1]);
    }
}


