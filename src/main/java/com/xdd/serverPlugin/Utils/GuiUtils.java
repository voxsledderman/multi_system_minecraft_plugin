package com.xdd.serverPlugin.Utils;

import com.nexomc.nexo.api.NexoItems;
import com.xdd.serverPlugin.Tooltip;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;

public class GuiUtils {

    public static Item blackGlass = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE));
    public static Item whiteGlass = new SimpleItem(new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE));

    public static final ItemStack settingsIcon = buildCustomItem("settings", Tooltip.UNCOMMON);
    public static final ItemStack upArrow = buildCustomItem("arrow_up", Tooltip.UNCOMMON);
    public static final ItemStack homeIcon = buildCustomItem("home", Tooltip.UNCOMMON);
    public static final ItemStack roundedLeftArrow = buildCustomItem("arrow_left2");
    public static final ItemStack cross0Icon = buildCustomItem("cross_o", Tooltip.COMMON);
    public static final ItemStack infoIcon = buildCustomItem("info", Tooltip.LEGENDARY);
    public static final ItemStack checkIcon = buildCustomItem("check", Tooltip.UNCOMMON);
    public static final ItemStack redCrossIcon = buildCustomItem("cross");
    public static final ItemStack blueCrossIcon = buildCustomItem("cross_blue", Tooltip.RARE);
    public static final ItemStack humanIcon = buildCustomItem("camera", Tooltip.RARE);
    public static final ItemStack hammer = buildCustomItem("hammer", Tooltip.WOODEN);
    public static final ItemStack chest = buildCustomItem("m-chest", Tooltip.WOODEN);
    public static final ItemStack sleeping = buildCustomItem("sleeping", Tooltip.WOODEN);
    public static final ItemStack nextPageIcon = buildCustomItem("arrow_right");
    public static final ItemStack prevPageIcon = buildCustomItem("arrow_left");
    public static final ItemStack refreshIcon = buildCustomItem("refresh");


    private static class Sounds {
        private static final String guiClickSoundID = "ui_click";
        private static final String guiCloseSoundID = "window_close";
        private static final String permOnSoundID = "ui_permission_on";
        private static final String permOffSoundID = "ui_permission_off";
    }


    private static ItemStack buildCustomItem(String id) {
        com.nexomc.nexo.items.ItemBuilder itemBuilder = NexoItems.itemFromId(id);
        if (itemBuilder == null) {
            return new ItemStack(Material.PAPER);
        }
        itemBuilder.clone().setTooltipStyle(NamespacedKey.fromString(Tooltip.COMMON.getKey()));
        return itemBuilder.build();
    }
    private static ItemStack buildCustomItem(String id, Tooltip tooltip) {
        com.nexomc.nexo.items.ItemBuilder itemBuilder = NexoItems.itemFromId(id);
        if (itemBuilder == null) {
            return new ItemStack(Material.PAPER);
        }
        itemBuilder.setTooltipStyle(NamespacedKey.fromString(tooltip.getKey()));
        return itemBuilder.build();
    }
    public static void playGuiClickSound(Player player){
        player.playSound(player, Sounds.guiClickSoundID, 1.25f,1f);
    }
    public static void playGuiCloseSound(Player player){
        player.playSound(player, Sounds.guiCloseSoundID, 0.72f,1f);
    }
    public static void playPermOnSound(Player player){
        player.playSound(player, Sounds.permOnSoundID, 1f,1f);
    }
    public static void playPermOffSound(Player player){
        player.playSound(player, Sounds.permOffSoundID, 0.88f,1f);
    }

}
