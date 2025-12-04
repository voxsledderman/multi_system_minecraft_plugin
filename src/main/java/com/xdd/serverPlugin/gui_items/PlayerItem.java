package com.xdd.serverPlugin.gui_items;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.Utils.GuiUtils;
import com.xdd.serverPlugin.Utils.Sounds;
import com.xdd.serverPlugin.Utils.TextUtils;
import com.xdd.serverPlugin.cache.PlayerInputsManager;
import com.xdd.serverPlugin.cuboids.camp.settings.guis.SpecificPlayerMenu;
import com.xdd.serverPlugin.records.UuidNick;
import com.xdd.serverPlugin.tittle.subclasses.InfoTitle;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerItem extends AbstractItem {

    private final Map<UuidNick, List<String>> playerPerms;
    private final int index;
    private final boolean isSpotTaken;
    private UuidNick uuidNick;

    private final ServerPlugin plugin = ServerPlugin.getInstance();
    private final PlayerInputsManager manager = plugin.getPlayerInputsManager();

    public PlayerItem(Map<UuidNick, List<String>> playerPerms , int number) {
        this.playerPerms = playerPerms;
        index = number - 1;
        isSpotTaken = playerPerms.size() >= number;
    }

    @Override
    public ItemProvider getItemProvider() {
        if(isSpotTaken){
            UuidNick playerKey = new ArrayList<>(playerPerms.keySet()).get(index);
            uuidNick = playerKey;
            return new ItemBuilder(GuiUtils.humanIcon).setDisplayName("§9" + playerKey.nick()).setLegacyLore(List.of(" ","§fKliknij aby zarządzać", "§fpermisjami gracza!"));
        } else {
            return new ItemBuilder(GuiUtils.blueCrossIcon).setDisplayName("§9Dodaj permisje gracza").setLegacyLore(List.of(" ", "§fKliknij, a następnie", "§fwpisz nick na czacie"));
        }
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        if(!isSpotTaken){
            player.closeInventory();
            InfoTitle infoTitle = new InfoTitle(player.getUniqueId(), 10, Component.text("Napisz na czacie").color(TextColor.color(0x45FF4A)),
                    Component.text("nick gracza do dodania!").color(TextColor.color(0xFFFFFF)), Title.Times.times(Duration.ofMillis(450),
                    Duration.ofSeconds(4), Duration.ofMillis(450)), Sound.ENTITY_PLAYER_LEVELUP, ServerPlugin.getInstance().getTitleManager());
            infoTitle.sendTitle();
            startChatCheckTask(player, manager);

        } else{
            Sounds.playGuiClickSound(player);
            new SpecificPlayerMenu(plugin, plugin.getCampManager().getPlayerCamp(player), uuidNick, player).openMenu(player);
        }
    }

    private void startChatCheckTask(Player player, PlayerInputsManager manager) {
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                if (manager.chatContains(player)) {
                    player.sendMessage(TextUtils.formatMessage(
                            "Czas na podanie nicku gracza minął...",
                            TextColor.color(0xFF7070)
                    ));
                    manager.removeFromChatMap(player);
                    cancel();
                }
            }
        }.runTaskLater(plugin, 20 * 30);

        manager.addToChatMap(player, task);
    }
}
