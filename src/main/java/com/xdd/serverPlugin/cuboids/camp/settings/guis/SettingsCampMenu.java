package com.xdd.serverPlugin.cuboids.camp.settings.guis;

import com.xdd.serverPlugin.Menu;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.SpecificItems;
import com.xdd.serverPlugin.Utils.GuiUtils;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.gui_items.ClickableItem;
import com.xdd.serverPlugin.gui_items.CloseEqItem;
import com.xdd.serverPlugin.gui_items.PermissionItem;
import com.xdd.serverPlugin.gui_items.ReturnItem;
import com.xdd.serverPlugin.permissions.CampPerms;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.impl.SimpleItem;

import java.util.List;

public class SettingsCampMenu extends Menu {

    private final Camp camp;
    private final Player player;
    public SettingsCampMenu(ServerPlugin plugin, Camp camp, Player player) {
        super(plugin);
        this.camp = camp;
        this.player = player;
    }

    @Override
    public String setupTitle() {
        return "Ustawienia";
    }

    @Override
    public Gui setupGui() {
        var item = SpecificItems.FromGUI.humanItem();
        return Gui.normal()
                .setStructure(
                        "X#.#.#.#.",
                        "#PVD.RIM#",
                        "*#.....#*",
                        "B*#@%@#*?"
                )
                .addIngredient('P', new PermissionItem(camp, CampPerms.GlobalPerms.KILL_MONSTERS))
                .addIngredient('M', new PermissionItem(camp, CampPerms.GlobalPerms.KILL_MOBS))
                .addIngredient('D', new PermissionItem(camp, CampPerms.GlobalPerms.SETTINGS_OPEN_DOOR_PERM))

                .addIngredient('R', new PermissionItem(camp, CampPerms.GlobalPerms.REDSTONE_BLOCKS_INTERACTION))
                .addIngredient('I', new PermissionItem(camp, CampPerms.GlobalPerms.PICKUP_ITEMS))
                .addIngredient('V', new PermissionItem(camp, CampPerms.GlobalPerms.SEND_VISIT_REQUEST))

                .addIngredient('B', new ReturnItem(new MainCampMenu(getPlugin(), camp, player)::openMenu))
                .addIngredient('?', new SimpleItem(SpecificItems.FromGUI.infoItem(List.of(
                        Component.text(" "), MiniMessage.miniMessage().deserialize("<white>tutaj możesz zarządzać"),
                        MiniMessage.miniMessage().deserialize("<white>permisjami odwiedzających"),
                        MiniMessage.miniMessage().deserialize("<white>i dodawać nowych członków")
                        ))))
                .addIngredient('%', new ClickableItem(SpecificItems.FromGUI.blueXItem(item.effectiveName(), item.lore()), new PlayerPermMenu(getPlugin(), camp, player)))
                .addIngredient('@', new ClickableItem(item, new PlayerPermMenu(getPlugin(), camp, player)))
                .addIngredient('X', new CloseEqItem())
                .addIngredient('#', GuiUtils.blackGlass)
                .addIngredient('*', GuiUtils.whiteGlass)
                .build();
    }
}






