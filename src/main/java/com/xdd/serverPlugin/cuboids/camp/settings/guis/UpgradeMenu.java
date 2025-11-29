package com.xdd.serverPlugin.cuboids.camp.settings.guis;

import com.xdd.serverPlugin.Menu;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.SpecificItems;
import com.xdd.serverPlugin.cuboids.UpgradeType;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.gui_items.CloseEqItem;
import com.xdd.serverPlugin.gui_items.ReturnItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.impl.SimpleItem;

import java.util.List;

public class UpgradeMenu extends Menu {
    private final Camp camp;
    private final Player player;
    private final UpgradeType upgradeType;

    public UpgradeMenu(ServerPlugin plugin, Camp camp, Player player, UpgradeType upgradeType) {
        super(plugin);
        this.camp = camp;
        this.player = player;
        this.upgradeType = upgradeType;
    }

    @Override
    public String setupTitle() {
        return "";
    }

    @Override
    public Gui setupGui() {
        return Gui.normal().setStructure(
                "Q . . . . . . . .",
                ". . . . . . . . .",
                ". . . . . . . . .",
                "B . . . . . . . ?"
        )
                .addIngredient('Q', new CloseEqItem(true))
                .addIngredient('B', new ReturnItem(new ChoseUpgradeMenu(getPlugin(), camp, player)))
                .addIngredient('?', new SimpleItem(SpecificItems.FromGUI.infoItem(List.of(
                        Component.text(" "),
                        MiniMessage.miniMessage().deserialize("<white>Tutaj możesz zobaczyć wymagania"),
                        MiniMessage.miniMessage().deserialize("<white>i ulepszyć wybraną kategorię")
                ))))
                .build();
    }
    @Override
    public void playOpenSound() {
        player.playSound(player, Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1f,1f);
    }
}
