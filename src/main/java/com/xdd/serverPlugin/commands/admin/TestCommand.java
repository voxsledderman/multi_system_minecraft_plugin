package com.xdd.serverPlugin.commands.admin;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.cuboids.camp.settings.guis.MainCampMenu;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.window.Window;

@Command(name = "test")
@Permission("test.kod")
public class TestCommand {
    private final ServerPlugin plugin = ServerPlugin.getInstance();
    @Execute
    void test(@Context Player player){
        Camp camp = plugin.getCampManager().getPlayerCamp(player);
        if(camp == null){
            player.sendMessage("Nie masz obozu");
            return;
        }
        MainCampMenu mainCampMenu = new MainCampMenu(plugin, plugin.getCampManager().getPlayerCamp(player), player);
        mainCampMenu.openMenu(player);
    }
}
