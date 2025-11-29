package com.xdd.serverPlugin.shopSystem;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.xdd.serverPlugin.shopSystem.enums.ShopCategory;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;

import java.io.IOException;

public class ShopOfferTypeAdapter extends TypeAdapter<ShopOffer> {

    @Override
    public void write(JsonWriter out, ShopOffer offer) throws IOException {
        out.beginObject();
        out.name("material").value(offer.getMaterial().name());
        out.name("category").value(offer.getCategory().name());
        out.name("buyPrice").value(offer.getBuyPrice());
        out.name("sellPrice").value(offer.getSellPrice());
        out.name("nextPage").value(offer.isNextPage());

        if (offer.getEnchantment() != null) {
            out.name("enchantment").value(offer.getEnchantment().getKey().toString());
            if (offer.getEnchantmentLvl() > 0) {
                out.name("enchantmentLvl").value(offer.getEnchantmentLvl());
            }
        }

        out.endObject();
    }

    @Override
    public ShopOffer read(JsonReader in) throws IOException {
        Material material = null;
        ShopCategory category = null;
        double buyPrice = 0;
        double sellPrice = 0;
        boolean nextPage = false;

        Enchantment enchantment = null;
        int enchantmentLvl = 0;

        try {
            in.beginObject();
            while (in.hasNext()) {
                String name = in.nextName();
                switch (name) {
                    case "material": {
                        String matName = in.nextString();
                        try {
                            material = Material.valueOf(matName);
                        } catch (IllegalArgumentException ex) {
                            material = null;
                            Bukkit.getLogger().warning("[Shop] Nieprawidłowy material w shop-offers.json: '" + matName + "' — pomijam ofertę.");
                        }
                        break;
                    }

                    case "category": {
                        String catName = in.nextString();
                        try {
                            category = ShopCategory.valueOf(catName);
                        } catch (IllegalArgumentException ex) {
                            category = null;
                            Bukkit.getLogger().warning("[Shop] Nieprawidłowa kategoria w shop-offers.json: '" + catName + "' — pomijam ofertę.");
                        }
                        break;
                    }

                    case "buyPrice":
                        try {
                            buyPrice = in.nextDouble();
                        } catch (Exception ex) {
                            try {
                                in.skipValue();
                            } catch (Exception ignored) {}
                            buyPrice = 0;
                            Bukkit.getLogger().warning("[Shop] Nieprawidłowa wartość buyPrice — ustawiam 0.");
                        }
                        break;

                    case "sellPrice":
                        try {
                            sellPrice = in.nextDouble();
                        } catch (Exception ex) {
                            try {
                                in.skipValue();
                            } catch (Exception ignored) {}
                            sellPrice = 0;
                            Bukkit.getLogger().warning("[Shop] Nieprawidłowa wartość sellPrice — ustawiam 0.");
                        }
                        break;

                    case "nextPage":
                        try {
                            nextPage = in.nextBoolean();
                        } catch (Exception ex) {
                            try {
                                in.skipValue();
                            } catch (Exception ignored) {}
                            nextPage = false;
                        }
                        break;

                    case "enchantment": {
                        String enchantKey = in.nextString();

                        try {
                            NamespacedKey key = NamespacedKey.fromString(enchantKey);

                            if (key == null) {
                                Bukkit.getLogger().warning("[Shop] Nieprawidłowy NamespacedKey enchantu: '" + enchantKey + "' — pomijam enchant.");
                                enchantment = null;
                                break;
                            }

                            final Registry<Enchantment> enchantmentRegistry = RegistryAccess
                                    .registryAccess()
                                    .getRegistry(RegistryKey.ENCHANTMENT);

                            Enchantment found = enchantmentRegistry.get(RegistryKey.ENCHANTMENT.typedKey(key));

                            if (found == null) {
                                Bukkit.getLogger().warning("[Shop] Enchant nie istnieje w 1.21.4: '" + enchantKey + "' — pomijam enchant.");
                                enchantment = null;
                            } else {
                                enchantment = found;
                            }

                        } catch (Exception ex) {
                            Bukkit.getLogger().warning("[Shop] Błąd podczas ładowania enchantu '" + enchantKey + "' — pomijam enchant.");
                            enchantment = null;
                        }

                        break;
                    }
                    case "enchantmentLvl":
                        try {
                            enchantmentLvl = in.nextInt();
                            if (enchantmentLvl < 1) {
                                Bukkit.getLogger().warning("[Shop] enchantmentLvl < 1 — ustawiam leve na 1.");
                                enchantmentLvl = 1;
                            }
                        } catch (Exception ex) {
                            in.skipValue();
                            enchantmentLvl = 1;
                            Bukkit.getLogger().warning("[Shop] Nieprawidłowy enchantmentLvl — ustawiam 1.");
                        }
                        break;

                    default:
                        in.skipValue();
                        break;
                }
            }

            in.endObject();
        } catch (Exception e) {
            try {
                in.endObject();
            } catch (Exception ignored) {}
            Bukkit.getLogger().warning("[Shop] Nie udało się wczytać oferty (błąd JSON) — pomijam wpis: " + e.getMessage());
            return null;
        }
        if (material == null || category == null) {
            return null;
        }
        return new ShopOffer(material, category, buyPrice, sellPrice, nextPage, enchantment, enchantmentLvl);
    }
}
