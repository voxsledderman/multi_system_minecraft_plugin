package com.xdd.serverPlugin.cuboids.camp.settings.guis;

import com.xdd.serverPlugin.Menu;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.SpecificItems;
import com.xdd.serverPlugin.Utils.GuiUtils;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.gui_items.ClickableItem;
import com.xdd.serverPlugin.gui_items.CloseEqItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.impl.SimpleItem;

import java.util.List;

public class MainCampMenu extends Menu {

    private final Camp camp;
    private final Player player;
    public MainCampMenu(ServerPlugin plugin, Camp camp, Player player) {
        super(plugin);
        this.camp = camp;
        this.player = player;
    }

    @Override
    public String setupTitle() {
        return " Panel Zarządzania Obozem <glyph:crown>";
    }

    @Override
    public Gui setupGui() {
        return Gui.normal()
                .setStructure(
                        "B . . # . # . . . ",
                        "# . # . * . # . # ",
                        ". # H * U * S # . ",
                        "# . # . # . # . ? ")
                .addIngredient('#', GuiUtils.blackGlass)
                .addIngredient('*', GuiUtils.whiteGlass)
                .addIngredient('B', new CloseEqItem(true))

                .addIngredient('U', new ClickableItem(SpecificItems.FromGUI.upgradeCampItem(), new ChoseUpgradeMenu(getPlugin(), camp, player)))
                .addIngredient('H', new ClickableItem(SpecificItems.FromGUI.yourCampsItem(), new YourCampsMenu(getPlugin(), player, camp)))
                .addIngredient('S', new ClickableItem(SpecificItems.FromGUI.settingsCampItem(), new SettingsCampMenu(getPlugin(), camp, player)))
                .addIngredient('?', new SimpleItem(SpecificItems.FromGUI.infoItem(List.of(Component.text(" "),
                        MiniMessage.miniMessage().deserialize("<white>W tym menu możesz zarządzać"),
                        MiniMessage.miniMessage().deserialize("<white>i rozwijać swój obóz")

                ))))
                .build();
    }
    @Override
    public void playOpenSound() {
        player.playSound(player, Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1f,1f);
    }
}
