package com.xdd.serverPlugin.Utils;

import org.bukkit.Material;

import java.util.EnumSet;

public class BlocksUtils {

    public static class BlockSets{

        public final static EnumSet<Material> DOORS_SET = EnumSet.of(
                Material.OAK_DOOR, Material.SPRUCE_DOOR, Material.BIRCH_DOOR, Material.JUNGLE_DOOR, Material.ACACIA_DOOR,
                Material.DARK_OAK_DOOR, Material.MANGROVE_DOOR, Material.CHERRY_DOOR, Material.PALE_OAK_DOOR, Material.BAMBOO_DOOR,
                Material.CRIMSON_DOOR, Material.WARPED_DOOR, Material.IRON_DOOR, Material.COPPER_DOOR, Material.EXPOSED_COPPER_DOOR,
                Material.WEATHERED_COPPER_DOOR, Material.OXIDIZED_COPPER_DOOR, Material.WAXED_COPPER_DOOR, Material.WAXED_EXPOSED_COPPER_DOOR,
                Material.WAXED_WEATHERED_COPPER_DOOR, Material.WAXED_OXIDIZED_COPPER_DOOR
        );
        public final static EnumSet<Material> REDSTONE_BLOCKS_SET = EnumSet.of(
                Material.LEVER,
                Material.STONE_BUTTON,
                Material.OAK_BUTTON,
                Material.SPRUCE_BUTTON,
                Material.BIRCH_BUTTON,
                Material.JUNGLE_BUTTON,
                Material.ACACIA_BUTTON,
                Material.DARK_OAK_BUTTON,
                Material.MANGROVE_BUTTON,
                Material.CHERRY_BUTTON,
                Material.PALE_OAK_BUTTON,
                Material.CRIMSON_BUTTON,
                Material.WARPED_BUTTON,
                Material.POLISHED_BLACKSTONE_BUTTON,

                Material.STONE_PRESSURE_PLATE,
                Material.OAK_PRESSURE_PLATE,
                Material.SPRUCE_PRESSURE_PLATE,
                Material.BIRCH_PRESSURE_PLATE,
                Material.JUNGLE_PRESSURE_PLATE,
                Material.ACACIA_PRESSURE_PLATE,
                Material.DARK_OAK_PRESSURE_PLATE,
                Material.MANGROVE_PRESSURE_PLATE,
                Material.CHERRY_PRESSURE_PLATE,
                Material.PALE_OAK_PRESSURE_PLATE,
                Material.CRIMSON_PRESSURE_PLATE,
                Material.WARPED_PRESSURE_PLATE,
                Material.POLISHED_BLACKSTONE_PRESSURE_PLATE,
                Material.LIGHT_WEIGHTED_PRESSURE_PLATE,
                Material.HEAVY_WEIGHTED_PRESSURE_PLATE
        );
        public static final EnumSet<Material> CHESTS_SET = EnumSet.of(
                Material.CHEST,
                Material.TRAPPED_CHEST,
                Material.BARREL,
                Material.SHULKER_BOX,
                Material.WHITE_SHULKER_BOX,
                Material.ORANGE_SHULKER_BOX,
                Material.MAGENTA_SHULKER_BOX,
                Material.LIGHT_BLUE_SHULKER_BOX,
                Material.YELLOW_SHULKER_BOX,
                Material.LIME_SHULKER_BOX,
                Material.PINK_SHULKER_BOX,
                Material.GRAY_SHULKER_BOX,
                Material.LIGHT_GRAY_SHULKER_BOX,
                Material.CYAN_SHULKER_BOX,
                Material.PURPLE_SHULKER_BOX,
                Material.BLUE_SHULKER_BOX,
                Material.BROWN_SHULKER_BOX,
                Material.GREEN_SHULKER_BOX,
                Material.RED_SHULKER_BOX,
                Material.BLACK_SHULKER_BOX,
                Material.FURNACE,
                Material.BLAST_FURNACE,
                Material.SMOKER
        );
    }
}
