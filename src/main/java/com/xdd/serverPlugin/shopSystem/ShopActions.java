package com.xdd.serverPlugin.shopSystem;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.Utils.Format;
import com.xdd.serverPlugin.Utils.PlayerUtils;
import com.xdd.serverPlugin.Utils.Sounds;
import com.xdd.serverPlugin.database.data.subdatas.economy.EconomyData;
import com.xdd.serverPlugin.database.data.subdatas.economy.Money;
import com.xdd.serverPlugin.shopSystem.menus.ChoseAmountMenu;
import com.xdd.serverPlugin.shopSystem.menus.MainShopMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShopActions {

    public static boolean buyItem(ShopOffer offer, Player player, int amount, MainShopMenu menu){
        if(amount < 1) return false;
        final EconomyData economyData = menu.getEconomyData();
        Money money = economyData.getMoney();
        double payment = offer.getBuyPrice() * amount;
        if(!money.canMakePayment(payment)){
            player.sendMessage(Component.text("Brakuje ci pieniędzy aby dokonać tego zakupu!").color(TextColor.color(0xFF7070)));
            Sounds.playErrorSound(player);
            return false;
        } else if(!PlayerUtils.canPlayerInventoryFit(player.getInventory(), offer.getItemStack(amount))){
            player.sendMessage(Component.text("Nie masz wystarczająco miejsca w ekwipunku!").color(TextColor.color(0xFF7070)));
            Sounds.playErrorSound(player);
            return false;
        }
        handleBuy(offer, money, player, payment, amount, menu);
        Sounds.playCashRegisterSound(player);
        return true;
    }

    public static boolean sellItem(ShopOffer offer, Player player, int amount, MainShopMenu menu) {
        if (amount < 1) return false;
        final EconomyData economyData = menu.getEconomyData();

        ItemStack itemForSell = offer.getItemStack(amount);

        if (!PlayerUtils.doesPlayerHaveItemInEq(player.getInventory(), itemForSell)) {
            player.sendMessage(Component.text("Nie masz tego przedmiotu w ekwipunku!").color(TextColor.color(0xFF7070)));
            Sounds.playErrorSound(player);
            return false;
        }
        handleSell(offer, player, economyData, amount, menu);
        Sounds.playGrabCoinsSound(player);
        return true;

    }
    public static void choseAmount(ShopOffer offer, Player player, MainShopMenu menu){
        ChoseAmountMenu choseAmountMenu = new ChoseAmountMenu(ServerPlugin.getInstance(), offer, menu, player);
        choseAmountMenu.playOpenSound();
        choseAmountMenu.openMenu(player);
    }

    private static void handleSell(ShopOffer offer, Player player, EconomyData economyData, int amount, MainShopMenu menu){
        player.getInventory().removeItem(offer.getItemStack(amount));
        Money money = economyData.getMoney();
        double payment = offer.getSellPrice() * amount;
        money.addMoney(payment);
        String msg = amount == 1 ? "Sprzedano przedmiot w sklepie za %s$".formatted(Format.formatMoney(payment))
                : "Sprzedano przedmioty w sklepie za %s$".formatted(Format.formatMoney(payment));

        player.sendMessage(Component.text(msg).color(TextColor.color(0x45FF4A)));
        MainShopMenu shopMenu = new MainShopMenu(ServerPlugin.getInstance(), offer.getCategory(),
                player, menu.getPagedGui().getCurrentPage(), menu.isSecondPage());
        shopMenu.openMenu(player);
    }

    private static void handleBuy(ShopOffer offer, Money money, Player player, double payment, int amount, MainShopMenu menu){
        money.subtractMoney(payment);
        player.getInventory().addItem(offer.getItemStack(amount));
        String msg = amount == 1 ? "Pomyślnie zakupiono przedmiot w sklepie za %s$".formatted(Format.formatMoney(payment))
                : "Pomyślnie zakupiono przedmioty w sklepie za %s$".formatted(Format.formatMoney(payment));
        player.sendMessage(Component.text(msg).color(TextColor.color(0x45FF4A)));
        MainShopMenu shopMenu = new MainShopMenu(ServerPlugin.getInstance(), offer.getCategory(),
                player, menu.getPagedGui().getCurrentPage(), menu.isSecondPage());
        shopMenu.openMenu(player);
    }
}
