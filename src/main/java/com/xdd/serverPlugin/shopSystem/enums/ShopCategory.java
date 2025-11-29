package com.xdd.serverPlugin.shopSystem.enums;

import lombok.Getter;

@Getter
public enum ShopCategory {

    BUILDING_BLOCKS("<shift:-8><glyph:shop_1>"),
    COLORED_BLOCKS("<shift:-8><glyph:shop_2>"),
    DECORATIONS("<shift:-8><glyph:shop_3>"),
    MINERALS("<shift:-8><glyph:shop_4>"),
    TOOLS_ARMORS("<shift:-8><glyph:shop_5>"),
    FARMING("<shift:-8><glyph:shop_6>"),
    FOOD("<shift:-8><glyph:shop_7>"),
    OTHER("<shift:-8><glyph:shop_8>"),
    REDSTONE_BLOCKS("<shift:-8><glyph:shop2_1>"),
    ENCHANTED_BOOKS("<shift:-8><glyph:shop2_2>"),
    SPAWNERS("<shift:-8><glyph:shop2_3>"),
    SPAWN_EGGS("<shift:-8><glyph:shop2_4>"),
    MOB_DROPS("<shift:-8><glyph:shop2_5>");

    private transient final String menuKey;

    ShopCategory(String menuKey) {
        this.menuKey = menuKey;
    }
}
