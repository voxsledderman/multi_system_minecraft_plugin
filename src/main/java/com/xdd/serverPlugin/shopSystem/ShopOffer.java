package com.xdd.serverPlugin.shopSystem;

import com.xdd.serverPlugin.shopSystem.enums.ShopCategory;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.jetbrains.annotations.Nullable;

@Getter @Setter
public class ShopOffer {

    private final Material material;
    private final ShopCategory category;
    private final double buyPrice;
    private final double sellPrice;
    private final boolean nextPage;
    @Nullable private final Enchantment enchantment;
    private final int enchantmentLvl;

    public ShopOffer(Material material, ShopCategory category, double buyPrice, double sellPrice, boolean nextPage, @Nullable Enchantment enchantment, int enchantmentLvl) {
        this.material = material;
        this.category = category;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.nextPage = nextPage;
        this.enchantment = enchantment;
        this.enchantmentLvl = enchantmentLvl;
    }
    public ItemStack getItemStack(int amount){
        ItemStack item = new ItemStack(material, amount);
        if(enchantment == null || enchantmentLvl == 0) return item;
        if(material.equals(Material.ENCHANTED_BOOK)){
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
            meta.addStoredEnchant(enchantment, enchantmentLvl, true);
            item.setItemMeta(meta);
        } else {
           item.addEnchantment(enchantment, enchantmentLvl);
        }

        return item;
    }
}
