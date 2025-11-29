package com.xdd.serverPlugin.commands.admin;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.shopSystem.enums.ShopCategory;
import com.xdd.serverPlugin.shopSystem.menus.MainShopMenu;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Command(name = "test")
@Permission("test.kod")
public class TestCommand {
    private final ServerPlugin plugin = ServerPlugin.getInstance();

    @Execute
    void test(@Context Player player){
        MainShopMenu shop = new MainShopMenu(plugin, ShopCategory.BUILDING_BLOCKS, player);
        shop.openMenu(player);
        shop.playOpenSound();
    }
    @Execute
    void string(@Context Player player, @Arg String str){
        Material material  = Material.valueOf(str);
        player.sendMessage(str);
        if(!material.isAir()){
         player.getInventory().addItem(new ItemStack(material));
        }
    }
}
