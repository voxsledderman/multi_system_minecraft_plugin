package com.xdd.serverPlugin.cuboids.camp.settings.guis;

import com.xdd.serverPlugin.Menu;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.Utils.GuiUtils;
import com.xdd.serverPlugin.Utils.SpecificItems;
import com.xdd.serverPlugin.cuboids.UpgradeType;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.gui_items.ClickableItem;
import com.xdd.serverPlugin.gui_items.CloseEqItem;
import com.xdd.serverPlugin.gui_items.ReturnItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.impl.SimpleItem;

import java.util.List;

public class ChoseUpgradeMenu extends Menu {
    private final Camp camp;
    private final Player player;

    public ChoseUpgradeMenu(ServerPlugin plugin, Camp camp, Player player) {
        super(plugin);
        this.camp = camp;
        this.player = player;
    }

    @Override
    public String setupTitle() {
        return "Wybierz co ulepszyć";
    }

    @Override
    public Gui setupGui() {
        return Gui.normal()
                .setStructure(
                        "B . # . # . # . .",
                        "# * . . * . . * #",
                        "* # F * L * H # *",
                        "R . # . # . # . ?")
                .addIngredient('B', new CloseEqItem(true))
                .addIngredient('R', new ReturnItem(new MainCampMenu(getPlugin(), camp, player)))
                .addIngredient('?', new SimpleItem(SpecificItems.FromGUI.infoItem(List.of(
                        Component.text(" "), MiniMessage.miniMessage().deserialize("<white>W tym menu możesz"),
                        MiniMessage.miniMessage().deserialize("<white>wybrać co ulepszyć")
                ))))
                .addIngredient('F', new ClickableItem(SpecificItems.FromGUI.farmLvlUpItem(), new UpgradeMenu(getPlugin(), camp, player, UpgradeType.FARM)))
                .addIngredient('L', new ClickableItem(SpecificItems.FromGUI.campLvlUpItem(), new UpgradeMenu(getPlugin(), camp, player, UpgradeType.CAMP)))
                .addIngredient('H', new ClickableItem(SpecificItems.FromGUI.helpersLvlUpItem(), new UpgradeMenu(getPlugin(), camp, player, UpgradeType.WORKERS)))
                .addIngredient('#', GuiUtils.blackGlass)
                .addIngredient('*', GuiUtils.whiteGlass)
                .build();
    }

    @Override
    public void playOpenSound() {
        player.playSound(player, Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1f,1f);
    }
}
