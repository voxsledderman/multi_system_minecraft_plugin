package com.xdd.serverPlugin.a_listeners.camp;

import com.destroystokyo.paper.event.entity.EntityPathfindEvent;
import com.xdd.serverPlugin.ConstantValues;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.cuboids.camp.CampManager;
import com.xdd.serverPlugin.locations.ServerWorlds;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collection;
import java.util.List;


public class MobsDoNotCrossBorderListener implements Listener {
    private final ServerPlugin plugin = ServerPlugin.getInstance();
    private final CampManager campManager = plugin.getCampManager();

    @EventHandler
    void onPathFind(EntityPathfindEvent e) {
        if (!(e.getEntity() instanceof Mob mob)) return;
        World world = mob.getWorld();
        if (!world.equals(ServerWorlds.CAMP_WORLD.getWorld())) return;
        if (!mob.getPersistentDataContainer().has(ConstantValues.keyForMobCampID)) return;

        Integer campID = mob.getPersistentDataContainer().get(
                ConstantValues.keyForMobCampID, PersistentDataType.INTEGER
        );
        if (campID == null) return;

        Camp camp = campManager.getCampByID(campID);
        if (camp == null) return;

        WorldBorder border = camp.getCurrentBorder();
        if (mob instanceof Animals animal) {
            Location target = e.getLoc();

            if (border.isInside(animal.getLocation()) && !border.isInside(target)) {
                animal.getPathfinder().moveTo(camp.getCenterLocation(), animal.getSeed() * 1.2);
                e.setCancelled(true);
            }
        }
        else if(mob instanceof Monster monster){
            if(!border.isInside(monster.getLocation()) && border.isInside(e.getLoc())){
                e.setCancelled(true);
                monster.setTarget(null);
            }
        }
    }

    @EventHandler
    void onSpawn(CreatureSpawnEvent e){
        Entity entity = e.getEntity();
        if(entity instanceof Player) return;
        if (!entity.getWorld().equals(ServerWorlds.CAMP_WORLD.getWorld())) return;
        Collection<Camp> allCamps = campManager.getCampsWherePlayerCanTeleport().values()
                .stream()
                .flatMap(List::stream)
                .toList();
        Camp camp = tryToFindCamp(allCamps, entity.getLocation());
        if (camp == null) {
            camp = tryToFindCamp(campManager.getPlayerCampList().values(), entity.getLocation());
        }
        if (camp != null) {
            entity.getPersistentDataContainer().set(
                    ConstantValues.keyForMobCampID,
                    PersistentDataType.INTEGER,
                    camp.getCampID()
            );
        }
    }

    private Camp tryToFindCamp(Collection<Camp> camps, Location location){
        for(Camp camp : camps){
            if(camp.contains(location)) return camp;
        }
        return null;
    }
}
