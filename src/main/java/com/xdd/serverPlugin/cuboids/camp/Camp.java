package com.xdd.serverPlugin.cuboids.camp;

import com.xdd.serverPlugin.ConstantValues;
import com.xdd.serverPlugin.ServerPlugin;
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
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.*;

@Getter
public class Camp extends Cuboid {

    private final int campID;
    private final UUID ownerUUID;
    @Setter private CampLevels campLevel;
    @Setter private transient WorldBorder currentBorder;
    private final String ownerName;
    @Setter private List<String> permissions = new ArrayList<>();
    @Setter private Map<UuidNick, List<String>> permissionsPerPlayer = new HashMap<>();
    private final Location spawnLocation;
    private final List<NPC> npcs = new ArrayList<>();
    @Setter private transient boolean hasTriggeredSpawn = false;


     public Camp(Location upperCorner, Location bottomCorner,
                 int campID, UUID ownerUUID, String ownerName , int campLevel, List<String> permissions, Map<UuidNick, List<String>> permissionsPerPlayer) {

        super(upperCorner, bottomCorner);
        this.campID = campID;
        this.ownerUUID = ownerUUID;
         var level = CampLevels.getEnumFromInteger(campLevel);
        if(level == null) {
            ServerPlugin.getInstance().getLogger().warning("[Camp] podczas wywoływania konstruktora napotkano błędy poziom obozu %d".formatted(campLevel));
            level = CampLevels.LEVEL1;
        }
        this.campLevel = level;
        this.ownerName = ownerName;
        recalculateWorldBorder();
        this.permissions = permissions;
        spawnLocation = getCenterLocation().clone().subtract(0, (double) (ConstantValues.CENTER_CAMP_Y - ConstantValues.CAMP_DOWN_HEIGHT) / 2, 0);
        this.permissionsPerPlayer = permissionsPerPlayer;
    }
    
    public void recalculateWorldBorder(){
         WorldBorder worldBorder = Bukkit.createWorldBorder();
         worldBorder.setCenter(getCenterLocation());

         worldBorder.setSize(campLevel.getBorderSize());
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

    public void changeLevel(int newLevel) throws SQLException {
         BorderUtils.clearBarrierBlocks(this);
         campLevel = CampLevels.getEnumFromInteger(newLevel);
         if(campLevel == null) campLevel = CampLevels.LEVEL1;
         BorderUtils.putBarrierBlocks(this);
         recalculateWorldBorder();
         Player player = Bukkit.getPlayer(ownerUUID);
         if(player != null){
            player.setWorldBorder(currentBorder);
        }
         ServerPlugin.getInstance().getCampDao().save(this);
         //TODO: zmiana elementow wizualnych
    }
    public void deleteCamp(@NotNull CampManager campManager) throws SQLException {
         campManager.unregisterPlayerCamp(ownerUUID);
         for(Player player : getPlayersInCuboid()){
             player.teleport(ServerLocations.SPAWN.getLocation());
         }
         unloadCuboidChunks();
         ServerPlugin.getInstance().getCampDao().deletePermanently(this);
    }

    public void spawnNPCs(){
        var loc = this.getSpawnLocation().clone().add(0,0,6);
        loc.setYaw(180);
        MythicMob npc = MythicBukkit.inst().getMobManager().getMythicMob(MayorNPC.npcKey).orElse(null);
        if(npc != null && !isNpcSpawned(MayorNPC.npcKey)){
            ActiveMob activeNpc = npc.spawn(BukkitAdapter.adapt(loc), 1);
            activeNpc.setDespawnMode(DespawnMode.CHUNK);
            System.out.println(activeNpc);
            npcs.add(new MayorNPC(BukkitAdapter.adapt(activeNpc.getEntity())));
        }
    }
    private boolean isNpcSpawned(String key){
        for (ActiveMob activeMob : MythicBukkit.inst().getMobManager().getActiveMobs()) {
            Location location = BukkitAdapter.adapt(activeMob.getLocation());
            if(!location.getWorld().equals(ServerWorlds.CAMP_WORLD.getWorld()) || !this.contains(location)) continue;
            String currentMobName = activeMob.getType().getInternalName();
            if (currentMobName.equalsIgnoreCase(key)) {
                return true;
            }
        }
        return false;
    }

    public SimpleCuboid getSimpleCuboidFromCurrentLevel() {

        double radius = (double) campLevel.getBorderSize() / 2;

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
