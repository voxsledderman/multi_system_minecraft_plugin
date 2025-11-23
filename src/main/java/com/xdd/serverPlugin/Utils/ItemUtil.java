package com.xdd.serverPlugin.Utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemUtil {
    public static ItemStack addMetaToItemStack(ItemStack item, Component itemName, @Nullable List<Component> lore) {
        if(item == null) return new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(itemName);
        if(lore != null) meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
