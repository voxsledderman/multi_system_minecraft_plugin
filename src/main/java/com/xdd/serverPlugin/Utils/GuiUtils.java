package com.xdd.serverPlugin.Utils;

import com.nexomc.nexo.api.NexoItems;
import com.xdd.serverPlugin.Tooltip;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;

import java.util.List;

public class GuiUtils {

    public static Item blackGlass = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE));
    public static Item whiteGlass = new SimpleItem(new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE));


    public static final ItemStack farmLevelUpIcon = buildItemWithNormalTooltipAdding("icon_farming", Tooltip.RARE);
    public static final ItemStack campLevelUpIcon = buildItemWithNormalTooltipAdding("star", Tooltip.RARE);
    public static final ItemStack helperLevelUpIcon = buildItemWithNormalTooltipAdding("pixelmon-8", Tooltip.RARE);

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
    public static final ItemStack alert2Icon = buildCustomItem("alert2");
    public static final ItemStack nextPageIconNoBackground = buildCustomItem("arrow_right2_1");
    public static final ItemStack prevPageIconNoBackground = buildCustomItem("arrow_left2_1");
    public static final ItemStack returnItemNoBackground = buildCustomItem("arrow_left2_2");
    @Getter public static final ItemStack moneyItem = buildCustomItem("coin");



    public static final ItemStack rewardsEpic = buildItemWithNormalTooltipAdding("icon_daily_rare_reward", Tooltip.WOODEN);
    public static final ItemStack rewardsLegendary = buildItemWithNormalTooltipAdding("icon_daily_legendary_reward", Tooltip.WOODEN);
    public static final ItemStack rewardsNormal = buildItemWithNormalTooltipAdding("icon_daily_gift_unclaimed", Tooltip.WOODEN);
    public static final ItemStack rewardsClaimed = buildItemWithNormalTooltipAdding("icon_daily_gift_claimed", Tooltip.WOODEN);
    public static final ItemStack rewardsAwaiting = buildItemWithNormalTooltipAdding("not_ready_box", Tooltip.WOODEN);
    public static final ItemStack rewardsReady = buildItemWithNormalTooltipAdding("icon_daily_gift_ready", Tooltip.WOODEN);

    public static class ShopIcons {

        public static final ItemStack buildingBlocks = buildItemWithBukkitDisplayName("icon_building_blocks", "Bloki Budowlane", TextColor.color(0x7C7C7C));
        public static final ItemStack coloredBlocks = buildItemWithBukkitDisplayName("icon_colored_blocks", "Kolorowe Bloki", TextColor.color(0x7C7C7C));
        public static final ItemStack decorations = buildItemWithBukkitDisplayName("icon_decorations", "Dekoracje", TextColor.color(0x7C7C7C));
        public static final ItemStack minerals = buildItemWithBukkitDisplayName("icon_minerals", "Minerały", TextColor.color(0x7C7C7C));
        public static final ItemStack toolsArmors = buildItemWithBukkitDisplayName("icon_tools_armors", "Zbroje i Narzędzia", TextColor.color(0x7C7C7C));
        public static final ItemStack farming = buildItemWithBukkitDisplayName("icon_farming", "Rzeczy do Farm", TextColor.color(0x7C7C7C));
        public static final ItemStack food = buildItemWithBukkitDisplayName("icon_foods", "Jedzenie", TextColor.color(0x7C7C7C));
        public static final ItemStack other = buildItemWithBukkitDisplayName("icon_miscellaneous", "Przedmioty Specjalne", TextColor.color(0x7C7C7C));
        public static final ItemStack redstoneBlocks = buildItemWithBukkitDisplayName("icon_redstone", "Przedmioty Redstone", TextColor.color(0x7C7C7C));
        public static final ItemStack enchantedBooks = buildItemWithBukkitDisplayName("icon_enchants", "Zaklęte Książki", TextColor.color(0x7C7C7C));
        public static final ItemStack spawners = buildItemWithBukkitDisplayName("icon_spawners", "Spawnery", TextColor.color(0x7C7C7C));
        public static final ItemStack spawningEggs = buildItemWithBukkitDisplayName("icon_spawn_eggs", "Jajka Spawnujące", TextColor.color(0x7C7C7C));
        public static final ItemStack mobDrops = buildItemWithBukkitDisplayName("icon_mob_drops", "Przedmioty z Mobów", TextColor.color(0x7C7C7C));

    }
    public static ItemStack getInvisibleItem(String displayName, TextColor color){
        return buildItemWithBukkitDisplayName("r_invisible_item", displayName, color);
    }

    public static ItemStack getInvisibleItem(){
        com.nexomc.nexo.items.ItemBuilder itemBuilder = NexoItems.itemFromId("r_invisible_item");
        if(itemBuilder == null) return new ItemStack(Material.PAPER);
        return itemBuilder.build();
    }

    public static ItemStack getInvisibleItem(Component displayName, List<Component> lore){
        return buildItemWithNameLore(getInvisibleItem(),displayName, lore);
    }

    private static ItemStack buildCustomItem(String id) {
        com.nexomc.nexo.items.ItemBuilder itemBuilder = NexoItems.itemFromId(id);
        if (itemBuilder == null) {
            return new ItemStack(Material.PAPER);
        }
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

    private static ItemStack buildItemWithBukkitDisplayName(String id, String displayName, TextColor color){
        ItemStack stack = buildCustomItem(id);
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(Component.text(displayName).color(color));
        stack.setItemMeta(meta);
        return stack;
    }

    private static ItemStack buildItemWithNormalTooltipAdding(String id, Tooltip tooltip){
        ItemStack stack = buildCustomItem(id);
        ItemMeta meta = stack.getItemMeta();
        if(tooltip != null) meta.setTooltipStyle(NamespacedKey.fromString(tooltip.getKey()));
        stack.setItemMeta(meta);
        return stack;
    }
    public static ItemStack buildItemWithNameLore(ItemStack item,@Nullable Component name, @Nullable List<Component> components){
        ItemMeta meta = item.getItemMeta();
        if(name != null) {
            meta.displayName(name);
        }
        if(components != null) meta.lore(components);
        item.setItemMeta(meta);
        return item;
    }
}
