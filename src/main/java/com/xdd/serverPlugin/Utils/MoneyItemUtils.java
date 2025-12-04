package com.xdd.serverPlugin.Utils;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.database.data.subdatas.economy.EconomyData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class MoneyItemUtils {
    public static NamespacedKey key = NamespacedKey.fromString("money_value", ServerPlugin.getInstance());

    public static ItemStack getMoneyItem(double moneyAmount){
        ItemStack item = GuiUtils.getMoneyItem().clone();
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(Format.formatMoney(moneyAmount)  + "$", TextColor.color(0x389B4A)));
        meta.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, moneyAmount);
        item.setItemMeta(meta);

        return item;
    }
    public static boolean addMoneyFromItem(ItemStack item, Player player){
        if(!item.getPersistentDataContainer().has(key)) return false;
        Double moneyAmountObj = item.getPersistentDataContainer().get(key, PersistentDataType.DOUBLE);
        if (moneyAmountObj == null) {
            return false;
        }
        double moneyAmount = moneyAmountObj;
        if(player == null) return false;

        final EconomyData economyData = ServerPlugin.getInstance().getCacheManager().getPlayerData(player).getEconomyData();
        economyData.getMoney().addMoney(moneyAmount);
        return true;
    }
}
