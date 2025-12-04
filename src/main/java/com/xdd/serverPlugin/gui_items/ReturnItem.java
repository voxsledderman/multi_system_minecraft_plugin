package com.xdd.serverPlugin.gui_items;

import com.xdd.serverPlugin.Menu;
import com.xdd.serverPlugin.Utils.Sounds;
import com.xdd.serverPlugin.Utils.SpecificItems;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.impl.SimpleItem;


public class ReturnItem extends SimpleItem {

    private final Menu menu;

    public ReturnItem(@NotNull Menu menu) {
        super(SpecificItems.FromGUI.returnItem());
        this.menu = menu;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        super.handleClick(clickType, player, event);

        Sounds.playGuiClickSound(player);
        menu.openMenu(player);
    }
}
