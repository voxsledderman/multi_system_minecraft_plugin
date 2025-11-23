package com.xdd.serverPlugin.a_listeners.camp;

import com.destroystokyo.paper.event.entity.EntityPathfindEvent;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.Utils.BlocksUtils;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.cuboids.camp.CampManager;
import com.xdd.serverPlugin.locations.ServerWorlds;
import com.xdd.serverPlugin.permissions.CampPerms;
import com.xdd.serverPlugin.permissions.Perms;
import com.xdd.serverPlugin.records.UuidNick;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SpecificCampListeners implements Listener {
    private final CampManager campManager = ServerPlugin.getInstance().getCampManager();

    @EventHandler
    void onBlockPlace(BlockPlaceEvent e){
        blockEventHandlers(e, e.getPlayer(), true);
    }
    @EventHandler
    void onBreak(BlockBreakEvent e){
        blockEventHandlers(e, e.getPlayer(), false);
    }

    @EventHandler
    void onContainerInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (!BlocksUtils.BlockSets.CHESTS_SET.contains(e.getClickedBlock().getType())) return;

        Player player = e.getPlayer();
        if (player.hasPermission(Perms.ALLOW_ALL_MC_ACTIONS_ON_CAMPS)) return;
        if (!player.getWorld().equals(ServerWorlds.CAMP_WORLD.getWorld())) return;

        Set<Camp> campSet = new HashSet<>(campManager.getPlayerCampsAvailableForTp(player));
        Camp playerCamp = campManager.getPlayerCamp(player);
        if (playerCamp != null) campSet.add(playerCamp);

        Camp camp = null;
        for (Camp temp : campSet) {
            if (temp.contains(player.getLocation())) {
                camp = temp;
                break;
            }
        }
        if (camp == null) return;

        UUID uuid = player.getUniqueId();
        UuidNick uuidNick = UuidNick.of(player);

        boolean canOpen = camp.getOwnerUUID().equals(uuid)
                || (camp.getPermissionsPerPlayer().containsKey(uuidNick)
                && camp.getPermissionsPerPlayer().get(uuidNick).contains(CampPerms.SpecialPerms.OPEN_CHESTS));

        if (!canOpen) {
            e.setCancelled(true);
            player.sendMessage(Component.text("Nie możesz otwierać skrzyń w obozie gracza [" + camp.getOwnerName() + "]")
                    .color(TextColor.color(0xFF7070)));
        }
    }




    private void blockEventHandlers(Cancellable e, Player player, boolean isPlace){
        if(player.hasPermission(Perms.ALLOW_ALL_MC_ACTIONS_ON_CAMPS)) return;
        if(!player.getWorld().equals(ServerWorlds.CAMP_WORLD.getWorld())) return;

        Set<Camp> campSet = new HashSet<>(campManager.getPlayerCampsAvailableForTp(player));
        Camp playerCamp = campManager.getPlayerCamp(player);
        if(playerCamp != null) campSet.add(playerCamp);

        Camp camp = null;
        for(Camp temp : campSet){
            if(temp.contains(player.getLocation())){
                camp = temp;
                break;
            }
        }

        if(camp == null) return;
        boolean canBuild = false;
        UUID uuid = player.getUniqueId();
        UuidNick uuidNick = UuidNick.of(player);

        if(camp.getOwnerUUID().equals(uuid)){
            canBuild = true;
        }
        else if(camp.getPermissionsPerPlayer().containsKey(uuidNick)
                && camp.getPermissionsPerPlayer().get(uuidNick).contains(CampPerms.SpecialPerms.BUILD_DESTROY_BLOCKS)){
            canBuild = true;
        }
        e.setCancelled(!canBuild);
        if(!canBuild){
            player.sendMessage(Component.text("Nie możesz %s bloków w obozie gracza [%s]".formatted(isPlace ? "stawiać" : "niszczyć", camp.getOwnerName()))
                    .color(TextColor.color(0xFF7070)));
        }
    }
}
