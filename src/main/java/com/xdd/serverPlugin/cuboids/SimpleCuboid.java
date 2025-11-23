package com.xdd.serverPlugin.cuboids;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.BoundingBox;

public class SimpleCuboid extends Cuboid{

    public SimpleCuboid(Location upperCorner, Location bottomCorner) {
        super(upperCorner, bottomCorner);
    }

    public BoundingBox toBoundingBox() {
        return new BoundingBox(
                getBottomCorner().getX(), getBottomCorner().getY(), getBottomCorner().getZ(),
                getUpperCorner().getX(), getUpperCorner().getY(), getUpperCorner().getZ()
        );
    }
    public static Cuboid fromBoundingBox(BoundingBox box, World world) {
        Location min = new Location(world, box.getMinX(), box.getMinY(), box.getMinZ());
        Location max = new Location(world, box.getMaxX(), box.getMaxY(), box.getMaxZ());
        return new Cuboid(max, min) {};

    }
}
