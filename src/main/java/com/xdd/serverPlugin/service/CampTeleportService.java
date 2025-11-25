package com.xdd.serverPlugin.service;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.cuboids.camp.CampManager;
import com.xdd.serverPlugin.tittle.subclasses.CountdownTitle;
import org.bukkit.entity.Player;

import java.sql.SQLException;


public class CampTeleportService {
    private final CampManager campManager;


    public CampTeleportService(CampManager campManager) {
        this.campManager = campManager;
    }

    public void teleportPlayerToCamp(Player player) throws SQLException {
        Camp camp = campManager.getPlayerCamp(player);
        if (camp == null) camp = campManager.createNewCamp(player);

        CountdownTitle.doCampCountdown(player, camp);
    }
}
