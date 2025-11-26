package com.xdd.serverPlugin.database.serializers.jsonAdapters;

import com.google.gson.*;
import com.xdd.serverPlugin.database.data.subdatas.economy.Money;

import java.lang.reflect.Type;

public class MoneyAdapter implements JsonSerializer<Money>, JsonDeserializer<Money> {

    @Override
    public JsonElement serialize(Money src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getDouble());
    }

    @Override
    public Money deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        double value = json.getAsDouble();
        return new Money(value);
    }
}
