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
    public static final String[] zombieKeys = {"zc_walker","zc_runner", "zc_scavenger"};

    public static final NamespacedKey keyForMobCampID = new NamespacedKey(ServerPlugin.getInstance(),"camp_id");

}
