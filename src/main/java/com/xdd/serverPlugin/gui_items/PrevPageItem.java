package com.xdd.serverPlugin.gui_items;

import com.xdd.serverPlugin.SpecificItems;
import com.xdd.serverPlugin.Utils.GuiUtils;
import com.xdd.serverPlugin.shopSystem.menus.MainShopMenu;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.PageItem;

public class PrevPageItem extends PageItem {

    private final boolean needIcon;
    private MainShopMenu mainShopMenu;

    public PrevPageItem(){
        super(false);
        needIcon = true;
    }
    public PrevPageItem(boolean needIcon){
        super(false);
        this.needIcon = needIcon;
    }
    public PrevPageItem(boolean needIcon, MainShopMenu mainShopMenu){
        super(false);
        this.needIcon = needIcon;
        this.mainShopMenu = mainShopMenu;
    }
    @Override
    public ItemProvider getItemProvider(PagedGui<?> pagedGui) {
        ItemBuilder itemBuilder = new ItemBuilder(SpecificItems.FromGUI.prevPageItem());
        itemBuilder.setDisplayName("§aPoprzednia strona")
                .addLoreLines(pagedGui.hasPreviousPage()
                ? "Przejdź na stronę %d/%d".formatted(pagedGui.getCurrentPage(), pagedGui.getPageAmount())
                        : "Jesteś na pierwszej stronie");


        ItemStack invItem = GuiUtils.getInvisibleItem(" ", TextColor.color(0xFFFFFF));
        ItemBuilder invBuilder = new ItemBuilder(invItem);
        invBuilder.setDisplayName(pagedGui.hasPreviousPage() ? "§aPoprzednia strona" : "§cJesteś na początku");

        return needIcon ? itemBuilder : invBuilder;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        super.handleClick(clickType, player, event);
        GuiUtils.playGuiClickSound(player);

        if (mainShopMenu != null) {
            PagedGui<?> pagedGui = mainShopMenu.getPagedGui();
            if (pagedGui != null && pagedGui.getCurrentPage() + 1 != mainShopMenu.getCurrentItemPage()) {
                mainShopMenu.setCurrentItemPage(pagedGui.getCurrentPage() + 1);
                mainShopMenu.setMaxItemPages(pagedGui.getPageAmount());
                mainShopMenu.getWindow().changeTitle(mainShopMenu.setupTitle());
            }
        }
    }
}
