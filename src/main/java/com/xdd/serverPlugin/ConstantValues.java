package com.xdd.serverPlugin;

import lombok.Getter;
import org.bukkit.NamespacedKey;

@Getter
public class ConstantValues {

    public static final int CAMP_SIZE = 200;
    public static final int GAP_BETWEEN_CAMPS = 200;
    public static final int CENTER_CAMP_Y = 64;

    public static final int CAMP_HEIGHT = 64;
    public static final int CAMP_DOWN_HEIGHT = 40;
    public static final String[] zombieKeys = {"zc_walker","zc_runner", "zc_scavenger", "zc_spitter"};

    public static final NamespacedKey keyForMobCampID = new NamespacedKey(ServerPlugin.getInstance(),"camp_id");

    private static final int LVL1_SIZE = 15;
    private static final int LVL2_SIZE = 30;
    private static final int LVL3_SIZE = 50;
    private static final int LVL4_SIZE = 75;
    private static final int LVL5_SIZE = 100;
    private static final int LVL6_SIZE = 130;
    private static final int LVL7_SIZE = 160;
    private static final int LVL8_SIZE = 200;
    private static final int LVL9_SIZE = 225;
    private static final int LVL10_SIZE = 250;


    public static final int[] borderSizeByLevel = {LVL1_SIZE, LVL2_SIZE, LVL3_SIZE, LVL4_SIZE, LVL5_SIZE, LVL6_SIZE, LVL7_SIZE, LVL8_SIZE, LVL9_SIZE, LVL10_SIZE};

}
