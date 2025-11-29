package com.xdd.serverPlugin.shopSystem.menus;

import com.xdd.serverPlugin.Menu;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.Utils.Format;
import com.xdd.serverPlugin.Utils.GuiUtils;
import com.xdd.serverPlugin.gui_items.ClickableItem;
import com.xdd.serverPlugin.shopSystem.ShopActions;
import com.xdd.serverPlugin.shopSystem.ShopOffer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.impl.SimpleItem;

import java.util.List;

public class ChoseAmountMenu extends Menu {

    private static final TextColor COLOR_ADD = TextColor.color(0x45FF4A);
    private static final TextColor COLOR_SUBTRACT = TextColor.color(0xFF7070);
    private static final TextColor COLOR_INFO = TextColor.color(0xFFFFFF);
    private static final TextColor COLOR_BACK = TextColor.color(0x7C7C7C);

    private final ShopOffer offer;
    private final MainShopMenu shopMenu;
    private final Player player;
    private final int amount;
    private final Material material;

    public ChoseAmountMenu(ServerPlugin plugin, ShopOffer offer, MainShopMenu shopMenu, Player player) {
        this(plugin, offer, shopMenu, player, 1);
    }
    private ChoseAmountMenu(ServerPlugin plugin, ShopOffer offer, MainShopMenu shopMenu, Player player, int amount) {
        super(plugin);
        this.offer = offer;
        this.shopMenu = shopMenu;
        this.player = player;
        this.amount = amount;
        this.material = offer.getMaterial();
    }

    @Override
    public String setupTitle() {
        return "'<shift:-8><glyph:quantity_selector>'";
    }

    @Override
    public Gui setupGui() {
        return Gui.normal()
                .setStructure(
                        ". . . . . . . X .",
                        ". . . . . . . . .",
                        ". 3 3 . . . 4 4 .",
                        ". 2 2 . i . 5 5 .",
                        ". 1 1 . . . 6 6 .",
                        ". S S . . . B B ."
                )
                .addIngredient('X', new ClickableItem(GuiUtils.getInvisibleItem("Powrót", COLOR_BACK), shopMenu))
                .addIngredient('i', new SimpleItem(offer.getItemStack(amount)))
                .addIngredient('3', createAdjustmentItem(-1))
                .addIngredient('2', createAdjustmentItem(-10))
                .addIngredient('1', createAdjustmentItem(-32))
                .addIngredient('4', createAdjustmentItem(1))
                .addIngredient('5', createAdjustmentItem(10))
                .addIngredient('6', createAdjustmentItem(32))

                .addIngredient('B', new SimpleItem(GuiUtils.getInvisibleItem(
                        MiniMessage.miniMessage().deserialize(
                                "<bold><#38A94A>Kliknij aby kupić <white>x" + amount
                        ),    List.of(
                                MiniMessage.miniMessage().deserialize("<#E9C558>Cena kupna: <white>%s$".formatted(Format.formatMoney(offer.getSellPrice() * amount))),
                                Component.text(" "),
                                MiniMessage.miniMessage().deserialize("<glyph:shop_left_click><dark_green> kliknij aby potwierdzić")
                        )), click -> {
                    ShopActions.buyItem(offer, player, amount, shopMenu);
                }))


                .addIngredient('S', new SimpleItem(GuiUtils.getInvisibleItem(
                        MiniMessage.miniMessage().deserialize(
                                "<bold><#DC5C47>Kliknij aby sprzedać <white>x" + amount
                        ),
                        List.of(
                                MiniMessage.miniMessage().deserialize("<#E9C558>Cena sprzedaży: <white>%s$".formatted(Format.formatMoney(offer.getSellPrice() * amount))),
                                Component.text(" "),
                                MiniMessage.miniMessage().deserialize("<glyph:shop_left_click><dark_green> kliknij aby potwierdzić")
                        )), click -> {
                    ShopActions.sellItem(offer, player, amount, shopMenu);
                }))

                .build();
    }

    private Item createAdjustmentItem(int changeAmount) {
        boolean isPositive = changeAmount > 0;
        TextColor color = isPositive ? COLOR_ADD : COLOR_SUBTRACT;
        String sign = isPositive ? "+" : "";

        Component name = Component.text(sign + changeAmount).color(color);
        Component desc = Component.text(isPositive ? "Kliknij aby dodać " : "Kliknij aby odjąć ")
                .append(Component.text(Math.abs(changeAmount)))
                .append(Component.text("."))
                .color(COLOR_INFO);

        ItemStack visualItem = GuiUtils.getInvisibleItem(name, List.of(desc));

        return new SimpleItem(visualItem, click -> {
            int newAmount = amountCalculator(changeAmount);
            new ChoseAmountMenu(getPlugin(), offer, shopMenu, player, newAmount).openMenu(player);
            GuiUtils.playGuiClickSound(player);
        });
    }

    private int amountCalculator(int num) {
        int sum = amount + num;
        int maxStackSize = material.getMaxStackSize();
        if (sum < 1) {
            return 1;
        } else if (sum > maxStackSize) {
            return maxStackSize;
        }
        return sum;
    }

    @Override
    public void playOpenSound() {
        GuiUtils.playGuiClickSound(player);
    }
}