package com.xdd.serverPlugin.gui_items;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.Utils.GuiUtils;
import com.xdd.serverPlugin.shopSystem.ShopActions;
import com.xdd.serverPlugin.shopSystem.ShopOffer;
import com.xdd.serverPlugin.shopSystem.menus.MainShopMenu;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
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
            case LEFT -> {
                if(!ShopActions.buyItem(shopOffer, player, 1, menu)){
                    changeItemForBarrier(event);
                }
            }
            case RIGHT -> {
                if(!ShopActions.sellItem(shopOffer, player, 1, menu)){
                    changeItemForBarrier(event);
                }
            }
            case SHIFT_LEFT, SHIFT_RIGHT -> ShopActions.choseAmount(shopOffer, player, menu);
        }
    }

    private void changeItemForBarrier(InventoryClickEvent e){
        final ItemStack cantDoThatIcon = new ItemBuilder(GuiUtils.alert2Icon).setDisplayName(
                new AdventureComponentWrapper(MiniMessage.miniMessage().deserialize("<red>Nie możesz tego zrobić!"))).get();
        final ItemStack oldItem = e.getCurrentItem();
        if(cantDoThatIcon.isSimilar(oldItem)) return;
        final Inventory clickedInv = e.getClickedInventory();
        final int clickedSlot = e.getRawSlot();

       if(clickedInv == null) return;
       clickedInv.setItem(clickedSlot, cantDoThatIcon);

        Bukkit.getScheduler().runTaskLater(ServerPlugin.getInstance(), bukkitTask -> clickedInv.setItem(clickedSlot ,oldItem), 35);
    }
}
