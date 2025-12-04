package com.xdd.serverPlugin.commands.admin;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.daily_rewards.DailyRewardMenu;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@Command(name = "test")
@Permission("test.kod")
public class TestCommand {
    private final ServerPlugin plugin = ServerPlugin.getInstance();

    @Execute
    void test(@Context Player player){
        DailyRewardMenu dailyRewardMenu = new DailyRewardMenu(ServerPlugin.getInstance(), player);
        dailyRewardMenu.openMenu(player);
    }
    @Execute(name = "menu")
    void string(@Context Player player, @Arg String glyph) {
        Inventory inventory = Bukkit.createInventory(player, 54, Component.text("<glyph:%s>ee".formatted(glyph)));
        player.openInventory(inventory);
    }
}
