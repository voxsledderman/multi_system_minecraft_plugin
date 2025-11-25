package com.xdd.serverPlugin.cache;

import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class PeriodicDataSaverRunnable extends BukkitRunnable {
    private final ServerPlugin plugin = ServerPlugin.getInstance();
    @Override
    public void run() {
        for(Camp camp : plugin.getCampManager().getPlayerCampList().values()){
            try {
                plugin.getCampDao().save(camp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        //TODO: Po implementacji playerData odkomentuj
        for(Player player : Bukkit.getOnlinePlayers()){
//            try {
//                plugin.getPlayerDao().save(player);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
        }
    }
}
