package com.xdd.serverPlugin.a_listeners.camp;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.Utils.BlocksUtils;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.cuboids.camp.CampManager;
import com.xdd.serverPlugin.locations.ServerLocations;
import com.xdd.serverPlugin.locations.ServerWorlds;
import com.xdd.serverPlugin.permissions.CampPerms;
import com.xdd.serverPlugin.permissions.Perms;
import com.xdd.serverPlugin.records.UuidNick;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.*;

public class GlobalCampListeners implements Listener {
    private final ServerPlugin plugin = ServerPlugin.getInstance();
    private final CampManager campManager = plugin.getCampManager();



    @EventHandler
    void onEntityDamage(EntityDamageByEntityEvent e){
        entityDamageByEntityHandler(e);
    }
    @EventHandler
    void onItemPickup(PlayerAttemptPickupItemEvent e){
        itemEventsHandler(e);
    }

    @EventHandler
    void onItemThrow(PlayerDropItemEvent e){
        itemEventsHandler(e);
    }
    @EventHandler
    void onDoorOpen(PlayerInteractEvent e){
        blockInteractHandler(e, BlocksUtils.BlockSets.DOORS_SET, CampPerms.GlobalPerms.SETTINGS_OPEN_DOOR_PERM, "korzystanie z drzwi");
    }
    @EventHandler
    void onRedstoneInteract(PlayerInteractEvent e){
        blockInteractHandler(e, BlocksUtils.BlockSets.REDSTONE_BLOCKS_SET , CampPerms.GlobalPerms.REDSTONE_BLOCKS_INTERACTION, "korzystanie z bloków redstone");
    }

    private Camp findCampThatPlayerIsOnSync(Player player) {
        Location loc = player.getLocation();
        UUID uuid = player.getUniqueId();

        Map<UUID, Camp> cache = campManager.getLastlyCheckedCamp();
        Camp last = cache.get(uuid);
        if (last != null && last.contains(loc)) {
            return last;
        }
        Set<Camp> visited = new HashSet<>();
        Camp found = null;
        for (Camp camp : campManager.getPlayerCampList().values()) {
            if (visited.add(camp) && camp.contains(loc)) {
                found = camp;
                break;
            }
        }
        if (found == null) {
            for (Camp camp : campManager.getPlayerCampsAvailableForTp(player)) {
                if (visited.add(camp) && camp.contains(loc)) {
                    found = camp;
                    break;
                }
            }
        }
        if (found == null) {
            for (Camp camp : campManager.getCampsWithPlayerWarp()) {
                if (visited.add(camp) && camp.contains(loc)) {
                    found = camp;
                    break;
                }
            }
        }

        if (found != null) {
            cache.put(uuid, found);
        } else {
            cache.remove(uuid);
        }

        return found;
    }

    private void blockInteractHandler(PlayerInteractEvent e, Set<Material> allowedMaterials, String perm, String msgPart){
        Player player = e.getPlayer();
        if(!player.getWorld().equals(ServerWorlds.CAMP_WORLD.getWorld())) return;
        if(e.getClickedBlock() == null) return;
        if(player.hasPermission(Perms.ALLOW_ALL_MC_ACTIONS_ON_CAMPS)) return;

        Camp camp = findCampThatPlayerIsOnSync(player);
        if(camp == null){
            player.teleport(ServerLocations.SPAWN.getLocation());
            player.setWorldBorder(null);
            e.setCancelled(true);
            player.sendMessage(Component.text("Obóz w którym byłeś został zamknięty, przeteleportowano na spawn!").color(TextColor.color(0xFF7070)));
            return;
        }

        if(player.getUniqueId().equals(camp.getOwnerUUID())) return;
        if(camp.getPermissionsPerPlayer().containsKey(UuidNick.of(player))) return;
        var perms = camp.getPermissions();
        if(perms.contains(perm) && allowedMaterials.contains(e.getClickedBlock().getType())) return;

        e.setCancelled(true);
        player.sendMessage(Component.text("W obozie gracza [%s] %s jest zabronione!".formatted(camp.getOwnerName(), msgPart))
                .color(TextColor.color(0xFF7070)));
    }

    private void entityDamageByEntityHandler(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Player player)) return;
        if(player.hasPermission(Perms.ALLOW_ALL_MC_ACTIONS_ON_CAMPS)) return;
        if(e.getEntity() instanceof Player && player.getWorld().equals(ServerWorlds.CAMP_WORLD.getWorld())) {
            e.setCancelled(true);
            player.sendMessage(Component.text("PVP na terenie obozów jest zabronione!").color(TextColor.color(0xFF7070)));
            return;
        }
        if(!player.getWorld().equals(ServerWorlds.CAMP_WORLD.getWorld())) return;
        if(e.getEntity() instanceof Player) return;

        Camp camp = findCampThatPlayerIsOnSync(player);
        if(camp == null){
            player.teleport(ServerLocations.SPAWN.getLocation());
            player.setWorldBorder(null);
            e.setCancelled(true);
            player.sendMessage(Component.text("Obóz w którym byłeś został zamknięty, przeteleportowano na spawn!").color(TextColor.color(0xFF7070)));
            return;
        }

        if(player.getUniqueId().equals(camp.getOwnerUUID())) return;
        if(camp.getPermissionsPerPlayer().containsKey(UuidNick.of(player))) return;

        var perms = camp.getPermissions();
        boolean isAnimal = e.getEntity() instanceof Animals;
        if(!isAnimal && perms.contains(CampPerms.GlobalPerms.KILL_MONSTERS)) return;
        if(isAnimal && perms.contains(CampPerms.GlobalPerms.KILL_MOBS)) return;

        e.setCancelled(true);
        player.sendMessage(Component.text("W obozie gracza [%s] zabijanie %s jest wyłączone!".formatted(camp.getOwnerName(), isAnimal ? "zwierząt" : "potworów"))
                .color(TextColor.color(0xFF7070)));
    }

    private void itemEventsHandler(PlayerEvent e){
        if (!(e instanceof PlayerDropItemEvent || e instanceof PlayerAttemptPickupItemEvent)) {
            throw new IllegalArgumentException("[GlobalCampListeners] podano zły event do itemEventsHandler");
        }

        boolean isDropItem = e instanceof PlayerDropItemEvent;
        Cancellable cancellableEvent = (Cancellable) e;
        Player player = e.getPlayer();
        if(!player.getWorld().equals(ServerWorlds.CAMP_WORLD.getWorld())) return;
        if(player.hasPermission(Perms.ALLOW_ALL_MC_ACTIONS_ON_CAMPS)) return;

        Camp camp = findCampThatPlayerIsOnSync(player);
        if(camp == null){
            player.teleport(ServerLocations.SPAWN.getLocation());
            player.setWorldBorder(null);
            cancellableEvent.setCancelled(true);
            player.sendMessage(Component.text("Obóz w którym byłeś został zamknięty, przeteleportowano na spawn!").color(TextColor.color(0xFF7070)));
            return;
        }

        if(player.getUniqueId().equals(camp.getOwnerUUID())) return;
        if(camp.getPermissionsPerPlayer().containsKey(UuidNick.of(player))) return;

        var perms = camp.getPermissions();
        if(!isDropItem && perms.contains(CampPerms.GlobalPerms.PICKUP_ITEMS)) return;

        cancellableEvent.setCancelled(true);
        player.sendMessage(Component.text(
                "W obozie gracza [%s] %s itemów jest wyłączone!"
                        .formatted(camp.getOwnerName(), isDropItem ? "wyrzucanie" : "podnoszenie")
        ).color(TextColor.color(0xFF7070)));
    }
}
