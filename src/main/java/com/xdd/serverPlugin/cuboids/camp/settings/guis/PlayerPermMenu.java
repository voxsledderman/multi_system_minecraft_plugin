package com.xdd.serverPlugin.cuboids.camp.settings.guis;

import com.xdd.serverPlugin.Menu;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.Utils.GuiUtils;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.gui_items.CloseEqItem;
import com.xdd.serverPlugin.gui_items.PlayerItem;
import com.xdd.serverPlugin.gui_items.ReturnItem;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.impl.SimpleItem;

public class PlayerPermMenu extends Menu {

    private final Camp camp;
    private final Player player;
    public PlayerPermMenu(ServerPlugin plugin, Camp camp, Player player) {
        super(plugin);
        this.camp = camp;
        this.player = player;
    }

    @Override
    public String setupTitle() {
        return "Pojedyńcze Permisje";
    }

    @Override
    public Gui setupGui() {
        return Gui.normal()
                .setStructure(
                        "X . # . . . # . .",
                        "* # 1 2 3 4 5 # *",
                        ". * # . . . # * .",
                        "B . * # * # * . ?"
                )
                .addIngredient('#', GuiUtils.blackGlass)
                .addIngredient('*', GuiUtils.whiteGlass)
                .addIngredient('X', new CloseEqItem())
                .addIngredient('B', new ReturnItem(new SettingsCampMenu(getPlugin(), camp, player)::openMenu))
                .addIngredient('?', new SimpleItem(GuiUtils.infoIcon))
                .addIngredient('@', new SimpleItem(GuiUtils.blueCrossIcon))
                .addIngredient('1', new PlayerItem(camp.getPermissionsPerPlayer(), 1))
                .addIngredient('2', new PlayerItem(camp.getPermissionsPerPlayer(), 2))
                .addIngredient('3', new PlayerItem(camp.getPermissionsPerPlayer(), 3))
                .addIngredient('4', new PlayerItem(camp.getPermissionsPerPlayer(), 4))
                .addIngredient('5', new PlayerItem(camp.getPermissionsPerPlayer(), 5))
                .build();
    }
}
