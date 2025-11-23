package com.xdd.serverPlugin.gui_items;

import com.xdd.serverPlugin.Utils.GuiUtils;
import com.xdd.serverPlugin.SpecificItems;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.impl.SimpleItem;

public class CloseEqItem extends SimpleItem {

    public CloseEqItem() {
        super(SpecificItems.FromGUI.closeEqItem());
    }


    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        player.closeInventory();
        GuiUtils.playGuiCloseSound(player);
    }
}
