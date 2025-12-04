package com.xdd.serverPlugin.gui_items;

import com.xdd.serverPlugin.Utils.GuiUtils;
import com.xdd.serverPlugin.Utils.Sounds;
import com.xdd.serverPlugin.shopSystem.menus.MainShopMenu;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.PageItem;

public class NextPageItem extends PageItem {

    private MainShopMenu mainShopMenu;
    private final ItemStack iconItem;
    private final ItemStack noNextPageItem;

    public NextPageItem(ItemStack iconItem, ItemStack noNextPageItem) {
        super(true);
        this.iconItem = iconItem;
        this.noNextPageItem = noNextPageItem;
    }
    public NextPageItem(MainShopMenu mainShopMenu, ItemStack iconItem, @Nullable ItemStack noNextPageItem) {
        super(true);
        this.iconItem = iconItem;
        this.mainShopMenu = mainShopMenu;
        this.noNextPageItem = noNextPageItem;
    }


    @Override
    public ItemProvider getItemProvider(PagedGui<?> pagedGui) {
        if(pagedGui.hasNextPage() || noNextPageItem == null) {
            ItemBuilder itemBuilder = new ItemBuilder(iconItem);
            ItemStack invisibleItem = GuiUtils.getInvisibleItem(" ", TextColor.color(0xFFFFFF));
            ItemBuilder invBuilder = new ItemBuilder(invisibleItem);
            invBuilder.setDisplayName(pagedGui.hasNextPage() ? "§aNastępna strona" : "§cJesteś na końcu");
            return itemBuilder;
        }
        return new ItemBuilder(noNextPageItem);
    }


    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        super.handleClick(clickType, player, event);
        Sounds.playGuiClickSound(player);
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
