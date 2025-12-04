package com.xdd.serverPlugin.gui_items;

import com.xdd.serverPlugin.Utils.GuiUtils;
import com.xdd.serverPlugin.Utils.Sounds;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.tittle.subclasses.CountdownTitle;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.UUID;


public class HouseItem extends AbstractItem {

    private final Camp camp;
    @Getter private final UUID uuid;

    public HouseItem(Camp camp) {
        this.camp = camp;
        uuid = UUID.randomUUID();
    }

    @Override
    public ItemProvider getItemProvider() {
        ItemBuilder builder = new ItemBuilder(GuiUtils.homeIcon);
        builder.setDisplayName("§aObóz gracza: §f" + camp.getOwnerName());

        return builder;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        CountdownTitle.doCampCountdown(player, camp);
        Sounds.playGuiClickSound(player);
    }
}
