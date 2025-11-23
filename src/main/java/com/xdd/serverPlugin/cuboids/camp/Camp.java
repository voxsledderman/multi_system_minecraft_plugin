package com.xdd.serverPlugin.cuboids.camp;

import com.xdd.serverPlugin.ConstantValues;
import com.xdd.serverPlugin.Utils.BorderUtils;
import com.xdd.serverPlugin.cuboids.Cuboid;
import com.xdd.serverPlugin.cuboids.SimpleCuboid;
import com.xdd.serverPlugin.locations.ServerLocations;
import com.xdd.serverPlugin.locations.ServerWorlds;
import com.xdd.serverPlugin.npc.NPC;
import com.xdd.serverPlugin.npc.campNpc.MayorNPC;
import com.xdd.serverPlugin.permissions.CampPerms;
import com.xdd.serverPlugin.records.UuidNick;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import io.lumine.mythic.core.mobs.DespawnMode;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
public class Camp extends Cuboid {

    private final int campID;
    private final UUID ownerUUID;
    @Setter private int campLevel;
    @Setter private WorldBorder currentBorder;
    private final String ownerName;
    @Setter private List<String> permissions = new ArrayList<>();
    @Setter private Map<UuidNick, List<String>> permissionsPerPlayer = new HashMap<>();
    private final Location spawnLocation;
    private final List<NPC> npcs = new ArrayList<>();
    @Setter  private boolean hasTriggeredSpawn = false;


     public Camp(Location upperCorner, Location bottomCorner,
                 int campID, UUID ownerUUID, String ownerName , int campLevel, List<String> permissions) {


        super(upperCorner, bottomCorner);
        this.campID = campID;
        this.ownerUUID = ownerUUID;
        this.campLevel = campLevel;
        this.ownerName = ownerName;
        recalculateWorldBorder();
        this.permissions = permissions;
        spawnLocation = getCenterLocation().clone().subtract(0, (double) (ConstantValues.CENTER_CAMP_Y - ConstantValues.CAMP_DOWN_HEIGHT) / 2, 0);

    }
    
    public void recalculateWorldBorder(){
         WorldBorder worldBorder = Bukkit.createWorldBorder();
         worldBorder.setCenter(getCenterLocation());
         int index = campLevel - 1;

         worldBorder.setSize(ConstantValues.borderSizeByLevel[index]);
         if(worldBorder.getSize() == 0){
             worldBorder.setSize(15);
         }
         worldBorder.setDamageAmount(4);
         worldBorder.setWarningDistance(0);

         setCurrentBorder(worldBorder);
    }

    public boolean isAnyPlayerWithPermissionOnCamp() {
        for (Player member : getPlayersInCuboid()) {
            if (member.getUniqueId().equals(ownerUUID)) return true;

            UuidNick uuidNick = new UuidNick(member.getUniqueId(), member.getName());
            var perms = getPermissionsPerPlayer().get(uuidNick);
            if (perms != null && perms.contains(CampPerms.SpecialPerms.ALLOW_PLAYING_ALONE)) {
                return true;
            }
        }
        return false;
    }

    public void changeLevel(int newLevel){
         BorderUtils.clearBarrierBlocks(this);
         campLevel = newLevel;
         BorderUtils.putBarrierBlocks(this);
         recalculateWorldBorder();
         Player player = Bukkit.getPlayer(ownerUUID);
         if(player != null){
            player.setWorldBorder(currentBorder);
        }
         //TODO: Zapis do bazy danych, zmiana elementow wizualnych
    }
    public void deleteCamp(CampManager campManager){
         campManager.unregisterPlayerCamp(ownerUUID);
         for(Player player : getPlayersInCuboid()){
             player.teleport(ServerLocations.SPAWN.getLocation());
         }
         unloadCuboidChunks();

         //TODO: Usuwanie rekordu z bazy danych

    }

    void spawnNPCs(){
        var loc = this.getSpawnLocation().clone().add(0,0,6);
        loc.setYaw(180);
        MythicMob npc = MythicBukkit.inst().getMobManager().getMythicMob("NPC_Mayor").orElse(null);
        if(npc != null){
            ActiveMob activeNpc = npc.spawn(BukkitAdapter.adapt(loc), 1);
            activeNpc.setDespawnMode(DespawnMode.PERSISTENT);
            System.out.println(activeNpc);
            npcs.add(new MayorNPC(BukkitAdapter.adapt(activeNpc.getEntity())));
        }
    }

    public SimpleCuboid getSimpleCuboidFromCurrentLevel() {
        int index = campLevel - 1;
        var sizes = ConstantValues.borderSizeByLevel;

        double radius = (double) sizes[index] / 2;

        Location center = getCenterBlockLocation().clone();

        Location bottomCorner = new Location(
                ServerWorlds.CAMP_WORLD.getWorld(),
                center.x() - radius,
                ConstantValues.CENTER_CAMP_Y - ConstantValues.CAMP_DOWN_HEIGHT,
                center.z() - radius
        );

        Location upperCorner = new Location(
                ServerWorlds.CAMP_WORLD.getWorld(),
                center.x() + radius,
                ConstantValues.CENTER_CAMP_Y + ConstantValues.CAMP_HEIGHT,
                center.z() + radius
        );

        return new SimpleCuboid(upperCorner, bottomCorner);
    }
}
