package com.xdd.serverPlugin.cuboids.camp.settings.guis;

import com.xdd.serverPlugin.Menu;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.Utils.GuiUtils;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.gui_items.CloseEqItem;
import com.xdd.serverPlugin.gui_items.PermissionItem;
import com.xdd.serverPlugin.gui_items.ReturnItem;
import com.xdd.serverPlugin.permissions.CampPerms;
import com.xdd.serverPlugin.records.UuidNick;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.impl.SimpleItem;


public class SpecificPlayerMenu extends Menu {

    private final Camp camp;
    private final UuidNick uuidNick;
    private final Player player;
    public SpecificPlayerMenu(ServerPlugin plugin, Camp camp, UuidNick uuidNick, Player player) {
        super(plugin);
        this.camp = camp;
        this.uuidNick = uuidNick;
        this.player = player;
    }

    @Override
    public String setupTitle() {
        return "Ustawienia członka obozu";
    }

    @Override
    public Gui setupGui() {
        return Gui.normal()
                .setStructure(
                        "X # * . . . * # .",
                        "# * # H C S # * #",
                        "* # . 1 2 3 . # *",
                        "B * # . @ . # * ?"
                )
                .addIngredient('#', GuiUtils.blackGlass)
                .addIngredient('*', GuiUtils.whiteGlass)
                .addIngredient('X', new CloseEqItem())
                .addIngredient('B', new ReturnItem(new PlayerPermMenu(getPlugin(), camp, player)::openMenu))
                .addIngredient('?', new SimpleItem(GuiUtils.infoIcon))
                .addIngredient('@', new SimpleItem(GuiUtils.blueCrossIcon, click -> {
                    camp.getPermissionsPerPlayer().remove(uuidNick);
                    Player player = click.getPlayer();
                    new PlayerPermMenu(getPlugin(), camp, player).openMenu(player);
                    GuiUtils.playGuiClickSound(player);

                    ServerPlugin.getInstance().getCampManager()
                            .getCampsWherePlayerCanTeleport()
                            .computeIfPresent(uuidNick.uuid(), (uuid, camps) -> {
                                camps.remove(camp);
                                return camps.isEmpty() ? null : camps;
                            });

                    player.sendMessage(Component.text("Usunięto uprawnienia gracza [%s]".formatted(uuidNick.nick())));
                }))
                .addIngredient('H', new SimpleItem(GuiUtils.hammer))
                .addIngredient('C', new SimpleItem(GuiUtils.chest))
                .addIngredient('S', new SimpleItem(GuiUtils.sleeping))
                .addIngredient('1', new PermissionItem(camp, CampPerms.SpecialPerms.BUILD_DESTROY_BLOCKS, uuidNick))
                .addIngredient('2', new PermissionItem(camp, CampPerms.SpecialPerms.OPEN_CHESTS, uuidNick))
                .addIngredient('3', new PermissionItem(camp, CampPerms.SpecialPerms.ALLOW_PLAYING_ALONE, uuidNick))

                .build();
    }
}
