package com.xdd.serverPlugin.gui_items;

import com.xdd.serverPlugin.SpecificItems;
import com.xdd.serverPlugin.Utils.GuiUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.impl.SimpleItem;

import java.util.function.Consumer;

public class ReturnItem extends SimpleItem {

    private final Consumer<Player> onReturn;

    public ReturnItem(@NotNull Consumer<Player> onReturn) {
        super(SpecificItems.FromGUI.returnItem());
        this.onReturn = onReturn;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        super.handleClick(clickType, player, event);

        GuiUtils.playGuiClickSound(player);
        onReturn.accept(player);
    }
}
