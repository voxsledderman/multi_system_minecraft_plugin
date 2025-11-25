package com.xdd.serverPlugin.service;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.cuboids.camp.CampManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;

import java.sql.SQLException;

public class CampAdminService {

    private static final CampManager campManager = ServerPlugin.getInstance().getCampManager();

    public static Camp findCamp(int id, CommandSender sender) throws SQLException {
        Camp camp = campManager.getCampByID(id);
        if(camp == null){
            sender.sendMessage(Component.text("Nie znaleziono wyspy o id %d, wpisano poprawnie komende?)"
                    .formatted(id)).color(TextColor.color(0xFF7070)));
        }
        return camp;
    }
    public static Camp findCamp(String ownerNick, CommandSender sender) throws SQLException {
        Camp camp = campManager.getCampByNick(ownerNick);
        if(camp == null) {
            sender.sendMessage(Component.text("Nie znaleziono obozu gracza o nicku - " + ownerNick
            ).color(TextColor.color(0xFF7070)));
        }
        return camp;
    }
}
