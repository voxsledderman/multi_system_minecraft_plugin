package com.xdd.serverPlugin.shopSystem.menus;

import com.xdd.serverPlugin.Menu;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.Utils.Format;
import com.xdd.serverPlugin.Utils.GuiUtils;
import com.xdd.serverPlugin.database.data.subdatas.economy.EconomyData;
import com.xdd.serverPlugin.gui_items.*;
import com.xdd.serverPlugin.shopSystem.ShopOffer;
import com.xdd.serverPlugin.shopSystem.enums.ShopCategory;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class MainShopMenu extends Menu {

   private final ShopCategory shopCategory;
   private final Player player;
   private final ServerPlugin plugin;
   private final EconomyData economyData;
   private boolean isSecondPage = false;

   private PagedGui<? extends Item> pagedGui;
   private List<Item> items = new ArrayList<>();
   private int currentItemPage = 1;
   private int maxItemPages = 1;

   private int pageIndex = -1;

    public MainShopMenu(ServerPlugin plugin, ShopCategory shopCategory, Player player) {
        super(plugin);
        this.shopCategory = shopCategory;
        this.player = player;
        this.plugin = plugin;
        economyData = plugin.getCacheManager().getPlayerData(player).getEconomyData();
        loadItems();
    }
    private MainShopMenu(ServerPlugin plugin, ShopCategory shopCategory, Player player, boolean isSecondPage) {
        super(plugin);
        this.shopCategory = shopCategory;
        this.player = player;
        this.plugin = plugin;
        economyData = plugin.getCacheManager().getPlayerData(player).getEconomyData();
        this.isSecondPage = isSecondPage;
        loadItems();
    }

    public MainShopMenu(ServerPlugin plugin, ShopCategory shopCategory, Player player, int page, boolean isSecondPage) {
        super(plugin);
        this.shopCategory = shopCategory;
        this.player = player;
        this.plugin = plugin;
        economyData = plugin.getCacheManager().getPlayerData(player).getEconomyData();
        this.isSecondPage = isSecondPage;
        pageIndex = page;
        loadItems();
    }

    @Override
    public String setupTitle() {
        String pageVisualisation = "<shift:-99><white><font:minecraft:page_text>%d / %d".formatted(currentItemPage, maxItemPages);
        String balanceVisualization = "<shift:-0><white><font:minecraft:balance>%s".formatted(economyData.getMoney().smartFormated());

        return shopCategory.getMenuKey() + pageVisualisation + balanceVisualization;
    }

    @Override
    public Gui setupGui() {

        if(!isSecondPage && shopCategory.equals(ShopCategory.TOOLS_ARMORS)){
            pagedGui = PagedGui.items().setStructure(
                            "X . . . . . . . .",
                            "B C D M T F f O 2",
                            "i i i i . i i i i",
                            "i i i i . i i i i",
                            "i i i i . i i i i",
                            "< < < . . . > > >"
                    )
                    .addIngredient('X', new CloseEqItem(false))
                    .addIngredient('B', new ClickableItem(GuiUtils.ShopIcons.buildingBlocks, new MainShopMenu(getPlugin(), ShopCategory.BUILDING_BLOCKS, player)))
                    .addIngredient('C', new ClickableItem(GuiUtils.ShopIcons.coloredBlocks, new MainShopMenu(getPlugin(), ShopCategory.COLORED_BLOCKS, player)))
                    .addIngredient('D', new ClickableItem(GuiUtils.ShopIcons.decorations, new MainShopMenu(getPlugin(), ShopCategory.DECORATIONS, player)))
                    .addIngredient('M', new ClickableItem(GuiUtils.ShopIcons.minerals, new MainShopMenu(getPlugin(), ShopCategory.MINERALS, player)))
                    .addIngredient('T', new ClickableItem(GuiUtils.ShopIcons.toolsArmors, new MainShopMenu(getPlugin(), ShopCategory.TOOLS_ARMORS, player)))
                    .addIngredient('F', new ClickableItem(GuiUtils.ShopIcons.farming, new MainShopMenu(getPlugin(), ShopCategory.FARMING, player)))
                    .addIngredient('f', new ClickableItem(GuiUtils.ShopIcons.food, new MainShopMenu(getPlugin(), ShopCategory.FOOD, player)))
                    .addIngredient('O', new ClickableItem(GuiUtils.ShopIcons.other, new MainShopMenu(getPlugin(), ShopCategory.OTHER, player)))
                    .addIngredient('2', new ClickableItem(GuiUtils.getInvisibleItem("Następna strona", TextColor.color(0xFFFFFF)),
                            new MainShopMenu(plugin, ShopCategory.REDSTONE_BLOCKS, player, true)
                    ))
                    .addIngredient('>', new NextPageItem(false, this))
                    .addIngredient('<', new PrevPageItem(false, this))
                    .addIngredient('i', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                    .setContent(items)
                    .build();

        } else if(!isSecondPage) {
            pagedGui = PagedGui.items().setStructure(
                            "X . . . . . . . .",
                            "B C D M T F f O 2",
                            "i i i i i i i i i",
                            "i i i i i i i i i",
                            "i i i i i i i i i",
                            "< < < . . . > > >"
                    )
                    .addIngredient('X', new CloseEqItem(false))
                    .addIngredient('B', new ClickableItem(GuiUtils.ShopIcons.buildingBlocks, new MainShopMenu(getPlugin(), ShopCategory.BUILDING_BLOCKS, player)))
                    .addIngredient('C', new ClickableItem(GuiUtils.ShopIcons.coloredBlocks, new MainShopMenu(getPlugin(), ShopCategory.COLORED_BLOCKS, player)))
                    .addIngredient('D', new ClickableItem(GuiUtils.ShopIcons.decorations, new MainShopMenu(getPlugin(), ShopCategory.DECORATIONS, player)))
                    .addIngredient('M', new ClickableItem(GuiUtils.ShopIcons.minerals, new MainShopMenu(getPlugin(), ShopCategory.MINERALS, player)))
                    .addIngredient('T', new ClickableItem(GuiUtils.ShopIcons.toolsArmors, new MainShopMenu(getPlugin(), ShopCategory.TOOLS_ARMORS, player)))
                    .addIngredient('F', new ClickableItem(GuiUtils.ShopIcons.farming, new MainShopMenu(getPlugin(), ShopCategory.FARMING, player)))
                    .addIngredient('f', new ClickableItem(GuiUtils.ShopIcons.food, new MainShopMenu(getPlugin(), ShopCategory.FOOD, player)))
                    .addIngredient('O', new ClickableItem(GuiUtils.ShopIcons.other, new MainShopMenu(getPlugin(), ShopCategory.OTHER, player)))
                    .addIngredient('2', new ClickableItem(GuiUtils.getInvisibleItem("Następna strona", TextColor.color(0xFFFFFF)),
                            new MainShopMenu(plugin, ShopCategory.REDSTONE_BLOCKS, player, true)
                            ))
                    .addIngredient('>', new NextPageItem(false, this))
                    .addIngredient('<', new PrevPageItem(false, this))
                    .addIngredient('i', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                    .setContent(items)
                    .build();
        } else {
            pagedGui =  PagedGui.items().setStructure(
                            "X . . . . . . . .",
                            "1 R B S E D . . .",
                            "i i i i i i i i i",
                            "i i i i i i i i i",
                            "i i i i i i i i i",
                            "< < < . . . > > >"
                    )
                    .addIngredient('X', new CloseEqItem(false))
                    .addIngredient('R', new ClickableItem(GuiUtils.ShopIcons.redstoneBlocks, new MainShopMenu(getPlugin(),
                            ShopCategory.REDSTONE_BLOCKS, player, true)))
                    .addIngredient('B', new ClickableItem(GuiUtils.ShopIcons.enchantedBooks, new MainShopMenu(getPlugin(),
                            ShopCategory.ENCHANTED_BOOKS, player, true)))
                    .addIngredient('S', new ClickableItem(GuiUtils.ShopIcons.spawners, new MainShopMenu(getPlugin(),
                            ShopCategory.SPAWNERS, player, true)))
                    .addIngredient('E', new ClickableItem(GuiUtils.ShopIcons.spawningEggs, new MainShopMenu(getPlugin(),
                            ShopCategory.SPAWN_EGGS, player, true)))
                    .addIngredient('D', new ClickableItem(GuiUtils.ShopIcons.mobDrops, new MainShopMenu(getPlugin(),
                            ShopCategory.MOB_DROPS, player,true)))
                    .addIngredient('1', new ClickableItem(GuiUtils.getInvisibleItem("Poprzednia strona", TextColor.color(0xFFFFFF)),
                            new MainShopMenu(plugin, ShopCategory.OTHER, player,false))
                            )
                    .addIngredient('>', new NextPageItem(false, this))
                    .addIngredient('<', new PrevPageItem(false, this))
                    .addIngredient('i', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                    .setContent(items)
                    .build();
        }
        if(pageIndex >= 0) pagedGui.setPage(pageIndex);
        maxItemPages = pagedGui.getPageAmount();
        currentItemPage = pagedGui.getCurrentPage() + 1;

        return pagedGui;
    }

    @Override
    public void playOpenSound() {
        GuiUtils.playCashRegisterSound(player);
    }

    private ItemProvider addElementsToVisualItem(ShopOffer offer){
        ItemStack itemStack = offer.getItemStack(1).clone();
        ItemMeta meta = itemStack.getItemMeta();

        if(meta == null) {
            return new ItemBuilder(itemStack);
        }
        ItemBuilder builder = new ItemBuilder(itemStack);
        builder.setLore(List.of(
                new AdventureComponentWrapper(Component.text(" ")),
                new AdventureComponentWrapper(Component.text("Kupno: <white>%s$".formatted(Format.formatMoney(offer.getBuyPrice()))).color(TextColor.color(0xE9C558))),
                new AdventureComponentWrapper(Component.text("Sprzedaż: <white>%s$".formatted(Format.formatMoney(offer.getSellPrice()))).color(TextColor.color(0xE9C558))),
                new AdventureComponentWrapper(Component.text(" ")),
                new AdventureComponentWrapper(Component.text("<glyph:shop_left_click><dark_green> Lewy-Przycisk aby kupić!")),
                new AdventureComponentWrapper(Component.text("<glyph:shop_right_click><dark_aqua> Prawy-Przycisk aby sprzedać!")),
                new AdventureComponentWrapper(MiniMessage.miniMessage().deserialize("<glyph:shop_orange_right_click> <glyph:shop_shift_icon><gold> Przycisk + Shift aby wybrać ilość!")),
                new AdventureComponentWrapper(Component.text(" "))
                )
        );
        return builder;
    }
    private void loadItems(){
        var list =  plugin.getShopManager().getCategoryOffers(shopCategory);

        for(ShopOffer offer : list){
            if(offer.getMaterial() == null) continue;
            items.add(new ShopItem(addElementsToVisualItem(offer), offer, this));
        }
    }
}
