package com.xdd.serverPlugin.gui_items;

import com.xdd.serverPlugin.SpecificItems;
import com.xdd.serverPlugin.Utils.GuiUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.PageItem;

public class PrevPageItem extends PageItem {

    public PrevPageItem(){
        super(false);
    }
    @Override
    public ItemProvider getItemProvider(PagedGui<?> pagedGui) {
        ItemBuilder itemBuilder = new ItemBuilder(SpecificItems.FromGUI.prevPageItem());
        itemBuilder.setDisplayName("§aPoprzednia strona")
                .addLoreLines(pagedGui.hasPreviousPage()
                ? "Przejdź na stronę %d/%d".formatted(pagedGui.getCurrentPage(), pagedGui.getPageAmount())
                        : "Jesteś na pierwszej stronie");
        return itemBuilder;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        super.handleClick(clickType, player, event);
        GuiUtils.playGuiClickSound(player);
    }
}
