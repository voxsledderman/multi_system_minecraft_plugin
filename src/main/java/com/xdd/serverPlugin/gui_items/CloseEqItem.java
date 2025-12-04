package com.xdd.serverPlugin.gui_items;

import com.xdd.serverPlugin.Utils.GuiUtils;
import com.xdd.serverPlugin.Utils.Sounds;
import com.xdd.serverPlugin.Utils.SpecificItems;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.impl.SimpleItem;

public class CloseEqItem extends SimpleItem {

    public CloseEqItem(boolean needIcon) {
        super(needIcon ? SpecificItems.FromGUI.closeEqItem() : GuiUtils.getInvisibleItem("Zamknij sklep", TextColor.color(0xFF7070)));
    }


    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        player.closeInventory();
        Sounds.playGuiCloseSound(player);
    }
}
