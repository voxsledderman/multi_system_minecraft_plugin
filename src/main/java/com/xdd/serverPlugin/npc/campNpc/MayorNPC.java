package com.xdd.serverPlugin.npc.campNpc;

import com.xdd.serverPlugin.Utils.TextUtils;
import com.xdd.serverPlugin.cuboids.camp.settings.guis.MainCampMenu;
import com.xdd.serverPlugin.npc.MythicNPC;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class MayorNPC extends MythicNPC {
    public static String npcKey = "NPC_Mayor";

    public MayorNPC(Entity entity) {
        super(entity, Sound.BLOCK_WOODEN_BUTTON_CLICK_ON);
    }

    @Override
    public Runnable getInteractAction(Player player) {
        return () -> {
            var campManager = getPlugin().getCampManager();
            var camp = campManager.getPlayerCamp(player);
            if(camp == null){
                player.sendMessage(TextUtils.formatMessage("Nie jesteś członkiem żadnego obozu!", TextColor.color(TextColor.color(0xFF7070))));
                return;
            }
            var menu = new MainCampMenu(getPlugin(), campManager.getPlayerCamp(player), player);
            menu.openMenu(player);
            menu.playOpenSound();
        };
    }

    @Override
    public String getKey() {
        return npcKey;
    }
}
