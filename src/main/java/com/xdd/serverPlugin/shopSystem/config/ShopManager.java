package com.xdd.serverPlugin.shopSystem.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.shopSystem.ShopOffer;
import com.xdd.serverPlugin.shopSystem.ShopOfferTypeAdapter;
import com.xdd.serverPlugin.shopSystem.enums.ShopCategory;
import lombok.Getter;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShopManager {

    private final ServerPlugin plugin = ServerPlugin.getInstance();
    private final File file;
    private final Gson gson;

    @Getter private List<ShopOffer> offers;

    public ShopManager() {
        this.file = new File(plugin.getDataFolder(), "shop-offers.json");
        this.gson = new GsonBuilder()
                .registerTypeAdapter(ShopOffer.class, new ShopOfferTypeAdapter()).setPrettyPrinting().create();
        load();
    }

    public void load() {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();

                offers = new ArrayList<>();
                save();
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            Type listType = new TypeToken<List<ShopOffer>>() {}.getType();
            offers = gson.fromJson(reader, listType);
            if (offers == null) offers = new ArrayList<>();
            reader.close();
            offers.removeIf(Objects::isNull);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void save() {
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(offers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addOffer(ShopOffer offer) {
        offers.add(offer);
        save();
    }

    public void removeOffer(ShopOffer offer) {
        offers.remove(offer);
        save();
    }

    public void clear() {
        offers.clear();
        save();
    }
    public boolean reload() {
        try {
            if (!file.exists()) {
                plugin.getLogger().warning("shop.json nie istnieje – tworzę nowy pusty plik.");
                file.getParentFile().mkdirs();
                file.createNewFile();
                offers = new ArrayList<>();
                save();
                return true;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));

            Type listType = new TypeToken<List<ShopOffer>>() {}.getType();
            List<ShopOffer> loaded = gson.fromJson(reader, listType);

            if (loaded == null) {
                loaded = new ArrayList<>();
            }

            this.offers = loaded;

            reader.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ShopOffer> getCategoryOffers(ShopCategory category){
        List<ShopOffer> newOffers = new ArrayList<>();

        for(ShopOffer offer : offers){
            if(offer.getCategory().equals(category)) newOffers.add(offer);
        }
        return newOffers;
    }

}
