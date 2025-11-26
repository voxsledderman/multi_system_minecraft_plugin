package com.xdd.serverPlugin.basicListeners;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.Utils.TextUtils;
import com.xdd.serverPlugin.cache.PlayerInputsManager;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.cuboids.camp.CampManager;
import com.xdd.serverPlugin.cuboids.camp.settings.guis.SpecificPlayerMenu;
import com.xdd.serverPlugin.database.data.PlayerData;
import com.xdd.serverPlugin.records.UuidNick;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Optional;

public class PlayerChatListener implements Listener {

    private final ServerPlugin plugin = ServerPlugin.getInstance();
    private final PlayerInputsManager playerInputsManager = plugin.getPlayerInputsManager();
    private final CampManager campManager = plugin.getCampManager();

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        Player player = e.getPlayer();
        String msg = PlainTextComponentSerializer.plainText().serialize(e.message());

        Bukkit.getScheduler().runTask(plugin, runnable -> {
            if (handleChatInput(player, msg)) {
                e.setCancelled(true);
            }
        });


    }

    private boolean handleChatInput(Player player, String msg) {
        if (!playerInputsManager.chatContains(player)) return false;

        Player toAddPlayer = Bukkit.getPlayerExact(msg);
        if (toAddPlayer == null) {
            player.sendMessage(TextUtils.formatMessage(
                    "Gracz [%s] nie istnieje lub jest offline!".formatted(msg),
                    TextColor.color(0xFF7070)
            ));
            removeCache(player);
            return true;
        }

        Camp camp = campManager.getPlayerCamp(player);
        if (camp == null) {
            player.sendMessage(TextUtils.formatMessage(
                    "Nie należysz do żadnego obozu!",
                    TextColor.color(0xFF7070)
            ));
            removeCache(player);
            return true;
        }

        UuidNick uuidNick = UuidNick.of(toAddPlayer);
        camp.getPermissionsPerPlayer().putIfAbsent(uuidNick, new ArrayList<>());

        campManager.getCampsWherePlayerCanTeleport()
                .computeIfAbsent(uuidNick.uuid(), k -> new ArrayList<>())
                .add(camp);

        SpecificPlayerMenu specificPlayerMenu = new SpecificPlayerMenu(plugin, camp, uuidNick, player);
        specificPlayerMenu.openMenu(player);

        PlayerData playerData = plugin.getCacheManager().getPlayerData(toAddPlayer);
        if(playerData == null){
            toAddPlayer.sendMessage(TextUtils.formatMessage("Nie udało się załadować twoich danych", TextColor.color(0xFF7070)));
            return false;
        }
        playerData.getCampsID().add(camp.getCampID());
        toAddPlayer.sendMessage(MiniMessage.miniMessage().deserialize("<green>Zostałeś dodany do obozu gracza %s".formatted(camp.getOwnerName())));

        return true;
    }

    private void removeCache(Player player) {
        Optional.ofNullable(playerInputsManager.getChatTask(player))
                .filter(task -> !task.isCancelled())
                .ifPresent(BukkitTask::cancel);

        playerInputsManager.removeFromChatMap(player);
    }
}
