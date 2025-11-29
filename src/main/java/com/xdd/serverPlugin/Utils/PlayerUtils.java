package com.xdd.serverPlugin.Utils;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerUtils {

    public static boolean canPlayerInventoryFit(Inventory inventory, ItemStack itemToAdd) {
        int amountLeftToCheck = itemToAdd.getAmount();

        ItemStack[] storageContents = inventory.getStorageContents();

        for (ItemStack itemInSlot : storageContents) {
            if (itemInSlot == null || itemInSlot.getType() == Material.AIR) {
                int maxStackSize = Math.min(itemToAdd.getMaxStackSize(), inventory.getMaxStackSize());
                amountLeftToCheck -= maxStackSize;
            }
            else if (itemInSlot.isSimilar(itemToAdd)) {
                int maxStackSize = Math.min(itemInSlot.getMaxStackSize(), inventory.getMaxStackSize());
                int spaceInStack = maxStackSize - itemInSlot.getAmount();

                if (spaceInStack > 0) {
                    amountLeftToCheck -= spaceInStack;
                }
            }
            if (amountLeftToCheck <= 0) {
                return true;
            }
        }
        return false;
    }
    public static boolean doesPlayerHaveItemInEq(Inventory inventory, ItemStack requiredItem) {
        if (requiredItem == null || requiredItem.getType() == Material.AIR) {
            return false;
        }
        return inventory.containsAtLeast(requiredItem, requiredItem.getAmount());
    }
}
