package com.xdd.serverPlugin.Utils;

import org.bukkit.entity.Player;

public class Sounds {

    private static final String guiClickSoundID = "ui_click";
    private static final String guiCloseSoundID = "window_close";
    private static final String permOnSoundID = "ui_permission_on";
    private static final String permOffSoundID = "ui_permission_off";
    private static final String cash_register = "cash_register";
    private static final String grabCoins = "grab_coins";
    private static final String error_sound = "error_sound";
    private static final String claim_item = "claim_item";


    public static void playGuiClickSound(Player player){
        player.playSound(player, guiClickSoundID, 1.25f,1f);
    }
    public static void playGuiCloseSound(Player player){
        player.playSound(player, guiCloseSoundID, 0.72f,1f);
    }
    public static void playPermOnSound(Player player){
        player.playSound(player, permOnSoundID, 1f,1f);
    }
    public static void playPermOffSound(Player player){
        player.playSound(player, permOffSoundID, 0.88f,1f);
    }
    public static void playCashRegisterSound(Player player){
        player.playSound(player, cash_register, 0.7f,1f);
    }
    public static void playGrabCoinsSound(Player player){
        player.playSound(player, grabCoins, 1f,1f);
    }
    public static void playErrorSound(Player player){
        player.playSound(player, error_sound, 0.65f,1f);
    }
    public static void playItemClaimSound(Player player){
        player.playSound(player, claim_item, 0.35f, 1f);
    }
}
