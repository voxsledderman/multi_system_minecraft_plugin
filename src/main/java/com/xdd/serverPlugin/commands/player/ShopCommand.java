package com.xdd.serverPlugin.commands.player;

import com.xdd.serverPlugin.Menu;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.permissions.Perms;
import com.xdd.serverPlugin.shopSystem.enums.ShopCategory;
import com.xdd.serverPlugin.shopSystem.menus.MainShopMenu;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "sklep", aliases = {"shop", "skl"})
public class ShopCommand {
    private final ServerPlugin plugin = ServerPlugin.getInstance();

    @Execute
    void openShop(@Context Player player){
        Menu menu = new MainShopMenu(plugin, ShopCategory.BUILDING_BLOCKS, player);
        menu.openMenu(player);
        menu.playOpenSound();
    }
    @Execute (name = "reload") @Permission(Perms.SHOP_ADMIN_ACTIONS)
    void reloadConfig(@Context CommandSender sender){
        boolean success = plugin.getShopManager().reload();

        if(success) sender.sendMessage("§ashop-offers.json zostało poprawnie załadowane ponownie!");
        else {
            sender.sendMessage("§cNie udało się przeładować shop-offers.json");
        }
    }
}
