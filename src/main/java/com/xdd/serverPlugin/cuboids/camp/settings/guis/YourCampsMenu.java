package com.xdd.serverPlugin.cuboids.camp.settings.guis;

import com.xdd.serverPlugin.Menu;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.SpecificItems;
import com.xdd.serverPlugin.Utils.GuiUtils;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.gui_items.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.impl.SimpleItem;

import java.util.ArrayList;
import java.util.List;

public class YourCampsMenu extends Menu {

    private final Player player;
    private final Camp camp;
    public YourCampsMenu(ServerPlugin plugin, Player player, Camp camp) {
        super(plugin);
        this.player = player;
        this.camp = camp;
    }

    @Override
    public String setupTitle() {
        return "Twoje teleporty";
    }

    @Override
    public Gui setupGui() {
        List<Item> houseItems = new ArrayList<>();

        for (Camp camp : ServerPlugin.getInstance().getCampManager().getPlayerCampsAvailableForTp(player)) {
            HouseItem item = new HouseItem(camp);
            houseItems.add(item);
        }

        if (houseItems.isEmpty()) {
            return Gui.normal().setStructure(
                            "X . . # . # . . R",
                            "* . # . x . # . *",
                            "# * . . . . . * #",
                            "B # < * * * > # ?"
                    )
                    .addIngredient('x', SpecificItems.FromGUI.infoItem(List.of(
                            Component.text(" "),
                            MiniMessage.miniMessage().deserialize("<white>Nie należych do żadnego obozu...")
                    )))
                    .addIngredient('B', new ReturnItem(new MainCampMenu(getPlugin(), camp, player)))
                    .addIngredient('X', new CloseEqItem())
                    .addIngredient('#', GuiUtils.blackGlass)
                    .addIngredient('*', GuiUtils.whiteGlass)
                    .addIngredient('?', SpecificItems.FromGUI.infoItem(List.of(
                            Component.text(" "),
                            MiniMessage.miniMessage().deserialize("<white>Zobaczysz tutaj listę obozów"),
                            MiniMessage.miniMessage().deserialize("<white>do których należysz")
                    )))
                    .addIngredient('R', new SimpleItem(SpecificItems.FromGUI.refreshItem(), click -> {
                        GuiUtils.playGuiClickSound(player);
                        this.openMenu(player);
                    }))
                    .addIngredient('>', new SimpleItem(GuiUtils.nextPageIcon))
                    .addIngredient('<', new SimpleItem(GuiUtils.prevPageIcon))
                    .build();
        }
        return PagedGui.items().setStructure(
                        "X . . # . # . . R",
                        "* . # x x x # . *",
                        "# * . x x x  . * #",
                        "B # < * * * > # ?"
                )
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('B', new ReturnItem(new MainCampMenu(getPlugin(), camp, player)))
                .addIngredient('X', new CloseEqItem())
                .addIngredient('#', GuiUtils.blackGlass)
                .addIngredient('*', GuiUtils.whiteGlass)
                .addIngredient('?', SpecificItems.FromGUI.infoItem(List.of(
                        Component.text(" "),
                        MiniMessage.miniMessage().deserialize("<white>Zobaczysz tutaj listę obozów"),
                        MiniMessage.miniMessage().deserialize("<white>do których należysz")
                )))
                .addIngredient('R', new SimpleItem(SpecificItems.FromGUI.refreshItem(), click -> {
                    GuiUtils.playGuiClickSound(player);
                    this.openMenu(player);
                }))
                .addIngredient('>', new NextPageItem())
                .addIngredient('<', new PrevPageItem())
                .setContent(houseItems)
                .build();
    }
}
