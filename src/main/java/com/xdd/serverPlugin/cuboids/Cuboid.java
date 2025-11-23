package com.xdd.serverPlugin.cuboids;

import com.xdd.serverPlugin.ServerPlugin;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public abstract class Cuboid {

    private Location upperCorner;
    private Location bottomCorner;

    public Cuboid(Location upperCorner, Location bottomCorner){
        if(!upperCorner.getWorld().equals(bottomCorner.getWorld())){
            throw new IllegalArgumentException("Podane lokalizacje znajdują się w różnych światach!");
        }
        double maxY = Math.max(bottomCorner.getY(), upperCorner.getY());
        double minY = Math.min(bottomCorner.getY(), upperCorner.getY());
        double minX = Math.min(bottomCorner.getX(), upperCorner.getX());
        double maxX = Math.max(bottomCorner.getX(), upperCorner.getX());
        double minZ = Math.min(bottomCorner.getZ(), upperCorner.getZ());
        double maxZ = Math.max(bottomCorner.getZ(), upperCorner.getZ());

        this.upperCorner = new Location(upperCorner.getWorld(), maxX, maxY, maxZ);
        this.bottomCorner = new Location(bottomCorner.getWorld(), minX, minY, minZ);
    }

    public boolean contains(Location location){
        if(!upperCorner.getWorld().equals(location.getWorld())) return false;

        boolean isInX = (location.x() >= bottomCorner.x()) && (location.x() <= upperCorner.x());
        boolean isInY = (location.y() >= bottomCorner.y()) && (location.y() <= upperCorner.y());
        boolean isInZ = (location.z() >= bottomCorner.z()) && (location.z() <= upperCorner.z());

        return isInX && isInY && isInZ;
    }
    public List<Player> getPlayersInCuboid(){
        List<Player> players = new ArrayList<>();
        for(Player player : Bukkit.getOnlinePlayers()){
            if(contains(player.getLocation())){
                players.add(player);
            }
        }
        return players;
    }

    public void unloadCuboidChunks() {
        World world = upperCorner.getWorld();

        int minChunkX = bottomCorner.getBlockX() / 16;
        int maxChunkX = upperCorner.getBlockX() / 16;
        int minChunkZ = bottomCorner.getBlockZ() / 16;
        int maxChunkZ = upperCorner.getBlockZ() / 16;

        List<Chunk> chunksToUnload = new ArrayList<>();
        for (int cx = minChunkX; cx <= maxChunkX; cx++) {
            for (int cz = minChunkZ; cz <= maxChunkZ; cz++) {
                chunksToUnload.add(world.getChunkAt(cx, cz));
            }
        }

        new BukkitRunnable() {
            int index = 0;
            final int batchSize = 3;

            @Override
            public void run() {
                if (index >= chunksToUnload.size()) {
                    cancel();
                    return;
                }

                int unloaded = 0;
                while (unloaded < batchSize && index < chunksToUnload.size()) {
                    Chunk chunk = chunksToUnload.get(index);
                    index++;

                    boolean hasPlayer = chunk.getWorld().getPlayers().stream()
                            .anyMatch(p -> p.getLocation().getChunk().equals(chunk));

                    if (!hasPlayer && !chunk.isForceLoaded()) {
                        chunk.unload(true);
                    }
                    unloaded++;
                }
            }
        }.runTaskTimer(ServerPlugin.getInstance(), 0L, 5L);
    }

    public List<Location> getBorderBlockLocations() {
        List<Location> borderBlocks = new ArrayList<>();
        World world = upperCorner.getWorld();

        int minX = bottomCorner.getBlockX();
        int maxX = upperCorner.getBlockX();
        int minY = bottomCorner.getBlockY();
        int maxY = upperCorner.getBlockY();
        int minZ = bottomCorner.getBlockZ();
        int maxZ = upperCorner.getBlockZ();

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                borderBlocks.add(new Location(world, x, y, minZ));
                borderBlocks.add(new Location(world, x, y, maxZ));
            }
            for (int z = minZ + 1; z <= maxZ - 1; z++) {
                borderBlocks.add(new Location(world, minX, y, z));
                borderBlocks.add(new Location(world, maxX, y, z));
            }
        }
        return borderBlocks;
    }

    public void changeY(int yMin, int yMax) {
        this.bottomCorner = new Location(
                bottomCorner.getWorld(),
                bottomCorner.getX(),
                yMin,
                bottomCorner.getZ()
        );
        this.upperCorner = new Location(
                upperCorner.getWorld(),
                upperCorner.getX(),
                yMax,
                upperCorner.getZ()
        );
    }

    public double getLengthX(){
        return upperCorner.x() - bottomCorner.x();
    }
    public double getLengthZ(){
        return upperCorner.z() - bottomCorner.z();
    }

    public boolean containsCuboid(Cuboid cuboid) {
        if (!this.upperCorner.getWorld().equals(cuboid.getUpperCorner().getWorld())) return false;

        return this.bottomCorner.getX() <= cuboid.getBottomCorner().getX() &&
                this.upperCorner.getX() >= cuboid.getUpperCorner().getX() &&
                this.bottomCorner.getY() <= cuboid.getBottomCorner().getY() &&
                this.upperCorner.getY() >= cuboid.getUpperCorner().getY() &&
                this.bottomCorner.getZ() <= cuboid.getBottomCorner().getZ() &&
                this.upperCorner.getZ() >= cuboid.getUpperCorner().getZ();
    }

    public Location getRandomLocationXZ(double yCord){
        final Random random = new Random();

        double minX = getBottomCorner().getX();
        double maxX = getUpperCorner().getX();
        double minZ = getBottomCorner().getZ();
        double maxZ = getUpperCorner().getZ();

        double newX = minX + random.nextDouble() * (maxX - minX);
        double newZ = minZ + random.nextDouble() * (maxZ - minZ);

        return new Location(getCenterLocation().getWorld(), newX, yCord, newZ);

    }

    public ArrayList<Location> getRandomLocationsWithinCuboidXZ(int amount, double yCord) {
        final Random random = new Random();
        ArrayList<Location> locations = new ArrayList<>();
        int attempts = 0;
        int maxAttempts = amount * 10;

        double minX = getBottomCorner().getX();
        double maxX = getUpperCorner().getX();
        double minZ = getBottomCorner().getZ();
        double maxZ = getUpperCorner().getZ();

        while (locations.size() < amount && attempts < maxAttempts) {
            double newX = minX + random.nextDouble() * (maxX - minX);
            double newZ = minZ + random.nextDouble() * (maxZ - minZ);

            Location loc = new Location(getUpperCorner().getWorld(), newX, yCord, newZ);
            locations.add(loc);
            attempts++;
        }

        return locations;
    }




    public Location getCenterLocation(){
        return new Location(upperCorner.getWorld(), getAverage(upperCorner.x(), bottomCorner.x()),
                getAverage(upperCorner.y(), bottomCorner.y()), getAverage(upperCorner.z(), bottomCorner.z()));
    }
    public Location getCenterBlockLocation(){
        return new Location(upperCorner.getWorld(), getAverage(upperCorner.getBlockX(), bottomCorner.getBlockX()),
                getAverage(upperCorner.getBlockY(), bottomCorner.getBlockY()), getAverage(upperCorner.getBlockZ(), bottomCorner.getBlockZ()));
    }
    private double getAverage(double x1, double x2){
        return (x1 + x2) / 2;
    }
}
