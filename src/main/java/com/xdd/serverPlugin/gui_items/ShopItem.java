package com.xdd.serverPlugin.gui_items;

import com.xdd.serverPlugin.shopSystem.ShopActions;
import com.xdd.serverPlugin.shopSystem.ShopOffer;
import com.xdd.serverPlugin.shopSystem.menus.MainShopMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.impl.SimpleItem;

public class ShopItem extends SimpleItem {

    private final ShopOffer shopOffer;
    private final MainShopMenu menu;

    public ShopItem(@NotNull ItemProvider itemProvider, ShopOffer shopOffer, MainShopMenu menu){
        super(itemProvider);
        this.shopOffer = shopOffer;
        this.menu = menu;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        switch (clickType){
            case LEFT -> ShopActions.buyItem(shopOffer, player, 1, menu);
            case RIGHT -> ShopActions.sellItem(shopOffer, player, 1, menu);
            case SHIFT_LEFT, SHIFT_RIGHT -> ShopActions.choseAmount(shopOffer, player, menu);
        }
    }
}
